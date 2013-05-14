package ares.application.controllers;

import ares.application.boundaries.view.ActionBarViewer;
import ares.application.boundaries.view.BoardViewer;
import ares.application.boundaries.view.OOBViewer;
import ares.application.boundaries.view.InfoViewer;
import ares.application.commands.FileCommands;
import ares.application.models.ScenarioModel;
import ares.application.commands.AresCommandGroup;
import ares.application.AresPlayerGUI;
import ares.application.gui.components.StartScenarioPane;
import ares.application.views.MessagesHandler;
import ares.data.jaxb.EquipmentDB;
import ares.application.io.AresFileType;
import ares.application.io.AresIO;
import ares.platform.io.ResourcePath;
import ares.platform.application.AbstractAresApplication;
import ares.platform.controllers.AbstractSecondaryController;
import ares.platform.model.UserRole;
import ares.platform.util.AsynchronousOperation;
import ares.engine.time.Clock;
import ares.platform.commands.CommandAction;
import ares.platform.commands.CommandGroup;
import ares.platform.view.ComponentFactory;
import ares.scenario.Scenario;
import ares.scenario.forces.Force;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class ScenarioIOController extends AbstractSecondaryController {

    private static final Logger LOG = Logger.getLogger(ScenarioIOController.class.getName());
    private final AbstractAresApplication mainView;
    private final ActionBarViewer<JMenu> menuView;
    private final ActionBarViewer<JButton> welcomeView;
    private final ActionBarViewer<JButton> toolBarView;
    private final BoardViewer boardView;
    private final InfoViewer infoView;
    private final OOBViewer oobView;
    private final Action open = new CommandAction(FileCommands.GAME_NEW, new OpenScenarioActionListener());
    private final Action load = new CommandAction(FileCommands.GAME_LOAD, new LoadScenarioActionListener());
    private final Action close = new CommandAction(FileCommands.GAME_CLOSE, new CloseScenarioActionListener(), false);
    private final Action exit = new CommandAction(FileCommands.EXIT, new ExitActionListener());
    private final Action settings = new CommandAction(FileCommands.SETTINGS, new SettingsActionListener());

    public ScenarioIOController(WeGoPlayerController mainController) {
        super(mainController);
        this.mainView = mainController.getMainView();
        this.menuView = mainController.getMenuView();
        this.toolBarView = mainController.getToolBarView();
        this.boardView = mainController.getBoardView();
        this.infoView = mainController.getInfoView();
        this.welcomeView = mainController.getWelcomeScreenView();
        this.oobView = mainController.getOobView();
        LOG.addHandler(mainController.getMessagesView().getHandler());

        //Add actions to the views
        CommandGroup group = AresCommandGroup.FILE;
        Action[] fileActions = {open, load, close, settings, exit};
        menuView.addActionButton(ComponentFactory.menu(group.getName(), group.getText(), group.getMnemonic(), fileActions));
        for (Action action : fileActions) {
            toolBarView.addActionButton(ComponentFactory.button(action));
        }
        Action[] welcomeActions = {open, load, settings, exit};
        for (Action action : welcomeActions) {
            welcomeView.addActionButton(ComponentFactory.translucidButton(action));
        }

    }

    private class OpenScenarioInteractor extends AsynchronousOperation<Scenario> {

        @Override
        protected Scenario performOperation() throws Exception {

            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(AresIO.ARES_IO.getAbsolutePath(ResourcePath.SCENARIOS.getPath()).toFile());
            fc.setFileFilter(AresFileType.SCENARIO.getFileTypeFilter());
            int returnVal = fc.showOpenDialog(mainView.getContentPane());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                Container container = welcomeView.getContentPane();
                container.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                File file = fc.getSelectedFile();
                // Load scenario and equipment files
                ares.data.jaxb.Scenario scen = AresIO.ARES_IO.unmarshallJson(file, ares.data.jaxb.Scenario.class);
                File equipmentFile = AresIO.ARES_IO.getAbsolutePath(ResourcePath.EQUIPMENT.getPath(), "ToawEquipment" + AresFileType.EQUIPMENT.getFileExtension()).toFile();
                EquipmentDB eqp = AresIO.ARES_IO.unmarshallJson(equipmentFile, EquipmentDB.class);
                Scenario scenario = new Scenario(scen, eqp);
                container.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                StartScenarioPane startScenarioPane = new StartScenarioPane(scenario);
                UserRole userRole = startScenarioPane.showOptionDialog(container);
                if (userRole != null) {
                    mainController.setUserRole(userRole);
                    return scenario;
                }
            }
            return null;
        }

        @Override
        protected void onSuccess(Scenario scenario) {

            if (scenario != null) {
                Container container = boardView.getContentPane();
                container.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                // Show the menu bar and tool bar
                menuView.setVisible(true);
                toolBarView.setVisible(true);
                mainView.switchCard(AresPlayerGUI.PLAY_CARD);

                // Set the engine with the new scenario
                mainController.setScenario(scenario);

                // obtain the scenario model with the active userRole
                ScenarioModel scenarioModel = scenario.getModel(mainController.getUserRole());
                boardView.loadScenario(scenarioModel);
                // set main window title & show appropriate views
                mainView.setTitle("ARES   " + scenario.getName() + "   " + Clock.INSTANCE.toStringVerbose()
                        + "   Role: " + mainController.getUserRole());
                mainController.getEngine().activate();
                close.setEnabled(true);
                menuView.setVisible(true);
                toolBarView.setVisible(true);
                String scenInfo = scenario.getName() + "\n" + Clock.INSTANCE.toStringVerbose() + "\nRole: " + mainController.getUserRole();
                infoView.updateScenarioInfo(scenInfo);
                oobView.loadScenario(scenarioModel);
                System.gc();
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
            close.setEnabled(false);
            menuView.setVisible(false);
            toolBarView.setVisible(false);
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
    private class SettingsActionListener implements ActionListener {

        public SettingsActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }
}
