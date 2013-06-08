package ares.application.shared.gui.profiles;

import ares.application.shared.gui.decorators.ImageDecorators;
import ares.application.shared.gui.views.layerviews.AbstractImageLayerView;
import ares.application.shared.gui.providers.ImageProviderFactory;
import ares.application.shared.gui.providers.ImageProvider;
import ares.platform.scenario.board.Board;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
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
    private int boardColumns;
    /**
     * Board height in tiles
     */
    private int boardRows;
    /**
     * Board image width in pixels
     */
    private int[] boardWidth;
    /**
     * Board image height in pixels
     */
    private int[] boardHeight;
    /**
     * Graphics profile currently in use. Typically a profile corresponds to a different zoom level (although profiles
     * could possibly be used to use alternate graphics)
     */
    private int activeProfile;
    private final int numProfiles;
    private List<Map<ImageProviderFactory, ImageProvider>> providers;
    private static final double hexRise = GraphicProperties.getRealProperty(NonProfiledGraphicProperty.TILE_RISE);
    private ImageDecorators[] imageDecorators;

    private GraphicsModel() {
        this.numProfiles = GraphicProperties.getNumProfiles();
        providers = new ArrayList<>();
        imageDecorators = new ImageDecorators[numProfiles];
        for (int i = 0; i < numProfiles; i++) {
            providers.add(new HashMap<ImageProviderFactory, ImageProvider>());
        }
        for (int i = 0; i < numProfiles; i++) {
            imageDecorators[i] = new ImageDecorators(i);
        }
        boardWidth = new int[numProfiles];
        boardHeight = new int[numProfiles];
        activeProfile = GraphicProperties.getNumProfiles() / 2;
    }

    public void initialize(Board board) {
        boardColumns = board.getWidth();
        boardRows = board.getHeight();
        for (int i = 0; i < numProfiles; i++) {
            boardWidth[i] = (int) (GraphicProperties.getProperty(ProfiledGraphicProperty.TILE_WIDTH, i)+ (boardColumns - 1) * GraphicProperties.getRealProperty(ProfiledGraphicProperty.TILE_OFFSET, i));
            int hexHeight = GraphicProperties.getProperty(ProfiledGraphicProperty.TILE_HEIGHT,i);
            boardHeight[i] = boardRows * hexHeight + hexHeight / 2;
            System.out.println(Double.toString((double)boardWidth[i]/boardHeight[i]));
        }
        System.out.println("Widths= " + Arrays.toString(boardWidth));
        System.out.println("Heights= " + Arrays.toString(boardHeight));
        System.out.print("");
    }

    public void addGraphics(ImageProviderFactory factory) {
        for (int i = 0; i < numProfiles; i++) {
            providers.get(i).put(factory, factory.createImageProvider(i));
        }
    }

    public void addAllGraphics(ImageProviderFactory[] factories) {
        for (int i = 0; i < numProfiles; i++) {
            Map<ImageProviderFactory, ImageProvider> providersMap = providers.get(i);
            for (ImageProviderFactory factory : factories) {
                providersMap.put(factory, factory.createImageProvider(i));
            }
        }
    }

//    public ImageProvider getActiveProvider(ImageProviderFactory factory) {
//        return activeProviders.get(factory);
//    }
    public ImageProvider getImageProvider(ImageProviderFactory factory, int profile) {
        return providers.get(profile).get(factory);
    }

    public int getActiveProfile() {
        return activeProfile;
    }

    public int nextActiveProfile() {
        if (activeProfile < numProfiles - 1) {
            activeProfile++;
        }
        return activeProfile;
    }

    public int previousActiveProfile() {
        if (activeProfile > 0) {
            activeProfile--;
        }
        return activeProfile;
    }

    public int getBoardRows() {
        return boardRows;
    }

    public int getBoardColumns() {
        return boardColumns;
    }

    /**
     *
     * @return the board image width in pixesl
     */
    public int getBoardWidth(int profile) {
        return boardWidth[profile];
    }

    /**
     *
     * @return the board image height in pixels
     */
    public int getBoardHeight(int profile) {
        return boardHeight[profile];
    }


    /**
     * Check valid coordinates
     *
     * @param i as row
     * @param j as column
     * @return true if (i,j) is within the board range
     */
    public boolean validCoordinates(int i, int j) {
        return i >= 0 && i < boardColumns && j >= 0 && j < boardRows;
    }

    /**
     * Converts a tile location to its corresponding pixel on the global image
     *
     * @param tile position to be converted
     * @return the pixel at the upper left corner of the square circumscribed about the hexagon
     * @see AresGraphicsModel
     * @see AbstractImageLayerView
     */
    public Point tileToPixel(Point tile, int profile) {
        return tileToPixel(tile.x, tile.y, profile);
    }

    public Point tileToPixel(int x, int y, int profile) {
        Point pixel = new Point();
        //X component is "row" times the "offset"
        pixel.x = (int) (GraphicProperties.getRealProperty(ProfiledGraphicProperty.TILE_OFFSET, profile) * x);
        //Y component depends on the row.
        //If it's even number, then "column" times the "height" plus half the height, if it's odd then just "column" times the "height"
        int hexHeight = GraphicProperties.getProperty(ProfiledGraphicProperty.TILE_HEIGHT, profile);
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
    public Point pixelToTile(Point pixel, int profile) {
        return pixelToTile(pixel.x, pixel.y, profile);
    }

    public Point pixelToTile(int x, int y, int profile) {
        Point tile = new Point();
        tile.x = (int) (x / GraphicProperties.getRealProperty(ProfiledGraphicProperty.TILE_OFFSET, profile));
        //If tile is on even row, first we substract half the hexagon height to the Y component, then we divide it by the height
        //if it's on odd row, divide Y component by hexagon height
        int hexHeight = GraphicProperties.getProperty(ProfiledGraphicProperty.TILE_HEIGHT, profile);
        tile.y = (tile.x % 2 == 0 ? (y - (hexHeight / 2)) / hexHeight : (y / hexHeight));
        return tile;
    }

    /**
     * Converts pixel coordinates into tile coordinates using an accurate method
     *
     * @param pixel
     * @return the map coordinates corresponding to the pixel coordinates passed as a parameter
     */
    public Point pixelToTileAccurate(Point pixel, int profile) {
        return pixelToTileAccurate(pixel.x, pixel.y, profile);
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
    public Point pixelToTileAccurate(int x, int y, int profile) {

        int hexHeight = GraphicProperties.getProperty(ProfiledGraphicProperty.TILE_HEIGHT, profile);
        int dy = hexHeight / 2;
        // gradient = dy/dx
        double hexOffset = GraphicProperties.getRealProperty(ProfiledGraphicProperty.TILE_OFFSET, profile);
        Point section = new Point((int) (x / hexOffset), y / hexHeight);
        // Pixel within the section
        Point pixelInSection = new Point((int) (x % hexOffset), y % hexHeight);

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

    public boolean isWithinImageRange(Point pixel, int profile) {
        return ((pixel.x < boardWidth[profile] && pixel.x > 0) && (pixel.y > 0 && pixel.y < boardHeight[profile]));
    }

    public boolean isWithinImageRange(int x, int y, int profile) {
        return ((x < boardWidth[profile] && x > 0) && (y > 0 && y < boardHeight[profile]));
    }

    public int getNumProfiles() {
        return numProfiles;
    }

    public Map<ImageProviderFactory, ImageProvider> getImageProviders(int profile) {
        return providers.get(profile);
    }

    public ImageDecorators getImageDecorators(int profile) {
        return imageDecorators[profile];
    }
//    public int getActiveProfilerProperty(ProfiledGraphicProperty property) {
//        return GraphicProperties.getProperty(property, activeProfileIndex);
//    }
}
