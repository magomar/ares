package ares.application.gui.board;

import ares.application.gui.AresGraphicsModel;
import ares.application.gui.AbstractImageLayer;
import ares.application.gui.AresGraphicsProfile;
import ares.application.gui.AresMiscGraphics;
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
        BufferedImage terrainImage = AresMiscGraphics.TERRAIN_MISCELANEOUS.getImage(profile, AresIO.ARES_IO);
        g2.drawImage(terrainImage, pos.x, pos.y, this);

        Map<Terrain, Directions> m = tile.getTerrain();
        for (Map.Entry<Terrain, Directions> entry : m.entrySet()) {
            Terrain terrain = entry.getKey();
            Directions directions = entry.getValue();
            Point imageCoordinates = directions.getCoordinates();
            terrainImage = terrain.getImage(profile, imageCoordinates, AresIO.ARES_IO);
            // Paint terrain image
            g2.drawImage(terrainImage, pos.x, pos.y, this);
        }
        // Paint features 
        for (Feature feature : tile.getTerrainFeatures()) {
            terrainImage = AresMiscGraphics.TERRAIN_MISCELANEOUS.getImage(profile, feature.getCoordinates(), AresIO.ARES_IO);
            g2.drawImage(terrainImage, pos.x, pos.y, this);
        }
        repaint(pos.x, pos.y, terrainImage.getWidth(), terrainImage.getHeight());
    }

}
