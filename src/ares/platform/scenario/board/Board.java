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
 *
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
    private Tile[][] map;
    private Map<UserRole, BoardModel> models;
    private List<Place> places;

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
            map[x][y].initialize(getNeighbors(map[x][y]), forces[cell.getOwner()], scenario);
        }

        models = new HashMap<>();
        models.put(UserRole.GOD, new BoardModel(this, UserRole.GOD));
        for (Force f : scenario.getForces()) {
            models.put(UserRole.getForceRole(f), new BoardModel(this, UserRole.getForceRole(f)));
        }
    }

    /**
     * @param from
     * @param dir
     * @return neighbor tile in a given dir
     */
    public Tile getNeighbor(Tile from, Direction dir) {
        Point coord = from.getCoordinates();
        int x = coord.x + dir.getIncColumn();
        int y = coord.y + (coord.x % 2 == 0 ? dir.getIncRowEven() : dir.getIncRowOdd());
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return map[x][y];
        } else {
            return null;
        }
    }

    /**
     *
     * @param from
     * @return a list of all adjacent neighbors
     */
    public Map<Direction, Tile> getNeighbors(Tile from) {
        Map<Direction, Tile> neighbors = new EnumMap<>(Direction.class);
        Point coord = from.getCoordinates();
        for (Direction dir : Direction.DIRECTIONS) {
            int x = coord.x + dir.getIncColumn();
            int y = coord.y + (coord.x % 2 == 0 ? dir.getIncRowEven() : dir.getIncRowOdd());
            if (x >= 0 && x < width && y >= 0 && y < height) {
                neighbors.put(dir, map[x][y]);
            }
        }
        return neighbors;
    }

    /**
     * Direction between to neighbor tiles
     *
     * @param from
     * @param to
     * @return
     */
    public static Direction getDirBetween(Tile from, Tile to) {
        int incX = to.getCoordinates().x - from.getCoordinates().x;
        int incY = to.getCoordinates().y - from.getCoordinates().y;
        if (from.getCoordinates().x % 2 == 0) {
            for (Direction dir : Direction.values()) {
                if (dir.getIncColumn()== incX && dir.getIncRowEven() == incY) {
                    return dir;
                }
            }
        } else {
            for (Direction dir : Direction.values()) {
                if (dir.getIncColumn()== incX && dir.getIncRowOdd() == incY) {
                    return dir;
                }
            }
        }
        return null;
    }

    public int getHeight() {
        return height;
    }

    public Tile[][] getMap() {
        return map;
    }

    public Tile getTile(int x, int y) {
        return map[x][y];
    }

    public int getWidth() {
        return width;
    }

    public List<Place> getPlaces() {
        return places;
    }

    @Override
    public BoardModel getModel(UserRole role) {
        return models.get(role);
    }
}
