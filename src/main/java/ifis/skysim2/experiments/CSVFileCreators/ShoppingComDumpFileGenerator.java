/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifis.skysim2.experiments.CSVFileCreators;

import ifis.skysim2.data.tools.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christoph
 */
public class ShoppingComDumpFileGenerator extends DbDumpFileCreator_NBA {

    public float numerifyCPU_TYPE(String value) {
        value = "";
        //
        if (value.startsWith("AMD Turion 64")) {
            return new Float(0.5);
        } else if (value.startsWith("Core 2 Quad")) {
            return new Float(1.0);
        } else if (value.startsWith("Core 2 Extreme")) {
            return new Float(0.9);
        } else if (value.startsWith("Core 2 Duo")) {
            return new Float(0.8);
        } else if (value.startsWith("AMD Athlon")) {
            return new Float(0.6);
        } else if (value.startsWith("AMD Turion")) {
            return new Float(0.6);
        } else if (value.startsWith("Core 2 Solo")) {
            return new Float(0.4);
        } else if (value.startsWith("Atom")) {
            return new Float(0.1);
        } else {
            return new Float(0.0);
        }
    }

    public float numerifyMANUFACTURER(String value) {
        return new Float(0.0);
    }

    public static ShoppingComDumpFileGenerator getGenerator() {
        ShoppingComDumpFileGenerator generator = new ShoppingComDumpFileGenerator();
        generator.setUrl("jdbc:db2://is60.idb.cs.tu-bs.de:50000/prefs").setTable("NOTEBOOKS_SHOPPINGCOM.PRODUCTS_TEST");
        generator.setDumpfileName("data\\notebooks2.csv");
        String[] columns = {"CPU_TYPE", "CPU_SPEED", "RAM_INSTALLED", "HARD_DRIVE", "SCREEN_SIZE", "WEIGHT", "BEST_PRICE"};
        generator.setColumns(columns);
        PrefType[] colPrefs = {PrefType.CATEGORY, PrefType.NUMERIC_ASC, PrefType.NUMERIC_ASC, PrefType.NUMERIC_ASC, PrefType.NUMERIC_ASC, PrefType.NUMERIC_DESC, PrefType.NUMERIC_DESC};
        generator.setColPref(colPrefs);
        generator.setNotNullFilter(true);
        generator.setFilter("BEST_PRICE>50");
        //
        String user = "lofi";
        String password = null;
        if (user == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter your DB user name: ");
            user = scanner.nextLine();
        }
        if (password == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter your DB password: ");
            password = scanner.nextLine();
        }
        generator.setUser(user).setPassword(password);
        return generator;
    }

    public static void main(String[] args) {
        try {
            ShoppingComDumpFileGenerator generator = getGenerator();
            generator.dumpDataToFile();
        } catch (Exception ex) {
            Logger.getLogger(ShoppingComDumpFileGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
