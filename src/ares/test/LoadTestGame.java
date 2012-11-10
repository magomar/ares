/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ares.test;

import ares.data.jaxb.EquipmentDB;
import ares.model.scenario.Scenario;
import ares.io.AresPaths;
import ares.io.AresIO;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class LoadTestGame {

    public static void main(String[] args) {
         String fileName = "Test\\Arracourt 44 ManeuverTest.scenario";
//        String fileName = "Test\\Arracourt 44 Test.scenario";
//        String fileName = "Classic TOAW\\Barbarossa 41.scenario";
//        String fileName = "Classic TOAW\\Arracourt 44.scenario";
//        String fileName = "Classic TOAW\\Cobra 44.scenario";
//        String fileName = "Classic TOAW\\African Campaign 42.scenario";

        File equipmentFile = AresIO.ARES_IO.getAbsolutePath(AresPaths.EQUIPMENT.getPath(), "ToawEquipment.equipment").toFile();
        EquipmentDB eqp = (EquipmentDB) AresIO.ARES_IO.unmarshall(equipmentFile);
        Logger.getLogger(LoadTestGame.class.getName()).log(Level.INFO, "Equipment database opened: {0}", equipmentFile.getName());

        File scenariofile =  AresIO.ARES_IO.getAbsolutePath(AresPaths.SCENARIOS.getPath(), fileName).toFile();
        ares.data.jaxb.Scenario scen = (ares.data.jaxb.Scenario) AresIO.ARES_IO.unmarshall(scenariofile);
        Logger.getLogger(LoadTestGame.class.getName()).log(Level.INFO, "Scenario opened: {0}", scenariofile.getName());

        Scenario scenario = new Scenario(scen, eqp);
//        scenario.start();
    }
}
