package ares.application.graphics;

import ares.application.graphics.command.ArrowType;
import ares.application.graphics.forces.UnitColor;
import ares.io.AresPaths;
import ares.scenario.board.Terrain;
import java.io.File;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public enum ImageProfile {

    // Units (Width,Height), Terrain(W,H), Hex(Side,Offset,Height,rise), Path
    SMALL(272, 128, 216, 192, 13, 21, 22, 0.0, AresPaths.GRAPHICS_SMALL.getPath()),
    MEDIUM(496, 248, 408, 352, 28, 39, 44, 1.833, AresPaths.GRAPHICS_MEDIUM.getPath()),
    HIGH(992, 446, 816, 704, 51, 78, 88, 1.833, AresPaths.GRAPHICS_HIGH.getPath());
    private final int unitsImageWidth;
    private final int unitsImageHeight;
    private final int unitSquareSide;
    private final int terrainImageWidth;
    private final int terrainImageHeight;
    private final int hexDiameter;
    private final int hexSide;
    private final int hexOffset;
    private final int hexHeight;
    private final double hexRise;
    private final String path;
    public final static int UNITS_IMAGE_ROWS = 8;
    public final static int UNITS_IMAGE_COLS = 16;
    public final static int TERRAIN_IMAGE_ROWS = 8;
    public final static int TERRAIN_IMAGE_COLS = 8;

    /**
     *
     * ImageProfile stores information concerning the graphics used in the game i.e: units and terrain image files and
     * hexagon measures based on the desired profile (SMALL, MEDIUM or HIGH)
     *
     * @param unitsImageWidth
     * @param unitsImageHeight
     * @param terrainImageWidth
     * @param terrainImageHeight
     * @param hexSide
     * @param hexOffset
     * @param hexHeight
     * @param path
     */
    private ImageProfile(
            final int unitsImageWidth, final int unitsImageHeight,
            final int terrainImageWidth, final int terrainImageHeight,
            final int hexSide, final int hexOffset, final int hexHeight,
            final double hexRise, final String path) {
        this.unitsImageWidth = unitsImageWidth;
        this.unitsImageHeight = unitsImageHeight;
        this.terrainImageWidth = terrainImageWidth;
        this.terrainImageHeight = terrainImageHeight;
        this.hexSide = hexSide;
        this.hexOffset = hexOffset;
        this.hexHeight = hexHeight;
        this.hexRise = hexRise;
        this.path = path;
        this.unitSquareSide = unitsImageWidth / UNITS_IMAGE_COLS;
        this.hexDiameter = terrainImageWidth / TERRAIN_IMAGE_COLS;
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
     * A ____ B F / \ C \____/ E D hexDiamter goes from F.x to C.x
     *
     * hexDiameter = terrainImageWidth / TERRAIN_IMAGE_COLS;
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
    public File getGridHexFile() {
        return new File(path, "Hexoutline.png");
    }

    /**
     *
     * @return the file containing arrow images
     */
    public File getArrowFile(ArrowType arrowType) {
        String baseFilename = arrowType.getFilename();
        switch (this) {
            case SMALL:
                return null;
            case MEDIUM:
                return new File(path, baseFilename);
            case HIGH:
                return new File(path, "h_" + baseFilename);
            default:
                throw new AssertionError("Assertion failed: unkown image profile " + this);
        }
    }

    /**
     *
     * @return the file with the brass cursor image
     */
    public File getBrassCursorFile() {
        String baseFilename = "brass_cursor.png";
        switch (this) {
            case SMALL:
                return null;
            case MEDIUM:
                return new File(path, baseFilename);
            case HIGH:
                return new File(path, "h_" + baseFilename);
            default:
                throw new AssertionError("Assertion failed: unkown image profile " + this);
        }
    }

    /**
     *
     * @return the file with the steel cursor image
     */
    public File getSteelCursorFile() {
        String baseFilename = "steel_cursor.png";
        switch (this) {
            case SMALL:
                return null;
            case MEDIUM:
                return new File(path, baseFilename);
            case HIGH:
                return new File(path, "h_" + baseFilename);
            default:
                throw new AssertionError("Assertion failed: unkown image profile " + this);
        }
    }

    /**
     *
     * @param icons
     * @return unit template image filename based on the Image Profile
     */
    public String getUnitIconsFilename(UnitColor icons) {

        switch (this) {
            case SMALL:
                return icons.getGraphicFileSmall();
            case MEDIUM:
                return icons.getGraphicFileMedium();
            case HIGH:
                return icons.getGraphicFileHigh();
            default:
                throw new AssertionError("Assertion failed: unkown image profile " + this);
        }
    }
}
