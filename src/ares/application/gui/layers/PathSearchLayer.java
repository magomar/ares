package ares.application.gui.layers;

import ares.application.gui.profiles.GraphicsModel;
import ares.application.gui.providers.AresMiscGraphics;
import ares.scenario.board.Tile;
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

    private Collection<Tile> openSet;
    private Collection<Tile> closedSet;
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
    public void paintPathfindingProcess(Collection<Tile> openSet, Collection<Tile> closedSet) {
        this.openSet = openSet;
        this.closedSet = closedSet;
        updateLayer();
    }

    private void paintOpenSet() {
        for (Tile tile : openSet) {
            paintTile(tile, TileType.OPEN_SET);
        }
    }

    private void paintClosedSet() {
        for (Tile tile : closedSet) {
            paintTile(tile, TileType.CLOSED_SET);
        }
    }

    private void paintTile(Tile tile, TileType type) {
        BufferedImage tileImage = GraphicsModel.INSTANCE.getActiveProvider(type.getProvider()).getImage(0,0);
        Point pos = GraphicsModel.INSTANCE.tileToPixel(tile.getCoordinates());
        g2.drawImage(tileImage, pos.x, pos.y, this);
        repaint(pos.x, pos.y, tileImage.getWidth(), tileImage.getHeight());
    }

    private enum TileType {

        OPEN_SET(AresMiscGraphics.GRID_GREEN),
        CLOSED_SET(AresMiscGraphics.GRID_YELLOW);
        private final AresMiscGraphics provider;

        private TileType(final AresMiscGraphics provider) {
            this.provider = provider;
        }

        public AresMiscGraphics getProvider() {
            return provider;
        }
    }
}
