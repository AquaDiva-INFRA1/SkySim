package ifis.skysim2.experiments.EDBT;

import ifis.skysim2.algorithms.SkylineAlgorithmBNL;
import ifis.skysim2.algorithms.SkylineAlgorithmDomFreeQuadTreePLLazyDelete;
import ifis.skysim2.algorithms.SkylineAlgorithmDomFreeQuadTreePLRealDelete;
import ifis.skysim2.algorithms.SkylineAlgorithmDomFreeQuadTreePartitioning;
import ifis.skysim2.algorithms.SkylineAlgorithmDominanceDecisionTree;
import ifis.skysim2.algorithms.SkylineAlgorithmKdTrie;
import ifis.skysim2.algorithms.SkylineAlgorithmKdTriePartition;
import ifis.skysim2.algorithms.SkylineAlgorithmKdTrieZBulkload;
import ifis.skysim2.algorithms.SkylineAlgorithmSskyline;
import ifis.skysim2.data.generator.DataGeneratorBKS01Anticorrelated;
import ifis.skysim2.data.generator.DataGeneratorBKS01Correlated;
import ifis.skysim2.data.generator.DataGeneratorFromCSVFile;
import ifis.skysim2.data.generator.DataGeneratorIndependent;
import ifis.skysim2.data.generator.DataGeneratorUniformMarginals;
import ifis.skysim2.simulator.DataSource;
import ifis.skysim2.simulator.Simulator;
import ifis.skysim2.simulator.SimulatorConfiguration;
import ifis.skysim2.simulator.SimulatorConfiguration.PresortStrategy;
import ifis.skysim2.simulator.reporting.Reporter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EDBT {
    private static Writer out;
    private static boolean writeToFile;

    public static void main(String[] args) {
        SimulatorConfiguration config = new SimulatorConfiguration();

	config.setN(100000);

	config.setDataSource(DataSource.MEMORY);

	config.setUseDefaultGeneratorSeed(true);

//	config.setDataGenerator(new DataGeneratorIndependent());
//	config.setDataGenerator(new DataGeneratorBKS01Correlated());
	config.setDataGenerator(new DataGeneratorBKS01Anticorrelated());

//	config.setN(275465);

	config.setNumTrials(25);

//	config.setSkylineAlgorithm(new SkylineAlgorithmBNL());
//	config.setSkylineAlgorithm(new SkylineAlgorithmSskyline());
//	config.setSkylineAlgorithm(new SkylineAlgorithmKdTrie(false));
//	config.setSkylineAlgorithm(new SkylineAlgorithmKdTriePartition(false));
//	config.setSkylineAlgorithm(new SkylineAlgorithmKdTrieZBulkload());
//	config.setSkylineAlgorithm(new SkylineAlgorithmDomFreeQuadTreePartitioning());
	config.setSkylineAlgorithm(new SkylineAlgorithmDomFreeQuadTreePLRealDelete());
//	config.setSkylineAlgorithm(new SkylineAlgorithmDomFreeQuadTreePLLazyDelete(false));
//	config.setSkylineAlgorithm(new SkylineAlgorithmDominanceDecisionTree());

	config.setPresortStrategy(PresortStrategy.FullPresortSum);

	Simulator sim = new Simulator();

	int from = 2;
	int to = 50;

	int[] dims = new int[to - from + 1];
	for (int i = from; i <= to; i++) {
	    dims[i - from] = i;
	}

	initOutput(true);

	output(Simulator.getInfo(config) + "\n", Simulator.getGnuplotInfo(config));
	output(String.format("%n%n"), String.format("#%n#%n"));
	int trial = 1;
	for (int d : dims) {
	    config.setD(d);
	    Reporter r = sim.run(config, false);
	    if (trial == 1) {
		output(r.getHeader(), r.getGnuplotHeader());
		output(r.getDivider(), String.format("#%n"));
	    }
	    output(r.getAverages(), r.getGnuplotAverages());
	    if (trial == dims.length) {
		output(r.getDivider(), String.format("#%n"));
		output(r.getHeader(), r.getGnuplotHeader());
	    }
	    trial++;
	}

	closeOutput();
    }

    private static void initOutput(boolean writeToFileL) {
	writeToFile = writeToFileL;
	if (writeToFileL) {
	    try {
		out = new FileWriter("EDBT.result");
	    } catch (IOException ex) {
		Logger.getLogger(EDBT.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

    private static void closeOutput() {
	if (writeToFile) {
	    try {
		out.close();
	    } catch (IOException ex) {
		Logger.getLogger(EDBT.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

    private static void output(String string) {
	if (writeToFile) {
	    try {
		out.write(string);
		out.flush();
	    } catch (IOException ex) {
		Logger.getLogger(EDBT.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	System.out.print(string);
    }

    private static void output(String stringScreen, String stringFile) {
	if (writeToFile) {
	    try {
		out.write(stringFile);
		out.flush();
	    } catch (IOException ex) {
		Logger.getLogger(EDBT.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	System.out.print(stringScreen);
    }
}
