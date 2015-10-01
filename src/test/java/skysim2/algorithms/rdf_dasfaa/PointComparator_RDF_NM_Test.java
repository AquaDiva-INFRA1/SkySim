/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skysim2.algorithms.rdf_dasfaa;

import ifis.skysim2.algorithms.rdf_dasfaa.PointComparator_RDF;
import ifis.skysim2.algorithms.rdf_dasfaa.PointComparator_RDF_NM;
import ifis.skysim2.data.tools.PointRelationship;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Christoph
 */
public class PointComparator_RDF_NM_Test {

    public PointComparator_RDF_NM_Test() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCompare() {

        PointComparator_RDF comparator = new PointComparator_RDF_NM(0.2f);
        {
            float[] a = {0.7f, 0.7f, 0.9f, 0.9f};
            float[] b = {1f, 1f, 0.8f, 0.8f};
            assertEquals(PointRelationship.IS_DOMINATED_BY, comparator.compare(a, b));
        }
        {
            float[] a = {1f, 1f, 1f, 1f};
            float[] b = {0f, 0f, 0f, 0f};
            assertEquals(PointRelationship.DOMINATES, comparator.compare(a, b));
        }
        {
            float[] a = {0f, 0f, 0f, 0f};
            float[] b = {1f, 1f, 1f, 1f};
            assertEquals(PointRelationship.IS_DOMINATED_BY, comparator.compare(a, b));
        }
        {
            float[] a = {0.9f, 0.9f, 1f, 1f};
            float[] b = {1f, 1f, 0f, 0f};
            assertEquals(PointRelationship.IS_INCOMPARABLE_TO, comparator.compare(a, b));
        }
        {
            float[] a = {1f, 1f, 0.8f, 0.8f};
            float[] b = {0.7f, 0.7f, 0.9f, 0.9f};
            assertEquals(PointRelationship.DOMINATES, comparator.compare(a, b));
        }

        {
            float[] a = {1f, 1f, 0.5f, 0.5f};
            float[] b = {0.7f, 0.7f, 0.9f, 0.9f};
            assertEquals(PointRelationship.IS_INCOMPARABLE_TO, comparator.compare(a, b));

        }
        {
            float[] b = {1f, 1f, 0.5f, 0.5f};
            float[] a = {0.7f, 0.7f, 0.9f, 0.9f};
            assertEquals(PointRelationship.IS_INCOMPARABLE_TO, comparator.compare(a, b));

        }
        {
            float[] b = {0.8f, 0.8f, 0.8f, 0.8f};
            float[] a = {0.8f, 0.8f, 0.8f, 0.8f};
            assertEquals(PointRelationship.EQUALS, comparator.compare(a, b));
        }

    }
}
