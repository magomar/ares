package ares.application.controllers;

import ares.application.boundaries.view.BoardViewer;
import ares.application.boundaries.view.CommandBarViewer;
import ares.application.boundaries.view.UnitInfoViewer;
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
import ares.scenario.Clock;
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
public final class ScenarioIOController extends AbstractSecondaryController {

    private static final Logger LOG = Logger.getLogger(ScenarioIOController.class.getName());
    private final AbstractAresApplication mainView;
    private final CommandBarViewer menuView;
    private final CommandBarViewer welcomeView;
    private final BoardViewer boardView;
    private final UnitInfoViewer infoView;

    public ScenarioIOController(WeGoPlayerController mainController) {
        super(mainController);
        this.mainView = mainController.getMainView();
        this.menuView = mainController.getMenuView();
        this.boardView = mainController.getBoardView();
        this.infoView = mainController.getInfoView();
        this.welcomeView = mainController.getWelcomeScreenView();
        LOG.addHandler(mainController.getMessagesView().getHandler());

        //Create & add listeners to the views
        OpenScenarioActionListener open = new OpenScenarioActionListener();
        LoadScenarioActionListener load = new LoadScenarioActionListener();
        ExitActionListener exit = new ExitActionListener();

        menuView.addActionListener(FileCommands.OPEN_SCENARIO.getName(), open);
        menuView.addActionListener(FileCommands.CLOSE_SCENARIO.getName(), new CloseScenarioActionListener());
        menuView.addActionListener(FileCommands.LOAD_SCENARIO.getName(), load);
        menuView.addActionListener(FileCommands.EXIT.getName(), exit);

        welcomeView.addActionListener(FileCommands.OPEN_SCENARIO.getName(), open);
        welcomeView.addActionListener(FileCommands.LOAD_SCENARIO.getName(), load);
        welcomeView.addActionListener(FileCommands.SETTINGS.getName(), new SettingsActionListener());
        welcomeView.addActionListener(FileCommands.EXIT.getName(), exit);
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
                ares.data.jaxb.Scenario scen = AresIO.ARES_IO.unmarshallJson(file, ares.data.jaxb.Scenario.class);
                File equipmentFile = AresIO.ARES_IO.getAbsolutePath(AresPaths.EQUIPMENT.getPath(), "ToawEquipment" + AresFileType.EQUIPMENT.getFileExtension()).toFile();
                EquipmentDB eqp = AresIO.ARES_IO.unmarshallJson(equipmentFile, EquipmentDB.class);
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
                mainView.switchCard(AresPlayerGUI.PLAY_CARD);

                // Set the engine with the new scenario
                mainController.setScenario(scenario);

                // obtain the scenario model with the active userRole
                ScenarioModel scenarioModel = scenario.getModel(mainController.getUserRole());
                boardView.loadScenario(scenarioModel);
                // set main window title & show appropriate views
                mainView.setTitle("ARES   " + scenario.getName() + "   " + Clock.INSTANCE.toStringVerbose()
                        + "   Role: " + mainController.getUserRole());
                menuView.setCommandEnabled(FileCommands.CLOSE_SCENARIO.getName(), true);
                menuView.setCommandEnabled(AresMenus.ENGINE_MENU.getName(), true);
                menuView.setCommandEnabled(EngineCommands.START.getName(), true);
                menuView.setCommandEnabled(EngineCommands.PAUSE.getName(), false);
                menuView.setCommandEnabled(EngineCommands.NEXT.getName(), false);
                String scenInfo = scenario.getName() + "\n" + Clock.INSTANCE.toStringVerbose() + "\nRole: " + mainController.getUserRole();
                infoView.updateScenInfo(scenInfo);
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
            mainController.setScenario(null);
            menuView.setCommandEnabled(FileCommands.CLOSE_SCENARIO.getName(), false);
            menuView.setCommandEnabled(AresMenus.ENGINE_MENU.getName(), false);
            boardView.closeScenario();
            mainView.switchCard(AresPlayerGUI.MAIN_MENU_CARD);

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
    class SettingsActionListener implements ActionListener {

        public SettingsActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }
}
