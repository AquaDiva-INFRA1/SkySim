package ifis.skysim2.experiments.EDBT;

import ifis.skysim2.algorithms.*;
import ifis.skysim2.algorithms.parallelbnl.*;
import ifis.skysim2.algorithms.SkylineAlgorithmBNL.BNLWindowPolicy;
import ifis.skysim2.algorithms.pDistributed.*;
import ifis.skysim2.algorithms.pQueueSync.*;
import ifis.skysim2.algorithms.rdf_dasfaa.PointComparator_RDF;
import ifis.skysim2.algorithms.rdf_dasfaa.PointComparator_RDF_N1;
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

public class Experiment_Dasfaa2012_n1 {

    public static void main(String[] args) {
        SimulatorConfiguration config = new SimulatorConfiguration();

        config.setD(7);
        PointComparator_RDF comparator = new PointComparator_RDF_N1(0.15f);



        config.setDataSource(DataSource.MEMORY);

        config.setUseDefaultGeneratorSeed(true);

        config.setDataGenerator(new DataGeneratorIndependent());
//	config.setDataGenerator(new DataGeneratorBKS01Correlated());
//	config.setDataGenerator(new DataGeneratorUniformMarginals(new DataGeneratorBKS01Correlated()));
//	config.setDataGenerator(new DataGeneratorBKS01Anticorrelated());
//	config.setDataGenerator(new DataGeneratorUniformMarginals(new DataGeneratorBKS01Anticorrelated()));
//	config.setDataGenerator(new DataGeneratorCorrelatedUniform(0.53));
//	config.setDataGenerator(new DataGeneratorUnitAnnulusUnif(0.3));

        config.setNumTrials(10);


        //   config.setSkylineAlgorithm(new SkylineAlgorithm_RDF_SQUARE2(comparator));

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
        //
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
