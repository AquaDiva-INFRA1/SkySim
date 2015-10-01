package ifis.skysim2.algorithms.rdf_dasfaa;

import ifis.skysim2.data.tools.*;
import java.util.Arrays;

/**
 * This is the comparator for extended definition 4b of the jounral version. It
 * allows for malleable and non-malleable attributes, with no restrictions or
 * improvements. It assumes that the first half of the tuple is non-malleable,
 * and the second one is malleable.
 *
 * @author Christoph
 */
public final class PointComparator_RDF_NM_ExtendedDef4b implements PointComparator_RDF {

    private float delta = 0.2f;

    public PointComparator_RDF_NM_ExtendedDef4b(float delta) {
        this.delta = delta;
    }

    // compares two data points a and b
    // returns DOMINATES if a > b
    // returns IS_DOMINATED_BY if a < b
    // returns IS_INCOMPARABLE_TO if a <> b
    // returns EQUALS if a == b
    public final PointRelationship compare(final float[] pointA, final float[] pointB) {

        // Equals
        int i = pointA.length;
        // equalas
        outerloop:
        while (--i >= 0) {
            if (pointA[i] != pointB[i]) {
                break outerloop;
            }
        }
        if (i < 0) {
            return PointRelationship.EQUALS;
        }

        // dominance
        i = pointA.length - 1;
        PointRelationship result = null;

        // first phase: check for overall pareto dominance
        outerif:
        if (pointA[i] >= pointB[i]) {
            while (--i >= 0) {
                if (pointA[i] < pointB[i]) {
                    result = PointRelationship.IS_INCOMPARABLE_TO;
                    break outerif;
                }
            }
            return PointRelationship.DOMINATES;
        } else {
            while (--i >= 0) {
                if (pointA[i] > pointB[i]) {
                    result = PointRelationship.IS_INCOMPARABLE_TO;
                    break outerif;
                }
            }
            return PointRelationship.IS_DOMINATED_BY;
        }
        // if it was incomparable, check if we get a malleabe dominance
        // 
        { // pareto dominance for first part
            i = (pointA.length / 2) - 1;
            result = null;
            outer2if:
            if (pointA[i] >= pointB[i]) {
                while (--i >= 0) {
                    if (pointA[i] < pointB[i]) {
                        return PointRelationship.IS_INCOMPARABLE_TO;
                    }
                }
                result = PointRelationship.DOMINATES;
            } else {
                while (--i >= 0) {
                    if (pointA[i] > pointB[i]) {
                        return PointRelationship.IS_INCOMPARABLE_TO;
                    }
                }
                result = PointRelationship.IS_DOMINATED_BY;
            }
            // now, we have a data domincance including equvalence
            // test if data part is equivalent
            i = (pointA.length / 2) - 1;
            outerloop:
            while (--i >= 0) {
                if (pointA[i] != pointB[i]) {
                    break outerloop;
                }
            }
            if (i < 0) {
                result = PointRelationship.EQUALS;
            }


            // Okey,now we go on for the case that we do NOT have data equivalence
            if (!result.equals(PointRelationship.EQUALS)) {


                // test for malleability domaincane
                i = (pointA.length - 1);
                int u = (pointA.length / 2);
                if (result.equals(PointRelationship.DOMINATES)) {
                    outer2if:
                    if (pointA[i] + delta >= pointB[i]) {
                        while (--i >= u) {
                            if (pointA[i] + delta < pointB[i]) {
                                return PointRelationship.IS_INCOMPARABLE_TO;

                            }
                        }
                        return PointRelationship.DOMINATES;
                    }
                }
                if (result.equals(PointRelationship.IS_DOMINATED_BY)) {
                    outer2if:
                    if (pointB[i] + delta >= pointA[i]) {
                        while (--i >= u) {
                            if (pointB[i] + delta < pointA[i]) {
                                return PointRelationship.IS_INCOMPARABLE_TO;

                            }
                        }
                        return PointRelationship.IS_DOMINATED_BY;
                    }
                }
            }

            // now, the extension starts with (3): we continue the case that data is equal
            if (result.equals(PointRelationship.EQUALS)) {
                // and we either need strict dominance (4)
                // 
                {
                    i = (pointA.length);
                    int u = (pointA.length / 2);
                    boolean someSmaller = false;
                    boolean someLarger = false;
                    outer2if:
                    while (--i >= u) {
                        if (pointA[i] > pointB[i]) {
                            someLarger = true;
                        } else if (pointA[i] < pointB[i]) {
                            someSmaller = true;
                        }
                    }
                    if (someSmaller & !someLarger) {
                        return PointRelationship.IS_DOMINATED_BY;
                    }
                    if (someLarger & !someSmaller) {
                        return PointRelationship.DOMINATES;
                    }
                }
                // if we get here, then a) data is equal b) there is no dominance in mealleable attributes
                // therefore,we do (5)
                {
                    i = (pointA.length);
                    int u = (pointA.length / 2);
                    boolean someSmaller = false;
                    boolean someLarger = false;
                    outer2if:
                    while (--i >= u) {
                        if (pointA[i] > pointB[i] + delta) {
                            someLarger = true;
                        } else if (pointA[i] + delta < pointB[i]) {
                            someSmaller = true;
                        }
                    }
                    if (someSmaller & !someLarger) {
                        result = PointRelationship.IS_DOMINATED_BY;
                    }
                    if (someLarger & !someSmaller) {
                        result = PointRelationship.DOMINATES;
                    }
                }
                // at this point, there is mainly noise in the malleable attributes
                // do (6)
                {
                    i = (pointA.length);
                    int u = (pointA.length / 2);

                    float sumA = 0;
                    float sumB = 0;
                    outer2if:
                    while (--i >= u) {
                        sumA = sumA + pointA[i];
                        sumB = sumB + pointB[i];
                    }
                    if ((sumA > sumB + delta) && (result.equals(PointRelationship.DOMINATES))) {
                        return PointRelationship.DOMINATES;
                    }
                    if ((sumA + delta < sumB) && (result.equals(PointRelationship.IS_DOMINATED_BY))) {
                        return PointRelationship.IS_DOMINATED_BY;
                    }
                }

            }


        }
        return PointRelationship.IS_INCOMPARABLE_TO;
    }

    @Override
    public String toString() {
        return "Comparator_RDF_NM_Def4b_Ext(delta=" + delta + ")";
    }

    @Override
    public void setDelta(float delta) {
        this.delta = delta;
    }
}
