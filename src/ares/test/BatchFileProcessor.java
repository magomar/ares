/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ares.test;

import ares.data.jaxb.EquipmentDB;
import ares.scenario.Scenario;
import ares.io.AresFileType;
import ares.io.AresIO;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario
 */
public class BatchFileProcessor {

    final static String scenarioPath = "\\Data\\Scenarios\\Classic TOAW";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File directory = new File(System.getProperty("user.dir") + scenarioPath);
        Collection<File> files = listFiles(directory, AresFileType.SCENARIO.getFileTypeFilter(), true);

        File equipmentFile = new File(System.getProperty("user.dir") + "\\Data\\Equipment\\ToawEquipment.equipment");
        EquipmentDB eqp = (EquipmentDB) AresIO.ARES_IO.unmarshall(equipmentFile);
        Logger.getLogger(LoadTestGame.class.getName()).log(Level.INFO, "Equipment database opened: {0}", equipmentFile.getName());
        for (File file : files) {
            System.out.println(file.getName());
            ares.data.jaxb.Scenario scen = (ares.data.jaxb.Scenario) AresIO.ARES_IO.unmarshall(file);
            Logger.getLogger(BatchFileProcessor.class.getName()).log(Level.INFO, "Scenario loaded: {0}", file.getName());
            Scenario scenario = new Scenario(scen, eqp);
        }
        Logger.getLogger(BatchFileProcessor.class.getName()).log(Level.INFO, "*** Number of files processed {0}", files.size());
    }

    static File[] listFilesAsArray(File directory, FilenameFilter filter, boolean recurse) {
        Collection<File> files = listFiles(directory, filter, recurse);
        File[] arr = new File[files.size()];
        return files.toArray(arr);
    }

    static Collection<File> listFiles(File directory, FilenameFilter filter, boolean recurse) {
        Collection<File> files = new ArrayList<>();
        File[] entries = directory.listFiles();
        for (File entry : entries) {
            if (filter == null || filter.accept(directory, entry.getName())) {
                files.add(entry);
            }

            if (recurse && entry.isDirectory()) {
                files.addAll(listFiles(entry, filter, recurse));
            }
        }

        return files;
    }
}
