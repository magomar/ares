package ares.application.graphics;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface GraphicsProfile {

    /**
     * Gets the actual filename based on the base filename passed as parameter
     *
     * @param baseFilename
     * @return the actual name of the file containing the graphics of a particular provider
     */
    String getFilename(String baseFilename);

    /**
     * Gets the relative path to the graphics folder
     *
     * @return the relative path to the graphics folder
     */
    String getPath();

    /**
     * Units image width in pixels
     *
     * @return the width of the units image
     */
    int getUnitsImageWidth();

    /**
     * Units image height in pixels
     *
     * @return the height of the units image
     */
    int getUnitsImageHeight();

    /**
     * Gets the width of the unit sprite image in pixels
     *
     * @return the side of the unit image
     */
    int getUnitWidth();

    /**
     * Gets the height of the unit sprite image in pixels
     *
     * @return the unitSquareSide
     */
    public int getUnitHeight();

    /**
     * Gets the Terrain image height in pixels
     *
     * @return the width of the terrain image
     */
    int getTerrainImageWidth();

    /**
     * Terrain image height in pixels
     *
     * @return the height of the terrain image
     */
    int getTerrainImageHeight();

    /**
     * A ____ B F / \ C \____/ E D hexDiamter goes from F.x to C.x
     *
     * hexDiameter = terrainImageWidth / TERRAIN_IMAGE_COLS;
     *
     * @return the diameter of the hexagon
     */
    int getHexDiameter();

    /**
     * A ____ B F / \ C \____/ E D
     *
     * hexSide goes from E.x to D.x
     *
     * hexSide ~= hexDiameter / 2 (approx)
     *
     * @return the side of the hexagon
     */
    int getHexSide();

    /**
     * A ____ B F / \ C \____/ E D hexOffset goes from F.x to B.x
     *
     * offset = (Diameter - Side)/2 + Side
     *
     * @return the offset of the hexagon
     */
    int getHexOffset();

    /**
     *
     * A ____ B F / \ C \____/ E D hexHeight goes from A.y to E.y (flat-to-flat distance) hexHeight = hexRadius *
     * SQRT(3);
     *
     * @return the height of the hexagon
     */
    int getHexHeight();

    /**
     * A ____ B F / \ C \____/ E D
     *
     * The rise (gradient or slope) of CD To get the BC rise just change this value sign
     *
     * @return the rise of the hexagon
     */
    double getHexRise();
}