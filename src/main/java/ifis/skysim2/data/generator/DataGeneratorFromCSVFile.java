package ifis.skysim2.data.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataGeneratorFromCSVFile implements DataGenerator {

    private String shortName = null;
    private int n;
    private int d;
    private float[] data;

    private static final String SEPARATOR = ",";

    public DataGeneratorFromCSVFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String line = null;
            StringTokenizer st;

            // Read header (d, n, and shortName).
            try {
                line = reader.readLine();
                while (line.startsWith("#")) {
                    // Comment line found.
                    line = reader.readLine();
                }
            } catch (IOException ex) {
                Logger.getLogger(DataGeneratorFromCSVFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            st = new StringTokenizer(line, SEPARATOR);
            d = Integer.parseInt(st.nextToken());
            n = Integer.parseInt(st.nextToken());
            shortName = st.nextToken();

            data = new float[d * n];

            // Read data, skipping all comment lines.
            int pos = 0;
            try {
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("#")) {
                        // Comment line found.
                        continue;
                    }
                    st = new StringTokenizer(line, SEPARATOR);
                    while (st.hasMoreTokens()) {
                        data[pos] = Float.parseFloat(st.nextToken());
                        pos++;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(DataGeneratorFromCSVFile.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(DataGeneratorFromCSVFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public float[] generate(int d, int n) {
        if ((d != this.d) || (n != this.n)) {
            throw new UnsupportedOperationException("Wrong data description (d/n).");
        }
        return data;
    }

    @Override
    public float[] generate(int d, int n, int[] levels) {
        if (levels != null) {
            Logger.getLogger(DataGeneratorFromCSVFile.class.getName()).log(Level.INFO, "You tried to use the levels config, but this is of course not supported by CSV files and therefore ignored.");
        }
        return generate(d, n);
    }

    @Override
    public void resetToDefaultSeed() {
    }

    @Override
    public float[] generate(int d, int n, int levels) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void generate(int d, int n, File file, int bytesPerRecord) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void generate(int d, int n, int[] levels, File file, int bytesPerRecord) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void generate(int d, int n, int levels, File file, int bytesPerRecord) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    @Override
    public boolean providesRandomData() {
        return false;
    }

    public int getD() {
        return d;
    }

    public int getN() {
        return n;
    }

}
