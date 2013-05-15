package ares.application.gui.layers;

import ares.application.gui.GraphicsModel;
import ares.application.gui.AbstractImageLayer;
import ares.application.gui.providers.AresMiscGraphics;
import ares.application.models.ScenarioModel;
import ares.application.models.board.*;
import ares.engine.knowledge.KnowledgeCategory;
import ares.application.io.AresIO;
import ares.scenario.board.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.JViewport;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class TerrainLayer extends AbstractImageLayer {

    private ScenarioModel scenario;

    public TerrainLayer(JViewport viewport) {
        super(viewport);
    }

    @Override
    public void updateLayer() {
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
        if (!tile.isPlayable()) {
            return;
        }
        //If I don't know anything about it
        if (tile.getKnowledgeCategory() == KnowledgeCategory.NONE) {
            return;
        }

        //Calculate tile position
        Point pos = GraphicsModel.INSTANCE.tileToPixel(tile.getCoordinates());

        // First paints the open terrain, any other terrain will be rendered upon it
        BufferedImage terrainImage = GraphicsModel.INSTANCE.getActiveProvider(AresMiscGraphics.TERRAIN_MISCELANEOUS).getImage(AresIO.ARES_IO);
        g2.drawImage(terrainImage, pos.x, pos.y, this);

        Map<Terrain, Directions> m = tile.getTerrain();
        for (Map.Entry<Terrain, Directions> entry : m.entrySet()) {
            Terrain terrain = entry.getKey();
            Directions directions = entry.getValue();
            Point imageCoordinates = directions.getCoordinates();
            terrainImage = GraphicsModel.INSTANCE.getActiveProvider(terrain).getImage(imageCoordinates, AresIO.ARES_IO);
            // Paint terrain image
            g2.drawImage(terrainImage, pos.x, pos.y, this);
        }
        // Paint features 
        for (Feature feature : tile.getTerrainFeatures()) {
            terrainImage = GraphicsModel.INSTANCE.getActiveProvider(AresMiscGraphics.TERRAIN_MISCELANEOUS).getImage(feature.getCoordinates(), AresIO.ARES_IO);
            g2.drawImage(terrainImage, pos.x, pos.y, this);
        }
        repaint(pos.x, pos.y, terrainImage.getWidth(), terrainImage.getHeight());
    }
}
