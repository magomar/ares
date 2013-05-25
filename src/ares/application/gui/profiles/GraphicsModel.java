package ares.application.gui.profiles;

import ares.application.gui.providers.GraphicsDescriptor;
import ares.application.gui.providers.ImageProvider;
import ares.scenario.board.Board;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * This class provides information on the graphics being used for a particular scenario
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public class GraphicsModel {

    public static final GraphicsModel INSTANCE = new GraphicsModel();
    /**
     * Board width in tiles
     */
    private int tileColumns;
    /**
     * Board height in tiles
     */
    private int tileRows;
    /**
     * Board image width in pixels
     */
    private int imageWidth;
    /**
     * Board image height in pixels
     */
    private int imageHeight;
    /**
     * Graphics profile currently in use. Typically a profile corresponds to a different zoom level, but it could also
     * correspond to an alternate graphics set
     */
    private GraphicsProfile activeProfile;
    private Map<GraphicsDescriptor, ImageProvider> activeProviders;
    int activeProfileIndex;
    private GraphicsProfile[] profiles;
    private List<Map<GraphicsDescriptor, ImageProvider>> providers;
//    private static final Logger LOG = Logger.getLogger(GraphicsModel.class.getName());


    private GraphicsModel() {
        Properties properties = GraphicProperties.GRAPHICS;
        Enumeration keys  = properties.propertyNames();
        while (keys.hasMoreElements() ) {
            String propName = (String) keys.nextElement();
            System.out.println(propName);
        }

    }

    public void initialize(Board board, GraphicsProfile[] profiles) {

        tileColumns = board.getWidth();
        tileRows = board.getHeight();
        this.profiles = profiles;
        providers = new ArrayList<>();
        for (int i = 0; i < profiles.length; i++) {
            providers.add(new HashMap<GraphicsDescriptor, ImageProvider>());
        }
        setActiveProfile(0);
    }

    public void addGraphics(GraphicsDescriptor descriptor) {
        for (int i = 0; i < profiles.length; i++) {
            ImageProvider newImageProvider = descriptor.getImageProviderType().createImageProvider(descriptor.getFilename(), descriptor.getRows(), descriptor.getColumns(), profiles[i]);
            providers.get(i).put(descriptor, newImageProvider);
        }
    }

    public void addAllGraphics(GraphicsDescriptor[] descriptors) {
        for (int i = 0; i < profiles.length; i++) {
            Map<GraphicsDescriptor, ImageProvider> providersMap = providers.get(i);
            for (GraphicsDescriptor descriptor : descriptors) {
                ImageProvider newImageProvider = descriptor.getImageProviderType().createImageProvider(descriptor.getFilename(), descriptor.getRows(), descriptor.getColumns(), profiles[i]);
                providersMap.put(descriptor, newImageProvider);
            }
        }
    }

    public ImageProvider getActiveProvider(GraphicsDescriptor descriptor) {
        return activeProviders.get(descriptor);
    }

    /*
     * Width =  first column + (columns-1) * offset (around 3/4 Diameter)
     * Hexagons aren't regular
     */
    private void setActiveProfile(int profileIndex) {
        this.activeProfileIndex = profileIndex;
        activeProfile = profiles[activeProfileIndex];
        activeProviders = providers.get(activeProfileIndex);
        imageWidth = getHexDiameter() + (tileColumns - 1) * getHexOffset();
        imageHeight = tileRows * getHexHeight() + getHexHeight() / 2;

    }

    public GraphicsProfile getActiveProfile() {
        return activeProfile;
    }

    public void nextActiveProfile() {
        if (activeProfileIndex < profiles.length - 1) {
            setActiveProfile(activeProfileIndex + 1);
        }
    }

    public void previousActiveProfile() {
        if (activeProfileIndex > 0) {
            setActiveProfile(activeProfileIndex - 1);
        }
    }

    public int getTileRows() {
        return tileRows;
    }

    public int getTileColumns() {
        return tileColumns;
    }

    /**
     *
     * @return the image width in pixesl
     */
    public int getImageWidth() {
        return imageWidth;
    }

    /**
     *
     * @return the image height in pixels
     */
    public int getImageHeight() {
        return imageHeight;
    }

    /**
     * Check valid coordinates
     *
     * @param i as row
     * @param j as column
     * @return true if (i,j) is within the board range
     */
    public boolean validCoordinates(int i, int j) {
        return i >= 0 && i < tileColumns && j >= 0 && j < tileRows;
    }

    /**
     * Tile diameter (vertex to opposite vertex)
     *
     * @see AresGraphicsProfile#getHexDiameter()
     */
    public int getHexDiameter() {
        return activeProfile.getHexDiameter();
    }

    /**
     * Tile side
     *
     * @see GraphicsProfile#getHexSide()
     */
    public int getHexSide() {
        return activeProfile.getHexSide();
    }

    /**
     * Tile offset position to draw in a new column
     *
     * @see GraphicsProfile#getHexOffset()
     */
    public int getHexOffset() {
        return activeProfile.getHexOffset();
    }

    /**
     * Tile height (flat side to flat side)
     *
     * @see GraphicsProfile#getHexHeight()
     */
    public int getHexHeight() {
        return activeProfile.getHexHeight();
    }

    /**
     * Tile side gradient
     *
     * @see GraphicsProfile#getHexRise()
     */
    public double getHexRise() {
        return activeProfile.getHexRise();
    }

    /**
     * Converts a tile location to its corresponding pixel on the global image
     *
     * @param tile position to be converted
     * @return the pixel at the upper left corner of the square circumscribed about the hexagon
     * @see AresGraphicsModel
     * @see AbstractImageLayer
     */
    public Point tileToPixel(Point tile) {
        return tileToPixel(tile.x, tile.y);
    }

    public Point tileToPixel(int x, int y) {
        Point pixel = new Point();
        //X component is "row" times the "offset"
        pixel.x = getHexOffset() * x;
        //Y component depends on the row.
        //If it's even number, then "column" times the "height" plus half the height, if it's odd then just "column" times the "height"
        pixel.y = (x % 2 == 0 ? (getHexHeight() * y) + (getHexHeight() / 2) : (getHexHeight() * y));
        return pixel;
    }

    /**
     * Converts a pixel position to its corresponding tile index
     *
     * @param pixel position to be converted
     * @return the row (x) and column(y) where the tile is located at the tile map
     * @see AresGraphicsModel
     * @see Board getTile
     */
    public Point pixelToTile(Point pixel) {
        return pixelToTile(pixel.x, pixel.y);
    }

    public Point pixelToTile(int x, int y) {
        Point tile = new Point();
        tile.x = x / getHexOffset();
        //If tile is on even row, first we substract half the hexagon height to the Y component, then we divide it by the height
        //if it's on odd row, divide Y component by hexagon height
        tile.y = (tile.x % 2 == 0 ? (y - (getHexHeight() / 2)) / getHexHeight() : (y / getHexHeight()));
        return tile;
    }

    /**
     * Converts pixel coordinates into tile coordinates using an accurate method
     *
     * @param pixel
     * @return the map coordinates corresponding to the pixel coordinates passed as a parameter
     */
    public Point pixelToTileAccurate(Point pixel) {
        return pixelToTileAccurate(pixel.x, pixel.y);
    }

    /**
     * Accurately converts pixel coordinates into tile coordinates
     *
     * Adapted from article in <a
     * href="http://www.gamedev.net/page/resources/_/technical/game-programming/coordinates-in-hexagon-based-tile-maps-r1800">gamedev.net</a>
     *
     * The map is composed of sections which can be of two types: A or B, each one with 3 areas.
     *
     * A sections are in odd columns. They have NW and SW neighbors, and the rest is the tile we want
     *
     * B sections are in even columns. areas: puff... easier done than explained.
     *
     * @param x
     * @param y
     * @return
     */
    public Point pixelToTileAccurate(int x, int y) {

        int dy = getHexHeight() / 2;
        // gradient = dy/dx
        Point section = new Point(x / getHexOffset(), y / getHexHeight());
        // Pixel within the section
        Point pixelInSection = new Point(x % getHexOffset(), y % getHexHeight());


        if ((section.x % 2) == 1) {
            //odd column
            if ((-getHexRise()) * pixelInSection.x + dy > pixelInSection.y) {
                //Pixel is in the NW neighbor tile
                /*  ________
                 *  |x /    |
                 *  |<      |
                 *  |__\____| 
                 */
                section.x--;
                section.y--;

            } else if (pixelInSection.x * getHexRise() + dy < pixelInSection.y) {
                //Pixel is in the SE neighbout tile     
                /*  ________
                 *  |  /    |
                 *  |<      |
                 *  |x_\____| 
                 */
                section.x--;
            } else {
                //pixel is in our tile
                /*  ________
                 *  |  /    |
                 *  |<   x  |
                 *  |__\____| 
                 */
            }
        } else {
            //even column
            if (pixelInSection.y < dy) {
                //upper side
                if ((getHexRise() * pixelInSection.x) > pixelInSection.y) {
                    //right side
                    /* Pixel is in the N neighbor tile
                     * ________
                     * | \  x  |
                     * |  >----|
                     * |_/_____|
                     */

                    section.y--;
                } else {
                    /* Left side
                     * ________
                     * |x\     |
                     * |  >----|
                     * |_/_____|
                     */
                    section.x--;

                }
            } else {
                //lower side
                if (((-getHexRise()) * pixelInSection.x + getHexHeight()) > pixelInSection.y) {
                    /* Left side
                     * ________
                     * | \     |
                     * |  >----|
                     * |x/_____|
                     */
                    section.x--;
                } else {
                    /* Pixel is in our tile 
                     * ________
                     * | \     |
                     * |  >----|
                     * |_/__x__|
                     */
                }
            }
        }
        return section;
    }

    public boolean isWithinImageRange(Point pixel) {
        return ((pixel.x < imageWidth && pixel.x > 0) && (pixel.y > 0 && pixel.y < imageHeight));
    }

    public boolean isWithinImageRange(int x, int y) {
        return ((x < imageWidth && x > 0) && (y > 0 && y < imageHeight));
    }

    //    public static int computeBoardImageWidth(GraphicsProfile profile, int columns) {
    //        return profile.getHexDiameter() + (columns - 1) * profile.getHexOffset();
    //
    //    }
    //
    //    public static int computeBoardImageHeight(GraphicsProfile profile, int boardHeight) {
    //        return boardHeight * profile.getHexHeight() + profile.getHexHeight() / 2;
    //    }
    
    public GraphicsProfile[] getProfiles() {
        return profiles;
    }
    
    public Map<GraphicsDescriptor, ImageProvider> getImageProviders(int profileIndex) {
        return providers.get(profileIndex);
    }
}
