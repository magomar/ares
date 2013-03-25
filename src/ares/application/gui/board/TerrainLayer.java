package ares.application.gui.board;

import ares.application.gui.graphics.BoardGraphicsModel;
import ares.application.gui.AbstractImageLayer;
import ares.application.models.ScenarioModel;
import ares.application.models.board.*;
import ares.engine.knowledge.KnowledgeCategory;
import ares.io.*;
import ares.platform.util.ImageTools;
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

        // First layer is the open terrain
        BufferedImage openTerrain = getTerrainImage(Terrain.OPEN, 0);
        g2.drawImage(openTerrain, pos.x, pos.y, null);

        // Get the index of the terrain image
        Map<Terrain, Integer> m = getTerrainToImageIndex(tile);
        for (Map.Entry<Terrain, Integer> entry : m.entrySet()) {
            Terrain t = entry.getKey();
            int index = entry.getValue();
            /*
             * This process would be faster without the "if" statement
             * but BORDER image file doesn't have the same size as other
             * terrain images nor same number of columns and rows.
             * It would increase performance to modify the file image and make it
             * behave as the rest, that is:
             *      * Scale the image to the size setted in the terrain image profile
             *      * Use the same columns and rows
             *      * Paint the borders of every possible case (N, NE, N/NE, N/S/NW/, and so on)
             *        following the pattern as any other terrain image
             *      
             * I would do it myself, but sadly I lack at Photoshop skills :)
             */

            BufferedImage bi;
            if (t == Terrain.BORDER) {
                bi = drawTileBorders(index);
            } else {
                int index2 = t.getIndexByDirections(index);
                bi = getTerrainImage(t, index2);
            }
            // Paint terrain image
            g2.drawImage(bi, pos.x, pos.y, null);
        }
        // Paint features 
        int cols = BoardGraphicsModel.getImageProfile().getTerrainImageCols();
        for (Feature f : tile.getTerrainFeatures()) {
            g2.drawImage(
                    getTerrainImage(Terrain.OPEN,
                    f.getCol() + cols * f.getRow()),
                    pos.x, pos.y, null);
        }
        repaint(pos.x, pos.y, openTerrain.getWidth(), openTerrain.getHeight());
    }

    private BufferedImage getTerrainImage(Terrain t, int index) {

        // Ensure terrain graphics are loaded
        loadTerrainGraphics(t);

        //Get the coordinates
        int cols = BoardGraphicsModel.getImageProfile().getTerrainImageCols();
        int w = BoardGraphicsModel.getHexDiameter();
        int h = BoardGraphicsModel.getHexHeight();

        return terrainBufferMap.get(t).get().getSubimage(w * (index % cols), h * (index / cols), w, h);
    }

    /**
     * Terrain class has a map with the index to the subimage based on directions. The map associates the subimage
     * position with the subimage index
     *
     * For example, a road from north to south would have the index 72 "N NE SE S SW NW C" -> "1 0 0 1 0 0 0", binary to
     * int -> 72
     *
     * This function returns a map with the terrain as key and its related index as value
     *
     * @param tile tile to get directions
     * @return a map which associates each {@link Terrain} in a tile with its image index
     * @see Terrain
     *
     */
    private static Map<Terrain, Integer> getTerrainToImageIndex(TileModel tile) {

        Map<Direction, Set<Terrain>> sideTerrain = tile.getSideTerrain();

        SortedMap<Terrain, Integer> m = new TreeMap<>();
        for (Map.Entry<Direction, Set<Terrain>> e : sideTerrain.entrySet()) {
            for (Terrain t : e.getValue()) {
                //Right shift until the 1 is in the position representing the direction
                int dirbit = 64 >>> e.getKey().ordinal();
                if (m.get(t) == null) {
                    //Terrain wasn't in the map yet
                    m.put(t, dirbit);

                } else {
                    //Update the value we had with bitwise OR
                    m.put(t, m.get(t) | dirbit);
                }
            }
        }
        return m;
    }

    /**
     * Since borders are in a special file, they need a special method which draws all borders of a tile
     *
     * @param value Integer with its bits as directions
     * @return Image with all borders painted
     * @see TerrainLayer#getTerrainToImageIndex()
     */
    private BufferedImage drawTileBorders(Integer value) {
        int mask = 64;
        int w = BoardGraphicsModel.getHexDiameter();
        int h = BoardGraphicsModel.getHexHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = bi.createGraphics();

        loadTerrainGraphics(Terrain.BORDER);

        for (Direction d : Direction.values()) {

            // Shift bites and draw when direction flag is 1
            if ((mask & value) == mask) {
                g2.drawImage(terrainBufferMap.get(Terrain.BORDER).get().getSubimage(2 * w, h * d.ordinal(), w, h), null, null);
                value = value << 1;
            } else {
                value = value << 1;
            }

        }
        g2.dispose();
        return bi;
    }

    /**
     * Stores in memory the terrain image
     *
     * @param t terrain to to load
     */
    private void loadTerrainGraphics(Terrain t) {

        SoftReference<BufferedImage> softImage = terrainBufferMap.get(t);
        //If image doesn't exist or has been GC'ed
        if (softImage == null || softImage.get() == null) {
            String filename = BoardGraphicsModel.getImageProfile().getTerrainFilename(t);
            BufferedImage bi = ImageTools.loadImage(AresIO.ARES_IO.getFile(BoardGraphicsModel.getImageProfile().getPath(), filename));
            terrainBufferMap.put(t, new SoftReference<>(bi));
        }
    }
}
