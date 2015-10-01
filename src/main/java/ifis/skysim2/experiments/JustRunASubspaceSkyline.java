package ifis.skysim2.experiments;

import ifis.skysim2.algorithms.*;
import ifis.skysim2.algorithms.subspaceAware.SkylineAlgorithmParallelBNLLinkedListLazySync_SubspaceAware;
import ifis.skysim2.algorithms.subspaceAware.SubspaceHelper;
import ifis.skysim2.data.generator.*;
import ifis.skysim2.data.sources.PointSource;
import ifis.skysim2.data.sources.PointSourceDisk;
import ifis.skysim2.data.sources.PointSourceRAM;
import ifis.skysim2.simulator.DataSource;
import ifis.skysim2.simulator.SimulatorConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JustRunASubspaceSkyline {

    /**
     * Creates a simple config setting loading the cars file with ID's, and
     * using a subspace-aware Skyline algorithm
     *
     * @return
     */
    public static SimulatorConfiguration getSimpleConfig() {
        SimulatorConfiguration config = new SimulatorConfiguration();
        // well, this is weird. Due to legacy reasons, loading a CSV file is indeed 
        // a memory source which loads from a file.... don't think about this, thats 
        // just the way it is... 
        // The Datasource.FILE will read a serialized binary file, thats something else.... 

        config.setDataSource(DataSource.MEMORY);

        //  use this for synthetic data
        /**
         * config.setUseDefaultGeneratorSeed(true); config.setD(4);
         * config.setN(8); config.setNumLevels(3); DataGenerator gen = new
         * DataGeneratorIndependent();
         */
        DataGeneratorFromCSVFile gen = new DataGeneratorFromCSVFile("./data/cars_ID.csv");
        config.setD(gen.getD());
        config.setN(gen.getN());

        config.setDataGenerator(gen);

        //      ** choose an algorithm
        config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLLinkedListLazySync_SubspaceAware());
       // config.setSkylineAlgorithm(new SkylineAlgorithmKdTrie(false));
        // config.setSkylineAlgorithm(new SkylineAlgorithmBNL());
        //config.setSkylineAlgorithm(new SkylineAlgorithmPskyline());
        config.getSkylineAlgorithm().setExperimentConfig(config);
        // its indeed a multi-cpu algorithm....
        config.setNumberOfCPUs(4);
        config.setDistributedNumBlocks(100);
        config.setStaticArrayWindowSize(200000);
        config.setDeleteDuringCleaning(true);
        return config;
    }

    /**
     * This prepares the data sets, e.g. generating them or loading them from a
     * file.
     *
     * @param config
     * @return
     */
    public static PointSource getData(SimulatorConfiguration config) {
        if (config == null) {
            throw new IllegalArgumentException("No configuration set.");
        }

        DataGenerator dg = config.getDataGenerator();
        if (config.isUseDefaultGeneratorSeed()) {
            dg.resetToDefaultSeed();
        }

        int d = config.getD();
        int n = config.getN();
        int[] numLevels = config.getNumLevels();
        DataSource ds = config.getDataSource();
        File df = config.getDataFile();
        int bytesPerRecord = config.getBytesPerRecord();

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

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        tStop = System.nanoTime();

        PointSource pointSource;
        if (ds == DataSource.MEMORY) {
            pointSource = new PointSourceRAM(d, data);
        } else {
            pointSource = new PointSourceDisk(df, bytesPerRecord, n, d);
        }

        // Clean memory (for measuring memory consumption)
        return pointSource;
    }

    /**
     * Simply run a skyline algorithm from a csv file.
     *
     * @param args
     */
    public static void main(String[] args) {
        SimulatorConfiguration config = getSimpleConfig();
        PointSource data = getData(config);
        SkylineAlgorithm alg = config.getSkylineAlgorithm();
        alg.setExperimentConfig(config);
        // subspaces are simply represented as arrays
        // this next code will get all subspaces, and then remove the dimension 0 which contains the record ID (and which is therefore very bad for skyline computaion)
        int[] subspaces = SubspaceHelper.getFullSubspace(config);
        subspaces = SubspaceHelper.removeDimension(subspaces, 0);
        config.setSubspaceIndexes(subspaces);
        // run
        // PointSource2CSV.writeToCSVFile(data, new File("./data/memoryDump.csv"));
        List<float[]> skyline = alg.compute(data);
        System.out.println("Skyline Algorithm: " + config.getSkylineAlgorithm().toString());
        System.out.println("Skyline size: " + skyline.size());

        //
    }

  

}
