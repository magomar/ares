package ares.application.analyser.views;

import ares.application.analyser.boundaries.viewers.PathfindingLayerViewer;
import ares.application.shared.gui.decorators.ImageDecorators;
import ares.application.shared.gui.profiles.GraphicProperties;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.profiles.ProfiledGraphicProperty;
import ares.application.shared.gui.providers.AresMiscTerrainGraphics;
import ares.application.shared.gui.views.layerviews.AbstractImageLayerView;
import ares.platform.engine.algorithms.pathfinding.Node;

import java.awt.*;
import java.awt.color.ICC_ProfileRGB;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Collection;

/**
 * Draws the pathfinding process on a BufferedImage
 *
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public class PathfindingLayerView extends AbstractImageLayerView implements PathfindingLayerViewer {
    private Collection<Node> openSet;
    private Collection<Node> closedSet;
    private ShowCostType costToShow;
    private int maxCost;

    @Override
    public String name() {
        return PathfindingLayerViewer.NAME;
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
     * @param openSet    the set of open nodes
     * @param closedSet  the the set of closed nodes
     * @param costToShow indicates the kind of cost to show  (G, H or F)
     * @inheritDoc
     */
    @Override
    public void updatePathSearch(Collection<Node> openSet, Collection<Node> closedSet, ShowCostType costToShow) {
        this.openSet = openSet;
        this.closedSet = closedSet;
        this.costToShow = costToShow;
        if (openSet == null || closedSet == null) return;
        maxCost = 0;
        for (Node node : openSet) {
            maxCost = Math.max(maxCost, getNodeCost(node));
        }
        for (Node node : closedSet) {
            maxCost = Math.max(maxCost, getNodeCost(node));
        }
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
        Point pos = GraphicsModel.INSTANCE.tileToPixel(node.getTile().getCoordinates(), profile);
        paintNodeCostAsColoredDot(g2,node,pos);
    }

    private void paintNodeCostAsColoredDot(Graphics2D g2, Node node, Point pos) {
        int tileWidth = GraphicProperties.getProperty(ProfiledGraphicProperty.TILE_WIDTH, profile);
        int tileHeight = GraphicProperties.getProperty(ProfiledGraphicProperty.TILE_HEIGHT, profile);
        int cost = getNodeCost(node) * 100 / maxCost;
        Color color = ImageDecorators.colorLevel(cost, ICC_ProfileRGB.GREENCOMPONENT, ICC_ProfileRGB.REDCOMPONENT);
        g2.setPaint(color);
        Ellipse2D.Double circle = new Ellipse2D.Double(pos.x + tileWidth/2, pos.y + tileHeight/2, tileWidth/4, tileWidth/4);
        g2.fill(circle);
        g2.setColor(Color.BLACK);
        g2.draw(circle);
        contentPane.repaint(pos.x, pos.y, tileWidth, tileHeight);
    }

    private void paintNodeCostAsTranslucidSquare(Graphics2D g2, Node node, Point pos) {
        int tileWidth = GraphicProperties.getProperty(ProfiledGraphicProperty.TILE_WIDTH, profile);
        int tileHeight = GraphicProperties.getProperty(ProfiledGraphicProperty.TILE_HEIGHT, profile);
        int cost = getNodeCost(node) * 100 / maxCost;
        Color baseColor = ImageDecorators.colorLevel(cost, ICC_ProfileRGB.GREENCOMPONENT, ICC_ProfileRGB.REDCOMPONENT);
        Color color = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 128);
        g2.setPaint(color);
        Rectangle2D.Double square = new Rectangle2D.Double(pos.x + tileWidth / 4, pos.y + tileHeight / 4, tileWidth / 2, tileHeight / 2);
        g2.fill(square);
        contentPane.repaint(pos.x, pos.y, tileWidth, tileHeight);
    }

    private void paintNodeCostsAsText(Graphics2D g2, Node node, TileType type, Point pos) {
        BufferedImage tileImage = GraphicsModel.INSTANCE.getProfiledImageProvider(type.getProvider(), profile).getImage(0, 0);
          g2.drawImage(tileImage, pos.x, pos.y, contentPane);
        int tileWidth = tileImage.getWidth();
        int tileHeight = tileImage.getHeight();
        int gCost = (int) node.getG();
        int fCost = (int) node.getF();
        g2.drawString(Integer.toString(gCost), pos.x + tileWidth/2, pos.y + tileHeight/2);
        g2.drawString(Integer.toString(fCost), pos.x + tileWidth/2, pos.y + tileHeight);
        contentPane.repaint(pos.x, pos.y, tileWidth, tileHeight);
    }

    private int getNodeCost(Node node) {
        int cost = 0;
        switch (costToShow) {
            case SHOW_G_COST:
                cost = (int) node.getG();
                break;
            case SHOW_H_COST:
                cost = (int) node.getH();
                break;
            case SHOW_F_COST:
                cost = (int) node.getF();
                break;
            default:
                assert false;
        }
        return cost;
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
