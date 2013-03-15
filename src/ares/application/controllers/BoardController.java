package ares.application.controllers;

import ares.application.boundaries.view.BoardViewer;
import ares.application.boundaries.view.UnitInfoViewer;
import ares.application.interaction.InteractionMode;
import ares.application.models.ScenarioModel;
import ares.application.graphics.BoardGraphicsModel;
import ares.application.models.board.TileModel;
import ares.application.models.forces.FormationModel;
import ares.application.models.forces.UnitModel;
import ares.application.views.MessagesHandler;
import ares.engine.RealTimeEngine;
import ares.engine.algorithms.planning.Planner;
import ares.engine.algorithms.routing.*;
import ares.engine.command.Objective;
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

        pathFinder = new AStar(BoardGraphicsModel.getTileRows() * BoardGraphicsModel.getTileColumns());

        boardView.addMouseListener(new BoardMouseListener());
        boardView.addMouseMotionListener(new BoardMouseMotionListener());
    }

    private class BoardMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent me) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "Mouse clicked {0}", me.getButton());
            switch (me.getButton()) {
                case MouseEvent.BUTTON1:
                    clickMouseButton1(me.getX(), me.getY());
                    break;
                case MouseEvent.BUTTON2:
                    clickMouseButton2();
                    break;
                case MouseEvent.BUTTON3:
                    clickMouseButton3(me.getX(), me.getY());
                    break;
            }

        }
    }

    private void clickMouseButton1(int x, int y) {
        if (BoardGraphicsModel.isWithinImageRange(x, y)) {
            Point tilePoint = BoardGraphicsModel.pixelToTileAccurate(x, y);
            // XXX pixel to tile conversion is more expensive than two coordinates checks
            if (!BoardGraphicsModel.validCoordinates(tilePoint.x, tilePoint.y)) {
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
                if (selectedUnit != null) {// interactionMode = InteractionMode.UNIT_ORDERS;
                    UnitModel unitModel = selectedUnit.getModel(role);
                    FormationModel formation = selectedUnit.getFormation().getModel(role);
                    ScenarioModel scenarioModel = mainController.getScenario().getModel(role);
                    boardView.updateSelectedUnit(unitModel, formation, scenarioModel);
                }

            }
        }
    }

    private void clickMouseButton2() {
        if (selectedTile != null) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "Deselecting");
            selectedTile = null;
            selectedUnit = null;
            interactionMode = InteractionMode.FREE;
            unitView.clear();
//            boardView.updateArrowPath(mainController.getScenario().getModel(mainController.getUserRole()), Path.NULL_PATH);
            boardView.updateArrowPath(mainController.getScenario().getModel(mainController.getUserRole()), null);
        }
    }

    private void clickMouseButton3(int x, int y) {
        if (interactionMode == InteractionMode.UNIT_ORDERS && BoardGraphicsModel.isWithinImageRange(x, y)) {
            Point tilePoint = BoardGraphicsModel.pixelToTileAccurate(x, y);
            if (!BoardGraphicsModel.validCoordinates(tilePoint.x, tilePoint.y)) {
                return;
            }
            Scenario scenario = mainController.getScenario();
            Tile objective = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);

            RealTimeEngine engine = mainController.getEngine();
            Planner planner = engine.getPlanner();
            planner.tacticalPlan(selectedUnit, new Objective(objective, 0));
//            Path path = engine.getPathFinder().getPath(selectedUnit.getLocation(), objective);
//            if (path != null && path.relink() != -1) {
//                LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "New path for {0}: {1}", new Object[]{selectedUnit.getName(), path.toString()});
//                TacticalMission mission = selectedUnit.getMission();
//                MoveAction moveAction = new SurfaceMoveAction(selectedUnit, ActionType.TACTICAL_MARCH, path);
//                mission.pushAction(moveAction);
//                if (!moveAction.checkPreconditions()) {
//                    mission.pushAction(new ChangeDeploymentAction(selectedUnit, ActionType.ASSEMBLE));
//                }
//                clickMouseButton2();
//            } else {
//                LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "No path found for {0}", selectedUnit.getName());
//            }
        }
    }

    private class BoardMouseMotionListener extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent me) {
            if (interactionMode == InteractionMode.UNIT_ORDERS) {
                Scenario scenario = mainController.getScenario();
                Point pixel = new Point(me.getX(), me.getY());
                if (BoardGraphicsModel.isWithinImageRange(pixel)) {
                    Point tilePoint = BoardGraphicsModel.pixelToTileAccurate(pixel);
                    // XXX pixel to tile conversion is more expensive than two coordinates checks
                    if (!BoardGraphicsModel.validCoordinates(tilePoint.x, tilePoint.y)) {
                        return;
                    }
                    Tile tile = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
                    Path path = pathFinder.getPath(selectedUnit.getLocation(), tile);
                    if (path == null || path.isEmpty()) {
                        return;
                    }
                    boardView.updateArrowPath(scenario.getModel(mainController.getUserRole()), path);
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (RealTimeEngine.CLOCK_EVENT_PROPERTY.equals(evt.getPropertyName())) {
            if (selectedTile != null) {
                unitView.updateInfo(selectedTile.getModel(mainController.getUserRole()));
            }
        }
    }
}
