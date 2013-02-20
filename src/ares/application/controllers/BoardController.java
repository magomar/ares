package ares.application.controllers;

import ares.application.boundaries.view.BoardViewer;
import ares.application.boundaries.view.UnitInfoViewer;
import ares.application.interaction.InteractionMode;
import ares.application.models.board.BoardGraphicsModel;
import ares.application.views.MessagesHandler;
import ares.engine.RealTimeEngine;
import ares.engine.algorithms.routing.*;
import ares.platform.controllers.AbstractSecondaryController;
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

    Tile getSelectedTile() {
        return selectedTile;
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
                    clickMouseButton2();
                    break;
            }

        }
    }

    private void clickMouseButton1(int x, int y) {
        if (BoardGraphicsModel.isWithinImageRange(x, y)) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "Clicked Button 1");
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
                    LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "New tile selected");
                } else {
                    selectedUnit = tile.getTopUnit();
                    LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "New unit selected");
                }
            } else {
                if (stack.size() > 1) {
                    LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "Next unit in stack selected");
                    stack.next();
                    changeUnit = true;
                    selectedUnit = tile.getTopUnit();
                }
            }
            if (changeTile || changeUnit) {
                unitView.updateInfo(selectedTile.getModel(mainController.getUserRole()));
                boardView.updateTile(selectedTile.getModel(mainController.getUserRole()));
            }
        }
    }

    private void clickMouseButton2() {
        if (selectedTile != null) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "Deselecting");
            selectedTile = null;
            unitView.clear();
            boardView.updateArrowPath(mainController.getScenario().getModel(mainController.getUserRole()), Path.NULL_PATH);
        }
    }

    private void clickMouseButton3() {
    }

    private class BoardMouseMotionListener extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent me) {
            if (selectedUnit != null) {
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
