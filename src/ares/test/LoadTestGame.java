package ares.test;

import ares.data.jaxb.EquipmentDB;
import ares.engine.RealTimeEngine;
import ares.application.io.AresIO;
import ares.platform.io.ResourcePaths;
import ares.scenario.Scenario;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class LoadTestGame {

    public static void main(String[] args) {
        String fileName = "Test\\singleUnitTest.scenario";
//        String fileName = "Classic TOAW\\Barbarossa 41.scenario";
//        String fileName = "Classic TOAW\\Arracourt 44.scenario";
//        String fileName = "Classic TOAW\\Cobra 44.scenario";
//        String fileName = "Classic TOAW\\African Campaign 42.scenario";

        Scenario scenario = getTestScenario(fileName);
        RealTimeEngine engine = new RealTimeEngine();
        engine.setScenario(scenario);
        engine.resume();
    }

    public static Scenario getTestScenario(String fileName) {
        File equipmentFile = AresIO.ARES_IO.getAbsolutePath(ResourcePaths.EQUIPMENT.getPath(), "ToawEquipment.equipment").toFile();
        EquipmentDB eqp = (EquipmentDB) AresIO.ARES_IO.unmarshall(equipmentFile);
        Logger.getLogger(LoadTestGame.class.getName()).log(Level.INFO, "Equipment database opened: {0}", equipmentFile.getName());

        File scenariofile = AresIO.ARES_IO.getAbsolutePath(ResourcePaths.SCENARIOS.getPath(), fileName).toFile();
        ares.data.jaxb.Scenario scen = (ares.data.jaxb.Scenario) AresIO.ARES_IO.unmarshall(scenariofile);
        Logger.getLogger(LoadTestGame.class.getName()).log(Level.INFO, "Scenario opened: {0}", scenariofile.getName());

        Scenario scenario = new Scenario(scen, eqp);
        return scenario;

    }
}
