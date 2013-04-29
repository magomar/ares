package ares.application.controllers;

import ares.engine.algorithms.pathfinding.PathFinder;
import ares.engine.algorithms.pathfinding.Path;
import ares.engine.algorithms.pathfinding.AStar;
import ares.application.boundaries.view.BoardViewer;
import ares.application.boundaries.view.UnitInfoViewer;
import ares.application.gui.AresGraphicsModel;
import ares.application.interaction.InteractionMode;
import ares.application.models.ScenarioModel;
import ares.application.models.board.TileModel;
import ares.application.models.forces.FormationModel;
import ares.application.models.forces.UnitModel;
import ares.application.views.MessagesHandler;
import ares.engine.RealTimeEngine;
import ares.engine.action.Action;
import ares.engine.action.actions.MoveAction;
import ares.engine.algorithms.pathfinding.heuristics.DistanceCalculator;
import ares.engine.algorithms.pathfinding.heuristics.MinimunDistance;
import ares.engine.command.tactical.TacticalMission;
import ares.engine.command.tactical.TacticalMissionType;
import ares.platform.controllers.AbstractSecondaryController;
import ares.platform.model.UserRole;
import ares.scenario.Scenario;
import ares.scenario.board.*;
import ares.scenario.forces.Unit;
import java.awt.Point;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class BoardController extends AbstractSecondaryController implements PropertyChangeListener {

    private static final Logger LOG = Logger.getLogger(BoardController.class.getName());
    private final BoardViewer boardView;
    private final UnitInfoViewer unitView;
    private final PathFinder pathFinder;
    private Tile selectedTile;
    private Unit selectedUnit;
    private InteractionMode interactionMode = InteractionMode.FREE;

    public BoardController(WeGoPlayerController mainController) {
        super(mainController);
        this.boardView = mainController.getBoardView();
        this.unitView = mainController.getInfoView();
        LOG.addHandler(mainController.getMessagesView().getHandler());

        pathFinder = new AStar(new MinimunDistance(DistanceCalculator.DELTA));

        boardView.addMouseListener(new BoardMouseListener());
        boardView.addMouseMotionListener(new BoardMouseMotionListener());

        //Add change listeners to entities
        mainController.getEngine().addPropertyChangeListener(this);
    }

    private class BoardMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent me) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "Mouse clicked {0}", me.getButton());
            switch (me.getButton()) {
                case MouseEvent.BUTTON1:
                    select(me.getX(), me.getY());
                    break;
                case MouseEvent.BUTTON2:
                    deselect();
                    break;
                case MouseEvent.BUTTON3:
                    command(me.getX(), me.getY());
                    break;
            }

        }
    }

    private void select(int x, int y) {
        if (AresGraphicsModel.isWithinImageRange(x, y)) {
            Point tilePoint = AresGraphicsModel.pixelToTileAccurate(x, y);
            if (!AresGraphicsModel.validCoordinates(tilePoint.x, tilePoint.y)) {
                return;
            }
            Tile tile = mainController.getScenario().getBoard().getTile(tilePoint.x, tilePoint.y);
            UnitsStack stack = tile.getUnitsStack();

            boolean changeTile = !tile.equals(selectedTile);
            boolean changeUnit = false;
            if (changeTile) {
                selectedTile = tile;
                if (stack.isEmpty()) {
                    selectedUnit = null;
                    interactionMode = InteractionMode.FREE;
                    LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "New tile selected");
                } else {
                    selectedUnit = tile.getTopUnit();
                    interactionMode = InteractionMode.UNIT_ORDERS;
                    LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "New unit selected");
                }
            } else {
                if (stack.size() > 1) {
                    stack.next();
                    changeUnit = true;
                    selectedUnit = tile.getTopUnit();
                    LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "Next unit in stack selected");
                }
            }

            if (changeTile || changeUnit) {
                UserRole role = mainController.getUserRole();
                TileModel tileModel = selectedTile.getModel(role);
                unitView.updateInfo(tileModel);
                boardView.updateUnitStack(tileModel);
//                if (selectedUnit != null) {
                if (interactionMode == InteractionMode.UNIT_ORDERS) {
                    UnitModel unit = selectedUnit.getModel(role);
                    FormationModel formation = selectedUnit.getFormation().getModel(role);
                    boardView.updateSelectedUnit(unit, formation);
                    boardView.updateLastOrders(null);
                    boardView.updateCurrentOrders(null);
                }

            }
        }
    }

    private void deselect() {
        if (selectedTile != null) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "Deselecting");
            selectedTile = null;
            selectedUnit = null;
            interactionMode = InteractionMode.FREE;
            unitView.clear();
            boardView.updateCurrentOrders(null);
            boardView.updateLastOrders(null);
            // the order matters here, updateSelectedUnit must be done after updateCurrentOrders, or updateCurrentOrders will have no effect
            boardView.updateSelectedUnit(null, null);
        }
    }

    private void command(int x, int y) {
        if (interactionMode == InteractionMode.UNIT_ORDERS && AresGraphicsModel.isWithinImageRange(x, y)) {
            Point tilePoint = AresGraphicsModel.pixelToTileAccurate(x, y);
            if (!AresGraphicsModel.validCoordinates(tilePoint.x, tilePoint.y)) {
                return;
            }
            Scenario scenario = mainController.getScenario();
            Tile tile = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
            TacticalMission mission = TacticalMissionType.OCCUPY.getNewTacticalMission(selectedUnit, tile, pathFinder);
            selectedUnit.setMission(mission);
            selectedUnit.schedule();
            boardView.updateLastOrders(mission.getPath());
        }
    }

    private class BoardMouseMotionListener extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent me) {
            if (interactionMode == InteractionMode.UNIT_ORDERS) {
                Scenario scenario = mainController.getScenario();
                Point pixel = new Point(me.getX(), me.getY());
                if (AresGraphicsModel.isWithinImageRange(pixel)) {
                    Point tilePoint = AresGraphicsModel.pixelToTileAccurate(pixel);
                    if (!AresGraphicsModel.validCoordinates(tilePoint.x, tilePoint.y)) {
                        return;
                    }
                    Tile tile = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
                    Path path = pathFinder.getPath(selectedUnit.getLocation(), tile, selectedUnit);
                    if (path == null || path.size() < 2) {
                        return;
                    }
                    boardView.updateCurrentOrders(path);
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (RealTimeEngine.CLOCK_EVENT_PROPERTY.equals(evt.getPropertyName())) {
//            if (selectedTile != null) {
//                unitView.updateInfo(selectedTile.getModel(mainController.getUserRole()));
//            }
            deselect();
        }
    }
}
