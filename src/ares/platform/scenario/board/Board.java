package ares.platform.scenario.board;

import ares.application.shared.models.board.BoardModel;
import ares.data.wrappers.scenario.Cell;
import ares.data.wrappers.scenario.Place;
import ares.platform.model.ModelProvider;
import ares.platform.model.UserRole;
import ares.platform.scenario.Scenario;
import ares.platform.scenario.forces.Force;

import java.awt.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public final class Board implements ModelProvider<BoardModel> {

    /*
     * Width of the board, ie number of tiles per row
     */
    private final int width;
    /*
     * Height of the board, ie number of tiles per column
     */
    private final int height;
    /**
     * All the tiles of map, represented as a bidimensional array of size width * height
     */
    private final Tile[][] map;
    private Map<UserRole, BoardModel> models;
    private final List<Place> places;

    public Board(ares.data.wrappers.scenario.Scenario scenario) {
        ares.data.wrappers.scenario.Map sourceMap = scenario.getMap();
        width = sourceMap.getMaxX() + 1;
        height = sourceMap.getMaxY() + 1;
        map = new Tile[width][height];
        for (Cell cell : sourceMap.getCell()) {
            map[cell.getX()][cell.getY()] = new Tile(cell);
        }
        places = sourceMap.getPlace();
    }

    public void initialize(ares.data.wrappers.scenario.Scenario scenarioXML, Scenario scenario, Force[] forces) {
        ares.data.wrappers.scenario.Map sourceMap = scenarioXML.getMap();
        for (Cell cell : sourceMap.getCell()) {
            int x = cell.getX();
            int y = cell.getY();
            map[x][y].initialize(obtainNeighbors(map[x][y]), forces[cell.getOwner()], scenario);
        }
        models = new HashMap<>();
        models.put(UserRole.GOD, new BoardModel(this, UserRole.GOD));
        for (Force f : scenario.getForces()) {
            models.put(UserRole.getForceRole(f), new BoardModel(this, UserRole.getForceRole(f)));
        }
    }

    /**
     * Gets all the tiles that are adjacent to (neighbor of) a given tile
     *
     * @param tile  a tile in the board
     * @return all neighbors (adjacent tiles) of the given {@code tile}
     */
    private Map<Direction, Tile> obtainNeighbors(Tile tile) {
        Map<Direction, Tile> neighbors = new EnumMap<>(Direction.class);
        for (Direction direction : Direction.DIRECTIONS) {
            Point p = direction.getNeighborCoordinates(tile.getCoordinates());
            // Note: Do not use method GraphicModel.INSTANCE.validCoordinates(...) here because it has not been initialized yet
            if (p.x >= 0 && p.x < width && p.y >= 0 && p.y < height)  {
                neighbors.put(direction, map[p.x][p.y]);
            }
        }
        return neighbors;
    }

    /**
     * Gets the height of this board in tiles (number of rows)
     *
     * @return the number of rows
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the width of this board in tiles (number of columns)
     *
     * @return the number of columns
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the entire map as an array of tiles
     *
     * @return
     */
    public Tile[][] getMap() {
        return map;
    }

    /**
     * Gets the tile in a particular location identified by its column and row
     *
     * @param x the column
     * @param y the row
     * @return
     */
    public Tile getTile(int x, int y) {
        return map[x][y];
    }

    public Tile getTile(Point coordinates) {
        return getTile(coordinates.x, coordinates.y);
    }

    /**
     * Gets all the places in the map
     * @return
     */
    public List<Place> getPlaces() {
        return places;
    }

    @Override
    public BoardModel getModel(UserRole role) {
        return models.get(role);
    }
}
