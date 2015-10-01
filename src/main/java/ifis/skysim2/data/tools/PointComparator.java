package ifis.skysim2.data.tools;

import java.util.Arrays;

public final class PointComparator {

    /**
     * compares two data points a and b returns DOMINATES if a > b
     * returnsIS_DOMINATED_BY if a < b returns IS_INCOMPARABLE_TO if a <> b
     * returns EQUALS if a == b (currently disabled, it will return incomparable
     * in case of equality)
     *
     */
    public final static PointRelationship compare(final float[] pointA, final float[] pointB) {

        int i = pointA.length - 1;
        // check if equal
        while (pointA[i] == pointB[i]) {
            i--;
            if (i < 0) {
                // this is wrong! should be equal, but algorithms are to stupid to work with that
                return PointRelationship.EQUALS;
            }
        }

        if (pointA[i] >= pointB[i]) {
            while (--i >= 0) {
                if (pointA[i] < pointB[i]) {
                    return PointRelationship.IS_INCOMPARABLE_TO;
                }
            }
            return PointRelationship.DOMINATES;
        } else {
            while (--i >= 0) {
                if (pointA[i] > pointB[i]) {
                    return PointRelationship.IS_INCOMPARABLE_TO;
                }
            }
            return PointRelationship.IS_DOMINATED_BY;
        }
    }

    /**
     * Similar to normal compare, but restricted to subspaces.
     *
     * @param pointA
     * @param pointB
     * @param subspaceIndexes
     * @return
     */
    public final static PointRelationship compare(final float[] pointA, final float[] pointB, final int[] subspaceIndexes) {
        int ssIx = subspaceIndexes.length - 1;
        int i = subspaceIndexes[ssIx];

        // check if equal
        while (pointA[i] == pointB[i]) {
            ssIx--;
            if (ssIx < 0) {
                // this is wrong! should be equal, but algorithms are to stupid to work with that
                return PointRelationship.EQUALS;
            }
            i = subspaceIndexes[ssIx];
        }

        if (pointA[i] >= pointB[i]) {
            while (--ssIx >= 0) {
                i = subspaceIndexes[ssIx];
                if (pointA[i] < pointB[i]) {
                    return PointRelationship.IS_INCOMPARABLE_TO;
                }
            }
            return PointRelationship.DOMINATES;
        } else {
            while (--ssIx >= 0) {
                i = subspaceIndexes[ssIx];
                if (pointA[i] > pointB[i]) {
                    return PointRelationship.IS_INCOMPARABLE_TO;
                }
            }
            return PointRelationship.IS_DOMINATED_BY;
        }
    }

    // compares two points located in a storage array
    public final static PointRelationship compare(float[] data, int indexA, int indexB, int d) {
        // this kind of indirection does not impose any performance penalty
        return compare(data, indexA, data, indexB, d);
    }

    // Checks whether a point @p is dominated by some point in a list of @d-dimensional points,
    // which is stored as an array @data, containing @m points.
    public final static boolean isDominated(float[] p, float[] data, int m, int d) {
        // equality handling not supported yet
        dataloop:
        for (int posDataStart = m * d - 1; posDataStart >= 0; posDataStart -= d) {
            int posData = posDataStart;
            int posP = d - 1;
            if (p[posP] >= data[posData]) {
                while (posP > 0) {
                    posP--;
                    posData--;
                    if (p[posP] < data[posData]) {
                        continue dataloop; // incomparable
                    }
                }
                // @p dominates (or is equal)
            } else {
                while (posP > 0) {
                    posP--;
                    posData--;
                    if (p[posP] > data[posData]) {
                        continue dataloop; // incomparable
                    }
                }
                return true; // @p is dominated
            }
        }
        return false;
    }

    // Finds some skyline point in a list of @d-dimensional points,
    // which is stored as an array @data, containing @m points.
    public final static float[] findUndominated(float[] data, int m, int d) {
        // equality handling not supported yet
        int md = m * d;
        float[] candidate = Arrays.copyOfRange(data, md - d, md);
        dataloop:
        for (int posDataStart = md - d - 1; posDataStart >= 0; posDataStart -= d) {
            int posData = posDataStart;
            int posCand = d - 1;
            if (candidate[posCand] >= data[posData]) {
                while (posCand > 0) {
                    posCand--;
                    posData--;
                    if (candidate[posCand] < data[posData]) {
                        continue dataloop; // incomparable
                    }
                }
                // @cand dominates
            } else {
                while (posCand > 0) {
                    posCand--;
                    posData--;
                    if (candidate[posCand] > data[posData]) {
                        continue dataloop; // incomparable
                    }
                }
                candidate = Arrays.copyOfRange(data, posDataStart - (d - 1), posDataStart + 1); // @cand is dominated
            }
        }
        return candidate;
    }

    public final static PointRelationship compare(float[] dataA, int indexA, float[] dataB, int indexB, int d) {

        int i = d - 1;
        int positionA = indexA;
        int positionB = indexB;

        while (dataA[positionA] == dataB[positionB]) {
            positionA++;
            positionB++;
            i--;
            if (i < 0) {
                return PointRelationship.EQUALS;
            }
        }

        if (dataA[positionA] >= dataB[positionB]) {
            while (--i >= 0) {
                positionA++;
                positionB++;
                if (dataA[positionA] < dataB[positionB]) {
                    return PointRelationship.IS_INCOMPARABLE_TO;
                }
            }
            return PointRelationship.DOMINATES;
        } else {
            while (--i >= 0) {
                positionA++;
                positionB++;
                if (dataA[positionA] > dataB[positionB]) {
                    return PointRelationship.IS_INCOMPARABLE_TO;
                }
            }
            return PointRelationship.IS_DOMINATED_BY;
        }
    }

    public final static PointRelationship compare(float[] data, int index, float[] point, int d) {
        return compare(data, index, point, 0, d);
    }

    /*
     * A binary vector indicating the relationship between a's and b's coordinates.
     * result's (i + 1)-th least significant bit is 1  iff  a[i] >= b[i].
     * This is my personal modified definition; it is also used in
     * Zhang's object-based space partitioning (modified such that max = good).
     */
    public static long getRegionIDOfBRelativeToAZhang(float[] a, float[] b, int d) {
        long successorship = 0;
        for (int i = d - 1; i >= 0; i--) {
            successorship = successorship << 1;
            if (a[i] >= b[i]) {
                successorship++;
            }
        }
        return successorship;
    }

    public static long getRegionIDOfBRelativeToAZhang2(float[] a, float[] b, int d) {
        long successorship = 0;
        for (int i = d - 1; i >= 0; i--) {
            successorship = successorship << 1;
            if (a[i] > b[i]) {
                successorship++;
            }
        }
        return successorship;
    }
}
