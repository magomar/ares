package ares.application.controllers;

import ares.application.commands.EngineCommands;
import ares.application.commands.FileCommands;
import ares.application.player.AresMenus;
import ares.application.views.BoardView;
import ares.application.views.MenuBarView;
import ares.application.views.MessagesView;
import ares.application.views.UnitInfoView;
import ares.data.jaxb.EquipmentDB;
import ares.engine.realtime.RealTimeEngine;
import ares.io.AresFileType;
import ares.io.AresIO;
import ares.io.AresPaths;
import ares.platform.application.AbstractAresApplication;
import ares.platform.controller.AbstractController;
import ares.platform.view.InternalFrameView;
import ares.scenario.Scenario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Mario
 */
public class WeGoPlayerController extends AbstractController {

    private final ExecutorService executor;
    private final AbstractAresApplication mainApplication;

    public WeGoPlayerController(AbstractAresApplication mainApplication) {
        this.mainApplication = mainApplication;
        executor = Executors.newCachedThreadPool();
    }
    private static final Logger LOG = Logger.getLogger(WeGoPlayerController.class.getName());

    @Override
    protected void registerAllActionListeners() {
        MenuBarView menuBarView = getView(MenuBarView.class);
        menuBarView.addActionListener(FileCommands.OPEN_SCENARIO.getName(), new OpenScenarioActionListener());
        menuBarView.addActionListener(FileCommands.CLOSE_SCENARIO.getName(), new CloseScenarioActionListener());
        menuBarView.addActionListener(FileCommands.EXIT.getName(), new ExitActionListener());
        menuBarView.addActionListener(EngineCommands.START.name(), new StartActionListener());
        menuBarView.addActionListener(EngineCommands.PAUSE.name(), new PauseActionListener());
        menuBarView.addActionListener(EngineCommands.NEXT.name(), new NextActionListener());
    }

    @Override
    protected void registerAllModels() {
        getModel(RealTimeEngine.class).addPropertyChangeListener(this);
    }

    private class OpenScenarioInteractor extends AsynchronousOperation<Scenario> {

        @Override
        protected Scenario performOperation() throws Exception {
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
                return new Scenario(scen, eqp);
            } else {
                return null;
            }
        }

        @Override
        protected void onSuccess(Scenario scenario) {
            if (scenario != null) {
                // change the RealTimeEngine model, set the Scenario property
                getModel(RealTimeEngine.class).setScenario(scenario);

                mainApplication.setTitle(scenario.getName() + "   " + scenario.getCalendar().toString());

                // show info frame
                InternalFrameView<UnitInfoView> infoFrame = getInternalFrameView(UnitInfoView.class);
                infoFrame.show();

                // show messagesView
                InternalFrameView<MessagesView> messagesFrame = getInternalFrameView(MessagesView.class);
                messagesFrame.show();

                // show board frame
                InternalFrameView<BoardView> boardFrame = getInternalFrameView(BoardView.class);
                boardFrame.show();

                MenuBarView menuBarView = getView(MenuBarView.class);
                menuBarView.getMenuElement(FileCommands.CLOSE_SCENARIO.getName()).getComponent().setEnabled(true);
                menuBarView.getMenuElement(AresMenus.ENGINE_MENU.getName()).getComponent().setEnabled(true);
                menuBarView.getMenuElement(EngineCommands.START.getName()).getComponent().setEnabled(true);
                menuBarView.getMenuElement(EngineCommands.PAUSE.getName()).getComponent().setEnabled(false);
                menuBarView.getMenuElement(EngineCommands.NEXT.getName()).getComponent().setEnabled(false);
            }
        }
    }

    private class OpenScenarioActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            executor.execute(new OpenScenarioInteractor());

        }
    }

private class CloseScenarioActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        LOG.log(Level.INFO, e.toString());
        getModel(RealTimeEngine.class).setScenario(null);
        MenuBarView menuBarView = getView(MenuBarView.class);
        menuBarView.getMenuElement(FileCommands.CLOSE_SCENARIO.getName()).getComponent().setEnabled(false);
        menuBarView.getMenuElement(AresMenus.ENGINE_MENU.getName()).getComponent().setEnabled(false);
        getInternalFrameView(BoardView.class).getContentPane().setVisible(false);
        getInternalFrameView(MessagesView.class).getContentPane().setVisible(false);
        getInternalFrameView(UnitInfoView.class).getContentPane().setVisible(false);
    }
}

private class ExitActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        LOG.log(Level.INFO, e.toString());
        System.exit(0);
    }
}

private class StartActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        LOG.log(Level.INFO, e.toString());
        getModel(RealTimeEngine.class).start();
        MenuBarView menuBarView = getView(MenuBarView.class);
        menuBarView.getMenuElement(EngineCommands.START.getName()).getComponent().setEnabled(false);
        menuBarView.getMenuElement(EngineCommands.PAUSE.getName()).getComponent().setEnabled(true);
//            menuBarView.getMenuElement(EngineCommands.NEXT.getName()).getComponent().setEnabled(false);
    }
}

private class PauseActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        LOG.log(Level.INFO, e.toString());
        getModel(RealTimeEngine.class).stop();
        MenuBarView menuBarView = getView(MenuBarView.class);
        menuBarView.getMenuElement(EngineCommands.START.getName()).getComponent().setEnabled(true);
        menuBarView.getMenuElement(EngineCommands.PAUSE.getName()).getComponent().setEnabled(false);
//            menuBarView.getMenuElement(EngineCommands.NEXT.getName()).getComponent().setEnabled(false);
    }
}

private class NextActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        LOG.log(Level.INFO, e.toString());
        MenuBarView menuBarView = getView(MenuBarView.class);
//            menuBarView.getMenuElement(EngineCommands.START.getName()).getComponent().setEnabled(true);
        menuBarView.getMenuElement(EngineCommands.PAUSE.getName()).getComponent().setEnabled(true);
        menuBarView.getMenuElement(EngineCommands.NEXT.getName()).getComponent().setEnabled(false);
        getModel(RealTimeEngine.class).start();
    }
}
}
