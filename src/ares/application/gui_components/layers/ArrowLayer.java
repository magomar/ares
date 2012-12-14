package ares.application.gui_components.layers;

import ares.application.models.ScenarioModel;
import ares.application.models.board.*;
import ares.engine.algorithms.routing.*;
import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;
import java.util.*;

/**
 * Draws the movement arrows on a BufferedImage
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class ArrowLayer extends AbstractImageLayer {

    private SoftReference<BufferedImage> arrowImage = new SoftReference<>(null);
    private final static Map<Integer, Point> imageIndexes = fillIndexMap();
    private Path lastPath;

    public ArrowLayer(AbstractImageLayer ail) {
        super(ail);
    }

    @Override
    public void initialize(ScenarioModel s) {
    }

    public void paintArrow(Path path) {
        if (path == null) {
            //TODO set mouse icon to X or something
            return;
        }
        Node last = path.getLast();
        // Paint the arrow
        path.toString();
        Direction from = last.getDirection();
        Direction to = from.getOpposite();
        paintTile(last.getTile(), getDirectionToImageIndex(true, from, to));
        // paint the body
        for (Node current = last.getPrev(); current != null; last = current, current = last.getPrev()) {
            from = current.getDirection();
            to = last.getDirection().getOpposite();
            paintTile(current.getTile(), getDirectionToImageIndex(false, from, to));
        }

    }

    public void paintTile(Tile tile, Integer index) {
        Point subImagePos = imageIndexes.get(index);
        if (subImagePos != null) {
            Graphics2D g2 = parentLayer.globalImage.createGraphics();

            if (arrowImage.get() == null) {
                arrowImage = new SoftReference<>(loadImage(BoardGraphicsModel.getImageProfile().getArrowFilename()));
            }
            // Y attribute contains number of columns, X contains rows
            Point subImagePixel = new Point(subImagePos.y * BoardGraphicsModel.getHexDiameter(), subImagePos.x * BoardGraphicsModel.getHexHeight());
            BufferedImage image = arrowImage.get().getSubimage(subImagePixel.x, subImagePixel.y, BoardGraphicsModel.getHexDiameter(), BoardGraphicsModel.getHexHeight());
            Point pos = BoardGraphicsModel.tileToPixel(tile.getCoordinates());
            g2.drawImage(image, pos.x, pos.y, null);
            repaint(pos.x, pos.y, BoardGraphicsModel.getHexDiameter(), BoardGraphicsModel.getHexHeight());
        }

    }

    /**
     *
     * @see TerrainLayer#getTerrainToImageIndex(ares.application.models.board.TileModel)
     */
    public int getDirectionToImageIndex(Boolean directed, Direction from, Direction to) {
        int index = 64 >>> from.ordinal();
        index |= 64 >>> to.ordinal();
        if (directed) {
            index = ~index;
            // from N/NE/SE to S/SW/NW
            if (from.ordinal() < to.ordinal()) {
                index &= 0x000000FF;
            }
        }
        return index;
    }

    @Override
    protected void createGlobalImage(ScenarioModel s) {
    }

    @Override
    public void paintTile(TileModel t) {
    }

    @Override
    public void paintComponent(Graphics g) {
        parentLayer.paintComponent(g);
    }

    private static Map<Integer, Point> fillIndexMap() {
        Map<Integer, Point> map = new HashMap<>();
        Integer[] indexArrray = {65, 72, 68, 183, 66, 127,
            33, 40, 36, 219, 34, 127,
            96, 127, 127, 237, 127, 127,
            17, 24, 20, -73, 18, 127,
            80, 127, 127, -37, 127, 127,
            48, 127, 127, -19, 127, 127,
            127, 127, 127, 1, 127, 127,
            9, 5, 12, 3, 10, 6,};
        int col = 0;
        int colMod = 6;
        int row = 0;
        int rowMod = 8;
        for (Integer i : indexArrray) {
            map.put(i, new Point(row, col));
            if (++col == colMod) {
                col = 0;
                if (++row == rowMod) {
                    row = 0;
                }
            }
        }

        return map;
    }
}
