package ares.application.gui.command;

import ares.engine.algorithms.pathfinding.Path;
import ares.engine.algorithms.pathfinding.Node;
import static ares.application.gui.command.ArrowType.PLANNED;
import static ares.application.gui.command.ArrowType.ACTIVE;
import ares.application.gui.AresGraphicsModel;
import ares.application.gui.AbstractImageLayer;
import ares.application.gui.AresGraphicsProfile;
import ares.application.gui.providers.AresMiscGraphics;
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

    private Path currentPath;
    private Path lastPath;
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
                paintArrow(g2, path, ArrowType.PLANNED);
            }
        }
        if (lastPath != null) {
            paintArrow(g2, lastPath, ArrowType.LAST);
        }
        if (currentPath != null) {
            paintArrow(g2, currentPath, ArrowType.ACTIVE);
        }
        g2.dispose();
    }

    /**
     * Paints complete arrow for the {@code path} passed as argument
     *
     * @param activePath
     */
    public void paintCurrentOrders(Path activePath) {
        this.currentPath = activePath;
        updateLayer();
    }
    
        /**
     * Paints complete arrow for the {@code path} passed as argument
     *
     * @param activePath
     */
    public void paintLastOrders(Path lastPath) {
        this.lastPath = lastPath;
        updateLayer();
    }

    /**
     * Paints complete arrows for the {@code plannedPaths} passed as argument
     *
     * @param path
     */
    public void paintPlannedOrders(Collection<Path> plannedPaths) {
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
     * Paints a single arrow segment for the path {@code node} passed as argument
     *
     * @param g2
     * @param node
     * @param direction
     * @param type
     */
    private void paintFinalArrowSegment(Graphics2D g2, Node node, Direction direction, ArrowType type) {
        AresGraphicsProfile profile = AresGraphicsModel.getProfile();
        Point coordinates = Directions.getDirections(direction.ordinal() + 25).getCoordinates();
        BufferedImage arrowImage = type.getProvider().getImage(profile, coordinates, AresIO.ARES_IO);
        Tile tile = node.getTile();
        Point pos = AresGraphicsModel.tileToPixel(tile.getCoordinates());
        g2.drawImage(arrowImage, pos.x, pos.y, this);
        int cost = (int) node.getG();
        g2.drawString(Integer.toString(cost), pos.x + arrowImage.getWidth() / 2, pos.y + arrowImage.getHeight() / 2);
        repaint(pos.x, pos.y, arrowImage.getWidth(), arrowImage.getHeight());
    }

    /**
     * Paints a single arrow segment in the {@code tile} passed as argument, using the graphic identified by the
     * {@code index} passed
     *
     * @param tile the tile where to paint an Arrow
     * @param index the position of the arrow segment within the array of arrow images
     */
    private void paintArrowSegment(Graphics2D g2, Node node, Set<Direction> directions, ArrowType type) {
        AresGraphicsProfile profile = AresGraphicsModel.getProfile();
        Point coordinates = Directions.getDirections(Direction.getBitmask(directions)).getCoordinates();
        BufferedImage arrowImage = type.getProvider().getImage(profile, coordinates, AresIO.ARES_IO);
        Tile tile = node.getTile();
        Point pos = AresGraphicsModel.tileToPixel(tile.getCoordinates());
        g2.drawImage(arrowImage, pos.x, pos.y, this);
        int cost = (int) node.getG();
        g2.drawString(Integer.toString(cost), pos.x + arrowImage.getWidth() / 2, pos.y + arrowImage.getHeight() / 2);
        repaint(pos.x, pos.y, arrowImage.getWidth(), arrowImage.getHeight());
    }
}
