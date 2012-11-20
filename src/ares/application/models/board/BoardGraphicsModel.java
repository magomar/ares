package ares.application.models.board;

import ares.io.ImageProfile;
import ares.scenario.board.Board;
import java.awt.Point;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class BoardGraphicsModel {

    final private int hexSide;
    final private int hexDiameter;
    final private int hexRadius;
    final private int hexHeight;
    final private int hexOffset;
    final private int width;
    final private int height;
    final private int imageWidth;
    final private int imageHeight;
    final private ImageProfile imgProfile;

    public BoardGraphicsModel(Board board) {
        width = board.getWidth();
        height = board.getHeight();

        imgProfile = ImageProfile.MEDIUM;
        hexSide = imgProfile.getHexSide();
        hexDiameter = imgProfile.getHexDiameter();
        hexRadius = hexDiameter / 2;
        hexOffset = imgProfile.getHexOffset();
        hexHeight = imgProfile.getHexHeight();

        /*
         * Width =  first column + (columns-1) * (around 3/4 Diameter)
         * Hexagons aren't regular
         */
        imageWidth = hexDiameter + (width - 1) * hexOffset;

        imageHeight = height * hexHeight + hexHeight / 2;
    }

    /**
     * Check valid coordinates
     *
     * @param i as row
     * @param j as column
     * @return true if (i,j) is within the board range
     */
    public boolean validCoordinates(int i, int j) {
        return i >= 0 && i < width && j >= 0 && j < height;
    }

    /**
     * Tile diameter (vertex to opposite vertex)
     *
     * @see ImageProfile method getHexDiameter()
     */
    public int getHexDiameter() {
        return hexDiameter;
    }

    /**
     * Tile radius (vertex to hex center), computes as <code>hexDiameter/2</code>
     *
     */
    public int getHexRadius() {
        return hexRadius;
    }

    /**
     * Tile side
     *
     * @see ImageProfile method getHexSide()
     */
    public int getHexSide() {
        return hexSide;
    }

    /**
     * Tile offset position to draw in a new column
     *
     * @see ImageProfile method getHexOffset
     */
    public int getHexOffset() {
        return hexOffset;
    }

    /**
     * Tile height (flat side to flat side)
     *
     * @see ImageProfile method getHexHeight
     */
    public int getHexHeight() {
        return hexHeight;
    }

    /**
     * @return board width in tile units
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return board height in tile units
     */
    public int getHeight() {
        return height;
    }

    /**
     * Image height in pixels
     */
    public int getImageHeight() {
        return imageHeight;
    }

    /**
     * Image width in pixels
     */
    public int getImageWidth() {
        return imageWidth;
    }

    /**
     * @return current image profile (SMALL, MEDIUM or HIGH)
     * @see ImageProfi;e
     */
    public ImageProfile getImageProfile() {
        return imgProfile;
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
    public Point tileToPixel(Point tile){
        Point pixel = new Point();
        
        //X component is "row" times the "offset"
        pixel.x = hexOffset * tile.x;
        //Y component depends on the row.
        //If it's even number, then "column" times the "height" plus half the height, if it's odd then just "column" times the "height"
        pixel.y = (tile.x % 2 == 0 ? (hexHeight * tile.y) + (hexHeight / 2) : (hexHeight * tile.y));
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
    public Point pixelToTile(Point pixel) {
        Point tile = new Point();
        
        tile.x = pixel.x / hexOffset;
        //If tile is on even row, first we substract half the hexagon height to the Y component, then we divide it by the height
        //if it's on odd row, divide Y component by hexagon height
        tile.y = (tile.x % 2 == 0 ? (pixel.y - (hexHeight/2))/hexHeight : (pixel.y / hexHeight) );
        
        return tile;

    }
    
    public boolean isWithinImageRange(Point pixel){
        return ((pixel.x < imageWidth && pixel.x > 0) && (pixel.y>0 && pixel.y < imageHeight));
    }
    
}
