package ares.application.models.board;

import ares.application.gui.AbstractImageLayer;
import ares.io.ImageProfile;
import ares.scenario.board.Board;
import java.awt.Point;

/**
 * This class provides information on the graphics being used for a particular scenario
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public class BoardGraphicsModel {

    /**
     * board width in tile units
     */
    private static int tileColumns;
    /**
     * board height in tile units
     */
    private static int tileRows;
    /**
     * Image width in pixels
     */
    private static int imageWidth;
    /**
     * Image height in pixels
     */
    private static int imageHeight;
    private static ImageProfile imgProfile;

    public BoardGraphicsModel(Board board) {

        // Constant during the same scenario
        tileColumns = board.getWidth();
        tileRows = board.getHeight();

        // Variable information
        imgProfile = ImageProfile.MEDIUM;
        initGraphicVariables();
    }

    private static void initGraphicVariables() {
        /*
         * Width =  first column + (columns-1) * (around 3/4 Diameter)
         * Hexagons aren't regular
         */
        imageWidth = getHexDiameter() + (tileColumns - 1) * getHexOffset();
        imageHeight = tileRows * getHexHeight() + getHexHeight() / 2;
    }

    public static int getTileRows() {
        return tileRows;
    }

    public static int getTileColumns() {
        return tileColumns;
    }

    /**
     * Tile diameter (vertex to opposite vertex)
     *
     * @see ImageProfile#getHexDiameter()
     */
    public static int getHexDiameter() {
        return imgProfile.getHexDiameter();
    }

    /**
     * Tile radius (vertex to hex center), computes as
     * <code>hexDiameter/2</code>
     *
     */
    public static int getHexRadius() {
        return getHexDiameter() / 2;
    }

    /**
     * Tile side
     *
     * @see ImageProfile#getHexSide()
     */
    public static int getHexSide() {
        return imgProfile.getHexSide();
    }

    /**
     * Tile offset position to draw in a new column
     *
     * @see ImageProfile method getHexOffset
     */
    public static int getHexOffset() {
        return imgProfile.getHexOffset();
    }

    /**
     * Tile height (flat side to flat side)
     *
     * @see ImageProfile#getHexHeight
     */
    public static int getHexHeight() {
        return imgProfile.getHexHeight();
    }

    /**
     * Tile side gradient
     *
     * @see ImageProfile#getHexRise()
     */
    public static double getHexRise() {
        return imgProfile.getHexRise();
    }

    public static int getImageWidth() {
        return imageWidth;
    }

    public static int getImageHeight() {
        return imageHeight;
    }

    /**
     * Check valid coordinates
     *
     * @param i as row
     * @param j as column
     * @return true if (i,j) is within the board range
     */
    public static boolean validCoordinates(int i, int j) {
        return i >= 0 && i < tileColumns && j >= 0 && j < tileRows;
    }

//    /**
//     * Converts tile coordinates [x,y] to full index form
//     *
//     *
//     * @param coordinates
//     * @return X * Columns + Y
//     */
//    public static int tileMapIndex(Point coordinates) {
//        return coordinates.x * tileColumns + coordinates.y;
//    }
    /**
     * @return current image profile (SMALL, MEDIUM or HIGH)
     * @see ImageProfile
     */
    public static ImageProfile getImageProfile() {
        return imgProfile;
    }

    public static void setImageProfile(ImageProfile ip) {
        imgProfile = ip;
        initGraphicVariables();
    }

    /**
     * Converts a tile location to its corresponding pixel on the global image
     *
     * @param tile position to be converted
     * @return the pixel at the upper left corner of the square circumscribed about the hexagon
     * @see BoardGraphicsModel
     * @see AbstractImageLayer
     */
    public static Point tileToPixel(Point tile) {
        return tileToPixel(tile.x, tile.y);
    }

    public static Point tileToPixel(int x, int y) {
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
     * @see BoardGraphicsModel
     * @see Board getTile
     */
    public static Point pixelToTile(Point pixel) {
        return pixelToTile(pixel.x, pixel.y);
    }

    public static Point pixelToTile(int x, int y) {
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
     * @return
     */
    public static Point pixelToTileAccurate(Point pixel) {
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
     * A sections are in odd columns. They have NW and SW neighbours, and the rest is the tile we want
     *
     * B sections are in even columns. areas: puff... easier done than explained.
     *
     * @param x
     * @param y
     * @return
     */
    public static Point pixelToTileAccurate(int x, int y) {

        int dy = getHexHeight() / 2;
        // gradient = dy/dx
        Point section = new Point(x / getHexOffset(), y / getHexHeight());
        // Pixel within the section
        Point pixelInSection = new Point(x % getHexOffset(), y % getHexHeight());


        if ((section.x % 2) == 1) {
            //odd column
            if ((-getHexRise()) * pixelInSection.x + dy > pixelInSection.y) {
                //Pixel is in the NW neighbour tile
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
                    /* Pixel is in the N neighbour tile
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

    public static boolean isWithinImageRange(Point pixel) {
        return ((pixel.x < imageWidth && pixel.x > 0) && (pixel.y > 0 && pixel.y < imageHeight));
    }

    public static boolean isWithinImageRange(int x, int y) {
        return ((x < imageWidth && x > 0) && (y > 0 && y < imageHeight));
    }
}
