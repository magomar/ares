/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ares.application.gui_components;

import ares.data.jaxb.TerrainFeature;
import ares.io.AresIO;
import ares.io.ImageProfile;
import ares.scenario.Scenario;
import ares.scenario.board.BoardInfo;
import ares.scenario.board.Direction;
import ares.scenario.board.Terrain;
import ares.scenario.board.Tile;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * Terrain image pane based on Sergio Musoles TerrainPanel
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class TerrainPane extends JPanel {

    private Image terrainImage = null;
    Map<Terrain, BufferedImage> terrainMap;
    private BoardInfo boardInfo;
    private String lastScenario;

    /**
     * Initializes the pane with the terrain image it will create a the image
     * when a new scenario is opened
     *
     * @param scenario current scenario information
     *
     */
    public void initialize(Scenario scenario) {


        if (lastScenario == null || !lastScenario.equals(scenario.getName())) {
            /*
             *  Create new scenario
             */
            terrainMap = new EnumMap<>(Terrain.class);
            boardInfo = scenario.getBoardInfo();
            terrainImage = new BufferedImage(
                    // Image size
                    boardInfo.getImageWidth(), boardInfo.getImageHeight(),
                    // RGB + Alpha
                    BufferedImage.TYPE_4BYTE_ABGR);
            Tile[][] tileMap = scenario.getBoard().getMap();
            loadGraphics(boardInfo.getImageProfile());

            createTerrainImage(tileMap);
            lastScenario = scenario.getName();
        } else {
            /*
             * Scenario already created
             */
        }
        repaint();

    }

    /**
     * Puts in memory all terrain graphics
     *
     * @param imageProfile information about the image file paths
     */
    // TODO Load only used terrain
    private void loadGraphics(ImageProfile imageProfile) {
        for (Terrain terrain : Terrain.ALL_TERRAINS) {
            try {
                terrainMap.put(terrain, ImageIO.read(
                        // File image
                        AresIO.ARES_IO.getFile(imageProfile.getPath(), imageProfile.getFileName(terrain))));
            } catch (IOException e) {

                System.out.println(e.getMessage() + "\nFile not found: " + imageProfile.getFileName(terrain));

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
        terrainGraphics.setColor(Color.BLACK);
        terrainGraphics.fillRect(0, 0, boardInfo.getImageWidth(), boardInfo.getImageHeight());

        int x = 0, y;
        int dx = boardInfo.gexHexOffset();
        // = createTileImage(tileMap[12][7]);
        for (int i = 0; i < boardInfo.getWidth(); ++i) {
            for (int j = 0; j < boardInfo.getHeight(); ++j) {
                y = boardInfo.getHexHeight() * (2 * j + ((i + 1) % 2)) / 2;
                if (playableTile(tileMap[i][j])) {
                    terrainGraphics.drawImage(createTileImage(tileMap[i][j]), x, y, null);
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
    private Image createTileImage(Tile tile) {


        //TODO draw grid
        //TODO draw features
        //TODO take w and h out of the loop
        int w = boardInfo.getHexDiameter();
        int h = boardInfo.getHexHeight();
        BufferedImage tileImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);

        Graphics2D tileGraphics = (Graphics2D) tileImage.createGraphics();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
        tileGraphics.setComposite(ac);

        // With grid
        //tileGraphics.drawImage(ImageUtils.loadImage(AresIO.ARES_IO.getFile(boardInfo.getImageProfile().getPath(), "Hexoutline.png")), 0, 0, this);
        tileGraphics.drawImage(terrainMap.get(Terrain.OPEN).getSubimage(0, 0, w, h), 0, 0, null);
        Map<Terrain, Integer> m = getTerrainToImageIndex(tile);

        int index = 0, cols, x, y;
        System.out.println(tile.toString());
        for (Map.Entry<Terrain, Integer> e : m.entrySet()) {

            BufferedImage bi;
            if (e.getKey() == Terrain.BORDER) {
                bi = drawTileBorders(e.getValue());
            } else {
                index = e.getKey().getIndexByDirections(e.getValue());
                cols = boardInfo.getImageProfile().getTerrainImageCols();
                x = index / cols;
                y = index % cols;
                bi = terrainMap.get(e.getKey()).getSubimage(y * w, x * h, w, h);
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

        //TODO add 
        Set<Terrain> tileTerrain = tile.getTileTerrain();
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
     * Checks if the tile is playable
     *
     * @param tile
     * @return true if playable, false otherwise
     */
    private boolean playableTile(Tile tile) {

        if (tile.getFeatures().isEmpty()) {
            return true;
        } else {
            Set<TerrainFeature> tf = tile.getFeatures();
            Iterator it = tf.iterator();
            while (it.hasNext()) {
                TerrainFeature f = (TerrainFeature) it.next();
                if (f == TerrainFeature.NON_PLAYABLE) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Since borders are in a special file, they need a special method
     * which draws all borders of a tile
     * 
     * @param value Integer with its bits as directions
     * @return  Image with all borders painted
     * @see getTerrainToImageIndex()
     */
    private BufferedImage drawTileBorders(Integer value) {
        int mask = 64;
        int w = boardInfo.getHexDiameter();
        int h = boardInfo.getHexHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = bi.createGraphics();

        for (Direction d : Direction.values()) {
            if ((mask & value) == mask) {
                //Draw
                g.drawImage(terrainMap.get(Terrain.BORDER).getSubimage(2 * w, h * d.ordinal(), w, h), null, this);
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
