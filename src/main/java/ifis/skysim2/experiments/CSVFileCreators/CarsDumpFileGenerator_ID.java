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
public class CarsDumpFileGenerator_ID extends DbDumpFileCreator_NBA {

    public static CarsDumpFileGenerator_ID getGenerator() {
        CarsDumpFileGenerator_ID generator = new CarsDumpFileGenerator_ID();
        generator.setUrl("jdbc:db2://is60.idb.cs.tu-bs.de:50000/prefs").setTable("LANGNER.CARS2");
        generator.setDumpfileName("data\\cars_id.csv");
        String[] columns = {"ID","PRICE", "POWER", "ACCELERATION", "FUELCONSUMPTION", "CO2EMISSION", "TAXES"};
        generator.setColumns(columns);
        PrefType[] colPrefs = {PrefType.LABEL, PrefType.NUMERIC_DESC, PrefType.NUMERIC_ASC, PrefType.NUMERIC_DESC, PrefType.NUMERIC_DESC, PrefType.NUMERIC_DESC, PrefType.NUMERIC_DESC};
        generator.setColPref(colPrefs);
        generator.setNotNullFilter(true);
        generator.setFilter("");
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
            CarsDumpFileGenerator_ID generator = getGenerator();
            generator.dumpDataToFile();
        } catch (Exception ex) {
            Logger.getLogger(CarsDumpFileGenerator_ID.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
