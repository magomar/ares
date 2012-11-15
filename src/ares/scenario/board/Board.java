package ares.scenario.board;

import ares.application.models.board.BoardModel;
import ares.data.jaxb.Map.Cell;
import ares.platform.model.AbstractModelProvider;
import ares.platform.model.UserRole;
import ares.scenario.Scenario;
import ares.scenario.forces.Force;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public final class Board extends AbstractModelProvider<BoardModel> {

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
    
    private BoardModel boardModel;

    public Board(ares.data.jaxb.Scenario scenario) {
        ares.data.jaxb.Map sourceMap = scenario.getMap();
        width = sourceMap.getMaxX() + 1;
        height = sourceMap.getMaxY() + 1;

        map = new Tile[width][height];
        for (Cell cell : sourceMap.getCell()) {
            map[cell.getX()][cell.getY()] = new Tile(cell);
        }
        boardModel = new BoardModel(this);
    }

    public void initialize(ares.data.jaxb.Scenario scenarioXML, Scenario scenario, Force[] forces) {

        ares.data.jaxb.Map sourceMap = scenarioXML.getMap();
        for (Cell cell : sourceMap.getCell()) {
            int x = cell.getX();
            int y = cell.getY();
            map[x][y].initialize(getNeighbors(map[x][y]), forces[cell.getOwner()], scenario);
        }
    }

    /**
     * @param from
     * @param dir
     * @return neighbor tile in a given dir
     */
    public Tile getNeighbor(Tile from, Direction dir) {
        int x = from.getX() + dir.getIncI();
        int y = from.getY() + (from.getX() % 2 == 0 ? dir.getIncJEven() : dir.getIncJOdd());
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
        for (Direction dir : Direction.DIRECTIONS) {
            int x = from.getX() + dir.getIncI();
            int y = from.getY() + (from.getX() % 2 == 0 ? dir.getIncJEven() : dir.getIncJOdd());
            if (x >= 0 && x < width && y >= 0 && y < height) {
                neighbors.put(dir, map[x][y]);
            }
        }
        return neighbors;
    }

    public static Direction getDirBetween(Tile from, Tile to) {
        int incX = to.getX() - from.getX();
        int incY = to.getY() - from.getY();
        if (from.getX() % 2 == 0) {
            for (Direction dir : Direction.values()) {
                if (dir.getIncI() == incX && dir.getIncJEven() == incY) {
                    return dir;
                }
            }
        } else {
            for (Direction dir : Direction.values()) {
                if (dir.getIncI() == incX && dir.getIncJOdd() == incY) {
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

    private static void getDistanceInMetersBetween(Tile from, Tile to) {
        //TODO implement getDistanceInMetersBetween(Tile from, Tile to)
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static int getDistanceInTilesBetween(Tile from, Tile to) {
        // adapted from http://www-cs-students.stanford.edu/~amitp/Articles/HexLOS.html
        int x1 = from.getX();
        int y1 = from.getY();
        int x2 = to.getX();
        int y2 = to.getY();
        int ax = y1 - Ceil2(x1);
        int ay = y1 + Floor2(x1);
        int bx = y2 - Ceil2(x2);
        int by = y2 + Floor2(x2);
        int dx = bx - ax;
        int dy = by - ay;
        return Math.abs(dx) + Math.abs(dy);
    }

    private static int Floor2(int val) {
        return (val >> 1);
    }

    private static int Ceil2(int val) {
        return ((val + 1) >> 1);
    }

    @Override
    public BoardModel getModel(UserRole userRole) {
        return boardModel;
    }
    @Override
    public BoardModel getCompleteModel(){
        return boardModel;
    }
    
}
