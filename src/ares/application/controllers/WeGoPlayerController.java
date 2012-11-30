package ares.application.controllers;

import ares.application.commands.*;
import ares.application.models.ScenarioModel;
import ares.application.models.board.BoardGraphicsModel;
import ares.application.models.forces.UnitModel;
import ares.application.player.AresMenus;
import ares.application.views.*;
import ares.data.jaxb.EquipmentDB;
import ares.engine.algorithms.PathFinder;
import ares.engine.realtime.*;
import ares.io.*;
import ares.platform.application.AbstractAresApplication;
import ares.platform.controller.AbstractController;
import ares.platform.model.UserRole;
import ares.platform.view.InternalFrameView;
import ares.scenario.Scenario;
import ares.scenario.board.*;
import ares.scenario.forces.Force;
import java.awt.Point;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.concurrent.*;
import java.util.logging.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Mario
 */
public class WeGoPlayerController extends AbstractController {

    private final ExecutorService executor;
    private final AbstractAresApplication mainApplication;
    private final RealTimeEngine engine;
    private UserRole userRole;
    private Tile selectedTile;
    private UnitModel selectedUnit;
    private static final Logger LOG = Logger.getLogger(WeGoPlayerController.class.getName());

    public WeGoPlayerController(AbstractAresApplication mainApplication) {
        this.mainApplication = mainApplication;
        executor = Executors.newCachedThreadPool();
        engine = new RealTimeEngine();
    }

    @Override
    protected void registerAllActionListeners() {
        // MenuBarView
        MenuBarView menuBarView = getView(MenuBarView.class);
        menuBarView.addActionListener(FileCommands.OPEN_SCENARIO.getName(), new OpenScenarioActionListener());
        menuBarView.addActionListener(FileCommands.CLOSE_SCENARIO.getName(), new CloseScenarioActionListener());
        menuBarView.addActionListener(FileCommands.EXIT.getName(), new ExitActionListener());
        menuBarView.addActionListener(EngineCommands.START.name(), new StartActionListener());
        menuBarView.addActionListener(EngineCommands.PAUSE.name(), new PauseActionListener());
        menuBarView.addActionListener(EngineCommands.NEXT.name(), new NextActionListener());
        // UnitInfoView
        getInternalFrameView(BoardView.class).getView().getContentPane().addMouseListener(new BoardMouseListener());
        
        //Messages panel log
        LOG.addHandler(new MessagesHandler(getInternalFrameView(MessagesView.class).getView()));
    }

    @Override
    protected void registerAllModels() {
//        throw new UnsupportedOperationException("Not supported yet.");
        engine.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (RealTimeEngine.CLOCK_EVENT_PROPERTY.equals(evt.getPropertyName())) {
            ClockEvent clockEvent = (ClockEvent) evt.getNewValue();
            InternalFrameView<BoardView> boardFrame = getInternalFrameView(BoardView.class);
            boardFrame.setTitle("Time: " + clockEvent.getClock().toString());
            BoardView boardView = boardFrame.getView();
            boardView.updateScenario(engine.getScenarioModel(userRole));
            if (selectedTile != null) {
                getInternalFrameView(UnitInfoView.class).getView().updateInfo(selectedTile.getModel(userRole));
            }
            if (clockEvent.getEventTypes().contains(ClockEventType.TURN)) {
                MenuBarView menuBarView = getView(MenuBarView.class);
                menuBarView.setMenuElementEnabled(EngineCommands.PAUSE.getName(), false);
                menuBarView.setMenuElementEnabled(EngineCommands.NEXT.getName(), true);
            }
        }
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
                Scenario scenario = new Scenario(scen, eqp);
                // set the user role by asking (using a dialog window)
                Force[] forces = scenario.getForces();
                UserRole[] options = new UserRole[forces.length + 1];
                for (int i = 0; i < forces.length; i++) {
                    Force force = forces[i];
                    options[i] = UserRole.getForceRole(force);
                }
                options[forces.length] = UserRole.GOD;

                int n = JOptionPane.showOptionDialog(mainApplication.getMainFrame(),
                        "Please select a user role",
                        "Select your role",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);
                if(n>=0){
                    userRole = options[n];
                    return scenario;
                }
                return null;
            } else {
                return null;
            }
        }

        @Override
        protected void onSuccess(Scenario scenario) {
            if (scenario != null) {
                // set the engine with the new scenario
                engine.setScenario(scenario);

                // obtain the scenario model with the active userRole
                ScenarioModel scenarioModel = engine.getScenarioModel(userRole);
                getInternalFrameView(BoardView.class).getView().loadScenario(scenarioModel);

                // set main window title & show appropriate views
                mainApplication.setTitle("ARES   " + scenario.getName() + "   " + scenario.getCalendar().toString() + "   Role: " + userRole);
                getInternalFrameView(BoardView.class).show();
                getInternalFrameView(UnitInfoView.class).show();
                getInternalFrameView(MessagesView.class).show();
                // Update menu bar
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
            engine.setScenario(null);
            MenuBarView menuBarView = getView(MenuBarView.class);
            menuBarView.setMenuElementEnabled(FileCommands.CLOSE_SCENARIO.getName(), false);
            menuBarView.setMenuElementEnabled(AresMenus.ENGINE_MENU.getName(), false);
            getInternalFrameView(BoardView.class).hide();
            getInternalFrameView(BoardView.class).getView().closeScenario();
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
            engine.start();
            MenuBarView menuBarView = getView(MenuBarView.class);
            menuBarView.setMenuElementEnabled(EngineCommands.START.getName(), false);
            menuBarView.setMenuElementEnabled(EngineCommands.PAUSE.getName(), true);
        }
    }

    private class PauseActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            engine.stop();
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
            engine.start();
        }
    }

    private class BoardMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent me) {
            
            Scenario scenario = engine.getScenario();
            Point pixel = new Point(me.getX(), me.getY());
            if (BoardGraphicsModel.isWithinImageRange(pixel) && me.getButton() == MouseEvent.BUTTON1) {
                Point tilePoint = BoardGraphicsModel.pixelToTile(pixel);
                Tile tile = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
                boolean changeTile = !tile.equals(selectedTile);
                if (me.isShiftDown() && selectedUnit != null) {
                    LOG.log(Level.INFO, "Destination Tile: {0} | selectedUnit: {1}", new Object[]{tile.getCoordinates(), selectedUnit.getName()});
                    PathFinder pf = new PathFinder();
                    getInternalFrameView(BoardView.class).getView().updateArrowPath(pf.findArrowPath(selectedUnit.getLocation(),tile.getModel(userRole)));
                } else {
                    selectedTile = tile;
                    selectedUnit = tile.getModel(userRole).getTopUnit();
                    UnitsStack stack = tile.getUnitsStack();
                    boolean changeUnit = false;
                    if (!changeTile && !stack.isEmpty()) {
                        stack.next();
                        changeUnit = true;
                        selectedUnit = tile.getModel(userRole).getTopUnit();
                    }
                    if (changeTile || changeUnit) {
                        getInternalFrameView(UnitInfoView.class).getView().updateInfo(selectedTile.getModel(userRole));
                        getInternalFrameView(BoardView.class).getView().updateTile(selectedTile.getModel(userRole));
                    }
                }
            } else if (me.getButton() == MouseEvent.BUTTON3) {
                selectedTile = null;
                selectedUnit = null;
                getInternalFrameView(UnitInfoView.class).getView().clear();
            }
        }
    }
}
