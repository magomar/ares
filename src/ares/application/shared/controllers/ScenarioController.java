package ares.application.shared.controllers;

import ares.application.shared.commands.FileCommands;
import ares.application.shared.commands.AresCommandGroup;
import ares.application.shared.gui.components.StartScenarioPane;
import ares.application.shared.views.MessagesHandler;
import ares.data.jaxb.EquipmentDB;
import ares.platform.io.AresFileType;
import ares.platform.io.ResourcePath;
import ares.platform.model.UserRole;
import ares.platform.action.CommandAction;
import ares.platform.action.CommandGroup;
import ares.platform.io.FileIO;
import ares.application.shared.boundaries.interactors.ScenarioInteractor;
import ares.platform.action.ActionGroup;
import ares.platform.scenario.Scenario;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JFileChooser;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class ScenarioController implements ActionController {

    private static final Logger LOG = Logger.getLogger(ScenarioController.class.getName());
    private final Action open = new CommandAction(FileCommands.GAME_NEW, new OpenScenarioActionListener());
    private final Action load = new CommandAction(FileCommands.GAME_LOAD, new LoadScenarioActionListener());
    private final Action close = new CommandAction(FileCommands.GAME_CLOSE, new CloseScenarioActionListener(), false);
    private final Action exit = new CommandAction(FileCommands.EXIT, new ExitActionListener());
    private final Action settings = new CommandAction(FileCommands.SETTINGS, new SettingsActionListener());
    private final ScenarioInteractor interactor;
    private final ActionGroup actions;

    public ScenarioController(ScenarioInteractor interactor) {
        this.interactor = interactor;
        // create action groups
        close.setEnabled(false);
        Action[] fileActions = new Action[]{open, load, close, settings, exit};
        CommandGroup group = AresCommandGroup.FILE;
        actions = new ActionGroup(group.getName(), group.getText(), group.getMnemonic(), fileActions);
    }

    @Override
    public ActionGroup getActionGroup() {
        return actions;
    }

    private class OpenScenarioActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, e.toString());
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(ResourcePath.SCENARIOS.getFolderPath().toFile());
            fc.setFileFilter(AresFileType.SCENARIO.getFileTypeFilter());
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                Container container = interactor.getGUIContainer();
                container.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                File file = fc.getSelectedFile();
                // Load scenario and equipment files
                ares.data.jaxb.Scenario scen = FileIO.unmarshallJson(file, ares.data.jaxb.Scenario.class);
                File equipmentFile = ResourcePath.EQUIPMENT.getFile("ToawEquipment" + AresFileType.EQUIPMENT.getFileExtension());
                EquipmentDB eqp = FileIO.unmarshallJson(equipmentFile, EquipmentDB.class);
                Scenario scenario = new Scenario(scen, eqp);
                container.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                StartScenarioPane startScenarioPane = new StartScenarioPane(scenario, file);
                UserRole userRole = startScenarioPane.showOptionDialog(container);
                if (userRole == null) {
                    return;
                }
                scenario.setUserRole(userRole);
                close.setEnabled(true);
//                Container container = boardView.getContentPane();
                container.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                // tell listeners new scenario has been loaded
                interactor.newScenario(scenario);
            }
        }
    }

    private class CloseScenarioActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, e.toString());
            interactor.forgetScenario();
            close.setEnabled(false);
            System.gc();
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
