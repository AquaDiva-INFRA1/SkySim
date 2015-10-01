package ifis.skysim2.experiments.EDBT;

import ifis.skysim2.algorithms.*;
import ifis.skysim2.algorithms.parallelbnl.*;
import ifis.skysim2.algorithms.SkylineAlgorithmBNL.BNLWindowPolicy;
import ifis.skysim2.algorithms.pDistributed.*;
import ifis.skysim2.algorithms.pQueueSync.*;
import ifis.skysim2.algorithms.rdf_dasfaa.PointComparator_RDF;
import ifis.skysim2.algorithms.rdf_dasfaa.PointComparator_RDF_NM;
import ifis.skysim2.algorithms.rdf_dasfaa.SkylineAlgorithm_RDF_SQUARE2;
import ifis.skysim2.algorithms.rdf_dasfaa.SkylineAlgorithm_RDF_SemiNaiv;
import ifis.skysim2.algorithms.rdf_dasfaa.SkylineAlgorithm_RDF_SemiNaiv_Flags;
import ifis.skysim2.algorithms.rdf_dasfaa.SkylineAlgorithm_RDF_SemiNaiv_Flags_V15;
import ifis.skysim2.simulator.DataSource;
import ifis.skysim2.simulator.SimulatorConfiguration;
import ifis.skysim2.data.generator.*;
import ifis.skysim2.junk.SkylineAlgorithmParallelScanner;
import ifis.skysim2.simulator.SimulatorConfiguration.PresortStrategy;
import ifis.skysim2.simulator.Simulator;

public class Experiment_Dasfaa2012_single {

    public static void main(String[] args) {
        SimulatorConfiguration config = new SimulatorConfiguration();

        config.setD(12);

        

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

//	config.setD(60);
//	config.setN(275465);
//	config.setDataGenerator(new DataGeneratorUniformMarginals(new DataGeneratorFromCSVFile("data/aerialtexture.csv")));
//	config.setDataGenerator(new DataGeneratorUnitMarginalsByLinearScaling(new DataGeneratorFromCSVFile("data/aerialtexture.csv")));

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
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLLinkedListFineGrainedSync());
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLLinkedListLazySync());
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelBNLLinkedListLockFreeSync());
//	config.setSkylineAlgorithm(new SkylineAlgorithmDistributedParallelBNL());
//	config.setSkylineAlgorithm(new SkylineAlgorithmParallelScanner());

//	config.setBnlWindowPolicy(BNLWindowPolicy.MoveToFront);
//	config.setBnlWindowPolicy(BNLWindowPolicy.KeepSorted);
//	config.setBnlWindowPolicy(BNLWindowPolicy.BubbleUp);
//	config.setBnlWindowPolicy(BNLWindowPolicy.BubbleUpSimple);

        //   config.setSkylineAlgorithm(new SkylineAlgorithm_RDF_SQUARE2(comparator));
        //     config.setSkylineAlgorithm(new SkylineAlgorithm_RDF_SemiNaiv(comparator));
        PointComparator_RDF comparator = new PointComparator_RDF_NM(0.15f);
        config.setSkylineAlgorithm(new SkylineAlgorithm_RDF_SemiNaiv_Flags_V15(comparator));
        //config.setSkylineAlgorithm(new SkylineAlgorithmKdTriePartition(false));

        config.setNodeCapacities(50, 100);

        config.setNumberOfCPUs(1);
        config.setDistributedNumBlocks(100);

        config.setStaticArrayWindowSize(200000);

        // Relevant only for distributed skyline algorithms.
        config.setDeleteDuringCleaning(false);
//	config.setDeleteDuringCleaning(true);

        Simulator sim = new Simulator();
        comparator.setDelta(0.15f);
        config.setN(10000);
        sim.run(config);
        config.setN(20000);
        sim.run(config);
        
        config.setN(30000);
        sim.run(config);
        config.setN(40000);
        sim.run(config);
        config.setN(50000);
        sim.run(config);
        config.setN(60000);
        sim.run(config);
        config.setN(70000);
        sim.run(config);
        config.setN(80000);
        sim.run(config);
        config.setN(90000);
        sim.run(config);
        config.setN(100000);
        sim.run(config);
    }
}
