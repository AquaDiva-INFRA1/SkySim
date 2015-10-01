package ifis.skysim2.simulator;

import ifis.skysim2.algorithms.SkylineAlgorithm;
import ifis.skysim2.algorithms.SkylineAlgorithmBNL;
import ifis.skysim2.algorithms.subspaceAware.SubspaceHelper;
import ifis.skysim2.data.generator.DataGenerator;
import java.io.File;
import java.util.Arrays;

public class SimulatorConfiguration {

    // number of dimensions
    private int d = -1;

    // number of data points
    private int n = -1;

    // number of levels per attribute
    private int[] numLevels = null;
    private int numLevelsAll = -1;

    // data generator
    private DataGenerator dataGenerator = null;
    private boolean useDefaultGeneratorSeed = false;

    // data source
    private DataSource dataSource = null;

    // file-based data
    private File dataFile = null;
    private int bytesPerRecord = -1;
    private boolean generateDataFile = false;

    // presorting
    private PresortStrategy presortStrategy = PresortStrategy.NoPresort;
    private double partialPresortThreshold = Double.NaN;

    // trials
    private int numTrials = -1;

    // algorithm
    private SkylineAlgorithm skylineAlgorithm = null;

    // algorithm-specific stuff
    private SkylineAlgorithmBNL.BNLWindowPolicy bnlWindowPolicy = SkylineAlgorithmBNL.BNLWindowPolicy.SimpleAppend;

    // tree-based algorithms
    private int nodeCapacityMin = 50;
    private int nodeCapacityMax = 100;

    // parallel algorithms
    private int numberOfCPUs = 1;

    // distributed algorithms
    private int distributedNumBlocks = -1;

    // static array windows
    private int staticArrayWindowSize = -1;

    // distributedParBNL
    private boolean deleteDuringCleaning = true;

    // used for skyline algorithms which support subspaces
    private int[] subspaceIndexes = null;

    /**
     * This sets the overall number of dimensions used for either data
     * generators and also skyline algorithms
     *
     * @param d
     */
    public void setD(int d) {
        if (d <= 0) {
            throw new IllegalArgumentException();
        }
        this.d = d;
        updateNumLevels();
    }

    /**
     * This gets the overall number of dimensions used for either data
     * generators and also skyline algorithms
     *
     * @param d
     */
    public int getD() {
        return d;
    }

    /**
     * This sets the overall number of records used for either data generators
     * and also skyline algorithms
     *
     * @param d
     */
    public void setN(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
    }

    /**
     * This gets the overall number of records used for either data generators
     * and also skyline algorithms
     *
     * @param d
     */
    public int getN() {
        return n;
    }

    /**
     * When generating synthetic data, this sets for EACH attribute
     * (individually) the number of different values. (e.g., if 3 for attribute
     * 1, that attribute can have the values {0, 0.5, 1}
     *
     * @param numLevels
     */
    public void setNumLevels(int[] numLevels) {
        if ((numLevels != null) && (numLevels.length != d)) {
            throw new IllegalArgumentException();
        }
        this.numLevels = numLevels;
    }

    /**
     * When generating synthetic data, this sets for ALL attribute the number of
     * different values. (e.g., if 3 for attribute 1, that attribute can have
     * the values {0, 0.5, 1}
     *
     * @param numLevelsAll
     */
    public void setNumLevels(int numLevelsAll) {
        if (numLevelsAll <= 0) {
            throw new IllegalArgumentException();
        }
        this.numLevelsAll = numLevelsAll;
        updateNumLevels();
    }

    /**
     * See {@link #setNumLevels(int numLevelsAll)}
     */
    private void updateNumLevels() {
        if (numLevelsAll > 0) {
            this.numLevels = new int[d];
            Arrays.fill(this.numLevels, numLevelsAll);
        } else if ((numLevels != null) && (numLevels.length != d)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * See {@link #setNumLevels(int numLevelsAll)}
     */
    public int[] getNumLevels() {
        if (numLevels != null) {
            return Arrays.copyOf(numLevels, d);
        } else {
            return numLevels;
        }
    }

    /**
     * Sets the data generator to be used. can be one of the synthetic or a
     * file-based data generator
     *
     * @param dataGenerator
     */
    public void setDataGenerator(DataGenerator dataGenerator) {
        if (dataGenerator == null) {
            throw new IllegalArgumentException();
        }
        this.dataGenerator = dataGenerator;
    }

    /**
     * Gets the data generator to be used. can be one of the synthetic or a
     * file-based data generator
     *
     * @param dataGenerator
     */
    public DataGenerator getDataGenerator() {
        return dataGenerator;
    }

    /**
     * Is a default generator seed used? if yes, each time you start the program
     * the exact same "random" number will be generated by the synthetic data
     * generators.
     *
     * @param dataGenerator
     */
    public boolean isUseDefaultGeneratorSeed() {
        return useDefaultGeneratorSeed;
    }

    /**
     * Sets a static generator seed. This will force that each time you start
     * the program the exact same "random" number will be generated by the
     * synthetic data generators.
     *
     * @param dataGenerator
     */
    public void setUseDefaultGeneratorSeed(boolean useDefaultGeneratorSeed) {
        this.useDefaultGeneratorSeed = useDefaultGeneratorSeed;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        if (dataSource == null) {
            throw new IllegalArgumentException();
        }
        this.dataSource = dataSource;
    }

    /**
     * How many experimental runs are within an experiment? Obviously, makes
     * only sense with synthetic data....
     *
     * @return
     */
    public int getNumTrials() {
        return numTrials;
    }

    /**
     * How many experimental runs are within an experiment? Obviously, makes
     * only sense with synthetic data....
     *
     * @return
     */
    public void setNumTrials(int numTrials) {
        if (numTrials <= 0) {
            throw new IllegalArgumentException();
        }
        this.numTrials = numTrials;
    }

    /**
     * Which algorithm to use? Each algorithm will pick the config parameters
     * which are relevant for it.
     *
     * @return
     */
    public SkylineAlgorithm getSkylineAlgorithm() {
        return skylineAlgorithm;
    }

    /**
     * Which algorithm to use? Each algorithm will pick the config parameters
     * which are relevant for it.
     *
     * @return
     */
    public void setSkylineAlgorithm(SkylineAlgorithm skylineAlgorithm) {
        if (skylineAlgorithm == null) {
            throw new IllegalArgumentException();
        }
        this.skylineAlgorithm = skylineAlgorithm;
    }

    public File getDataFile() {
        return dataFile;
    }

    public void setDataFile(File dataFile) {
        if (dataFile == null) {
            throw new IllegalArgumentException();
        }
        this.dataFile = dataFile;
    }

    public int getBytesPerRecord() {
        return bytesPerRecord;
    }

    public void setBytesPerRecord(int bytesPerRecord) {
        if (bytesPerRecord <= 0) {
            throw new IllegalArgumentException();
        }
        this.bytesPerRecord = bytesPerRecord;
    }

    public boolean isGenerateDataFile() {
        return generateDataFile;
    }

    public void setGenerateDataFile(boolean generateDataFile) {
        this.generateDataFile = generateDataFile;
    }

    public double getPartialPresortThreshold() {
        return partialPresortThreshold;
    }

    public void setPartialPresortThreshold(double partialPresortThreshold) {
        if ((partialPresortThreshold < 0) || (partialPresortThreshold > 1)) {
            throw new IllegalArgumentException();
        }
        this.partialPresortThreshold = partialPresortThreshold;
    }

    public void setNodeCapacities(int nodeCapacityMin, int nodeCapacityMax) {
        if ((nodeCapacityMin <= 0) || (nodeCapacityMax < nodeCapacityMin)) {
            throw new IllegalArgumentException();
        }
        this.nodeCapacityMin = nodeCapacityMin;
        this.nodeCapacityMax = nodeCapacityMax;
    }

    public int getNodeCapacityMin() {
        return nodeCapacityMin;
    }

    public int getNodeCapacityMax() {
        return nodeCapacityMax;
    }

    /**
     * How many CPU's should be used? This only works for multi-threaded
     * algorithms. Currently, these are PSkyline, Distributed,
     * DistributedParalellBNL, and all ParallellBNLVariants.
     *
     * @return
     */
    public int getNumberOfCPUs() {
        return numberOfCPUs;
    }

    /**
     * How many CPU's should be used? This only works for multi-threaded
     * algorithms. Currently, these are PSkyline, Distributed,
     * DistributedParalellBNL, and all ParallellBNLVariants.
     *
     * @param numberOfCPUs
     */
    public void setNumberOfCPUs(int numberOfCPUs) {
        if (numberOfCPUs < 1) {
            throw new IllegalArgumentException();
        }
        this.numberOfCPUs = numberOfCPUs;
    }

    public SkylineAlgorithmBNL.BNLWindowPolicy getBnlWindowPolicy() {
        return bnlWindowPolicy;
    }

    public void setBnlWindowPolicy(SkylineAlgorithmBNL.BNLWindowPolicy bnlWindowPolicy) {
        this.bnlWindowPolicy = bnlWindowPolicy;
    }

    public PresortStrategy getPresortStrategy() {
        return presortStrategy;
    }

    public void setPresortStrategy(PresortStrategy presortStrategy) {
        this.presortStrategy = presortStrategy;
    }

    public int getDistributedNumBlocks() {
        return distributedNumBlocks;
    }

    public void setDistributedNumBlocks(int distributedNumBlocks) {
        this.distributedNumBlocks = distributedNumBlocks;
    }

    public int getStaticArrayWindowSize() {
        return staticArrayWindowSize;
    }

    public void setStaticArrayWindowSize(int staticArrayWindowSize) {
        this.staticArrayWindowSize = staticArrayWindowSize;
    }

    public boolean isDeleteDuringCleaning() {
        return deleteDuringCleaning;
    }

    public void setDeleteDuringCleaning(boolean deleteDuringCleaning) {
        this.deleteDuringCleaning = deleteDuringCleaning;
    }

    public static enum PresortStrategy {

        NoPresort,
        FullPresortVolume,
        FullPresortSum,
        FullPresortZAddress,
        PartialPresort
    }

    /**
     * Sets the the subspaces to be considered in skyline computation. values of
     * the array can be between 0 and d-1. Only very few skyline algorithm will
     * actually respect this configuration.
     *
     * @param indexes
     */
    public void setSubspaceIndexes(int[] indexes) {
        if (indexes.length > this.d) {
            throw new IllegalArgumentException("Maximum number of indexes exeeded.");
        }
        for (int i = 0; i < indexes.length; i++) {
            if (indexes[i] > this.d || indexes[i] < 0) {
                throw new IllegalArgumentException("Illegal subspace indexes set.");
            }
        }
        this.subspaceIndexes = indexes;
    }

    /**
     * Gets the the subspaces to be considered in skyline computation. values of
     * the array can be between 0 and d-1. Only very few skyline algorithm will
     * actually respect this configuration.
     *
     */
    public int[] getSubspaceIndexes() {
        if (this.subspaceIndexes != null) {
            return this.subspaceIndexes;
        } else {
            int[] result = SubspaceHelper.getFullSubspace(this);
            return result;
        }
    }

}
