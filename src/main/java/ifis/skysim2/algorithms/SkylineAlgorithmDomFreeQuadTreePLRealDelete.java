package ifis.skysim2.algorithms;

import ifis.skysim2.data.trees.DomFreeTree;
import ifis.skysim2.data.trees.quadtree.DomFreeQuadTreePLRealDelete;

/**
 * This algorithm is potentially incorrect when faced with value collisions.
 *
 */
public class SkylineAlgorithmDomFreeQuadTreePLRealDelete extends AbstractSkylineAlgorithmDomFreeTree {

    @Override
    public String toString() {
	return "QuadTreePLReal" ;
    }

    @Override
    public String getShortName() {
	return "QuadTreePLReal";
    }

    @Override
    protected DomFreeTree _createTree(int d) {
	return new DomFreeQuadTreePLRealDelete(d);
    }
}