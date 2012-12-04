package ares.application.gui_components.layers;

import ares.application.models.ScenarioModel;
import ares.application.models.board.*;
import ares.data.jaxb.TerrainFeature;
import ares.engine.knowledge.KnowledgeCategory;
import ares.io.*;
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
    
     /**
     * Creates the whole terrain image.
     * Paints all the playable tiles stored in <code>tileMap</code>
     *
     * @param s
     * @see Tile
     * @see TerrainFeature
     */
    @Override
    public void createGlobalImage(ScenarioModel s){
        
        Graphics2D g2 = globalImage.createGraphics();
        // Paint it black!
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, BoardGraphicsModel.getImageWidth(), BoardGraphicsModel.getImageHeight());
        g2.dispose();
        for (TileModel[] tt : s.getBoardModel().getMapModel()) {
            for(TileModel t : tt){
                paintTile(t);
            }
        }
    }
    
    /**
     *
     * @param t
     */
    @Override
    public void paintTile(TileModel t){

        //If I don't know anything about it
        if(t.getKnowledgeCategory() == KnowledgeCategory.NONE) return;
        
        //Calculate tile position
        Point pos = BoardGraphicsModel.tileToPixel(t.getCoordinates());

        //Final image graphics
        Graphics2D g2 = globalImage.createGraphics();
        
        BufferedImage features = getTerrainFeaturesImage(t);
        //If non playable, don't paint
        if(features == null) return;
        
        //Where the terrain will be painted
        // First layer is the open terrain
        BufferedImage terrainImage = getTerrainImage(Terrain.OPEN,0);
        g2.drawImage(terrainImage,pos.x,pos.y,this);
        
        // Get the index of the terrain image
        Map<Terrain, Integer> m = getTerrainToImageIndex(t);
        
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
                int index = e.getKey().getIndexByDirections(e.getValue());
                
                bi = getTerrainImage(e.getKey(), index);
            }
            
            g2.drawImage(bi, pos.x, pos.y, null);
        }
        g2.drawImage(features, pos.x, pos.y,null);
        
        repaint(pos.x, pos.y, terrainImage.getWidth(null), terrainImage.getHeight(null));
        g2.dispose();        
    }
    
     private BufferedImage getTerrainImage(Terrain t, int index){
        
        //Make graphics are loaded
        loadTerrainGraphics(t);
        
        //Get the coordinates
        int cols = BoardGraphicsModel.getImageProfile().getTerrainImageCols();
        int w = BoardGraphicsModel.getHexDiameter(), h = BoardGraphicsModel.getHexHeight();
        
        return terrainBufferMap.get(t).get().getSubimage(w * (index%cols), h * (index/cols), w, h);
     }


    /**
     * Terrain class has a map with the index to the subimage based on
     * directions.
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
    private Map<Terrain, Integer> getTerrainToImageIndex(TileModel tile) {

        Map<Direction, Set<Terrain>> sideTerrain = tile.getSideTerrain();

        SortedMap<Terrain, Integer> m = new TreeMap<>();
        Iterator it;
        for (Map.Entry<Direction, Set<Terrain>> e : sideTerrain.entrySet()) {
            it = e.getValue().iterator();
            while (it.hasNext()) {
                Terrain t = (Terrain) it.next();
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
     * Creates the feature image. If tile is non playable, then it returns null
     *
     * @param tile
     * @param tileFeaturesImage where the images are painted
     * @return <code>Image</code> if tile is playable; otherwise null
     * @see TerrainFeatures
     */
    private BufferedImage getTerrainFeaturesImage(TileModel tile) {
        
        BufferedImage i = new BufferedImage(BoardGraphicsModel.getHexDiameter(), BoardGraphicsModel.getHexHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = i.createGraphics();

        Set<TerrainFeatures> tf = tile.getTerrainFeatures();
        Iterator it = tf.iterator();
        while (it.hasNext()) {
            TerrainFeatures f = (TerrainFeatures) it.next();

            switch (f) {
                case NON_PLAYABLE:
                    return null;

                case AIRFIELD:
                    g2.drawImage(
                            getTerrainImage(Terrain.OPEN,
                            //I'm actually wasting cpu here
                            TerrainFeatures.AIRFIELD.getCol()+BoardGraphicsModel.getImageProfile().getTerrainImageCols()*TerrainFeatures.AIRFIELD.getRow()),
                            0, 0, this);
                    break;
                case ANCHORAGE:
                    g2.drawImage(
                            getTerrainImage(Terrain.OPEN,
                            TerrainFeatures.ANCHORAGE.getCol()+BoardGraphicsModel.getImageProfile().getTerrainImageCols()*TerrainFeatures.ANCHORAGE.getRow()),
                            0, 0, this);
                    break;
                case PEAK:
                    g2.drawImage(
                            getTerrainImage(Terrain.OPEN,
                            TerrainFeatures.PEAK.getCol()+BoardGraphicsModel.getImageProfile().getTerrainImageCols()*TerrainFeatures.PEAK.getRow()),
                            0, 0, this);
                    break;

                case CONTAMINATED:
                    g2.drawImage(
                            getTerrainImage(Terrain.OPEN,
                            TerrainFeatures.CONTAMINATED.getCol()+BoardGraphicsModel.getImageProfile().getTerrainImageCols()*TerrainFeatures.CONTAMINATED.getRow()),
                            0, 0, this);
                    break;
                case MUDDY:
                    g2.drawImage(
                            getTerrainImage(Terrain.OPEN,
                            TerrainFeatures.MUDDY.getCol()+BoardGraphicsModel.getImageProfile().getTerrainImageCols()*TerrainFeatures.MUDDY.getRow()),
                            0, 0, this);

                    break;

                case SNOWY:
                case BRIDGE_DESTROYED:
                case FROZEN:
                case EXCLUDED_1:
                case EXCLUDED_2:
                 break;
                default:
                    // We shouldng't get here
                    throw new AssertionError("Assertion failed: unkown terrain feature " + f.toString());
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
     * @see TerrainLayer#getTerrainToImageIndex()
     */
    private BufferedImage drawTileBorders(Integer value) {
        int mask = 64;
        int w = BoardGraphicsModel.getHexDiameter(), h = BoardGraphicsModel.getHexHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = bi.createGraphics();
            
        loadTerrainGraphics(Terrain.BORDER);
        
        for (Direction d : Direction.values()) {

            // Shift bites and draw when direction flag is 1
            if ((mask & value) == mask) {
                g2.drawImage(terrainBufferMap.get(Terrain.BORDER).get().getSubimage(2 * w, h * d.ordinal(), w, h), null, this);
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
            String filename = BoardGraphicsModel.getImageProfile().getFileName(t);
            BufferedImage i = loadImage(AresIO.ARES_IO.getFile(BoardGraphicsModel.getImageProfile().getPath(), filename));
            terrainBufferMap.put(t, new SoftReference<>(i));
        }
    }
}
