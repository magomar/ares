package ares.application.controllers;

import ares.application.boundaries.view.BoardViewer;
import ares.application.boundaries.view.UnitInfoViewer;
import ares.application.models.board.BoardGraphicsModel;
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

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class BoardController extends AbstractSecondaryController implements PropertyChangeListener {

    private Tile selectedTile;
    private Unit selectedUnit;
    private final BoardViewer boardView;
    private final UnitInfoViewer unitView;
    private final PathFinder pathFinder;

    public BoardController(WeGoPlayerController mainController) {
        super(mainController);
        this.boardView = mainController.getBoardView();
        this.unitView = mainController.getUnitView();
        pathFinder = new AStar(BoardGraphicsModel.getTileRows() * BoardGraphicsModel.getTileColumns());

        boardView.addMouseListener(new BoardMouseListener());
    }

    Tile getSelectedTile() {
        return selectedTile;
    }

    private class BoardMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent me) {
            Scenario scenario = mainController.getScenario();
            Point pixel = new Point(me.getX(), me.getY());
            if (BoardGraphicsModel.isWithinImageRange(pixel) && me.getButton() == MouseEvent.BUTTON1) {
                Point tilePoint = BoardGraphicsModel.pixelToTileAccurate(pixel);
                // XXX pixel to tile conversion is more expensive than two coordinates checks
                if (!BoardGraphicsModel.validCoordinates(tilePoint.x, tilePoint.y)) {
                    return;
                }
                Tile tile = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
                boolean changeTile = !tile.equals(selectedTile);
                if (me.isShiftDown() && selectedUnit != null) {
                    boardView.updateArrowPath(scenario.getModel(mainController.getUserRole()), pathFinder.getPath(selectedUnit.getLocation(), tile));
                } else {
                    selectedTile = tile;
                    UnitsStack stack = tile.getUnitsStack();
                    if (stack.isEmpty()) {
                        selectedUnit = null;
                    } else {
                        selectedUnit = tile.getTopUnit();
                    }
                    boolean changeUnit = false;
                    if (!changeTile && !stack.isEmpty()) {
                        stack.next();
                        changeUnit = true;
                        selectedUnit = tile.getTopUnit();
                    }
                    if (changeTile || changeUnit) {
                        unitView.updateInfo(selectedTile.getModel(mainController.getUserRole()));
                        boardView.updateTile(selectedTile.getModel(mainController.getUserRole()));
                    }
                }
            } else if (me.getButton() == MouseEvent.BUTTON3) {
                selectedTile = null;
                unitView.clear();
            }
        }
    }

    private class BoardMouseMotionListener extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent me) {
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
                path.relink();
                boardView.updateArrowPath(scenario.getModel(mainController.getUserRole()), path);
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
