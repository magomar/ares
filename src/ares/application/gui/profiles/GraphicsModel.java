package ares.application.gui.profiles;

import ares.application.gui.layers.AbstractImageLayer;
import ares.application.gui.providers.GraphicsDescriptor;
import ares.application.gui.providers.ImageProvider;
import ares.scenario.board.Board;
import config.GraphicProperty;
import config.NonProfiledGraphicProperty;
import config.ProfiledGraphicProperty;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    static final double hexRise = GraphicProperties.getReal(NonProfiledGraphicProperty.TILE_RISE);

    private GraphicsModel() {
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
        imageWidth = getProperty(ProfiledGraphicProperty.TILE_WIDTH) + (tileColumns - 1) * getProperty(ProfiledGraphicProperty.TILE_OFFSET);
        int hexHeight = getProperty(ProfiledGraphicProperty.TILE_HEIGHT);
        imageHeight = tileRows * hexHeight + hexHeight / 2;

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
        pixel.x = getProperty(ProfiledGraphicProperty.TILE_OFFSET) * x;
        //Y component depends on the row.
        //If it's even number, then "column" times the "height" plus half the height, if it's odd then just "column" times the "height"
        int hexHeight = getProperty(ProfiledGraphicProperty.TILE_HEIGHT);
        pixel.y = (x % 2 == 0 ? (hexHeight * y) + (hexHeight / 2) : (hexHeight * y));
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
        tile.x = x / getProperty(ProfiledGraphicProperty.TILE_OFFSET);
        //If tile is on even row, first we substract half the hexagon height to the Y component, then we divide it by the height
        //if it's on odd row, divide Y component by hexagon height
        int hexHeight = getProperty(ProfiledGraphicProperty.TILE_HEIGHT);
        tile.y = (tile.x % 2 == 0 ? (y - (hexHeight / 2)) / hexHeight : (y / hexHeight));
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

        int hexHeight = getProperty(ProfiledGraphicProperty.TILE_HEIGHT);
        int dy = hexHeight / 2;
        // gradient = dy/dx
        int hexOffset = getProperty(ProfiledGraphicProperty.TILE_OFFSET);
        Point section = new Point(x / hexOffset, y / hexHeight);
        // Pixel within the section
        Point pixelInSection = new Point(x % hexOffset, y % hexHeight);

        if ((section.x % 2) == 1) {
            //odd column
            if ((-hexRise) * pixelInSection.x + dy > pixelInSection.y) {
                //Pixel is in the NW neighbor tile
                /*  ________
                 *  |x /    |
                 *  |<      |
                 *  |__\____| 
                 */
                section.x--;
                section.y--;

            } else if (pixelInSection.x * hexRise + dy < pixelInSection.y) {
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
                if ((hexRise * pixelInSection.x) > pixelInSection.y) {
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
                if (((-hexRise) * pixelInSection.x + hexHeight) > pixelInSection.y) {
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

    public GraphicsProfile[] getProfiles() {
        return profiles;
    }

    public Map<GraphicsDescriptor, ImageProvider> getImageProviders(int profileIndex) {
        return providers.get(profileIndex);
    }

    public int getProperty(GraphicProperty property) {
        if (property.isProfiled()) {
            return getProperty((ProfiledGraphicProperty) property);
        } else {
            return getProperty((NonProfiledGraphicProperty) property);
        }
    }

    private int getProperty(NonProfiledGraphicProperty property) {
        return GraphicProperties.getInt(property);
    }

    private int getProperty(ProfiledGraphicProperty property) {
        return GraphicProperties.getInt(property, activeProfile);
    }
}
