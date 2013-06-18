package ares.application.shared.gui.profiles;

/**
 * A ____ B F / \ C \____/ E D hexDiamter goes from F.x to C.x
 * <p/>
 * hexDiameter = terrainImageWidth / TERRAIN_IMAGE_COLS;
 * <p/>
 * A ____ B F / \ C \____/ E D
 * <p/>
 * hexSide goes from E.x to D.x
 * <p/>
 * hexSide ~= hexDiameter / 2 (approx)
 * <p/>
 * A ____ B F / \ C \____/ E D hexOffset goes from F.x to B.x
 * <p/>
 * offset = (Diameter - Side)/2 + Side
 * <p/>
 * <p/>
 * A ____ B F / \ C \____/ E D hexHeight goes from A.y to E.y (flat-to-flat distance) hexHeight = hexRadius * SQRT(3);
 * <p/>
 * A ____ B F / \ C \____/ E D
 * <p/>
 * The rise (gradient or slope) of CD To get the BC rise just change this value sign
 * <p/>
 * unit_image_offset
 * <p/>
 * the offset distance from the left and upper corners of the tile(same distance vertically and horizontally). The image
 * will be painted at Point(X+offset, Y+offset)
 * <p/>
 * Unit_stack_Offset the offset distance both horizontally and vertically between unit images painted in the same tile.
 * A stack of units is represented showing overlapping unit images. Each layer is shifted this number of pixels. For
 * example, say the first unit was painted at Point(X,Y), then the next layer will start at Point(X+offset,Y+offset),
 * and the third one at Point(X+2*offset, Y+2*offset) and so on.
 * <p/>
 * unit_max_stack the maximum numbers of units to be painted in a single tile
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public enum ProfiledGraphicProperty implements GraphicProperty {

    PROFILE_PREFIX,
    PATHS,
    TILES_WIDTH,
    TILES_HEIGHT,
    TILE_WIDTH,
    TILE_HEIGHT,
    TILE_SIDE,
    TILE_OFFSET,
    UNITS_WIDTH,
    UNITS_HEIGHT,
    UNIT_OFFSET,
    UNIT_STACK_OFFSET,
    UNIT_MAX_STACK,
    UNIT_WIDTH,
    UNIT_HEIGHT,
    UNIT_FONT_SIZE,
    UNIT_LED_LENGHT,
    UNIT_BAR_LENGHT,
    ARROW_FONT_SIZE;
    private final String name;

    private ProfiledGraphicProperty() {
        this.name = name().toLowerCase();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isProfiled() {
        return true;
    }
}
