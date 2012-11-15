package ares.application.controllers;

import ares.application.commands.EngineCommands;
import ares.application.commands.FileCommands;
import ares.application.models.ScenarioModel;
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
import ares.platform.model.UserRole;
import ares.platform.model.UserRoleType;
import ares.platform.view.InternalFrameView;
import ares.scenario.Scenario;
import ares.scenario.forces.Force;
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
    private final RealTimeEngine engine;
    private final UserRole userRole;

    public WeGoPlayerController(AbstractAresApplication mainApplication) {
        this.mainApplication = mainApplication;
        executor = Executors.newCachedThreadPool();
        engine = new RealTimeEngine();
        userRole = UserRole.GOD;
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
//        throw new UnsupportedOperationException("Not supported yet.");
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
                // set the engine with the new scenario
                engine.setScenario(scenario);
                // set the user role
                userRole.setForce(scenario.getForces()[0]);
                userRole.setRoleType(UserRoleType.FORCE);
                // obtain the scenario model with the active userRole
                ScenarioModel scenarioModel = engine.getScenarioModel(userRole);

                InternalFrameView<BoardView> boardFrame = getInternalFrameView(BoardView.class);
                boardFrame.getView().initializeBoard(scenarioModel);
                
                // set main window title to scenario name and calendar
                mainApplication.setTitle(scenario.getName() + "   " + scenario.getCalendar().toString());
                
                // show info frame
                InternalFrameView<UnitInfoView> infoFrame = getInternalFrameView(UnitInfoView.class);
                infoFrame.show();

                // show messagesView
                InternalFrameView<MessagesView> messagesFrame = getInternalFrameView(MessagesView.class);
                messagesFrame.show();

                // show board frame
                boardFrame.show();
                
                
                MenuBarView menuBarView = getView(MenuBarView.class);
                menuBarView.setMenuElementEnabled(FileCommands.CLOSE_SCENARIO.getName(), true);
                menuBarView.setMenuElementEnabled(AresMenus.ENGINE_MENU.getName(), true);
                menuBarView.setMenuElementEnabled(EngineCommands.START.getName(), true);
                menuBarView.setMenuElementEnabled(EngineCommands.PAUSE.getName(), false);
                menuBarView.setMenuElementEnabled(EngineCommands.NEXT.getName(), false);
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
            menuBarView.setMenuElementEnabled(FileCommands.CLOSE_SCENARIO.getName(), false);
            menuBarView.setMenuElementEnabled(AresMenus.ENGINE_MENU.getName(), false);
            getInternalFrameView(BoardView.class).hide();
            getInternalFrameView(BoardView.class).hide();
            getInternalFrameView(MessagesView.class).hide();
            getInternalFrameView(UnitInfoView.class).hide();
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
            menuBarView.setMenuElementEnabled(EngineCommands.START.getName(), false);
            menuBarView.setMenuElementEnabled(EngineCommands.PAUSE.getName(), true);
        }
    }

    private class PauseActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            getModel(RealTimeEngine.class).stop();
            MenuBarView menuBarView = getView(MenuBarView.class);
            menuBarView.setMenuElementEnabled(EngineCommands.START.getName(), true);
            menuBarView.setMenuElementEnabled(EngineCommands.PAUSE.getName(), false);
        }
    }

    private class NextActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            MenuBarView menuBarView = getView(MenuBarView.class);
            menuBarView.setMenuElementEnabled(EngineCommands.PAUSE.getName(), true);
            menuBarView.setMenuElementEnabled(EngineCommands.NEXT.getName(), false);
            getModel(RealTimeEngine.class).start();
        }
    }
}
