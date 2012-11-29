package ares.application.models.board;

import ares.io.ImageProfile;
import ares.scenario.board.Board;
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
     * @see ImageProfile method getHexSide()
     */
    public static int hexSide;
    /**
     * Tile diameter (vertex to opposite vertex)
     *
     * @see ImageProfile method getHexDiameter()
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
     * @see ImageProfile method getHexHeight
     */
    public static int hexHeight;
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
    
    private void initGraphicVariables(){
        hexSide = imgProfile.getHexSide();
        hexDiameter = imgProfile.getHexDiameter();
        hexRadius = hexDiameter / 2;
        hexOffset = imgProfile.getHexOffset();
        hexHeight = imgProfile.getHexHeight();
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
    
    public void setImageProfile(ImageProfile ip){
        imgProfile = ip;
        initGraphicVariables();
        //TODO Fire property change and let know the controller the model has changed.
        
    }
    
    /**
     * Converts a tile location to its corresponding pixel on the global image
     * 
     * @param tile  position to be converted
     * @return the pixel at the upper left corner of the square circumscribed
     *         about the hexagon
     * @see BoardGraphicsModel
     * @see AbstractImageLayer
     */
    public static Point tileToPixel(Point tile){
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
     * @param pixel  position to be converted
     * @return the row (x) and column(y) where the tile is located at the tile map
     * @see BoardGraphicsModel
     * @see Board getTile
     */
    public static Point pixelToTile(Point pixel) {
        return pixelToTile(pixel.x,pixel.y);
    }
    public static Point pixelToTile(int x, int y) {
        Point tile = new Point();
        tile.x = x / hexOffset;
        //If tile is on even row, first we substract half the hexagon height to the Y component, then we divide it by the height
        //if it's on odd row, divide Y component by hexagon height
        tile.y = (tile.x % 2 == 0 ? (y - (hexHeight / 2)) / hexHeight : (y / hexHeight));
        return tile;
    }

    public static boolean isWithinImageRange(Point pixel){
        return ((pixel.x < imageWidth && pixel.x > 0) && (pixel.y > 0 && pixel.y < imageHeight));
    }
}
