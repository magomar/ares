package ares.application.shared.gui.components.layers;

import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.providers.AresMiscTerrainGraphics;
import ares.platform.engine.algorithms.pathfinding.Node;
import ares.platform.scenario.board.Tile;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Collection;
import javax.swing.JViewport;

/**
 * Draws the pathfinding process on a BufferedImage
 *
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public class PathSearchLayer extends AbstractImageLayer {

    private Collection<Node> openSet;
    private Collection<Node> closedSet;
    private Graphics2D g2;

    public PathSearchLayer(JViewport viewport, AbstractImageLayer parentLayer) {
        super(viewport, parentLayer);
    }

    @Override
    public void updateLayer() {
        initialize();
        g2 = globalImage.createGraphics();

        paintOpenSet();
        paintClosedSet();

        g2.dispose();
    }

    /**
     * Paints complete process for the {@code openSet} and {@code closedSet} passed as argument
     *
     * @param openSet
     * @param closedSet
     */
    public void paintPathfindingProcess(Collection<Node> openSet, Collection<Node> closedSet) {
        this.openSet = openSet;
        this.closedSet = closedSet;
        updateLayer();
    }

    private void paintOpenSet() {
        for (Node node : openSet) {
            paintTile(node, TileType.OPEN_SET);
        }
    }

    private void paintClosedSet() {
        for (Node node : closedSet) {
            paintTile(node, TileType.CLOSED_SET);
        }
    }

    private void paintTile(Node node, TileType type) {
        BufferedImage tileImage = GraphicsModel.INSTANCE.getImageProvider(type.getProvider(), profile).getImage(0,0);
        Point pos = GraphicsModel.INSTANCE.tileToPixel(node.getTile().getCoordinates(), profile);
        g2.drawImage(tileImage, pos.x, pos.y, this);
        int cost = (int) node.getG();
        g2.drawString(Integer.toString(cost), pos.x + tileImage.getWidth() / 2, pos.y + tileImage.getHeight() / 2);
        repaint(pos.x, pos.y, tileImage.getWidth(), tileImage.getHeight());
    }

    private enum TileType {

        OPEN_SET(AresMiscTerrainGraphics.GRID_GREEN),
        CLOSED_SET(AresMiscTerrainGraphics.GRID_YELLOW);
        private final AresMiscTerrainGraphics provider;

        private TileType(final AresMiscTerrainGraphics provider) {
            this.provider = provider;
        }

        public AresMiscTerrainGraphics getProvider() {
            return provider;
        }
    }
}
