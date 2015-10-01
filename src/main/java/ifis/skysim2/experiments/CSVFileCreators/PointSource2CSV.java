/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifis.skysim2.experiments.CSVFileCreators;

import ifis.skysim2.data.sources.PointSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christoph
 */
public class PointSource2CSV {

    public static void writeToCSVFile(PointSource source, File file) {
        // init file

        try (Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF-8"));) {

            // write header
            out.write("#next line: numberOfColumns,numberOfRows,dataType\n");
            out.write("" + source.getD() + ",");
            out.write("" + source.size() + ",inMemoryDump\n");
            out.write("#\n# dumped from a mainmemory point source \n#\n");

            // write points
            for (float[] point : source) {
                for (int o = 0; o < point.length; o++) {
                    out.write(String.valueOf(point[o]));
                    if (o < point.length - 1) {
                        out.write(", ");
                    } else {
                        out.write('\n');
                    }
                }
            }

            Logger.getLogger(DbDumpFileCreator_NBA.class.getName()).log(Level.INFO, "Wrote {0} tuples to file {1}", new Object[]{source.size(), file.getAbsolutePath()});
        } catch (IOException ex) {
            Logger.getLogger(PointSource2CSV.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
