package skysim2.algorithms.rdf_dasfaa;

import ifis.skysim2.algorithms.*;
import ifis.skysim2.algorithms.parallelbnl.*;
import ifis.skysim2.algorithms.SkylineAlgorithmBNL.BNLWindowPolicy;
import ifis.skysim2.algorithms.pDistributed.*;
import ifis.skysim2.algorithms.pQueueSync.*;
import ifis.skysim2.algorithms.rdf_dasfaa.PointComparator_RDF;
import ifis.skysim2.algorithms.rdf_dasfaa.PointComparator_RDF_NM;
import ifis.skysim2.algorithms.rdf_dasfaa.SkylineAlgorithm_RDF_SQUARE2;
import ifis.skysim2.simulator.DataSource;
import ifis.skysim2.simulator.SimulatorConfiguration;
import ifis.skysim2.data.generator.*;
import ifis.skysim2.junk.SkylineAlgorithmParallelScanner;
import ifis.skysim2.simulator.SimulatorConfiguration.PresortStrategy;
import ifis.skysim2.simulator.Simulator;

public class rdf_skyline_test {

    public static void main(String[] args) {
        SimulatorConfiguration config = new SimulatorConfiguration();

        config.setD(4);
        config.setN(6);
        PointComparator_RDF comparator = new PointComparator_RDF_NM(0.2f);


        config.setDataSource(DataSource.MEMORY);

        config.setUseDefaultGeneratorSeed(true);



	config.setDataGenerator(new DataGeneratorFromCSVFile("data/test_rdf.csv"));

//	config.setD(60);
//	config.setN(275465);
//	config.setDataGenerator(new DataGeneratorUniformMarginals(new DataGeneratorFromCSVFile("data/aerialtexture.csv")));
//	config.setDataGenerator(new DataGeneratorUnitMarginalsByLinearScaling(new DataGeneratorFromCSVFile("data/aerialtexture.csv")));

        config.setNumTrials(25);

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
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLLinkedListFineGrainedSync());
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLLinkedListLazySync());
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLLinkedListLockFreeSync());
//	config.setSkylineAlgorithm(new SkylineAlgorithmDistributedParallelBNL());
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelScanner());

//	config.setBnlWindowPolicy(BNLWindowPolicy.MoveToFront);
//	config.setBnlWindowPolicy(BNLWindowPolicy.KeepSorted);
//	config.setBnlWindowPolicy(BNLWindowPolicy.BubbleUp);
//	config.setBnlWindowPolicy(BNLWindowPolicy.BubbleUpSimple);
        
        config.setSkylineAlgorithm(new SkylineAlgorithm_RDF_SQUARE2(comparator));
//        config.setSkylineAlgorithm(new SkylineAlgorithm_RDF_SemiNaiv());
        // config.setSkylineAlgorithm(new SkylineAlgorithmBNL());

        config.setNodeCapacities(50, 100);

        config.setNumberOfCPUs(1);
        config.setDistributedNumBlocks(100);

        config.setStaticArrayWindowSize(200000);

        // Relevant only for distributed skyline algorithms.
        config.setDeleteDuringCleaning(false);
//	config.setDeleteDuringCleaning(true);

        Simulator sim = new Simulator();
        sim.run(config);
    }
}