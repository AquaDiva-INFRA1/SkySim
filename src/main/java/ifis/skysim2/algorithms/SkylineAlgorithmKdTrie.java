package ifis.skysim2.algorithms;

import ifis.skysim2.data.sources.PointSource;
import ifis.skysim2.data.trees.DomFreeTree;
import ifis.skysim2.data.trees.kdtrie.KdTrie;
import java.util.List;

/**
 * This algorithm is potentially incorrect when faced with value collisions.
 *
 */
public class SkylineAlgorithmKdTrie extends AbstractSkylineAlgorithmDomFreeTree {

    private KdTrie lastTree;
    private final boolean debug;

    public SkylineAlgorithmKdTrie(boolean debug) {
        super();
        this.debug = debug;
    }

    @Override
    public List<float[]> compute(PointSource data) {
        List<float[]> result = super.compute(data);
        if (debug) {
            System.out.println(lastTree.getStats());
        }
        return result;
    }

    @Override
    public String toString() {
        return "kd-trie";
    }

    @Override
    public String getShortName() {
        return "kd-trie";
    }

    @Override
    protected DomFreeTree _createTree(int d) {
        lastTree = new KdTrie(d);
        return lastTree;
    }
}
