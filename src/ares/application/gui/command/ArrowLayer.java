package ares.application.gui.command;

import ares.engine.algorithms.pathfinding.Path;
import ares.engine.algorithms.pathfinding.Node;
import static ares.application.gui.command.ArrowType.CURRENT_ORDERS;
import static ares.application.gui.command.ArrowType.GIVING_ORDERS;
import ares.application.gui.AresGraphicsModel;
import ares.application.gui.AbstractImageLayer;
import ares.application.gui.AresGraphicsProfile;
import ares.application.gui.AresMiscGraphics;
import ares.application.io.AresIO;
import ares.scenario.board.Direction;
import ares.scenario.board.Directions;
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
    private Path activePath;
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
        if (activePath != null) {
            paintArrow(g2, activePath, ArrowType.GIVING_ORDERS);
        }
        g2.dispose();
    }

    /**
     * Paints complete arrow for the {@code path} passed as argument
     *
     * @param activePath
     */
    public void paintSelectedUnitArrow(Path activePath) {
        this.activePath = activePath;
        updateLayer();
    }

    /**
     * Paints complete arrows for the {@code plannedPaths} passed as argument
     *
     * @param path
     */
    public void paintFormationArrows(Collection<Path> plannedPaths) {
        this.plannedPaths = plannedPaths;
        updateLayer();
    }

    private void paintArrow(Graphics2D g2, Path path, ArrowType type) {
        // Paint the last segment of the arrow
        Node last = path.getLast();
        paintFinalArrowSegment(g2, last, last.getDirection(), type);
        // Paint the intermediary segments
        Node current;
        for (current = last.getPrev(); current.getPrev() != null; last = current, current = last.getPrev()) {
            Direction from = current.getDirection();
            Direction to = last.getDirection().getOpposite();
            paintArrowSegment(g2, current, EnumSet.of(from, to), type);
        }
        // Paint the final segment
        Direction to = last.getDirection().getOpposite();
        paintArrowSegment(g2, current, EnumSet.of(to), type);
    }

    /**
     * Paints a single arrow segment for the path {@code node} passed as
     * argument
     *
     * @param g2
     * @param node
     * @param direction
     * @param type
     */
    private void paintFinalArrowSegment(Graphics2D g2, Node node, Direction direction, ArrowType type) {
        BufferedImage arrowImage = null;
        AresGraphicsProfile profile = AresGraphicsModel.getProfile();
        Point coordinates = Directions.getDirections(direction.ordinal() + 25).getCoordinates();
        switch (type) {
            case GIVING_ORDERS:
                arrowImage = unitArrow.getImage(profile, coordinates, AresIO.ARES_IO);
                break;
            case CURRENT_ORDERS:
                arrowImage = formationArrow.getImage(profile, coordinates, AresIO.ARES_IO);
                break;
            default:
                throw new AssertionError("Assertion failed: unkown image profile " + this);
        }
        if (arrowImage == null) {
            return;
        }
        Tile tile = node.getTile();
        Point pos = AresGraphicsModel.tileToPixel(tile.getCoordinates());
        g2.drawImage(arrowImage, pos.x, pos.y, this);
        int cost = (int) node.getG();
        g2.drawString(Integer.toString(cost), pos.x + arrowImage.getWidth() / 2, pos.y + arrowImage.getHeight() / 2);
        repaint(pos.x, pos.y, arrowImage.getWidth(), arrowImage.getHeight());
    }

    /**
     * Paints a single arrow segment in the {@code tile} passed as argument,
     * using the graphic identified by the {@code index} passed
     *
     * @param tile the tile where to paint an Arrow
     * @param index the position of the arrow segment within the array of arrow
     * images
     */
    private void paintArrowSegment(Graphics2D g2, Node node, Set<Direction> directions, ArrowType type) {
        BufferedImage arrowImage = null;
        AresGraphicsProfile profile = AresGraphicsModel.getProfile();
        Point coordinates = Directions.getDirections(Direction.getBitmask(directions)).getCoordinates();
        switch (type) {
            case GIVING_ORDERS:
                arrowImage = unitArrow.getImage(profile, coordinates, AresIO.ARES_IO);
                break;
            case CURRENT_ORDERS:
                arrowImage = formationArrow.getImage(profile, coordinates, AresIO.ARES_IO);
                break;
            default:
                throw new AssertionError("Assertion failed: unkown image profile " + this);
        }
        if (arrowImage == null) {
            return;
        }
        Tile tile = node.getTile();
        Point pos = AresGraphicsModel.tileToPixel(tile.getCoordinates());
        g2.drawImage(arrowImage, pos.x, pos.y, this);
        int cost = (int) node.getG();
        g2.drawString(Integer.toString(cost), pos.x + arrowImage.getWidth() / 2, pos.y + arrowImage.getHeight() / 2);
        repaint(pos.x, pos.y, arrowImage.getWidth(), arrowImage.getHeight());
    }
}
