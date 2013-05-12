package ares.application.gui.command;

import ares.application.gui.AbstractImageLayer;
import ares.application.gui.GraphicsModel;
import ares.application.gui.providers.AresMiscGraphics;
import ares.application.io.AresIO;
import ares.engine.algorithms.pathfinding.Path;
import ares.scenario.board.Tile;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Collection;

/**
 *Draws the pathfinding process on a BufferedImage
 * 
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public class PathSearchLayer extends AbstractImageLayer {
    private Collection<Tile> openSet;
    private Collection<Tile> closedSet;
    private Graphics2D g2;
    
    public PathSearchLayer(AbstractImageLayer parentLayer) {
        super(parentLayer);
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
        BufferedImage tileImage = GraphicsModel.INSTANCE.getActiveProvider(type.getProvider()).getImage(AresIO.ARES_IO);
        Point pos = GraphicsModel.INSTANCE.tileToPixel(tile.getCoordinates());
        g2.drawImage(tileImage, pos.x, pos.y, this);
        repaint(pos.x, pos.y, tileImage.getWidth(), tileImage.getHeight());
    }
}
