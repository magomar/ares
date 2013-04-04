package ares.application.graphics.board;

import ares.application.graphics.AresGraphicsModel;
import ares.application.graphics.AbstractImageLayer;
import ares.application.graphics.AresGraphicsProfile;
import ares.application.graphics.AresMiscGraphics;
import ares.application.models.ScenarioModel;
import ares.application.models.board.*;
import ares.engine.knowledge.KnowledgeCategory;
import ares.io.AresIO;
import ares.scenario.board.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Terrain image layer based on Sergio Musoles TerrainPanel
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class TerrainLayer extends AbstractImageLayer {

    private ScenarioModel scenario;

    @Override
    protected void updateLayer() {
        initialize();
        Graphics2D g2 = globalImage.createGraphics();
        // Paint it black!
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, globalImage.getWidth(), globalImage.getHeight());
        for (TileModel[] tiles : scenario.getBoardModel().getMapModel()) {
            for (TileModel tile : tiles) {
                paintTile(g2, tile);
            }
        }
        g2.dispose();
    }

    public void paintTerrain(ScenarioModel scenario) {
        this.scenario = scenario;
        updateLayer();
    }

    /**
     * Paints the terrain of a single tile
     *
     * @param tile
     */
    private void paintTile(Graphics2D g2, TileModel tile) {
        if (tile.isNonPlayable()) {
            return;
        }
        //If I don't know anything about it
        if (tile.getKnowledgeCategory() == KnowledgeCategory.NONE) {
            return;
        }

        //Calculate tile position
        Point pos = AresGraphicsModel.tileToPixel(tile.getCoordinates());
        AresGraphicsProfile profile = AresGraphicsModel.getProfile();

        // First paints the open terrain, any other terrain will be rendered upon it
        BufferedImage bi = AresMiscGraphics.TERRAIN_MISCELANEOUS.getImage(profile, AresIO.ARES_IO);
        g2.drawImage(bi, pos.x, pos.y, null);

        Map<Terrain, Directions> m = tile.getTerrain();
        for (Map.Entry<Terrain, Directions> entry : m.entrySet()) {
            Terrain terrain = entry.getKey();
            Directions directions = entry.getValue();
//            int bitMask = entry.getValue().getBitmask();
            Point imageCoordinates = directions.getCoordinates();
            BufferedImage i = terrain.getImage(profile, imageCoordinates, AresIO.ARES_IO);
            // Paint terrain image
            g2.drawImage(i, pos.x, pos.y, null);
        }
        // Paint features 
        for (Feature feature : tile.getTerrainFeatures()) {
            BufferedImage i = AresMiscGraphics.TERRAIN_MISCELANEOUS.getImage(profile, feature.getCoordinates(), AresIO.ARES_IO);
            g2.drawImage(i, pos.x, pos.y, null);
        }
        if (bi == null) {
            return;
        }
        repaint(pos.x, pos.y, bi.getWidth(), bi.getHeight());
    }

}
