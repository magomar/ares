package ares.application.controllers;

import ares.application.boundaries.view.BoardViewer;
import ares.application.boundaries.view.CommandBarViewer;
import ares.application.commands.EngineCommands;
import ares.application.commands.FileCommands;
import ares.application.models.ScenarioModel;
import ares.application.player.AresMenus;
import ares.application.player.AresPlayerGUI;
import ares.application.views.MessagesHandler;
import ares.data.jaxb.EquipmentDB;
import ares.io.AresFileType;
import ares.io.AresIO;
import ares.io.AresPaths;
import ares.platform.application.AbstractAresApplication;
import ares.platform.controllers.AbstractSecondaryController;
import ares.platform.model.UserRole;
import ares.platform.util.AsynchronousOperation;
import ares.scenario.Scenario;
import ares.scenario.forces.Force;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class ScenarioIOController extends AbstractSecondaryController{
    private static final Logger LOG = Logger.getLogger(ScenarioIOController.class.getName());
    private final AbstractAresApplication aaa;
    private final CommandBarViewer menuView;
    private final BoardViewer boardView;

    public ScenarioIOController(AbstractAresApplication mainView, CommandBarViewer menuView, BoardViewer boardView, WeGoPlayerController mainController) {
        super(mainController);
        this.aaa = mainView;
        this.menuView = menuView;
        this.boardView = boardView;
    }
    
    private class OpenScenarioInteractor extends AsynchronousOperation<Scenario> {

        @Override
        protected Scenario performOperation() throws Exception {
            
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(AresIO.ARES_IO.getAbsolutePath(AresPaths.SCENARIOS.getPath()).toFile());
            fc.setFileFilter(AresFileType.SCENARIO.getFileTypeFilter());
//            int returnVal = fc.showOpenDialog(aaa.getContentPane());
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                // Load scenario and equipment files
                ares.data.jaxb.Scenario scen = (ares.data.jaxb.Scenario) AresIO.ARES_IO.unmarshall(file);
                File equipmentFile = AresIO.ARES_IO.getAbsolutePath(AresPaths.EQUIPMENT.getPath(), "ToawEquipment" + AresFileType.EQUIPMENT.getFileExtension()).toFile();
                EquipmentDB eqp = (EquipmentDB) AresIO.ARES_IO.unmarshall(equipmentFile);
                Scenario scenario = new Scenario(scen, eqp);
                // set the user role by asking (using a dialog window)
                Force[] forces = scenario.getForces();
                UserRole[] options = new UserRole[forces.length + 1];
                for (int i = 0; i < forces.length; i++) {
                    Force force = forces[i];
                    options[i] = UserRole.getForceRole(force);
                }
                options[forces.length] = UserRole.GOD;

//                int n = JOptionPane.showOptionDialog(aaa.getContentPane(),
                int n = JOptionPane.showOptionDialog(null,
                        "Please select a user role",
                        "Select your role",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);
                if (n >= 0) {
                    // Loading scenario...
                    mainController.setUserRole(options[n]);
                    return scenario;
                }
            }
            return null;
        }

        @Override
        protected void onSuccess(Scenario scenario) {

            if (scenario != null) {
                // Show the menu bar
                menuView.setVisible(true);
                aaa.switchCard(AresPlayerGUI.PLAY_CARD);

                // Set the engine with the new scenario
                mainController.getEngine().setScenario(scenario);

                // obtain the scenario model with the active userRole
                ScenarioModel scenarioModel = mainController.getEngine().getScenarioModel(mainController.getUserRole());
                boardView.loadScenario(scenarioModel);
                // set main window title & show appropriate views
                aaa.setTitle("ARES   " + scenario.getName() + "   " + scenario.getCalendar().toString() + "   Role: " + mainController.getUserRole());
                menuView.setCommandEnabled(FileCommands.CLOSE_SCENARIO.getName(), true);
                menuView.setCommandEnabled(AresMenus.ENGINE_MENU.getName(), true);
                menuView.setCommandEnabled(EngineCommands.START.getName(), true);
                menuView.setCommandEnabled(EngineCommands.PAUSE.getName(), false);
                menuView.setCommandEnabled(EngineCommands.NEXT.getName(), false);
            }
        }
    }

    private class OpenScenarioActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, e.toString());
            mainController.getExecutor().execute(new OpenScenarioInteractor());

        }
    }

    private class CloseScenarioActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, e.toString());
            mainController.getEngine().setScenario(null);
            menuView.setCommandEnabled(FileCommands.CLOSE_SCENARIO.getName(), false);
            menuView.setCommandEnabled(AresMenus.ENGINE_MENU.getName(), false);
            boardView.closeScenario();
            aaa.switchCard(AresPlayerGUI.MAIN_MENU_CARD);

        }
    }

    private class ExitActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, e.toString());
            System.exit(0);
        }
    }

    // TODO load saved games
    private class LoadScenarioActionListener implements ActionListener {

        public LoadScenarioActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    // TODO create settings window
    private static class SettingsActionListener implements ActionListener {

        public SettingsActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    ActionListener OpenScenarioActionListener() {
        return new OpenScenarioActionListener();
    }

    ActionListener CloseScenarioActionListener() {
        return new CloseScenarioActionListener();
    }

    ActionListener ExitActionListener() {
        return new ExitActionListener();
    }
    
    ActionListener LoadScenarioActionListener() {
        return new LoadScenarioActionListener();
    }

    ActionListener SettingsActionListener() {
        return new SettingsActionListener();
    }
        
}
