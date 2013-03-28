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

        Map<Terrain, Integer> m = getTerrainBitMasks(tile);
        for (Map.Entry<Terrain, Integer> entry : m.entrySet()) {
            Terrain terrain = entry.getKey();
            int bitMask = entry.getValue();
            BufferedImage i = terrain.getImage(profile, Terrain.getImageIndex(bitMask), AresIO.ARES_IO);
            // Paint terrain image
            g2.drawImage(i, pos.x, pos.y, null);
        }
        // Paint features 
        for (Feature feature : tile.getTerrainFeatures()) {
            BufferedImage i = AresMiscGraphics.TERRAIN_MISCELANEOUS.getImage(profile, feature.getImageRow(), feature.getImageCol(), AresIO.ARES_IO);
            g2.drawImage(i, pos.x, pos.y, null);
        }
        if (bi == null) {
            return;
        }
        repaint(pos.x, pos.y, bi.getWidth(), bi.getHeight());
    }

    /**
     * Obtains a bit mask for every terrain present in the tile. The resulting bit masks are easily transformed into
     * indexes to locate a terrain image in the terrain image file. Each direction is encoded as a simple bitflag (the
     * ordinal of Direction enum) Examples:<b>
     * "N" -> 000001<b>
     * "N NE" -> 000011<b>
     * "S SE" -> 001100
     *
     * @param tile tile to get directions
     * @return a Map which associates each {@link Terrain} in the tile with its bitmask
     *
     */
    private static Map<Terrain, Integer> getTerrainBitMasks(TileModel tile) {

        Map<Direction, Set<Terrain>> sideTerrain = tile.getSideTerrain();

        //SortedMap<Terrain, Integer> m = new TreeMap<>();
        Map<Terrain, Integer> terrainMasks = new EnumMap<>(Terrain.class);
        for (Map.Entry<Direction, Set<Terrain>> entry : sideTerrain.entrySet()) {
            Direction dir = entry.getKey();
            int dirbit = 1 << dir.ordinal();
            for (Terrain terrain : entry.getValue()) {
                if (!terrainMasks.containsKey(terrain)) {
                    terrainMasks.put(terrain, dirbit);
                } else {
                    terrainMasks.put(terrain, terrainMasks.get(terrain) | dirbit);
                }
            }
        }
        return terrainMasks;
    }
}
