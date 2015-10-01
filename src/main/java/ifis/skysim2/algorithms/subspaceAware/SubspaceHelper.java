/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifis.skysim2.algorithms.subspaceAware;

import ifis.skysim2.simulator.SimulatorConfiguration;

/**
 * Some utility functions for dealing with subspaces.
 *
 * @author Christoph
 */
public class SubspaceHelper {

    /**
     * Based on the current dataset configured in the configuration, return the
     * full subspace
     *
     * @param config
     * @return
     */
    public static int[] getFullSubspace(SimulatorConfiguration config) {
        int[] result = new int[config.getD()];
        for (int i = 0; i < config.getD(); i++) {
            result[i] = i;
        }
        return result;
    }

    /**
     * Removes the dimension with a given ID. . Does nothing if the dimension
     * was not part of the subspace.
     *
     * @param subspaces
     * @param dimensionId the id of the dimension to be removed. first dimension
     * has ID 0
     * @return
     */
    public static int[] removeDimension(int[] subspaces, int dimensionId) {
        boolean contained = false;
        int[] result = new int[subspaces.length - 1];
        int o = 0;
        for (int i = 0; i < subspaces.length; i++) {
            if (subspaces[i] != dimensionId) {
                result[o++] = subspaces[i];
            } else {
                contained = true;
            }
        }
        if (contained) {
            return result;
        } else {
            return subspaces;
        }
    }

}
