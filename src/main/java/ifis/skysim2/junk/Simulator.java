package ifis.skysim2.junk;

import ifis.skysim2.simulator.*;
import ifis.skysim2.data.tools.DataPresorter;
import ifis.skysim2.algorithms.SkylineAlgorithm;
import ifis.skysim2.data.generator.DataGenerator;
import ifis.skysim2.data.sources.PointSource;
import ifis.skysim2.data.sources.PointSourceDisk;
import ifis.skysim2.data.sources.PointSourceRAM;

import ifis.skysim2.simulator.SimulatorConfiguration.PresortStrategy;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class Simulator {

    private static int LOGGING_TIME_THRESHOLD = 0;

    public AlgorithmSummaryStatistics run(SimulatorConfiguration config) {
        if (config == null) {
            throw new IllegalArgumentException("No configuration set.");
        }
        long cpuCostTotal = 0;
        long ioCostTotal = 0;
        long sizeTotal = 0;
        long timeSortTotal = 0;
        long timeComputeTotal = 0;
        long timePreprocessTotal = 0;
        long timeReorgTotal = 0;
        long timeGenerateTotal = 0;
	long memoryConsumptionTotal = 0;
        double avgTimePerCompTotalNS = 0;

        DataGenerator dg = config.getDataGenerator();
        if (config.isUseDefaultGeneratorSeed()) {
            dg.resetToDefaultSeed();
        }
        SkylineAlgorithm alg = null;
        alg = config.getSkylineAlgorithm();
        alg.setExperimentConfig(config);
        System.out.format("%n    Algorithm: %s%n", alg);
	System.out.format("DataGenerator: %s %n%n", dg.getShortName());
        System.out.println(AlgorithmSummaryStatistics.HEADER);
        System.out.println(AlgorithmSummaryStatistics.DIVIDER);

        int numTrials = config.getNumTrials();
        int d = config.getD();
        int n = config.getN();
        int[] numLevels = config.getNumLevels();
        DataSource ds = config.getDataSource();
        File df = config.getDataFile();
        int bytesPerRecord = config.getBytesPerRecord();
        PresortStrategy presortStrategy = config.getPresortStrategy();

        // Start with the calibration trial,
        // then with all "real" trials
        for (int i = -1; i < numTrials; i++) {
            long tStart, tStop;
            float[] data = null;
            tStart = System.nanoTime();
            if (ds == DataSource.MEMORY) {
                data = dg.generate(d, n, numLevels);
            } else {
                try {
                    if (ds == DataSource.FILE) {
                        dg.generate(d, n, numLevels, df, bytesPerRecord);
                    }
                    // clear file system cache
                    System.out.print("clearing cache ... ");
                    String[] commands = {"/bin/bash"};
                    ProcessBuilder pb = new ProcessBuilder(commands);
                    Process proc = pb.start();
                    Writer outCommand = new OutputStreamWriter(proc.getOutputStream());
                    outCommand.write("sudo sh -c 'echo 3 >/proc/sys/vm/drop_caches'; exit\n");
                    outCommand.flush();
                    proc.waitFor();
                    outCommand.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            tStop = System.nanoTime();
            long timeGenerate = tStop - tStart;
            timeGenerateTotal += timeGenerate;

            PointSource pointSource;
            if (ds == DataSource.MEMORY) {
                pointSource = new PointSourceRAM(d, data);
            } else {
                pointSource = new PointSourceDisk(df, bytesPerRecord, n, d);
            }
            tStart = System.nanoTime();
            switch (presortStrategy) {
                case FullPresortVolume:
                    pointSource = DataPresorter.sortByVolume(pointSource);
                    break;
		case FullPresortSum:
                    pointSource = DataPresorter.sortBySum(pointSource);
                    break;
                case PartialPresort:
                    pointSource = DataPresorter.sortBestToFront(pointSource, config.getPartialPresortThreshold());
                    break;
                case NoPresort:
                default:
                    break;
            }
            tStop = System.nanoTime();
            long timeSort = tStop - tStart;

	    // Clean memory (for measuring memory consumption)
	    Runtime.getRuntime().gc();
	    long memoryConsumptionBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            List<float[]> window = alg.compute(pointSource);
	    long memoryConsumption = alg.getMemoryConsumptionInBytes() - memoryConsumptionBefore;
            long timeCompute = alg.getTotalTimeNS();
            long timePreprocess = alg.getPreprocessTimeNS();
            long timeReorg = alg.getReorgTimeNS();
            double avgTimePerCompNS = (double) timeCompute / alg.getCPUcost();
            if (i > -1) {
                timeComputeTotal += timeCompute;
                timeReorgTotal += timeReorg;
                timePreprocessTotal += timePreprocess;
                timeSortTotal += timeSort;
                cpuCostTotal += alg.getCPUcost();
                ioCostTotal += alg.getIOcost();
                sizeTotal += window.size();
                avgTimePerCompTotalNS += avgTimePerCompNS;
		memoryConsumptionTotal += memoryConsumption;
            }
            double timeGenerateMS = timeGenerate / 1000000.0;
            double timeComputeMS = timeCompute / 1000000.0;
            double timeSortMS = timeSort / 1000000.0;
            double timePreprocessMS = timePreprocess / 1000000.0;
            double timeReorgMS = timeReorg / 1000000.0;
            double compPerTuple = (double) alg.getCPUcost() / n;
	    double memoryMB = memoryConsumption * 1e-6;
            if (timeComputeTotal >= LOGGING_TIME_THRESHOLD) {
                String trialString;
                if (i == -1) {
                    trialString = "CALIBRATION";
                } else {
                    trialString = String.format("%12d / %3d", i + 1, numTrials);
                }
                System.out.format(AlgorithmSummaryStatistics.ENTRY + "%n",
                        trialString,
                        n,
                        d,
                        window.size(),
                        timeGenerateMS,
                        timeSortMS,
                        timePreprocessMS,
                        timeComputeMS,
                        timeReorgMS,
                        alg.getCPUcost(),
                        compPerTuple,
                        avgTimePerCompNS,
                        alg.getIOcost(),
                        config.getDataGenerator().getShortName());
            }
            // Calibration trial
            if (i == -1) {
                System.out.println(AlgorithmSummaryStatistics.DIVIDER);
            }
        }
        System.out.println(AlgorithmSummaryStatistics.DIVIDER);
        AlgorithmSummaryStatistics stats = new AlgorithmSummaryStatistics();
        stats.setBaseConfig(config);
        stats.setAlgorithmName(alg.getShortName());
        stats.setSizeOfData(n);
        stats.setSizeTotal((double) sizeTotal / numTrials);
        stats.setTimeGenerateTotal((double) timeGenerateTotal / 1000000 / numTrials);
        stats.setTimeComputeTotal((double) timeComputeTotal / 1000000 / numTrials);
        stats.setTimePreprocessTotal((double) timePreprocessTotal / 1000000 / numTrials);
        stats.setTimeReorgTotal((double) timeReorgTotal / 1000000 / numTrials);
        stats.setCpuCostTotal(cpuCostTotal / numTrials);
        stats.setCpuPerTuple((double) cpuCostTotal / numTrials / n);
        stats.setAvgTimePerCompTotalNS(avgTimePerCompTotalNS / numTrials);
        stats.setIoCostTotal((double) ioCostTotal / numTrials);
        stats.setTimeSortTotal((double) timeSortTotal / 1000000 / numTrials);
	stats.setMemoryConsumptionMB(memoryConsumptionTotal / numTrials);
        System.out.println(stats);
        System.out.println(AlgorithmSummaryStatistics.DIVIDER);
        System.out.println(AlgorithmSummaryStatistics.HEADER);
        return stats;
    }
}
