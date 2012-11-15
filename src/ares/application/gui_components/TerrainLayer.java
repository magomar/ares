package ares.application.gui_components;

import ares.application.models.board.BoardGraphicsModel;
import ares.application.models.ScenarioModel;
import ares.application.models.board.TileModel;
import ares.data.jaxb.TerrainFeature;
import ares.io.*;
import ares.scenario.board.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.*;
import javax.imageio.ImageIO;

/**
 * Terrain image layer based on Sergio Musoles TerrainPanel
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class TerrainLayer extends javax.swing.JPanel {

    // Final image to be painted on the JComponent
    private Image terrainImage = null;
    //Map to store loaded images
    private SoftReference<EnumMap<Terrain, BufferedImage>> terrainBufferMap;
    // BoardGraphicsModel provides hexagonal and image sizes
    private BoardGraphicsModel boardInfo;

    /**
     * Initializes the pane with the terrain image.
     * It will create the image only when a new scenario is opened.
     *
     * @param scenario current scenario information
     *
     */

    public void initialize(ScenarioModel scenario) {

        boardInfo = scenario.getBoardInfo();
        // If there is no terrain image
        if (terrainImage == null) {
            // If the buffer doesn't exist
            if (terrainBufferMap == null) {
                terrainBufferMap = new SoftReference<>(new EnumMap<Terrain, BufferedImage>(Terrain.class));
                loadGraphics(Terrain.OPEN);
                loadGraphics(Terrain.BORDER);
            }
            terrainImage = new BufferedImage(
                    // Image size
                    boardInfo.getImageWidth(), boardInfo.getImageHeight(),
                    // RGB + Alpha
                    BufferedImage.TYPE_4BYTE_ABGR);

            createTerrainImage(scenario.getBoardModel().getMapModel());
            //
        } else {
            /*
             * Terrain image already created
             */
        }
        repaint();

    }

    /**
     * Creates the whole terrain image.
     * Paints all the playable tiles stored in <code>tileMap</code>
     *
     * @param tileMap array of tiles
     * @see Tile
     * @see TerrainFeature
     */
    public void createTerrainImage(TileModel[][] tileMap) {



        Graphics2D g2 = (Graphics2D) terrainImage.getGraphics();
        // Set terrain image background color                                                   

        g2.setColor(Color.BLACK);
        // Paint it black!

        g2.fillRect(0, 0, boardInfo.getImageWidth(), boardInfo.getImageHeight());

        Image tileTerrainImage;
        Image tileFeaturesImage = null;


        int x = 0, y;
        int dx = boardInfo.getHexOffset();
        for (int i = 0; i < boardInfo.getWidth(); ++i) {
            for (int j = 0; j < boardInfo.getHeight(); ++j) {

                // The height depends on the column index 'j'.
                // It paints higher if 'j' is odd, lower if it's even
                y = boardInfo.getHexHeight() * (2 * j + ((i + 1) % 2)) / 2;

                // createFeaturesImage returns null if tile is non playable
                tileFeaturesImage = createFeaturesImage(tileMap[i][j]);
                if (tileFeaturesImage != null) {

                    tileTerrainImage = createTileImage(tileMap[i][j]);

                    g2.drawImage(tileFeaturesImage, x, y, this);
                    g2.drawImage(tileTerrainImage, x, y, null);
                    g2.drawImage(tileFeaturesImage, x, y, this);
                }
            }
            x += dx;

        }     
        g2.dispose();
    }

    /**
     * Returns an <code>Image</code> with all the features and terrains of a single tile
     *
     * @param tile tile to be represented
     * @return the tile image
     */
    public Image createTileImage(TileModel tile) {

        int w = boardInfo.getHexDiameter();
        int h = boardInfo.getHexHeight();
        // Prepare the buffer with the tile size
        BufferedImage tileImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = tileImage.createGraphics();
        
        // Get the index of the terrain image
        Map<Terrain, Integer> m = getTerrainToImageIndex(tile);

        // First layer is the open terrain
        g2.drawImage(terrainBufferMap.get().get(Terrain.OPEN).getSubimage(0, 0, w, h), 0, 0, null);
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
                if (terrainBufferMap.get().get(e.getKey()) == null) {
                    loadGraphics(e.getKey());
                }
                index = e.getKey().getIndexByDirections(e.getValue());
                cols = boardInfo.getImageProfile().getTerrainImageCols();

                //TODO create index converter. 64 bits reg that stores X in first 32 bit and Y in last 32 bits
                x = index / cols;
                y = index % cols;
                bi = terrainBufferMap.get().get(e.getKey()).getSubimage(y * w, x * h, w, h);
            }
            g2.drawImage(bi, 0, 0, null);
        }
        g2.dispose();
      return tileImage;
    }

    /**
     * Terrain class has a map with the index to the subimage based on
     * directions
     * The map associates the subimage position with the subimage
     * index
     *
     * For example, a road from north to south would have the index 72
     * "N NE SE S SW NW C" -> "1 0 0 1 0 0 0", binary to int -> 72
     *
     * This function returns a map with the terrain as key and its related index
     * as value
     *
     * @param tile tile to get directions
     * @return <code>Map<Terrain,Integer></code> map which associates a terrain
     *          with its image position
     * @see Terrain
     *
     */
    public Map<Terrain, Integer> getTerrainToImageIndex(TileModel tile) {

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
     * Creates the feature image. If tile is non playable, then it returns null
     *
     * @param tile
     * @param tileFeaturesImage where the images are painted
     * @return <code>Image</code> if tile is playable; otherwise null
     * @see TerrainFeatures
     */
    private Image createFeaturesImage(TileModel tile) {

        //TODO Tile features should be objects of TerrainFeatures class
        //     instead of ares.data.jaxb.TerrainFeature

        BufferedImage i = new BufferedImage(boardInfo.getHexDiameter(), boardInfo.getHexHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = (Graphics2D) i.getGraphics();
        int w = boardInfo.getHexDiameter();
        int h = boardInfo.getHexHeight();


        Set<TerrainFeatures> tf = tile.getTerrainFeatures();
        Iterator it = tf.iterator();
        while (it.hasNext()) {
            TerrainFeatures f = (TerrainFeatures) it.next();

            switch (f) {
                case NON_PLAYABLE:
                    return null;

                case AIRFIELD:
                    g2.drawImage(
                            terrainBufferMap.get().get(Terrain.OPEN).getSubimage(
                            w * TerrainFeatures.AIRFIELD.getCol(),
                            h * TerrainFeatures.AIRFIELD.getRow(),
                            w, h),
                            0, 0, this);
                    break;
                case ANCHORAGE:
                    g2.drawImage(
                            terrainBufferMap.get().get(Terrain.OPEN).getSubimage(
                            w * TerrainFeatures.ANCHORAGE.getCol(),
                            h * TerrainFeatures.ANCHORAGE.getRow(),
                            w, h),
                            0, 0, this);
                    break;
                case PEAK:
                    g2.drawImage(
                            terrainBufferMap.get().get(Terrain.OPEN).getSubimage(
                            w * TerrainFeatures.PEAK.getCol(),
                            h * TerrainFeatures.PEAK.getRow(),
                            w, h),
                            0, 0, this);
                    break;

                case CONTAMINATED:
                    g2.drawImage(
                            terrainBufferMap.get().get(Terrain.OPEN).getSubimage(
                            w * TerrainFeatures.CONTAMINATED.getCol(),
                            h * TerrainFeatures.CONTAMINATED.getRow(),
                            w, h),
                            0, 0, this);
                    break;
                case MUDDY:
                    g2.drawImage(
                            terrainBufferMap.get().get(Terrain.OPEN).getSubimage(
                            w * TerrainFeatures.MUDDY.getCol(),
                            h * TerrainFeatures.MUDDY.getRow(),
                            w, h), 0, 0, this);

                    break;

                //TODO Add all feature cases

                default:
                    break;
            }
        }
        
        g2.dispose();
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
        Graphics2D g2 = bi.createGraphics();

        for (Direction d : Direction.values()) {

            // Shift bites and draw when direction flag is 1
            if ((mask & value) == mask) {
                g2.drawImage(terrainBufferMap.get().get(Terrain.BORDER).getSubimage(2 * w, h * d.ordinal(), w, h), null, this);
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
    private void loadGraphics(Terrain t) {

        ImageProfile iP = boardInfo.getImageProfile();
        //for (Terrain terrain : Terrain.ALL_TERRAINS) {
        try {
            terrainBufferMap.get().put(t, ImageIO.read(
                    // File image
                    AresIO.ARES_IO.getFile(iP.getPath(), iP.getFileName(t))));
        } catch (IOException e) {

            System.out.println(e.getMessage() + "\nFile not found: " + iP.getFileName(t));

        }
        //}
    }

    /**
     * 
     */
    public void flushLayer() {
        terrainImage = null;
        terrainBufferMap = null;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(terrainImage, 0, 0, this);
    }
}
