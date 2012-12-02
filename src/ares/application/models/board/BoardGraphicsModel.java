package ares.application.models.board;

import ares.io.ImageProfile;
import ares.scenario.board.Board;
import ares.scenario.board.Direction;
import java.awt.Point;
import java.io.File;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class BoardGraphicsModel {

    /**
     * Tile side
     *
     * @see ImageProfile#getHexSide()
     */
    public static int hexSide;
    /**
     * Tile diameter (vertex to opposite vertex)
     *
     * @see ImageProfile#getHexDiameter()
     */
    public static int hexDiameter;
    /**
     * Tile radius (vertex to hex center), computes as
     * <code>hexDiameter/2</code>
     *
     */
    public static int hexRadius;
    /**
     * Tile height (flat side to flat side)
     *
     * @see ImageProfile#getHexHeight
     */
    public static int hexHeight;
    /**
     * Tile side gradient
     *
     * @see ImageProfile#getHexRise()
     */
    public static double hexRise;
    /**
     * Tile offset position to draw in a new column
     *
     * @see ImageProfile method getHexOffset
     */
    public static int hexOffset;
    /**
     * board width in tile units
     */
    public static int tileColumns;
    /**
     * board height in tile units
     */
    public static int tileRows;
    /**
     * Image width in pixels
     */
    public static int imageWidth;
    /**
     * Image height in pixels
     */
    public static int imageHeight;
    private static ImageProfile imgProfile;

    public BoardGraphicsModel(Board board) {

        // Constant during the same scenario
        tileColumns = board.getWidth();
        tileRows = board.getHeight();

        // Variable information
        imgProfile = ImageProfile.MEDIUM;
        initGraphicVariables();
    }

    private void initGraphicVariables() {
        hexSide = imgProfile.getHexSide();
        hexDiameter = imgProfile.getHexDiameter();
        hexRadius = hexDiameter / 2;
        hexOffset = imgProfile.getHexOffset();
        hexHeight = imgProfile.getHexHeight();
        hexRise = imgProfile.getHexRise();
        /*
         * Width =  first column + (columns-1) * (around 3/4 Diameter)
         * Hexagons aren't regular
         */
        imageWidth = hexDiameter + (tileColumns - 1) * hexOffset;
        imageHeight = tileRows * hexHeight + hexHeight / 2;
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

    /**
     * Converts tile coordinates [x,y] to full index form
     *
     *
     * @param coordinates
     * @return X * Columns + Y
     */
    public static int tileMapIndex(Point coordinates) {
        return coordinates.x * tileColumns + coordinates.y;
    }

    /**
     *
     * @return grid hexagon file
     */
    public File getGridHex() {
        return new File(imgProfile.getPath(), "Hexoutline.png");
    }

    /**
     * @return current image profile (SMALL, MEDIUM or HIGH)
     * @see ImageProfile
     */
    public ImageProfile getImageProfile() {
        return imgProfile;
    }

    public void setImageProfile(ImageProfile ip) {
        imgProfile = ip;
        initGraphicVariables();
        //TODO Fire property change to let know the controller the model has changed.

    }

    /**
     * Converts a tile location to its corresponding pixel on the global image
     *
     * @param tile position to be converted
     * @return the pixel at the upper left corner of the square circumscribed
     * about the hexagon
     * @see BoardGraphicsModel
     * @see AbstractImageLayer
     */
    public static Point tileToPixel(Point tile) {
        return tileToPixel(tile.x, tile.y);
    }

    public static Point tileToPixel(int x, int y) {
        Point pixel = new Point();
        //X component is "row" times the "offset"
        pixel.x = hexOffset * x;
        //Y component depends on the row.
        //If it's even number, then "column" times the "height" plus half the height, if it's odd then just "column" times the "height"
        pixel.y = (x % 2 == 0 ? (hexHeight * y) + (hexHeight / 2) : (hexHeight * y));
        return pixel;
    }

    /**
     * Converts a pixel position to its corresponding tile index
     *
     * @param pixel position to be converted
     * @return the row (x) and column(y) where the tile is located at the tile
     * map
     * @see BoardGraphicsModel
     * @see Board getTile
     */
    public static Point pixelToTile(Point pixel) {
        return pixelToTile(pixel.x, pixel.y);
    }

    public static Point pixelToTile(int x, int y) {
        Point tile = new Point();
        tile.x = x / hexOffset;
        //If tile is on even row, first we substract half the hexagon height to the Y component, then we divide it by the height
        //if it's on odd row, divide Y component by hexagon height
        tile.y = (tile.x % 2 == 0 ? (y - (hexHeight / 2)) / hexHeight : (y / hexHeight));
        return tile;
    }

    /**
     *
     * @param pixel
     * @return
     */
    // Modified version of gamedev.net
    // http://www.gamedev.net/page/resources/_/technical/game-programming/coordinates-in-hexagon-based-tile-maps-r1800
    public static Point pixelToTileAccurate(Point pixel) {
        return pixelToTileAccurate(pixel.x, pixel.y);
    }

    private static Point pixelToTileAccurate(int x, int y) {

        // The map is composed of sections which can be of two types: A or B, each one with 3 areas
        // A sections are in odd columns. They have NW and SW neighbours, and the rest is the tile we want
        // B sections are in even columns. areas: puff... easier done than explained.

        int dy = hexHeight / 2;
        // gradient = dy/dx
        Point section = new Point(x / hexOffset, y / hexHeight);
        // Pixel within the section
        Point pixelInSection = new Point(x % hexOffset, y % hexHeight);


        if ((section.x % 2) == 1) {
            //odd column
            if ((-hexRise) * pixelInSection.x + dy > pixelInSection.y) {
                //Pixel is in the NW neighbour tile
                section.x--;
                section.y--;
            } else if (pixelInSection.x * hexRise + dy < pixelInSection.y) {
                //Pixel is in the SE neighbout tile
                section.x--;
            } else {
                //pixel is in our tile
            }
        } else {
            //even column
            if (pixelInSection.y < dy) {
                //upper side
                if ((hexRise * pixelInSection.x) > pixelInSection.y) {
                    //right side
                    //Pixel is in the N neighbour tile
                    section.y--;
                } else {
                    //left side
                    // SW
                    section.x--;

                }
            } else {
                //lower side
                if (((-hexRise) * pixelInSection.x + hexHeight) > pixelInSection.y) {
                    //left side
                    // SW
                    section.x--;
                } else {
                    // right side
                    // Pixel is in our tile 
                }
            }
        }
        return section;
    }

    public static boolean isWithinImageRange(Point pixel) {
        return ((pixel.x < imageWidth && pixel.x > 0) && (pixel.y > 0 && pixel.y < imageHeight));
    }
}
