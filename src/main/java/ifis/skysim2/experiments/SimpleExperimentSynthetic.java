package ifis.skysim2.experiments;


import ifis.skysim2.algorithms.subspaceAware.SkylineAlgorithmParallelBNLLinkedListLazySync_SubspaceAware;
import ifis.skysim2.simulator.DataSource;
import ifis.skysim2.simulator.SimulatorConfiguration;
import ifis.skysim2.data.generator.*;
import ifis.skysim2.simulator.Simulator;

public class SimpleExperimentSynthetic {

    public static void main(String[] args) {
        SimulatorConfiguration config = new SimulatorConfiguration();

	config.setD(10);
	config.setN(5000);

//	config.setNumLevels(8);
//	config.setNumLevels(100);
//	config.setNumLevels(1000);

	config.setDataSource(DataSource.MEMORY);
//	config.setDataSource(DataSource.FILE);
//	config.setDataFile(new File("/home/selke/Desktop/sky/data.db"));
//	config.setBytesPerRecord(100);
//	config.setGenerateDataFile(true);

	config.setUseDefaultGeneratorSeed(true);

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

	config.setNumTrials(10);

//	config.setPresortStrategy(PresortStrategy.FullPresortSum);
//	config.setPresortStrategy(PresortStrategy.FullPresortZAddress);
//	config.setPresortStrategy(PresortStrategy.FullPresortVolume);
//	config.setPresortStrategy(PresortStrategy.PartialPresort);
//	config.setPartialPresortThreshold(0.001);

//	config.setSkylineAlgorithm(new SkylineAlgorithmBNL());

//	config.setSkylineAlgorithm(new SkylineAlgorithmKdTrie(false));
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
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLStaticArray());
       	config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLLinkedListLazySync_SubspaceAware());
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLLinkedListFineGrainedSync());
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLLinkedListLazySync());
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLLinkedListLockFreeSync());
//	config.setSkylineAlgorithm(new SkylineAlgorithmDistributedParallelBNL());
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelScanner());

//	config.setBnlWindowPolicy(BNLWindowPolicy.MoveToFront);
//	config.setBnlWindowPolicy(BNLWindowPolicy.KeepSorted);
//	config.setBnlWindowPolicy(BNLWindowPolicy.BubbleUp);
//	config.setBnlWindowPolicy(BNLWindowPolicy.BubbleUpSimple);

	config.setNodeCapacities(50, 100);

	config.setNumberOfCPUs(4);
	config.setDistributedNumBlocks(100);

	config.setStaticArrayWindowSize(200000);

	// Relevant only for distributed skyline algorithms.
//	config.setDeleteDuringCleaning(false);
	config.setDeleteDuringCleaning(true);

	Simulator sim = new Simulator();
        sim.run(config);
    }
}
