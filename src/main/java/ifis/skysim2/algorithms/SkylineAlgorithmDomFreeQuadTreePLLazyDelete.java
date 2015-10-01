package ifis.skysim2.algorithms;

import ifis.skysim2.data.trees.DomFreeTree;
import ifis.skysim2.data.trees.quadtree.DomFreeQuadTreePLLazyDelete;

/**
 * This algorithm is potentially incorrect when faced with value collisions.
 *
 */
public class SkylineAlgorithmDomFreeQuadTreePLLazyDelete extends AbstractSkylineAlgorithmDomFreeTree {

    public SkylineAlgorithmDomFreeQuadTreePLLazyDelete(boolean debug) {
	super(debug);
    }

    @Override
    public String toString() {
	return "QuadTreePLLazy" ;
    }

    @Override
    public String getShortName() {
	return "QuadTreePLLazy";
    }

    @Override
    protected DomFreeTree _createTree(int d) {
	return new DomFreeQuadTreePLLazyDelete(d);
    }
}