package ifis.skysim2.algorithms;

import ifis.skysim2.data.trees.ddtree.DominanceDecisionTree;
import ifis.skysim2.data.trees.DomFreeTree;

public class SkylineAlgorithmDominanceDecisionTree extends AbstractSkylineAlgorithmDomFreeTree {

    @Override
    public String toString() {
	return "DDTree" ;
    }

    @Override
    public String getShortName() {
	return "DDTree";
    }

    @Override
    protected DomFreeTree _createTree(int d) {
	return new DominanceDecisionTree(d);
    }
}
