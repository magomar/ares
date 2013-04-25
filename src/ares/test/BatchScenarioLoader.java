package ares.test;

import ares.data.jaxb.EquipmentDB;
import ares.data.jaxb.Scenario;
import ares.io.AresFileType;
import ares.application.io.AresIO;
import ares.io.ResourcePaths;
import ares.io.FileIO;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class BatchScenarioLoader {

    private final static String[] SCENARIO_FOLDERS = {"Classic TOAW"
//           , "Spanish Civil War", "Test", "WW II - Asia",
//        "WW II - East Front", "WW II - Mediterranean", "WW II - Misc", "WW II - West Front"
    };
    private static final Logger LOG = Logger.getLogger(BatchScenarioLoader.class.getName());

    public static EquipmentDB loadEquipment() {
        File equipmentFile = AresIO.ARES_IO.getAbsolutePath(ResourcePaths.EQUIPMENT.getPath(), "ToawEquipment.equipment").toFile();
        EquipmentDB eqp = (EquipmentDB) AresIO.ARES_IO.unmarshallJson(equipmentFile, EquipmentDB.class);
        Logger.getLogger(LoadTestGame.class.getName()).log(Level.INFO, "Equipment database opened: {0}", equipmentFile.getName());
        return eqp;

    }

    public static int loadScenarios(String scenarioPath, EquipmentDB eqp) {
        Stopwatch sw = new Stopwatch();
        int totalFiles = 0;
        sw.start();
        Path targetPath = AresIO.ARES_IO.getAbsolutePath(ResourcePaths.SCENARIOS.getPath(), scenarioPath);
        Collection<File> files = listFiles(targetPath.toFile(), AresFileType.SCENARIO.getFileTypeFilter(), true);
        for (File file : files) {
            Scenario scen = AresIO.ARES_IO.unmarshallJson(file, Scenario.class);
            ares.scenario.Scenario scenario = new ares.scenario.Scenario(scen, eqp);
        }
        return files.size();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Stopwatch sw = new Stopwatch();
        int totalFiles = 0;
        EquipmentDB eqp = loadEquipment();
        sw.start();
        for (String scenarioPath : SCENARIO_FOLDERS) {
            int files = loadScenarios(scenarioPath, eqp);
            LOG.log(Level.INFO, "*** Files loaded from {0} = {1}", new Object[]{scenarioPath, files});
            totalFiles += files;
        }

        sw.stop();
        LOG.log(Level.INFO, "****** Total number of files loaded = {0}", totalFiles);
        LOG.log(Level.INFO, sw.toString());

    }

    public static List<File> listFiles(File directory, FilenameFilter filter, boolean recurse) {
        List<File> files = new ArrayList<>();
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
