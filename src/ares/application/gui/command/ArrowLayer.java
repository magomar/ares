package ares.application.gui.command;

import ares.engine.algorithms.pathfinding.Path;
import ares.engine.algorithms.pathfinding.Node;
import ares.application.gui.AresGraphicsModel;
import ares.application.gui.AbstractImageLayer;
import ares.application.gui.AresGraphicsProfile;
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

    private Path activePath;
    private Path selectedUnitPath;
    private Collection<Path> formationPaths;
    private Collection<Path> forcePaths;

    public ArrowLayer(AbstractImageLayer parentLayer) {
        super(parentLayer);
    }

    @Override
    public void updateLayer() {
        initialize();
        Graphics2D g2 = globalImage.createGraphics();
        if (forcePaths != null) {
            for (Path path : forcePaths) {
                paintArrow(g2, path, ArrowType.FORCE);
            }
        }
        if (formationPaths != null) {
            for (Path path : formationPaths) {
                paintArrow(g2, path, ArrowType.FORMATION);
            }
        }
        if (selectedUnitPath != null) {
            paintArrow(g2, selectedUnitPath, ArrowType.UNIT);
        }
        if (activePath != null) {
            paintArrowWithCost(g2, activePath, ArrowType.ACTIVE);
        }
        g2.dispose();
    }

    /**
     * Paints complete arrow for the {@code path} passed as argument
     *
     * @param activePath
     */
    public void paintActiveOrders(Path activePath) {
        this.activePath = activePath;
        updateLayer();
    }

    /**
     * Paints complete arrow for the {@code path} passed as argument
     *
     * @param activePath
     */
    public void paintLastOrders(Path lastPath) {
        this.selectedUnitPath = lastPath;
        updateLayer();
    }

    /**
     * Paints complete arrows for the {@code plannedPaths} passed as argument
     *
     * @param path
     */
    public void paintPlannedOrders(Path selectedUnitPath, Collection<Path> formationPaths, Collection<Path> forcePaths) {
        this.selectedUnitPath = selectedUnitPath;
        this.formationPaths = formationPaths;
        this.forcePaths = forcePaths;
        updateLayer();
    }

    private void paintArrow(Graphics2D g2, Path path, ArrowType type) {
        if (path.size() < 2) {
            return;
        }
        Node last = path.getLast();
        Node current = path.getFirst();
        Node next = current.getNext();
        paintArrowSegment(g2, current, EnumSet.of(next.getDirection().getOpposite()), type);
        while (next != last) {
            current = next;
            next = next.getNext();
            Direction from = current.getDirection();
            Direction to = next.getDirection().getOpposite();
            paintArrowSegment(g2, current, EnumSet.of(from, to), type);
        }
        paintFinalArrowSegment(g2, last, last.getDirection(), type);
    }

    private void paintArrowWithCost(Graphics2D g2, Path path, ArrowType type) {
        if (path.size() < 2) {
            return;
        }
        Node last = path.getLast();
        Node current = path.getFirst();
        Node next = current.getNext();
        paintArrowSegmentWithCost(g2, current, EnumSet.of(next.getDirection().getOpposite()), type);
        while (next != last) {
            current = next;
            next = next.getNext();
            Direction from = current.getDirection();
            Direction to = next.getDirection().getOpposite();
            paintArrowSegmentWithCost(g2, current, EnumSet.of(from, to), type);
        }
        paintFinalArrowSegmentWithCost(g2, last, last.getDirection(), type);
    }

    /**
     * Paints a final arrow segment
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
        repaint(pos.x, pos.y, arrowImage.getWidth(), arrowImage.getHeight());
    }

    /**
     * Paints a final arrow segment together with the final movement cost
     *
     * @param g2
     * @param node
     * @param direction
     * @param type
     */
    private void paintFinalArrowSegmentWithCost(Graphics2D g2, Node node, Direction direction, ArrowType type) {
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
     * Paints a single arrow segment
     *
     * @param g2
     * @param node
     * @param directions
     * @param type
     */
    private void paintArrowSegment(Graphics2D g2, Node node, Set<Direction> directions, ArrowType type) {
        AresGraphicsProfile profile = AresGraphicsModel.getProfile();
        Point coordinates = Directions.getDirections(Direction.getBitmask(directions)).getCoordinates();
        BufferedImage arrowImage = type.getProvider().getImage(profile, coordinates, AresIO.ARES_IO);
        Tile tile = node.getTile();
        Point pos = AresGraphicsModel.tileToPixel(tile.getCoordinates());
        g2.drawImage(arrowImage, pos.x, pos.y, this);
        repaint(pos.x, pos.y, arrowImage.getWidth(), arrowImage.getHeight());
    }

    /**
     * Paints a single arrow segment together with the accumulated movement cost
     *
     * @param tile the tile where to paint an Arrow
     * @param index the position of the arrow segment within the array of arrow
     * images
     */
    private void paintArrowSegmentWithCost(Graphics2D g2, Node node, Set<Direction> directions, ArrowType type) {
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
