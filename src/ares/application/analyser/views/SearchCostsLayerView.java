package ares.application.analyser.views;

import ares.application.shared.boundaries.viewers.layerviewers.SearchCostsLayerViewer;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.providers.AresMiscTerrainGraphics;
import ares.application.shared.gui.views.layerviews.AbstractImageLayerView;
import ares.platform.engine.algorithms.pathfinding.Node;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.imageio.ImageIO;

/**
 *
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public class SearchCostsLayerView extends AbstractImageLayerView implements SearchCostsLayerViewer {

    private Collection<Node> openSet;
    private Collection<Node> closedSet;

    @Override
    public String name() {
        return SearchCostsLayerViewer.NAME;
    }

    @Override
    public void updateLayer() {
        initialize();
        Graphics2D g2 = globalImage.createGraphics();
        if (openSet != null) {
            paintOpenSetCosts(g2);
        }
        if (closedSet != null) {
            paintClosedSetCosts(g2);
        }
        g2.dispose();
    }

    @Override
    public void updateSearchCosts(Collection<Node> openSet, Collection<Node> closedSet) {
        this.openSet = openSet;
        this.closedSet = closedSet;
        updateLayer();
    }

    private void paintOpenSetCosts(Graphics2D g2) {
        for (Node node : openSet) {
            paintTileCosts(g2, node);
        }
    }

    private void paintClosedSetCosts(Graphics2D g2) {
        for (Node node : closedSet) {
            paintTileCosts(g2, node);
        }
    }

    private void paintTileCosts(Graphics2D g2, Node node) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(AresMiscTerrainGraphics.GRID.getFilename(profile)));
            int tileWidth = bufferedImage.getWidth();
            int tileHeight = bufferedImage.getHeight();
            bufferedImage.flush();
            Point pos = GraphicsModel.INSTANCE.tileToPixel(node.getTile().getCoordinates(), profile);
            int gCost = (int) node.getG();
            int fCost = (int) node.getF();
            g2.drawString(Integer.toString(gCost), pos.x + tileWidth/2, pos.y + tileHeight/2);
            g2.drawString(Integer.toString(fCost), pos.x + tileWidth/2, pos.y + tileHeight);
            contentPane.repaint(pos.x, pos.y, tileWidth, tileHeight);
        }
        catch(IOException iOException) {
        }
    }
}
