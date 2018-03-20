package ifis.skysim2.experiments;

import ifis.skysim2.algorithms.*;
import ifis.skysim2.algorithms.pDistributed.SkylineAlgorithmDistributed;
import ifis.skysim2.algorithms.pQueueSync.SkylineAlgorithmPQueueSync;
import ifis.skysim2.algorithms.parallelbnl.SkylineAlgorithmParallelBNLLinkedListFineGrainedSync;
import ifis.skysim2.algorithms.parallelbnl.SkylineAlgorithmParallelBNLLinkedListLockFreeSync;
import ifis.skysim2.algorithms.subspaceAware.SkylineAlgorithmParallelBNLLinkedListLazySync_SubspaceAware;
import ifis.skysim2.data.generator.*;
import ifis.skysim2.simulator.DataSource;
import ifis.skysim2.simulator.Simulator;
import ifis.skysim2.simulator.SimulatorConfiguration;
import ifis.skysim2.simulator.reporting.Reporter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CompareSkylineExperimentSynthetic {

    public static void main(String[] args) {
        SimulatorConfiguration config = new SimulatorConfiguration();

        config.setD(12);
        config.setN(50000);

//      config.setNumLevels(10);
//	config.setNumLevels(100);
//	config.setNumLevels(1000);
        config.setDataSource(DataSource.MEMORY);
//	config.setDataSource(DataSource.FILE);
//	config.setDataFile(new File("/home/selke/Desktop/sky/data.db"));
//	config.setBytesPerRecord(100);
//	config.setGenerateDataFile(true);

        config.setUseDefaultGeneratorSeed(true);

        
       /*  DataGeneratorFromCSVFile gen = new DataGeneratorFromCSVFile("./data/cars.csv");
         config.setD(gen.getD());
         config.setN(gen.getN());
         config.setDataGenerator(gen); */
         
        config.setDataGenerator(new DataGeneratorIndependent());
//	config.setDataGenerator(new DataGeneratorBKS01Correlated());
//	config.setDataGenerator(new DataGeneratorUniformMarginals(new DataGeneratorBKS01Correlated()));
//	config.setDataGenerator(new DataGeneratorBKS01Anticorrelated());
//	config.setDataGenerator(new DataGeneratorUniformMarginals(new DataGeneratorBKS01Anticorrelated()));
//	config.setDataGenerator(new DataGeneratorCorrelatedUniform(0.53));
//	config.setDataGenerator(new DataGeneratorUnitAnnulusUnif(0.3));
//	config.setDataGenerator(new DataGeneratorUniformMarginals(new DataGeneratorUnitAnnulusUnif(0.3)));
//	config.setDataGenerator(new DataGeneratorUnitAnnulusSun(0.3));
//	config.setDataGenerator(new DataGeneratorUniformMarginals(new DataGeneratorUnitAnnulusSun(0.3)));
//	config.setDataGenerator(new DataGeneratorUnitMarginalsByLinearScaling(new DataGeneratorUnitAnnulusSun(0.3)));
//	config.setD(16);
//	config.setN(68040);
//	config.setDataGenerator(new DataGeneratorUniformMarginals(new DataGeneratorFromCSVFile("data/coreltexture.csv")));
        config.setNumTrials(5);

//	config.setPresortStrategy(PresortStrategy.FullPresortSum);
//	config.setPresortStrategy(PresortStrategy.FullPresortZAddress);
//	config.setPresortStrategy(PresortStrategy.FullPresortVolume);
//	config.setPresortStrategy(PresortStrategy.PartialPresort);
//	config.setPartialPresortThreshold(0.001);
        config.setNodeCapacities(50, 100);
        config.setUseDefaultGeneratorSeed(true);

        config.setNumberOfCPUs(4);
        config.setDistributedNumBlocks(100);

        config.setStaticArrayWindowSize(200000);

        // Relevant only for distributed skyline algorithms.
//	config.setDeleteDuringCleaning(false);
        config.setDeleteDuringCleaning(true);

        /**
         * Please note that some skyline algorithms won't work with non-unique
         * data. This includes all KdTrie variants, and all QuadTree Variants.
         * If you have non-unique data (i.e. real data), use either
         * ParalelBNLlazySync if you have CPU cores to spare or sskyline if you
         * don't.
         *
         */
        List<SkylineAlgorithm> algorithms = new LinkedList<>();
        algorithms.add(new SkylineAlgorithmKdTrie(false));
        algorithms.add(new SkylineAlgorithmParallelBNLLinkedListLazySync_SubspaceAware());
        algorithms.add(new SkylineAlgorithmParallelBNLLinkedListLockFreeSync());
        algorithms.add(new SkylineAlgorithmParallelBNLLinkedListFineGrainedSync());
        algorithms.add(new SkylineAlgorithmZSearch());
        algorithms.add(new SkylineAlgorithmBNL());
        algorithms.add(new SkylineAlgorithmSskyline());
        algorithms.add(new SkylineAlgorithmDistributed());
        algorithms.add(new SkylineAlgorithmKdTriePartition());
        algorithms.add(new SkylineAlgorithmPskyline());
        algorithms.add(new SkylineAlgorithmBBS());
        algorithms.add(new SkylineAlgorithmDomFreeQuadTreePLLazyDelete(false));

        Map<String, Reporter> reports = new TreeMap<>();

        Simulator sim = new Simulator();
        for (SkylineAlgorithm alg : algorithms) {
            alg.setExperimentConfig(config);
            config.setSkylineAlgorithm(alg);
            Reporter rep = sim.run(config);
            reports.put(alg.getShortName(), rep);
        }
        // printout results
        System.out.println("\n\n\n**** FINAL RESULTS ****\n");
        boolean header = false;
        for (Map.Entry<String, Reporter> entries : reports.entrySet()) {
            if (!header) {
                System.out.print(entries.getValue().getHeader());
                System.out.print(entries.getValue().getDivider());
                header = true;
            }
            System.out.print(entries.getValue().getAverages());
        }
    }
}
