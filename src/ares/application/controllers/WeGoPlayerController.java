package ares.application.controllers;

import ares.application.commands.EngineCommands;
import ares.application.commands.FileCommands;
import ares.application.models.ScenarioModel;
import ares.application.models.board.BoardGraphicsModel;
import ares.application.models.board.TileModel;
import ares.application.models.forces.UnitModel;
import ares.application.player.AresMenus;
import ares.application.views.BoardView;
import ares.application.views.MenuBarView;
import ares.application.views.MessagesView;
import ares.application.views.UnitInfoView;
import ares.data.jaxb.EquipmentDB;
import ares.engine.realtime.ClockEvent;
import ares.engine.realtime.ClockEventType;
import ares.engine.realtime.RealTimeEngine;
import ares.io.AresFileType;
import ares.io.AresIO;
import ares.io.AresPaths;
import ares.platform.application.AbstractAresApplication;
import ares.platform.controller.AbstractController;
import ares.platform.model.UserRole;
import ares.platform.view.InternalFrameView;
import ares.scenario.Scenario;
import ares.scenario.board.KnowledgeLevel;
import ares.scenario.board.Tile;
import ares.scenario.board.UnitsStack;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
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
    private UserRole userRole;
//    private Tile selectedTile;
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
                userRole = UserRole.getForceRole(scenario.getForces()[1]);
                // obtain the scenario model with the active userRole
                ScenarioModel scenarioModel = engine.getScenarioModel(userRole);
                getInternalFrameView(BoardView.class).getView().loadScenario(scenarioModel);

                // set main window title & show appropriate views
                mainApplication.setTitle(scenario.getName() + "   " + scenario.getCalendar().toString());
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
            LOG.log(Level.INFO, me.toString());
            Scenario scenario = engine.getScenario();
            BoardGraphicsModel boardInfo = scenario.getBoardGraphicsModel();

            int i;
            int j;
            int ci = (int) Math.floor((double) me.getX() / (double) boardInfo.getHexOffset());
            int cx = me.getX() - boardInfo.getHexSide() * ci;
            int ty = me.getY() - ((ci + 1) % 2) * boardInfo.getHexHeight() / 2;
            int cj = (int) Math.floor((double) ty / (double) boardInfo.getHexHeight());
            int cy = ty - boardInfo.getHexHeight() * cj;

            if (cx > Math.abs(boardInfo.getHexRadius() / 2 - boardInfo.getHexRadius() * cy / boardInfo.getHexHeight())) {
                i = ci;
                j = cj;
            } else {
                i = ci - 1;
                j = cj + ((ci + 1) % 2) - ((cy < boardInfo.getHexHeight() / 2) ? 1 : 0);
            }
//            i = MathUtils.setBounds(i, 0, boardInfo.getWidth());
//            j = MathUtils.setBounds(j, 0, boardInfo.getHeight());
            if (boardInfo.validCoordinates(i, j)) {
                Tile tile = scenario.getBoard().getTile(i, j);
                TileModel tileModel = tile.getModel(userRole);
                if (tileModel.getKnowledgeLevel() != KnowledgeLevel.NONE) {
                    UnitsStack stack = tile.getUnitsStack();
                    if (!stack.isEmpty()) {
                        if (me.getButton() == MouseEvent.BUTTON1) {
                            stack.next();
                            UnitModel unit = stack.getPointOfInterest().getModel(userRole);
                            System.out.println("Selected unit:\n" + stack.getPointOfInterest().getName());
                            getInternalFrameView(UnitInfoView.class).getView().selectUnit(unit);
                            getInternalFrameView(BoardView.class).getView().updateTile(tileModel);
                        }
                    }
                }
            }
        }
    }
}
