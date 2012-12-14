package ares.application.controllers;

import ares.application.boundaries.view.BoardViewer;
import ares.application.boundaries.view.UnitInfoViewer;
import ares.application.models.board.BoardGraphicsModel;
import ares.engine.algorithms.routing.*;
import ares.platform.controllers.AbstractSecondaryController;
import ares.scenario.Scenario;
import ares.scenario.board.*;
import ares.scenario.forces.Unit;
import java.awt.Point;
import java.awt.event.*;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class BoardController extends AbstractSecondaryController {

    private PathFinder pathFinder = new AStar(BoardGraphicsModel.getTileRows() * BoardGraphicsModel.getTileColumns());
    private Tile selectedTile;
    private Unit selectedUnit;
    private final BoardViewer boardView;
    private final UnitInfoViewer unitView;

    public BoardController(BoardViewer boardView, UnitInfoViewer unitView, WeGoPlayerController wgpc) {
        super(wgpc);
        this.boardView = boardView;
        this.unitView = unitView;
    }

    Tile getSelectedTile() {
        return selectedTile;
    }

    MouseListener BoardMouseListener() {
        return new BoardMouseListener();
    }

    private class BoardMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent me) {
            Scenario scenario = mainController.getEngine().getScenario();
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
}
