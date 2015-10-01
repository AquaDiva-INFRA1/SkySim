package ifis.skysim2.experiments;

import ifis.skysim2.algorithms.*;
import ifis.skysim2.algorithms.SkylineAlgorithmBNL.BNLWindowPolicy;
import ifis.skysim2.algorithms.pDistributed.*;
import ifis.skysim2.algorithms.pQueueSync.*;
import ifis.skysim2.algorithms.parallelbnl.*;
import ifis.skysim2.data.generator.*;
import ifis.skysim2.experiments.*;
import ifis.skysim2.junk.SkylineAlgorithmParallelScanner;
import ifis.skysim2.simulator.DataSource;
import ifis.skysim2.simulator.Simulator;
import ifis.skysim2.simulator.SimulatorConfiguration;
import ifis.skysim2.simulator.SimulatorConfiguration.PresortStrategy;
import java.io.File;

public class SimpleExperimentFromFile {

    public static void main(String[] args) {
        SimulatorConfiguration config = new SimulatorConfiguration();

        config.setN(5000);

        config.setDataSource(DataSource.MEMORY);

    
        config.setUseDefaultGeneratorSeed(true);
        DataGeneratorFromCSVFile gen = new DataGeneratorFromCSVFile("data/cars_id.csv");
        config.setD(gen.getD());
        config.setN(gen.getN());
        config.setDataGenerator(gen);
//      ** this is how many simulation runs you do
        config.setNumTrials(25);

//      ** here, you configure which skyline algorithm to use. You can either presort, or you don't. And then there are several different algorithms.       
//	config.setPresortStrategy(PresortStrategy.FullPresortSum);
//	config.setPresortStrategy(PresortStrategy.FullPresortZAddress);
//	config.setPresortStrategy(PresortStrategy.FullPresortVolume);
//	config.setPresortStrategy(PresortStrategy.PartialPresort);
//	config.setPartialPresortThreshold(0.001);
//	config.setSkylineAlgorithm(new SkylineAlgorithmBNL());
//             config.setSkylineAlgorithm(new SkylineAlgorithmKdTrie(false));
//	config.setSkylineAlgorithm(new SkylineAlgorithmKdTriePartition(false));
//	config.setSkylineAlgorithm(new SkylineAlgorithmKdTrieZBulkload());
//	config.setSkylineAlgorithm(new SkylineAlgorithmDomFreeQuadTreePartitioning(false));
//	config.setSkylineAlgorithm(new SkylineAlgorithmDomFreeQuadTreePLRealDelete());
//	config.setSkylineAlgorithm(new SkylineAlgorithmDomFreeQuadTreePLLazyDelete(false));
//	config.setSkylineAlgorithm(new SkylineAlgorithmDominanceDecisionTree());
//	config.setSkylineAlgorithm(new SkylineAlgorithmDistributed());
//	config.setSkylineAlgorithm(new SkylineAlgorithmPQueueSync());
//	config.setSkylineAlgorithm(new SkylineAlgorithmPPackageSync());
//	config.setSkylineAlgorithm(new SkylineAlgorithmBBS());
//	config.setSkylineAlgorithm(new SkylineAlgorithmSskyline());
//	config.setSkylineAlgorithm(new SkylineAlgorithmPskyline());
//	config.setSkylineAlgorithm(new SkylineAlgorithmZSearch());
//      config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLStaticArray());
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLLinkedListFineGrainedSync());
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLLinkedListLazySync());
	config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLLinkedListLockFreeSync());
//	config.setSkylineAlgorithm(new SkylineAlgorithmDistributedParallelBNL());
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelScanner());
//	config.setBnlWindowPolicy(BNLWindowPolicy.MoveToFront);
//	config.setBnlWindowPolicy(BNLWindowPolicy.KeepSorted);
//	config.setBnlWindowPolicy(BNLWindowPolicy.BubbleUp);
//	config.setBnlWindowPolicy(BNLWindowPolicy.BubbleUpSimple);
//      config.setNodeCapacities(50, 100);
//      ** This is for distributed algorithms like PQueSync or PPackageSync        
        config.setNumberOfCPUs(2);
        config.setDistributedNumBlocks(100);
        config.setStaticArrayWindowSize(200000);
        config.setDeleteDuringCleaning(false);
//        config.setDeleteDuringCleaning(true);

        Simulator sim = new Simulator();
        sim.run(config);
    }
}
