package ares.application.analyser.views;

import ares.application.shared.boundaries.viewers.layerviewers.PathSearchLayerViewer;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.providers.AresMiscTerrainGraphics;
import ares.application.shared.gui.views.layerviews.AbstractImageLayerView;
import ares.platform.engine.algorithms.pathfinding.Node;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;

/**
 * Draws the pathfinding process on a BufferedImage
 *
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public class PathSearchLayerView extends AbstractImageLayerView implements PathSearchLayerViewer {

    private Collection<Node> openSet;
    private Collection<Node> closedSet;

    @Override
    public String name() {
        return PathSearchLayerViewer.NAME;
    }

    @Override
    public void updateLayer() {
        initialize();
        Graphics2D g2 = globalImage.createGraphics();
        if (openSet != null) {
            paintOpenSet(g2);
        }
        if (closedSet != null) {
            paintClosedSet(g2);
        }
        g2.dispose();
    }

    /**
     * Paints the space searched to compute a path, as described by the {@code openSet} and {@code closedSet} collection
     * of nodes passed as argument
     *
     * @param openSet
     * @param closedSet
     */
    @Override
    public void updatePathSearch(Collection<Node> openSet, Collection<Node> closedSet) {
        this.openSet = openSet;
        this.closedSet = closedSet;
        updateLayer();
    }

    private void paintOpenSet(Graphics2D g2) {
        for (Node node : openSet) {
            paintTile(g2, node, TileType.OPEN_SET);
        }
    }

    private void paintClosedSet(Graphics2D g2) {
        for (Node node : closedSet) {
            paintTile(g2, node, TileType.CLOSED_SET);
        }
    }

    private void paintTile(Graphics2D g2, Node node, TileType type) {
        BufferedImage tileImage = GraphicsModel.INSTANCE.getProfiledImageProvider(type.getProvider(), profile).getImage(0, 0);
        Point pos = GraphicsModel.INSTANCE.tileToPixel(node.getTile().getCoordinates(), profile);
        g2.drawImage(tileImage, pos.x, pos.y, contentPane);
        int cost = (int) node.getG();
        g2.drawString(Integer.toString(cost), pos.x + tileImage.getWidth() / 2, pos.y + tileImage.getHeight() / 2);
        contentPane.repaint(pos.x, pos.y, tileImage.getWidth(), tileImage.getHeight());
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
