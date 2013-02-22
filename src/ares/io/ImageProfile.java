/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ares.io;

import ares.application.gui.UnitColors;
import ares.scenario.board.Terrain;
import java.io.File;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public enum ImageProfile {

    // Units (Width,Height,Rows,Cols,Square side), Terran(W,H,R,C), Hex(Diam,Side,Offset,Height,rise), Path
    SMALL(272, 128, 8, 16,/*17x16*/ 0, 270, 192, 8, 10, 27, 13, 21, 22, 0.0, AresPaths.GRAPHICS_SMALL.getPath()),
    MEDIUM(496, 248, 8, 16, 31, 510, 352, 8, 10, 51, 28, 39, 44, 1.833, AresPaths.GRAPHICS_MEDIUM.getPath()),
    HIGH(992, 446, 8, 16, 62, 1020, 704, 8, 10, 102, 51, 78, 88, 1.833, AresPaths.GRAPHICS_HIGH.getPath()),;
    private final int unitsImageWidth;
    private final int unitsImageHeight;
    private final int unitsImageRows;
    private final int unitsImageCols;
    private final int unitSquareSide;
    private final int terrainImageWidth;
    private final int terrainImageHeight;
    private final int terrainImageRows;
    private final int terrainImageCols;
    private final int hexDiameter;
    private final int hexSide;
    private final int hexOffset;
    private final int hexHeight;
    private final double hexRise;
    private String path;

    /**
     *
     * ImageProfile stores information concerning the graphics used in the game i.e: units and terrain image files and
     * hexagon measures based on the desired profile (SMALL, MEDIUM or HIGH)
     *
     * @param unitsImageWidth
     * @param unitsImageHeight
     * @param unitsImageRows
     * @param unitsImageCols
     * @param unitSquareSide
     * @param terrainImageWidth
     * @param terrainImageHeight
     * @param terrainImageRows
     * @param terrainImageCols
     * @param hexDiameter
     * @param hexSide
     * @param hexOffset
     * @param hexHeight
     * @param path
     */
    private ImageProfile(int unitsImageWidth, int unitsImageHeight, int unitsImageRows, int unitsImageCols, int unitSquareSide, int terrainImageWidth, int terrainImageHeight, int terrainImageRows, int terrainImageCols, int hexDiameter, int hexSide, int hexOffset, int hexHeight, double hexRise, String path) {
        this.unitsImageWidth = unitsImageWidth;
        this.unitsImageHeight = unitsImageHeight;
        this.unitsImageRows = unitsImageRows;
        this.unitsImageCols = unitsImageCols;
        this.unitSquareSide = unitSquareSide;
        this.terrainImageWidth = terrainImageWidth;
        this.terrainImageHeight = terrainImageHeight;
        this.terrainImageRows = terrainImageRows;
        this.terrainImageCols = terrainImageCols;
        this.hexDiameter = hexDiameter;
        this.hexSide = hexSide;
        this.hexOffset = hexOffset;
        this.hexHeight = hexHeight;
        this.hexRise = hexRise;
        this.path = path;
    }

    /**
     * Units image width in pixels
     *
     * @return the unitImageWidth
     */
    public int getUnitsImageWidth() {
        return unitsImageWidth;
    }

    /**
     * Units image height in pixels
     *
     * @return the unitImageHeight
     */
    public int getUnitsImageHeight() {
        return unitsImageHeight;
    }

    /**
     * Units image file rows
     *
     * @return the unitImageRows
     */
    public int getUnitsImageRows() {
        return unitsImageRows;
    }

    /**
     * Units image file columns
     *
     * @return the unitImageCols
     */
    public int getUnitsImageCols() {
        return unitsImageCols;
    }

    /**
     * Unit single image square size (width and height are equal)
     *
     * @return the unitSquareSide
     */
    public int getUnitSquareSide() {
        return unitSquareSide;
    }

    /**
     * Terrain image height in pixels
     *
     * @return the terrainImageWidth
     */
    public int getTerrainImageWidth() {
        return terrainImageWidth;
    }

    /**
     * Terrain image height in pixels
     *
     * @return the terrainImageHeight
     */
    public int getTerrainImageHeight() {
        return terrainImageHeight;
    }

    /**
     * Terrain image file rows
     *
     * @return the terrainImageRows
     */
    public int getTerrainImageRows() {
        return terrainImageRows;
    }

    /**
     * Terrain image file columns
     *
     * @return the terrainImageCols
     */
    public int getTerrainImageCols() {
        return terrainImageCols;
    }

    /**
     * A ____ B F / \ C \____/ E D hexDiamter goes from F.x to C.x
     *
     * hexDiameter = terrainImageWidth / terrainImageCols;
     *
     * @return the hexDiameter
     */
    public int getHexDiameter() {
        return hexDiameter;
    }

    /**
     * A ____ B F / \ C \____/ E D
     *
     * hexSide goes from E.x to D.x
     *
     * hexSide ~= hexDiameter / 2 (approx)
     *
     * @return the hexSide
     */
    public int getHexSide() {
        return hexSide;

    }

    /**
     * A ____ B F / \ C \____/ E D hexOffset goes from F.x to B.x
     *
     * offset = (Diameter - Side)/2 + Side
     *
     * @return the hexOffset
     */
    public int getHexOffset() {
        return hexOffset;
    }

    /**
     *
     * A ____ B F / \ C \____/ E D hexHeight goes from A.y to E.y (flat-to-flat distance) hexHeight = hexRadius *
     * SQRT(3);
     *
     * @return the hexHeight
     */
    public int getHexHeight() {
        return hexHeight;
    }

    /**
     * A ____ B F / \ C \____/ E D
     *
     * The rise (gradient or slope) of CD To get the BC rise just change this value sign
     *
     * @return
     */
    public double getHexRise() {
        return hexRise;
    }

    /**
     * Path to graphics folder
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     *
     * @return grid hexagon file
     */
    public File getGridHexFilename() {
        return new File(path, "Hexoutline.png");
    }

    /**
     *
     * @return movement arrows image filename
     */
    public File getArrowFilename() {
        return new File(path, "Movement_arrows.png");
    }

    /**
     *
     * @param terrain
     * @return the terrain filename based on the Image Profile
     */
    public String getFileName(Terrain terrain) {

        switch (this) {

            case SMALL:
                return terrain.getGraphicFileSmall();
            case MEDIUM:
                return terrain.getGraphicFileMedium();
            case HIGH:
                return terrain.getGraphicFileHigh();
            default:
                throw new AssertionError("Assertion failed: unkown image profile " + this);
        }
    }

    /**
     *
     * @param unitColor
     * @return unit template image filename based on the Image Profile
     */
    public String getFileName(UnitColors unitColor) {

        switch (this) {
            case SMALL:
                return unitColor.getGraphicFileSmall();
            case MEDIUM:
                return unitColor.getGraphicFileMedium();
            case HIGH:
                return unitColor.getGraphicFileHigh();
            default:
                throw new AssertionError("Assertion failed: unkown image profile " + this);
        }
    }
}
