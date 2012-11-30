package ares.application.controllers;

import ares.application.boundaries.view.BoardViewer;
import ares.application.boundaries.view.MenuBarViewer;
import ares.application.boundaries.view.MessagesViewer;
import ares.application.boundaries.view.UnitInfoViewer;
import ares.application.commands.*;
import ares.application.models.ScenarioModel;
import ares.application.models.board.BoardGraphicsModel;
import ares.application.player.AresMenus;
import ares.application.views.*;
import ares.data.jaxb.EquipmentDB;
import ares.engine.realtime.*;
import ares.io.*;
import ares.platform.application.AbstractAresApplication;
import ares.platform.model.UserRole;
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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Mario
 */
public class WeGoPlayerController implements PropertyChangeListener {
    // Views

    private final AbstractAresApplication mainApplication;
    private final BoardViewer boardV;
    private final UnitInfoViewer unitV;
    private final MenuBarViewer menuV;
    private final MessagesViewer messagesV;
    // Entities (bussines logic), they interact with the model providers and provide models to the views
    private final RealTimeEngine engine;
    // Other fields
    private final ExecutorService executor;
    private UserRole userRole;
    private Tile selectedTile;
//    private Unit selectedUnit;
    private static final Logger LOG = Logger.getLogger(WeGoPlayerController.class.getName());

    public WeGoPlayerController(AbstractAresApplication mainApplication, BoardViewer boardV, UnitInfoViewer unitV, MenuBarViewer menuV, MessagesViewer messagesV) {
        executor = Executors.newCachedThreadPool();
        this.mainApplication = mainApplication;
        this.engine = new RealTimeEngine();
        this.boardV = boardV;
        this.unitV = unitV;
        this.menuV = menuV;
        this.messagesV = messagesV;
        //Add listeners to the views
        menuV.addActionListener(FileCommands.OPEN_SCENARIO.getName(), new OpenScenarioActionListener());
        menuV.addActionListener(FileCommands.CLOSE_SCENARIO.getName(), new CloseScenarioActionListener());
        menuV.addActionListener(FileCommands.EXIT.getName(), new ExitActionListener());
        menuV.addActionListener(EngineCommands.START.name(), new StartActionListener());
        menuV.addActionListener(EngineCommands.PAUSE.name(), new PauseActionListener());
        menuV.addActionListener(EngineCommands.NEXT.name(), new NextActionListener());
        boardV.addMouseListener(new BoardMouseListener());
        //Add change listeners to entities
        engine.addPropertyChangeListener(this);
        LOG.addHandler(new MessagesHandler(messagesV));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (RealTimeEngine.CLOCK_EVENT_PROPERTY.equals(evt.getPropertyName())) {
            ClockEvent clockEvent = (ClockEvent) evt.getNewValue();
            Scenario scenario = engine.getScenario();
            mainApplication.setTitle("ARES   " + scenario.getName() + "   " + scenario.getCalendar().toString()
                    + "   Role: " + userRole + "           Time: " + clockEvent.getClock().toString());

            boardV.updateScenario(engine.getScenarioModel(userRole));
            if (selectedTile != null) {
                unitV.updateInfo(selectedTile.getModel(userRole));
            }
            if (clockEvent.getEventTypes().contains(ClockEventType.TURN)) {
                menuV.setMenuElementEnabled(EngineCommands.PAUSE.getName(), false);
                menuV.setMenuElementEnabled(EngineCommands.NEXT.getName(), true);
            }
        }
    }

    private class OpenScenarioInteractor extends AsynchronousOperation<Scenario> {

        @Override
        protected Scenario performOperation() throws Exception {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(AresIO.ARES_IO.getAbsolutePath(AresPaths.SCENARIOS.getPath()).toFile());
            fc.setFileFilter(AresFileType.SCENARIO.getFileTypeFilter());
            int returnVal = fc.showOpenDialog(mainApplication.getMainFrame());
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
                boardV.loadScenario(scenarioModel);

                // set main window title & show appropriate views
                mainApplication.setTitle("ARES   " + scenario.getName() + "   " + scenario.getCalendar().toString() + "   Role: " + userRole);
                boardV.setVisible(true);
                unitV.setVisible(true);
                messagesV.setVisible(true);
                // Update menu bar
                menuV.setMenuElementEnabled(FileCommands.CLOSE_SCENARIO.getName(), true);
                menuV.setMenuElementEnabled(AresMenus.ENGINE_MENU.getName(), true);
                menuV.setMenuElementEnabled(EngineCommands.START.getName(), true);
                menuV.setMenuElementEnabled(EngineCommands.PAUSE.getName(), false);
                menuV.setMenuElementEnabled(EngineCommands.NEXT.getName(), false);
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
            menuV.setMenuElementEnabled(FileCommands.CLOSE_SCENARIO.getName(), false);
            menuV.setMenuElementEnabled(AresMenus.ENGINE_MENU.getName(), false);
            boardV.setVisible(false);
            boardV.closeScenario();
            messagesV.setVisible(false);
            unitV.setVisible(false);

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
            menuV.setMenuElementEnabled(EngineCommands.START.getName(), false);
            menuV.setMenuElementEnabled(EngineCommands.PAUSE.getName(), true);
        }
    }

    private class PauseActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            engine.stop();
            menuV.setMenuElementEnabled(EngineCommands.START.getName(), true);
            menuV.setMenuElementEnabled(EngineCommands.PAUSE.getName(), false);
        }
    }

    private class NextActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            menuV.setMenuElementEnabled(EngineCommands.PAUSE.getName(), true);
            menuV.setMenuElementEnabled(EngineCommands.NEXT.getName(), false);
            engine.start();
        }
    }

    private class BoardMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent me) {

            LOG.log(Level.INFO, me.toString());
            Scenario scenario = engine.getScenario();
            Point pixel = new Point(me.getX(), me.getY());
            if (BoardGraphicsModel.isWithinImageRange(pixel) && me.getButton() == MouseEvent.BUTTON1) {
                Point tilePoint = BoardGraphicsModel.pixelToTile(pixel);
                Tile tile = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
                boolean changeTile = !tile.equals(selectedTile);
                selectedTile = tile;
                UnitsStack stack = tile.getUnitsStack();
                boolean changeUnit = false;
                if (!changeTile && !stack.isEmpty()) {
                    stack.next();
                    changeUnit = true;
                }
                if (changeTile || changeUnit) {
                    unitV.updateInfo(selectedTile.getModel(userRole));
                    boardV.updateTile(selectedTile.getModel(userRole));
                }
            } else if (me.getButton() == MouseEvent.BUTTON3) {
                selectedTile = null;
                unitV.clear();
            }
        }
    }
}
