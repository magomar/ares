package ares.application.controllers;

import ares.application.commands.FileCommands;
import ares.application.views.BoardView;
import ares.application.views.MenuBarView;
import ares.application.views.MessagesView;
import ares.application.views.UnitInfoView;
import ares.data.jaxb.EquipmentDB;
import ares.engine.realtime.RealTimeEngine;
import ares.io.AresFileType;
import ares.io.AresIO;
import ares.io.AresPaths;
import ares.scenario.Scenario;
import ares.platform.controller.AbstractController;
import ares.platform.view.InternalFrameView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class FileIOController extends AbstractController {

    private static final Logger LOG = Logger.getLogger(RealTimeEngineController.class.getName());

    @Override
    protected void registerAllActionListeners() {
        MenuBarView menuBarView = getView(MenuBarView.class);
        menuBarView.addActionListener(FileCommands.OPEN_SCENARIO.name(), new OpenScenarioActionListener());
        menuBarView.addActionListener(FileCommands.CLOSE_SCENARIO.name(), new CloseScenarioActionListener());
        menuBarView.addActionListener(FileCommands.EXIT.name(), new ExitActionListener());
    }

    @Override
    protected void registerAllModels() {
        getModel(RealTimeEngine.class).addPropertyChangeListener(this);
    }

    private class OpenScenarioActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(AresIO.ARES_IO.getAbsolutePath(AresPaths.SCENARIOS.getPath()).toFile());
            fc.setFileFilter(AresFileType.SCENARIO.getFileTypeFilter());
            int returnVal = fc.showOpenDialog(getView(MenuBarView.class).getContentPane());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                // Load scenario and equipment files
                ares.data.jaxb.Scenario scen = (ares.data.jaxb.Scenario) AresIO.ARES_IO.unmarshall(file);
                File equipmentFile = AresIO.ARES_IO.getAbsolutePath(AresPaths.EQUIPMENT.getPath(), "ToawEquipment" + AresFileType.EQUIPMENT.getFileExtension()).toFile();
                EquipmentDB eqp = (EquipmentDB) AresIO.ARES_IO.unmarshall(equipmentFile);
                Scenario scenario = new Scenario(scen, eqp);

                // change the RealTimeEngine model, set the Scenario property
                getModel(RealTimeEngine.class).setScenario(scenario);

                // show info frame
                InternalFrameView<UnitInfoView> infoFrame = (InternalFrameView<UnitInfoView>) getInternalFrameView(UnitInfoView.class);
                infoFrame.show();

                // show messagesView
                InternalFrameView<MessagesView> messagesFrame = (InternalFrameView<MessagesView>) getInternalFrameView(MessagesView.class);
                messagesFrame.show();

                // show board frame
                InternalFrameView<BoardView> boardFrame = (InternalFrameView<BoardView>) getInternalFrameView(BoardView.class);
                boardFrame.setTitle(scenario.getName() + "   " + scenario.getCalendar().toString());
                boardFrame.show();
                
                MenuBarView menuBarView = getView(MenuBarView.class);
//                JMenuItem openScenarioMenuItem = (JMenuItem) menuBarView.getComponent(FileCommands.OPEN_SCENARIO.name());
                menuBarView.getMenuElement(FileCommands.OPEN_SCENARIO.name()).getComponent().setEnabled(false);
//                JMenuItem closeScenarioMenuItem = (JMenuItem) menuBarView.getComponent(FileCommands.CLOSE_SCENARIO.name());
                menuBarView.getMenuElement(FileCommands.CLOSE_SCENARIO.name()).getComponent().setEnabled(true);
            }
        }
    }

    private class CloseScenarioActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            getModel(RealTimeEngine.class).setScenario(null);        MenuBarView menuBarView = getView(MenuBarView.class);
                menuBarView.getMenuElement(FileCommands.CLOSE_SCENARIO.name()).getComponent().setEnabled(false);
                menuBarView.getMenuElement(FileCommands.OPEN_SCENARIO.name()).getComponent().setEnabled(true);

        }
    }

    private class ExitActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            System.exit(0);
        }
    }

}
