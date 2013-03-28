package ares.application.graphics.command;

import static ares.application.graphics.command.ArrowType.CURRENT_ORDERS;
import static ares.application.graphics.command.ArrowType.GIVING_ORDERS;
import ares.application.graphics.AresGraphicsModel;
import ares.application.graphics.AbstractImageLayer;
import ares.application.graphics.AresGraphicsProfile;
import ares.application.graphics.AresMiscGraphics;
import ares.application.graphics.board.TerrainLayer;
import ares.engine.algorithms.routing.*;
import ares.io.AresIO;
import ares.scenario.board.Direction;
import ares.scenario.board.Terrain;
import ares.scenario.board.Tile;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Draws the movement arrows on a BufferedImage
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class ArrowLayer extends AbstractImageLayer {

    private final AresMiscGraphics unitArrow = AresMiscGraphics.RED_ARROWS;
    private final AresMiscGraphics formationArrow = AresMiscGraphics.GRAY_ARROWS;
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
        paintFinalArrowSegment(g2, last.getTile(), last.getDirection(), type);
        // Paint the other segments
        for (Node current = last.getPrev(); current != null; last = current, current = last.getPrev()) {
            Direction from = current.getDirection();
            Direction to = last.getDirection().getOpposite();
            paintArrowSegment(g2, current.getTile(), EnumSet.of(from, to), type);
        }
    }

    /**
     * Paints a single arrow segment in the {@code tile} passed as argument, using the graphic identified by the
     * {@code index} passed
     *
     * @param tile the tile where to paint an Arrow
     * @param index the position of the arrow segment within the array of arrow images
     */
    private void paintFinalArrowSegment(Graphics2D g2, Tile tile, Direction direction, ArrowType type) {
        BufferedImage arrowImage = null;
        AresGraphicsProfile profile = AresGraphicsModel.getProfile();
        int index = Terrain.getImageIndex(direction.ordinal()+25);
        switch (type) {
            case GIVING_ORDERS:
                arrowImage = unitArrow.getImage(profile, index, AresIO.ARES_IO);
                break;
            case CURRENT_ORDERS:
                arrowImage = formationArrow.getImage(profile, index, AresIO.ARES_IO);
                break;
            default:
                throw new AssertionError("Assertion failed: unkown image profile " + this);
        }
        if (arrowImage == null) {
            return;
        }
        Point pos = AresGraphicsModel.tileToPixel(tile.getCoordinates());
        g2.drawImage(arrowImage, pos.x, pos.y, null);
        repaint(pos.x, pos.y, arrowImage.getWidth(), arrowImage.getHeight());
    }
    
    /**
     * Paints a single arrow segment in the {@code tile} passed as argument, using the graphic identified by the
     * {@code index} passed
     *
     * @param tile the tile where to paint an Arrow
     * @param index the position of the arrow segment within the array of arrow images
     */
    private void paintArrowSegment(Graphics2D g2, Tile tile, Set<Direction> directions, ArrowType type) {
        BufferedImage arrowImage = null;
        AresGraphicsProfile profile = AresGraphicsModel.getProfile();
        int index = Terrain.getImageIndex(Direction.convertDirectionsToBitMask(directions));
        switch (type) {
            case GIVING_ORDERS:
                arrowImage = unitArrow.getImage(profile, index, AresIO.ARES_IO);
                break;
            case CURRENT_ORDERS:
                arrowImage = formationArrow.getImage(profile, index, AresIO.ARES_IO);
                break;
            default:
                throw new AssertionError("Assertion failed: unkown image profile " + this);
        }
        Point pos = AresGraphicsModel.tileToPixel(tile.getCoordinates());
        g2.drawImage(arrowImage, pos.x, pos.y, null);
        repaint(pos.x, pos.y, arrowImage.getWidth(), arrowImage.getHeight());
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
}
