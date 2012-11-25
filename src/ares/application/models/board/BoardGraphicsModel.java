package ares.application.models.board;

import ares.io.ImageProfile;
import ares.scenario.board.Board;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public final class BoardGraphicsModel {

    private final int hexSide;
    private final int hexDiameter;
    private final int hexRadius;
    private final int hexHeight;
    private final int hexOffset;
    private final int tileColumns;
    private final int tileRows;
    private final int imageWidth;
    private final int imageHeight;
    private final ImageProfile imgProfile;
    private static Point lastPaintedPixelBoundary = new Point();
    private static Point lastPaintedTileBoundary = new Point();
    private static Rectangle viewport;
    

    public BoardGraphicsModel(Board board) {
        tileColumns = board.getWidth();
        tileRows = board.getHeight();
        
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
    public boolean tileIsWithinBoard(int i, int j) {
        return columnIsWithinBoard(i) && rowIsWithinBoard(j);
    }
    public boolean columnIsWithinBoard(int i){
        return i >= 0 && i < tileColumns;
    }
    public boolean rowIsWithinBoard(int j){
        return j >= 0 && j < tileRows;
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
    public int getTileColumns() {
        return tileColumns;
    }

    /**
     * @return board height in tile units
     */
    public int getTileRows() {
        return tileRows;
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
        return tileToPixel(tile.x, tile.y);
    }
    
    public Point tileToPixel(int x, int y){
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
    public Point pixelToTile(Point pixel) {
        return pixelToTile(pixel.x, pixel.y);
    }
    public Point pixelToTile(int x, int y) {
        Point tile = new Point();
        
        tile.x = x / hexOffset;
        //If tile is on even row, first we substract half the hexagon height to the Y component, then we divide it by the height
        //if it's on odd row, divide Y component by hexagon height
        tile.y = (tile.x % 2 == 0 ? (y - (hexHeight/2))/hexHeight : (y / hexHeight) );
        
        return tile;

    }

    public boolean isWithinImageRange(Point pixel){
        return ((pixel.x < imageWidth && pixel.x > 0) && (pixel.y > 0 && pixel.y < imageHeight));
    }
    
    
    public boolean mapIsCompletelyPainted(){
        return lastPaintedTileBoundary.x > tileColumns-2 && lastPaintedTileBoundary.y > tileRows-2;
    }
    
    public void setLastPaintedPixelBoundary(int x, int y) {
        x = (x>imageWidth) ? imageWidth : x;
        y = (y>imageHeight) ? imageHeight : y;
        BoardGraphicsModel.lastPaintedPixelBoundary.x = x;
        BoardGraphicsModel.lastPaintedPixelBoundary.y = y;
        Point p = pixelToTile(x, y);
        BoardGraphicsModel.lastPaintedTileBoundary.x = p.x;
        BoardGraphicsModel.lastPaintedTileBoundary.y = p.y;
    }

    public Point getLastPaintedTileBoundary() {
        return lastPaintedTileBoundary;
    }

    public void setLastPaintedTileBoundary(Point lastPaintedTileBoundary) {
        setLastPaintedPixelBoundary(lastPaintedTileBoundary.x, lastPaintedTileBoundary.y);
    }

    public void setLastPaintedTileBoundary(int x, int y) {
        x = (x > tileColumns) ? tileColumns-1 : x;
        y = (y > tileRows) ? tileRows-1 : y;
        BoardGraphicsModel.lastPaintedTileBoundary.x = x;
        BoardGraphicsModel.lastPaintedTileBoundary.y = y;
//        Point p = tileToPixel(x, y);
//        BoardGraphicsModel.lastPaintedPixelBoundary.x = p.x;
//        BoardGraphicsModel.lastPaintedPixelBoundary.y = p.y;
    }

    public Rectangle getViewport() {
        return viewport;
    }
    
    public Rectangle getViewportTiles(){
        Point upperleft = pixelToTile(viewport.x,viewport.y);
        Point bottomright = pixelToTile(viewport.x+viewport.width,viewport.y+viewport.height);
        
        upperleft.x = (rowIsWithinBoard(upperleft.x) ? upperleft.x : 0);
        upperleft.y = (columnIsWithinBoard(upperleft.y) ? upperleft.y : 0);
        bottomright.x = (rowIsWithinBoard(bottomright.x) ? bottomright.x : tileRows-1);
        bottomright.y = (columnIsWithinBoard(bottomright.y) ? bottomright.y : tileColumns-1);
        
        return new Rectangle(upperleft.x,upperleft.y, bottomright.x,bottomright.y);
    }
    
    public void setViewport(Rectangle viewport) {
        BoardGraphicsModel.viewport = viewport;
        setLastPaintedPixelBoundary(viewport.width,viewport.height);
    }
    public Object[] changeViewport(Rectangle r){
        BoardGraphicsModel.viewport = r;
        Point newTile = pixelToTile(r.x + r.width, r.y + r.height);
        
        //Return object array with idexes:
        // 0 = source is bgm
        // 1 = propertyName
        // 2 = oldValue as the last painted row/col
        // 3 = newValue as what has changed
        if(newTile.y > (lastPaintedTileBoundary.y) && rowIsWithinBoard(newTile.y) ){
            Object ob[] = new Object[]{this,"ViewportChanged",lastPaintedTileBoundary.y,"V_ROW"};
            setLastPaintedTileBoundary(lastPaintedTileBoundary.x,newTile.y);
            return ob;
        } else
        if(newTile.x > (lastPaintedTileBoundary.x) && columnIsWithinBoard(newTile.x)){
            Object ob[] = new Object[]{this,"ViewportChanged",lastPaintedTileBoundary.x,"V_COL"};
            setLastPaintedTileBoundary(newTile.x,lastPaintedTileBoundary.y);
            return ob;
        }
        return null;
    }
}
