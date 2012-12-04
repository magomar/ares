package ares.application.controllers;

import ares.application.boundaries.view.*;
import ares.application.commands.*;
import ares.application.models.ScenarioModel;
import ares.application.models.board.BoardGraphicsModel;
import ares.application.models.forces.UnitModel;
import ares.application.player.AresMenus;
import ares.application.views.*;
import ares.data.jaxb.EquipmentDB;
import ares.engine.algorithms.routing.*;
import ares.engine.realtime.*;
import ares.io.*;
import ares.platform.application.AbstractAresApplication;
import ares.platform.model.UserRole;
import ares.platform.view.InternalFrameView;
import ares.scenario.Scenario;
import ares.scenario.board.*;
import ares.scenario.forces.Force;
import java.awt.Point;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.concurrent.*;
import java.util.logging.*;
import javax.swing.*;

/**
 *
 * @author Mario
 */
public class WeGoPlayerController implements PropertyChangeListener {
    // Views

    private final AbstractAresApplication mainView;
    private final BoardViewer boardView;
    private final UnitInfoViewer unitView;
    private final MenuBarViewer menuView;
    private final MessagesViewer messagesView;
    // Entities (bussines logic), they interact with the model providers and provide models to the views
    private final RealTimeEngine engine;
    // Other fields
    private final ExecutorService executor;
    private UserRole userRole;
    private Tile selectedTile;
    private UnitModel selectedUnit;
    private static final Logger LOG = Logger.getLogger(WeGoPlayerController.class.getName());
    private PathFinder pathFinder;

    public WeGoPlayerController(AbstractAresApplication mainView, BoardViewer boardView, UnitInfoViewer unitView, MenuBarViewer menuView, MessagesViewer messagesView) {
        executor = Executors.newCachedThreadPool();
        this.mainView = mainView;
        this.engine = new RealTimeEngine();
        this.boardView = boardView;
        this.unitView = unitView;
        this.menuView = menuView;
        this.messagesView = messagesView;

        //Add listeners to the views
        menuView.addActionListener(FileCommands.OPEN_SCENARIO.getName(), new OpenScenarioActionListener());
        menuView.addActionListener(FileCommands.CLOSE_SCENARIO.getName(), new CloseScenarioActionListener());
        menuView.addActionListener(FileCommands.EXIT.getName(), new ExitActionListener());
        menuView.addActionListener(EngineCommands.START.name(), new StartActionListener());
        menuView.addActionListener(EngineCommands.PAUSE.name(), new PauseActionListener());
        menuView.addActionListener(EngineCommands.NEXT.name(), new NextActionListener());
        boardView.addMouseListener(new BoardMouseListener());
        
        //Add change listeners to entities
        //TODO param this?
        engine.addPropertyChangeListener(this);
        LOG.addHandler(new MessagesHandler(messagesView));

        //Initialize views
        mainView.setMainPaneVisible(false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (RealTimeEngine.CLOCK_EVENT_PROPERTY.equals(evt.getPropertyName())) {
            ClockEvent clockEvent = (ClockEvent) evt.getNewValue();
            Scenario scenario = engine.getScenario();
            mainView.setTitle("ARES   " + scenario.getName() + "   " + scenario.getCalendar().toString()
                    + "   Role: " + userRole + "           Time: " + clockEvent.getClock().toString());

            boardView.updateScenario(engine.getScenarioModel(userRole));
            if (selectedTile != null) {
                unitView.updateInfo(selectedTile.getModel(userRole));
            }
            if (clockEvent.getEventTypes().contains(ClockEventType.TURN)) {
                menuView.setMenuElementEnabled(EngineCommands.PAUSE.getName(), false);
                menuView.setMenuElementEnabled(EngineCommands.NEXT.getName(), true);
            }
        }
    }

    private class OpenScenarioInteractor extends AsynchronousOperation<Scenario> {

        @Override
        protected Scenario performOperation() throws Exception {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(AresIO.ARES_IO.getAbsolutePath(AresPaths.SCENARIOS.getPath()).toFile());
            fc.setFileFilter(AresFileType.SCENARIO.getFileTypeFilter());
            int returnVal = fc.showOpenDialog(mainView.getContentPane());
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

                int n = JOptionPane.showOptionDialog(mainView.getContentPane(),
                        "Please select a user role",
                        "Select your role",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);
                if (n >= 0) {
                    userRole = options[n];
                    pathFinder = new AStar(scenario.getBoard().getMap().length);
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
                boardView.loadScenario(scenarioModel);

                // set main window title & show appropriate views
                mainView.setTitle("ARES   " + scenario.getName() + "   " + scenario.getCalendar().toString() + "   Role: " + userRole);
//                boardView.setVisible(true);
//                unitView.setVisible(true);
//                messagesView.setVisible(true);
                mainView.setMainPaneVisible(true);
                // Update menu bar
                menuView.setMenuElementEnabled(FileCommands.CLOSE_SCENARIO.getName(), true);
                menuView.setMenuElementEnabled(AresMenus.ENGINE_MENU.getName(), true);
                menuView.setMenuElementEnabled(EngineCommands.START.getName(), true);
                menuView.setMenuElementEnabled(EngineCommands.PAUSE.getName(), false);
                menuView.setMenuElementEnabled(EngineCommands.NEXT.getName(), false);
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
            menuView.setMenuElementEnabled(FileCommands.CLOSE_SCENARIO.getName(), false);
            menuView.setMenuElementEnabled(AresMenus.ENGINE_MENU.getName(), false);
            boardView.setVisible(false);
            boardView.closeScenario();
            messagesView.setVisible(false);
            unitView.setVisible(false);

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
            menuView.setMenuElementEnabled(EngineCommands.START.getName(), false);
            menuView.setMenuElementEnabled(EngineCommands.PAUSE.getName(), true);
        }
    }

    private class PauseActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            engine.stop();
            menuView.setMenuElementEnabled(EngineCommands.START.getName(), true);
            menuView.setMenuElementEnabled(EngineCommands.PAUSE.getName(), false);
        }
    }

    private class NextActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            menuView.setMenuElementEnabled(EngineCommands.PAUSE.getName(), true);
            menuView.setMenuElementEnabled(EngineCommands.NEXT.getName(), false);
            engine.start();
        }
    }

    private class BoardMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent me) {

            Scenario scenario = engine.getScenario();
            Point pixel = new Point(me.getX(), me.getY());
            if (BoardGraphicsModel.isWithinImageRange(pixel) && me.getButton() == MouseEvent.BUTTON1) {
                Point tilePoint = BoardGraphicsModel.pixelToTileAccurate(pixel);
                // pixel to tile conversion is more expensive than two coordinates checks
                if (!BoardGraphicsModel.validCoordinates(tilePoint.x, tilePoint.y))
                    return;
                Tile tile = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
                boolean changeTile = !tile.equals(selectedTile);
                if (me.isShiftDown() && selectedUnit != null) {
                    //LOG.log(Level.INFO, (p==null) ? "null path" : p.toString());
                    boardView.updateArrowPath(scenario.getModel(userRole), pathFinder.getPath(selectedUnit.getLocation(), tile.getModel(userRole)));
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
                        unitView.updateInfo(selectedTile.getModel(userRole));
                        boardView.updateTile(selectedTile.getModel(userRole));
                    }
                }
            } else if (me.getButton() == MouseEvent.BUTTON3) {
                selectedTile = null;
                unitView.clear();
            }
        }
    }
}
