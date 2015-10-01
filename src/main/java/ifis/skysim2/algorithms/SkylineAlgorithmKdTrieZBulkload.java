package ifis.skysim2.algorithms;

import ifis.skysim2.data.points.LinkedPointList;
import ifis.skysim2.data.sources.PointSource;
import ifis.skysim2.data.trees.kdtrie.KdTrie;
import ifis.skysim2.simulator.SimulatorConfiguration;
import ifis.skysim2.simulator.SimulatorConfiguration.PresortStrategy;
import java.util.List;

/**
 * This algorithm is potentially incorrect when faced with value collisions.
 *
 */
public class SkylineAlgorithmKdTrieZBulkload extends AbstractSkylineAlgorithm {

    private final boolean debug;

    public SkylineAlgorithmKdTrieZBulkload() {
	this(false);
    }

    public SkylineAlgorithmKdTrieZBulkload(boolean debug) {
	this.debug = debug;
    }

    @Override
    public void setExperimentConfig(SimulatorConfiguration config) {
	super.setExperimentConfig(config);
    }

    @Override
    public List<float[]> compute(PointSource data) {
	ioCost = -1;
	reorgTimeNS = -1;
	long startTime = System.nanoTime();

	int d = data.getD();
	KdTrie tree = new KdTrie(data);
	totalTimeNS = System.nanoTime() - startTime;

	memoryConsumption = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	cpuCost = tree.getNumberOfComparions();
	numberOfNodesVisited = tree.getNumberOfNodesVisited();

	LinkedPointList result = new LinkedPointList(d);
	for (float[] point: tree) {
	    result.add(point);
	}

	if (debug) {
	    System.out.println(tree.getStats());
	}

	return result;
    }

    @Override
    public String toString() {
	return "KdTrieZBulk" ;
    }

    @Override
    public String getShortName() {
	return "KdTrieZBulk";
    }
}
