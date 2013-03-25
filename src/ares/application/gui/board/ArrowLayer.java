package ares.application.gui.board;

import ares.application.gui.graphics.ArrowType;
import static ares.application.gui.graphics.ArrowType.CURRENT_ORDERS;
import static ares.application.gui.graphics.ArrowType.GIVING_ORDERS;
import ares.application.gui.graphics.BoardGraphicsModel;
import ares.application.gui.AbstractImageLayer;
import ares.application.models.board.*;
import ares.engine.algorithms.routing.*;
import ares.platform.util.ImageTools;
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

    private SoftReference<BufferedImage> arrowImageUnit = new SoftReference<>(null);
    private SoftReference<BufferedImage> arrowImageFormation = new SoftReference<>(null);
    private final static Map<Integer, Point> imageIndexes = fillIndexMap();
    private Path currentPath;
    private Collection<Path> plannedPaths;

    public ArrowLayer(AbstractImageLayer parentLayer) {
        super(parentLayer);
    }

    @Override
    protected void updateLayer() {
        initialize();
        Graphics2D g2 = globalImage.createGraphics();
        if (plannedPaths != null) {
            for (Path path : plannedPaths) {
                paintArrow(g2, path, ArrowType.CURRENT_ORDERS);
            }
        }
        if (currentPath != null) {
            paintArrow(g2, currentPath, ArrowType.GIVING_ORDERS);
        }
        g2.dispose();
    }

    private void paintArrow(Graphics2D g2, Path path, ArrowType type) {
        // Paint the last segment of the arrow
        Node last = path.getLast();
        paintArrowSegment(g2, last.getTile(), getDirectionToImageIndex(last.getDirection()), type);
        // Paint the other segments
        for (Node current = last.getPrev(); current != null; last = current, current = last.getPrev()) {
            Direction from = current.getDirection();
            Direction to = last.getDirection().getOpposite();

            paintArrowSegment(g2, current.getTile(), getDirectionToImageIndex(from, to), type);
        }
    }

    /**
     * Paints a single arrow segment in the {@code tile} passed as argument, using the graphic identified by the
     * {@code index} passed
     *
     * @param tile the tile where to paint an Arrow
     * @param index the position of the arrow segment within the array of arrow images
     */
    private void paintArrowSegment(Graphics2D g2, Tile tile, Integer index, ArrowType type) {
        Point subImagePos = imageIndexes.get(index);
        if (subImagePos != null) {
            BufferedImage arrowImage = null;
            switch (type) {
                case GIVING_ORDERS:
                    if (arrowImageUnit.get() == null) {
                        arrowImageUnit = new SoftReference<>(ImageTools.loadImage(BoardGraphicsModel.getImageProfile().getArrowFile(type)));
                    }
                    arrowImage = arrowImageUnit.get();
                    break;
                case CURRENT_ORDERS:
                    if (arrowImageFormation.get() == null) {
                        arrowImageFormation = new SoftReference<>(ImageTools.loadImage(BoardGraphicsModel.getImageProfile().getArrowFile(type)));
                    }
                    arrowImage = arrowImageFormation.get();
                    break;
                default:
                    throw new AssertionError("Assertion failed: unkown image profile " + this);
            }

            // Y attribute contains number of columns, X contains rows
            Point subImagePixel = new Point(subImagePos.y * BoardGraphicsModel.getHexDiameter(), subImagePos.x * BoardGraphicsModel.getHexHeight());
            BufferedImage image = arrowImage.getSubimage(subImagePixel.x, subImagePixel.y, BoardGraphicsModel.getHexDiameter(), BoardGraphicsModel.getHexHeight());
            Point pos = BoardGraphicsModel.tileToPixel(tile.getCoordinates());
            g2.drawImage(image, pos.x, pos.y, null);
            repaint(pos.x, pos.y, BoardGraphicsModel.getHexDiameter(), BoardGraphicsModel.getHexHeight());
        }
    }

    /**
     * Paints complete arrow for the {@code path} passed as argument
     *
     * @param currentPath
     */
    public void paintArrow(Path currentPath) {
        this.currentPath = currentPath;
        updateLayer();
    }

    /**
     * Paints complete arrow for the {@code path} passed as argument
     *
     * @param path
     */
    public void paintArrows(Collection<Path> plannedPaths) {
        this.plannedPaths = plannedPaths;
        updateLayer();
    }
    /**
     *
     * @see TerrainLayer#getTerrainToImageIndex(ares.application.models.board.TileModel)
     */
//    public int getDirectionToImageIndex(Boolean directed, Direction from, Direction to) {
//        int index = 64 >>> from.ordinal();
//        index |= 64 >>> to.ordinal();
//        if (directed) {
//            index = ~index;
//            // from N/NE/SE to S/SW/NW
//            if (from.ordinal() < to.ordinal()) {
//                index &= 0x000000FF;
//            }
//        }
//        return index;
//    }
    private static final int[] arrowIndex = {76, 44, 108, 28, 92, 60};

    private int getDirectionToImageIndex(Direction to) {
        return arrowIndex[to.ordinal()];
    }

    private int getDirectionToImageIndex(Direction from, Direction to) {
        int index = 64 >>> from.ordinal();
        index |= 64 >>> to.ordinal();
        return index;
    }

    private static Map<Integer, Point> fillIndexMap() {
        Map<Integer, Point> map = new HashMap<>();
//        Integer[] indexArrray = {
//            65, 72, 68, 183, 66, 127,
//            33, 40, 36, 219, 34, 127,
//            96, 127, 127, 237, 127, 127,
//            17, 24, 20, -73, 18, 127,
//            80, 127, 127, -37, 127, 127,
//            48, 127, 127, -19, 127, 127,
//            127, 127, 127, 1, 127, 127,
//            9, 5, 12, 3, 10, 6
//        };
        Integer[] indexArrray = {
            64, 72, 68, 76, 66, 74,
            32, 40, 36, 44, 34, 42,
            96, 104, 100, 108, 98, 106,
            16, 24, 20, 28, 18, 26,
            80, 88, 84, 92, 82, 90,
            48, 56, 52, 60, 50, 58,
            112, 120, 116, 124, 114, 122,
            8, 4, 12, 2, 10, 6
        };
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
