/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifis.skysim2.experiments.CSVFileCreators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Christoph
 */
class FakeNotebookHistogram {

    protected Map<String, Integer> histogram = new HashMap<String, Integer>();

    public void incBucket(String name) {
        Integer value = histogram.get(name);
        if (value == null) {
            value = 0;
        }
        value++;
        histogram.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Histogram\n-------\n");
        for (Map.Entry<String, Integer> entry : histogram.entrySet()) {
            result.append(entry.getKey() + " : " + entry.getValue() + "\n");
        }
        return result.toString();
    }

// attribute 4: display
// 15.4 : 0.7368421
// 13.3 : 0.5526316
// 10.1 : 0.27192986
    public FakeNotebookHistogram(List<float[]> input, int mode) {
        int att = 4;

        for (float[] tuple : input) {
            float value = tuple[att];
            if (mode == 0) {
                if (value > 0.7368421) {
                    incBucket("DesktopReplacement");
                } else if (value >= 0.5526316) {
                    incBucket("Office");
                } else if (value >= 0.27192986) {
                    incBucket("Subnotebook");
                } else {
                    incBucket("Netbook");
                }
            }
            if (mode == 1) {
                incBucket(String.valueOf(value));
            }
        }
    }
}
