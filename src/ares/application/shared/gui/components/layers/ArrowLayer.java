package ares.application.shared.gui.components.layers;

import ares.application.shared.gui.decorators.ImageDecorators;
import ares.platform.engine.algorithms.pathfinding.Path;
import ares.platform.engine.algorithms.pathfinding.Node;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.providers.AresMiscTerrainGraphics;
import ares.platform.scenario.board.Direction;
import ares.platform.scenario.board.Directions;
import ares.platform.scenario.board.Tile;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.JViewport;

/**
 * Draws the movement arrows on a BufferedImage
 *
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class ArrowLayer extends AbstractImageLayer {

    private Path activePath;
    private Path selectedUnitPath;
    private Collection<Path> formationPaths;
    private Collection<Path> forcePaths;

    public ArrowLayer(JViewport viewport, AbstractImageLayer parentLayer) {
        super(viewport, parentLayer);
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
     * Paints complete arrow for the {@code activePath} passed as argument
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
        Point coordinates = Directions.getDirections(direction.ordinal() + 25).getCoordinates();
        BufferedImage arrowImage = GraphicsModel.INSTANCE.getImageProvider(type.getProvider(), profile).getImage(coordinates);
        Tile tile = node.getTile();
        Point pos = GraphicsModel.INSTANCE.tileToPixel(tile.getCoordinates(), profile);
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
        Point coordinates = Directions.getDirections(direction.ordinal() + 25).getCoordinates();
        BufferedImage arrowImage = GraphicsModel.INSTANCE.getImageProvider(type.getProvider(), profile).getImage(coordinates);
        Tile tile = node.getTile();
        Point pos = GraphicsModel.INSTANCE.tileToPixel(tile.getCoordinates(), profile);
        g2.drawImage(arrowImage, pos.x, pos.y, this);
        BufferedImage subImage = globalImage.getSubimage(pos.x, pos.y, arrowImage.getWidth(), arrowImage.getHeight());
        Graphics2D arrowG2 = subImage.createGraphics();
        arrowG2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        ImageDecorators decorator = GraphicsModel.INSTANCE.getImageDecorators(profile);
        int cost = (int) node.getG();
        decorator.paintArrowCost(arrowG2, cost);
        arrowG2.dispose();
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
        Point coordinates = Directions.getDirections(Direction.getBitmask(directions)).getCoordinates();
        BufferedImage arrowImage = GraphicsModel.INSTANCE.getImageProvider(type.getProvider(), profile).getImage(coordinates);
        Tile tile = node.getTile();
        Point pos = GraphicsModel.INSTANCE.tileToPixel(tile.getCoordinates(), profile);
        g2.drawImage(arrowImage, pos.x, pos.y, this);
        repaint(pos.x, pos.y, arrowImage.getWidth(), arrowImage.getHeight());
    }

    /**
     * Paints a single arrow segment together with the accumulated movement cost
     *
     * @param tile the tile where to paint an Arrow
     * @param index the position of the arrow segment within the array of arrow images
     */
    private void paintArrowSegmentWithCost(Graphics2D g2, Node node, Set<Direction> directions, ArrowType type) {
        Point coordinates = Directions.getDirections(Direction.getBitmask(directions)).getCoordinates();
        BufferedImage arrowImage = GraphicsModel.INSTANCE.getImageProvider(type.getProvider(), profile).getImage(coordinates);
        Tile tile = node.getTile();
        Point pos = GraphicsModel.INSTANCE.tileToPixel(tile.getCoordinates(), profile);
        g2.drawImage(arrowImage, pos.x, pos.y, this);
        BufferedImage subImage = globalImage.getSubimage(pos.x, pos.y, arrowImage.getWidth(), arrowImage.getHeight());
        Graphics2D arrowG2 = subImage.createGraphics();
        arrowG2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        ImageDecorators decorator = GraphicsModel.INSTANCE.getImageDecorators(profile);
        int cost = (int) node.getG();
        decorator.paintArrowCost(arrowG2, cost);
        arrowG2.dispose();
        repaint(pos.x, pos.y, arrowImage.getWidth(), arrowImage.getHeight());
    }

    private enum ArrowType {

        ACTIVE(AresMiscTerrainGraphics.RED_ARROWS),
        UNIT(AresMiscTerrainGraphics.PURPLE_ARROWS),
        FORMATION(AresMiscTerrainGraphics.DARK_BLUE_ARROWS),
        FORCE(AresMiscTerrainGraphics.BLUE_ARROWS);
        private final AresMiscTerrainGraphics provider;

        private ArrowType(final AresMiscTerrainGraphics provider) {
            this.provider = provider;
        }

        public AresMiscTerrainGraphics getProvider() {
            return provider;
        }
    }
}
