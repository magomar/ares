package ares.application.gui.board;

import ares.application.gui.graphics.BoardGraphicsModel;
import ares.application.gui.AbstractImageLayer;
import ares.application.gui.graphics.ImageProfile;
import ares.application.models.ScenarioModel;
import ares.application.models.board.*;
import ares.engine.knowledge.KnowledgeCategory;
import ares.io.*;
import ares.application.gui.graphics.ImageTools;
import ares.scenario.board.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;
import java.util.*;

/**
 * Terrain image layer based on Sergio Musoles TerrainPanel
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class TerrainLayer extends AbstractImageLayer {

    //Map to store loaded images    
    private EnumMap<Terrain, SoftReference<BufferedImage>> terrainBufferMap = new EnumMap<>(Terrain.class);
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
        Point pos = BoardGraphicsModel.tileToPixel(tile.getCoordinates());
        int w = BoardGraphicsModel.getHexDiameter();
        int h = BoardGraphicsModel.getHexHeight();
        // First layer is the open terrain
        BufferedImage openTerrain = getTerrainImage(Terrain.OPEN, 0, w, h);
        g2.drawImage(openTerrain, pos.x, pos.y, null);

        // Get the index of the terrain image
        Map<Terrain, Integer> m = getTerrainBitMasks(tile);
        for (Map.Entry<Terrain, Integer> entry : m.entrySet()) {
            Terrain terrain = entry.getKey();
            int bitMask = entry.getValue();

            BufferedImage bi = getTerrainImage(terrain, bitMask, w, h);
            // Paint terrain image
            g2.drawImage(bi, pos.x, pos.y, null);
        }
        // Paint features 
        for (Feature feature : tile.getTerrainFeatures()) {
            BufferedImage bi = terrainBufferMap.get(Terrain.OPEN).get().
                    getSubimage(w * feature.getImageCol(), h * feature.getImageRow(), w, h);
            g2.drawImage(bi, pos.x, pos.y, null);
        }
        repaint(pos.x, pos.y, openTerrain.getWidth(), openTerrain.getHeight());
    }

    private BufferedImage getTerrainImage(Terrain terrain, int bitMask, int w, int h) {

        // Ensure terrain graphics are loaded
        SoftReference<BufferedImage> softImage = terrainBufferMap.get(terrain);
        //If image doesn't exist or has been GC'ed
        if (softImage == null || softImage.get() == null) {
            String filename = BoardGraphicsModel.getImageProfile().getTerrainFilename(terrain);
            BufferedImage bi = ImageTools.loadImage(AresIO.ARES_IO.getFile(BoardGraphicsModel.getImageProfile().getPath(), filename));
            terrainBufferMap.put(terrain, new SoftReference<>(bi));
        }

        int imageIndex = terrain.getImageIndex(bitMask);
        int column = imageIndex / ImageProfile.TERRAIN_IMAGE_ROWS;
        int row = imageIndex % ImageProfile.TERRAIN_IMAGE_ROWS;
        return terrainBufferMap.get(terrain).get().getSubimage(w * column, h * row, w, h);
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
