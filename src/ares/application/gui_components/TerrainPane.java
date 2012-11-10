package ares.application.gui_components;

import ares.data.jaxb.TerrainFeature;
import ares.io.*;
import ares.scenario.Scenario;
import ares.scenario.board.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

/**
 *
 * Terrain panel based on Sergio Musoles TerrainPanel where the terrain of the
 * scenario is painted
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class TerrainPane extends javax.swing.JPanel {

    private Image terrainImage = null;
    private Map<Terrain, BufferedImage> terrainBufferMap = new EnumMap<>(Terrain.class);
    private BoardInfo boardInfo;
    private String lastScenario;

    /**
     * Initializes the pane with the terrain image. It will create the image
     * only when a new scenario is opened.
     *
     * @param scenario current scenario information
     *
     */
    public void initialize(Scenario scenario) {

        boardInfo = scenario.getBoardInfo();
        if (lastScenario == null) {
            /*
             * Load graphics only the first time
             */
            loadGraphics();
            lastScenario = "";
        }
        if (!lastScenario.equals(scenario.getName())) {
            /*
             *  Create the new terrain image
             */
            terrainImage = new BufferedImage(
                    // Image size
                    boardInfo.getImageWidth(), boardInfo.getImageHeight(),
                    // RGB + Alpha
                    BufferedImage.TYPE_4BYTE_ABGR);

            createTerrainImage(scenario.getBoard().getMap());
            lastScenario = scenario.getName();
        } else {
            /*
             * Scenario already created
             */
        }
        repaint();

    }

    /**
     * Puts in memory all terrain graphics This method should only be called on
     * the first instance of a scenario or when the Image Profile has been
     * changed
     *
     * @param imageProfile information about the image file paths
     */
    // TODO Load only used terrain
    private void loadGraphics() {


        //If loadGraphics is called with a non-empty map then it means
        //we need to load new images.
        if (!terrainBufferMap.isEmpty()) {
            terrainBufferMap = new EnumMap<>(Terrain.class);
        }

        ImageProfile iP = boardInfo.getImageProfile();
        for (Terrain terrain : Terrain.ALL_TERRAINS) {
            try {
                terrainBufferMap.put(terrain, ImageIO.read(
                        // File image
                        AresIO.ARES_IO.getFile(iP.getPath(), iP.getFileName(terrain))));
            } catch (IOException e) {

                System.out.println(e.getMessage() + "\nFile not found: " + iP.getFileName(terrain));

            }
        }
    }

    /**
     * Creates the whole terrain image First it paints a black background with
     * the Image dimension, then every tile in the tile map is painted over the
     * image
     *
     * @param tileMap array of tiles to iterate over
     */
    public void createTerrainImage(Tile[][] tileMap) {


        Graphics terrainGraphics = terrainImage.getGraphics();
        // Set terrain image background color                                                   
        terrainGraphics.setColor(Color.BLACK);
        terrainGraphics.fillRect(0, 0, boardInfo.getImageWidth(), boardInfo.getImageHeight());

        Image tileTerrainImage;
        Image tileFeaturesImage = null;


        int x = 0, y;
        int dx = boardInfo.gexHexOffset();
        for (int i = 0; i < boardInfo.getWidth(); ++i) {
            for (int j = 0; j < boardInfo.getHeight(); ++j) {

                /* The height depends on the column index 'j'.
                 * It paints higher if 'j' is odd, lower if it's even
                 */

                y = boardInfo.getHexHeight() * (2 * j + ((i + 1) % 2)) / 2;

                // createFeaturesImage returns null if tile is non playable
                tileFeaturesImage = createFeaturesImage(tileMap[i][j]);
                if (tileFeaturesImage != null) {

                    tileTerrainImage = createTileImage(tileMap[i][j]);

                    terrainGraphics.drawImage(tileTerrainImage, x, y, null);
                    terrainGraphics.drawImage(tileFeaturesImage, x, y, this);
                }
            }
            x += dx;
        }



    }

    /**
     * Paints all the features and terrains of a single tile and returns it as
     * an Image
     *
     * @param tile tile to be represented
     * @return the painted tile
     */
    public Image createTileImage(Tile tile) {


        //TODO draw features
        int w = boardInfo.getHexDiameter();
        int h = boardInfo.getHexHeight();
        BufferedImage tileImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);

        Graphics2D tileGraphics = (Graphics2D) tileImage.createGraphics();

        tileGraphics.drawImage(terrainBufferMap.get(Terrain.OPEN).getSubimage(0, 0, w, h), 0, 0, null);
        Map<Terrain, Integer> m = getTerrainToImageIndex(tile);

        int index = 0, cols, x, y;
        for (Map.Entry<Terrain, Integer> e : m.entrySet()) {

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
            if (e.getKey() == Terrain.BORDER) {
                bi = drawTileBorders(e.getValue());
            } else {
                index = e.getKey().getIndexByDirections(e.getValue());
                cols = boardInfo.getImageProfile().getTerrainImageCols();
                x = index / cols;
                y = index % cols;
                bi = terrainBufferMap.get(e.getKey()).getSubimage(y * w, x * h, w, h);
            }
            tileGraphics.drawImage(bi, 0, 0, null);
        }



        return tileImage;
    }

    /**
     * Terrain class has a map with the index to the subimage based on
     * directions The map associates the subimage position with the subimage
     * index
     *
     * For example, a road from north to south would have the index 72 "N NE SE
     * S SW NW C" -> "1 0 0 1 0 0 0", binary to int -> 72
     *
     * This function returns a map with the terrain as key and its related index
     * as value
     *
     * @param tile tile to get directions
     * @return Map<Terrain,Integer> map which associates a terrain with its
     * subimage position given a tile
     *
     */
    public Map<Terrain, Integer> getTerrainToImageIndex(Tile tile) {

        Map<Direction, Set<Terrain>> sideTerrain = tile.getSideTerrain();

        SortedMap<Terrain, Integer> m = new TreeMap<>();
        Iterator it;
        for (Map.Entry<Direction, Set<Terrain>> e : sideTerrain.entrySet()) {
            it = e.getValue().iterator();
            while (it.hasNext()) {
                Terrain t = (Terrain) it.next();
                int mask = 64 >> e.getKey().ordinal();
                if (m.get(t) == null) {
                    //Terrain wasn't in the map yet
                    m.put(t, mask);

                } else {
                    m.put(t, m.get(t) | mask);
                }
            }
        }
        return m;
    }

    /**
     * Checks if the tile is playable and while it iterates over the
     * TerrainFeatures set saves an image with all the features
     *
     * @param tile
     * @param tileFeaturesImage where the images are painted
     * @return true if playable, false otherwise
     * @see TerrainFeatures
     */
    private Image createFeaturesImage(Tile tile) {

        //TODO Tile features should be objects of TerrainFeatures class
        //     instead of ares.data.jaxb.TerrainFeature

        BufferedImage i = new BufferedImage(boardInfo.getHexDiameter(), boardInfo.getHexHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = i.getGraphics();
        int w = boardInfo.getHexDiameter();
        int h = boardInfo.getHexHeight();


        Set<TerrainFeature> tf = tile.getFeatures();
        Iterator it = tf.iterator();
        while (it.hasNext()) {
            TerrainFeature f = (TerrainFeature) it.next();

            switch (f) {
                case NON_PLAYABLE:
                    return null;

                case AIRFIELD:
                    g.drawImage(
                            terrainBufferMap.get(Terrain.OPEN).getSubimage(
                            w * TerrainFeatures.AIRFIELD.getCol(),
                            h * TerrainFeatures.AIRFIELD.getRow(),
                            w, h),
                            0, 0, this);
                    break;
                case ANCHORAGE:
                    g.drawImage(
                            terrainBufferMap.get(Terrain.OPEN).getSubimage(
                            w * TerrainFeatures.ANCHORAGE.getCol(),
                            h * TerrainFeatures.ANCHORAGE.getRow(),
                            w, h),
                            0, 0, this);
                    break;
                case PEAK:
                    g.drawImage(
                            terrainBufferMap.get(Terrain.OPEN).getSubimage(
                            w * TerrainFeatures.PEAK.getCol(),
                            h * TerrainFeatures.PEAK.getRow(),
                            w, h),
                            0, 0, this);
                    break;

                case CONTAMINATED:
                    g.drawImage(
                            terrainBufferMap.get(Terrain.OPEN).getSubimage(
                            w * TerrainFeatures.CONTAMINATED.getCol(),
                            h * TerrainFeatures.CONTAMINATED.getRow(),
                            w, h),
                            0, 0, this);
                    break;
                case MUDDY:
                    g.drawImage(
                            terrainBufferMap.get(Terrain.OPEN).getSubimage(
                            w * TerrainFeatures.MUDDY.getCol(),
                            h * TerrainFeatures.MUDDY.getRow(),
                            w, h), 0, 0, this);

                    break;

                //TODO Add all feature cases

                default:
                    break;
            }

        }

        return i;
    }

    /**
     * Since borders are in a special file, they need a special method which
     * draws all borders of a tile
     *
     * @param value Integer with its bits as directions
     * @return Image with all borders painted
     * @see getTerrainToImageIndex()
     */
    private BufferedImage drawTileBorders(Integer value) {
        int mask = 64;
        int w = boardInfo.getHexDiameter();
        int h = boardInfo.getHexHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = bi.createGraphics();

        for (Direction d : Direction.values()) {

            // Shift bites and draw when direction flag is 1
            if ((mask & value) == mask) {
                g.drawImage(terrainBufferMap.get(Terrain.BORDER).getSubimage(2 * w, h * d.ordinal(), w, h), null, this);
                value = value << 1;
            } else {
                value = value << 1;
            }

        }
        return bi;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(terrainImage, 0, 0, this);
    }
}
