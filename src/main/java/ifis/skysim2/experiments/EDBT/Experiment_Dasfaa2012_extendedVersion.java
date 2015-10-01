package ifis.skysim2.experiments.EDBT;

import ifis.skysim2.algorithms.*;
import ifis.skysim2.algorithms.parallelbnl.*;
import ifis.skysim2.algorithms.SkylineAlgorithmBNL.BNLWindowPolicy;
import ifis.skysim2.algorithms.pDistributed.*;
import ifis.skysim2.algorithms.pQueueSync.*;
import ifis.skysim2.algorithms.rdf_dasfaa.*;
import ifis.skysim2.simulator.DataSource;
import ifis.skysim2.simulator.SimulatorConfiguration;
import ifis.skysim2.data.generator.*;
import ifis.skysim2.junk.SkylineAlgorithmParallelScanner;
import ifis.skysim2.simulator.SimulatorConfiguration.PresortStrategy;
import ifis.skysim2.simulator.Simulator;

public class Experiment_Dasfaa2012_extendedVersion {

    public static void main(String[] args) {
        SimulatorConfiguration config = new SimulatorConfiguration();
        config.setD(10);
        
       PointComparator_RDF comparator = new PointComparator_RDF_NM_ExtendedDef4b(0.15f);
   //          PointComparator_RDF comparator = new PointComparator_RDF_NM(0.15f);



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
        config.setNumLevels(10);
        


        //   config.setSkylineAlgorithm(new SkylineAlgorithm_RDF_SQUARE2(comparator));

        config.setSkylineAlgorithm(new SkylineAlgorithm_RDF_SQUARE2(comparator));
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
        comparator.setDelta(0.0f);
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
        comparator.setDelta(0.05f);
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
        comparator.setDelta(0.10f);
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
        comparator.setDelta(0.2f);
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
        comparator.setDelta(0.25f);
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
        comparator.setDelta(0.3f);
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
    }
}
