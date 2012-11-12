package ares.scenario.board;

import ares.io.ImageProfile;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class BoardInfo {

    final private int hexSide;
    final private int hexDiameter;
    final private int hexHeight;
    final private int hexOffset;
    final private int width;
    final private int height;
    final private int imageWidth;
    final private int imageHeight;
    final private ImageProfile imgProfile;

    public BoardInfo(Board board) {
        width = board.getWidth();
        height = board.getHeight();
        
        imgProfile = ImageProfile.MEDIUM;
        hexSide = imgProfile.getHexSide();
        hexDiameter = imgProfile.getHexDiameter();
        hexOffset = imgProfile.getHexOffset();
        hexHeight = imgProfile.getHexHeight();
        
        /*
         * Width =  first column + (columns-1) * (around 3/4 Diameter)
         * Hexagons aren't regular
         */
        imageWidth = hexDiameter + (width-1) * hexOffset;
 
        imageHeight = height * hexHeight + hexHeight/2;
    }
    
    
    /**
     * Check valid coordinates
     * @param i as row
     * @param j as column
     * @return true if (i,j) is within the board range
     */
    public boolean validCoordinates(int i, int j) {
        return i >= 0 && i < width && j >= 0 && j < height;
    }

    /**
     * Tile diameter (vertex to opposite vertex)
     * @see ImageProfile method getHexDiameter()
     */
    public int getHexDiameter() {
        return hexDiameter;
    }
    
    /**
     * Tile side
     * @see ImageProfile method getHexSide()
     */
    public int getHexSide(){
        return hexSide;
    }

    /**
     * Tile offset position to draw in a new column
     * @see ImageProfile method getHexOffset
     */
    public int getHexOffset(){
        return hexOffset;
    }

    /**
     * Tile height (flat side to flat side)
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
    public ImageProfile getImageProfile(){
        return imgProfile;
    }
    
    
}
