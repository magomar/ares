package ares.application.gui;

import ares.application.gui.forces.UnitsInfographicProfile;
import ares.platform.io.ResourcePath;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public enum AresGraphicsProfile implements GraphicsProfile {

    // ResourcePath, Units (Width,Height), Terrain(W,H), Hex(Side,Offset), Font size, Led vert. size, Bar vert. size
    SMALL(ResourcePath.GRAPHICS_SMALL, 272, 128, 216, 192, 13, 21, 5, 2, 5) {
        @Override
        public String getFilename(String filename) {
            return "s_" + filename;
        }
    },
    MEDIUM(ResourcePath.GRAPHICS_MEDIUM, 496, 248, 408, 352, 28, 39, 10, 4, 10) {
        @Override
        public String getFilename(String filename) {
            return "m_" + filename;
        }
    },
    HIGH(ResourcePath.GRAPHICS_HIGH, 992, 496, 816, 704, 51, 78, 20, 8, 20) {
        @Override
        public String getFilename(String filename) {
            return "h_" + filename;
        }
    };
    public final static int UNITS_IMAGE_ROWS = 8;
    public final static int UNITS_IMAGE_COLS = 16;
    public final static int TERRAIN_IMAGE_ROWS = 8;
    public final static int TERRAIN_IMAGE_COLS = 8;
    private final int unitsImageWidth;
    private final int unitsImageHeight;
    private final int unitWitdh;
    private final int unitHeight;
    private final int terrainImageWidth;
    private final int terrainImageHeight;
    private final int hexDiameter;
    private final int hexSide;
    private final int hexOffset;
    private final int hexHeight;
    private final int fontSize;
    private final int ledSize;
    private final int barSize;
    private final double hexRise = 1.833;
    private final String path;
    private final UnitsInfographicProfile unitsProfile;

    /**
     *
     * AresGraphicsProfile stores information concerning the graphics used in the game i.e: units and terrain image
     * files and hexagon measures based on the desired profile (SMALL, MEDIUM or HIGH)
     *
     * @param resourcePath
     * @param unitsImageWidth
     * @param unitsImageHeight
     * @param terrainImageWidth
     * @param terrainImageHeight
     * @param hexSide
     * @param hexOffset
     * @param fontSize
     * @param ledSize
     * @param barSize
     */
    private AresGraphicsProfile(final ResourcePath resourcePath,
            final int unitsImageWidth, final int unitsImageHeight,
            final int terrainImageWidth, final int terrainImageHeight,
            final int hexSide, final int hexOffset, final int fontSize, final int ledSize, final int barSize) {
        this.path = resourcePath.getPath();
        this.unitsImageWidth = unitsImageWidth;
        this.unitsImageHeight = unitsImageHeight;
        this.terrainImageWidth = terrainImageWidth;
        this.terrainImageHeight = terrainImageHeight;
        this.hexSide = hexSide;
        this.hexOffset = hexOffset;
        this.fontSize = fontSize;
        this.ledSize = ledSize;
        this.barSize = barSize;
        unitWitdh = unitsImageWidth / UNITS_IMAGE_COLS;
        unitHeight = unitsImageHeight / UNITS_IMAGE_ROWS;
        hexDiameter = terrainImageWidth / TERRAIN_IMAGE_COLS;
        hexHeight = terrainImageHeight / TERRAIN_IMAGE_ROWS;
        unitsProfile = new UnitsInfographicProfile(this);
    }

    /**
     * Units image width in pixels
     *
     * @return the unitImageWidth
     */
    @Override
    public int getUnitsImageWidth() {
        return unitsImageWidth;
    }

    /**
     * Units image height in pixels
     *
     * @return the unitImageHeight
     */
    @Override
    public int getUnitsImageHeight() {
        return unitsImageHeight;
    }

    /**
     * Gets the width of the unit sprite image in pixels
     *
     * @return the unitSquareSide
     */
    @Override
    public int getUnitWidth() {
        return unitWitdh;
    }

    /**
     * Gets the height of the unit sprite image in pixels
     *
     * @return the unitSquareSide
     */
    @Override
    public int getUnitHeight() {
        return unitHeight;
    }

    /**
     * Terrain image height in pixels
     *
     * @return the terrainImageWidth
     */
    @Override
    public int getTerrainImageWidth() {
        return terrainImageWidth;
    }

    /**
     * Terrain image height in pixels
     *
     * @return the terrainImageHeight
     */
    @Override
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
    @Override
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
    @Override
    public int getHexSide() {
        return hexSide;

    }

    /**
     * A ____ B F / \ C \____/ E D hexOffset goes from F.x to B.x
     *
     * <p>offset ~= (Diameter - Side)/2 + Side
     *
     * @return the hexOffset
     */
    @Override
    public int getHexOffset() {
        return hexOffset;
    }

    /**
     *
     * A ____ B F / \ C \____/ E D hexHeight goes from A.y to E.y (flat-to-flat distance)
     * <p>hexHeight ~= hexRadius * SQRT(3);
     *
     * @return the hexHeight
     */
    @Override
    public int getHexHeight() {
        return hexHeight;
    }

    /**
     * A ____ B F / \ C \____/ E D
     *
     * The rise (gradient or slope) of CD
     * <p>To get the BC rise just change this value sign
     *
     * @return
     */
    @Override
    public double getHexRise() {
        return hexRise;
    }

    /**
     * Gets the relative path to the graphics folder
     *
     * @return the path
     */
    @Override
    public String getPath() {
        return path;
    }

    @Override
    public int getFontSize() {
        return fontSize;
    }

    @Override
    public int getLedSize() {
        return ledSize;
    }

    @Override
    public int getBarSize() {
        return barSize;
    }

    @Override
    public UnitsInfographicProfile getUnitsProfile() {
        return unitsProfile;
    }
    
}
