package ifis.skysim2.algorithms;

import ifis.skysim2.data.points.LinkedPointList;
import ifis.skysim2.data.sources.PointSource;
import ifis.skysim2.data.trees.quadtree.DomFreeQuadTreePLPartitioning;
import ifis.skysim2.simulator.SimulatorConfiguration;
import ifis.skysim2.simulator.SimulatorConfiguration.PresortStrategy;
import java.util.List;

/**
 * This algorithm is potentially incorrect when faced with value collisions.
 *
 */
public class SkylineAlgorithmDomFreeQuadTreePartitioning extends AbstractSkylineAlgorithm {

    private boolean presorted;
    private final boolean debug;

    public SkylineAlgorithmDomFreeQuadTreePartitioning() {
        this(false);
    }

    public SkylineAlgorithmDomFreeQuadTreePartitioning(boolean debug) {
        this.debug = debug;
    }

    @Override
    public void setExperimentConfig(SimulatorConfiguration config) {
        super.setExperimentConfig(config);
        presorted = ((config.getPresortStrategy() == PresortStrategy.FullPresortSum)
                || (config.getPresortStrategy() == PresortStrategy.FullPresortVolume));
    }

    @Override
    public List<float[]> compute(PointSource data) {
        ioCost = -1;
        reorgTimeNS = -1;
        long startTime = System.nanoTime();

        int d = data.getD();
        DomFreeQuadTreePLPartitioning tree = new DomFreeQuadTreePLPartitioning(data, presorted);
        totalTimeNS = System.nanoTime() - startTime;

        memoryConsumption = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        cpuCost = tree.getNumberOfComparions();
        numberOfNodesVisited = tree.getNumberOfNodesVisited();

        LinkedPointList result = new LinkedPointList(d);
        for (float[] point : tree) {
            result.add(point);
        }

        if (debug) {
            System.out.println(tree.getStats());
        }

        return result;
    }

    @Override
    public String toString() {
        return "QuadTreePLPart";
    }

    @Override
    public String getShortName() {
        return "QuadTreePLPart";
    }
}
