package ares.application.shared.gui.profiles;

/**
 * A ____ B F / \ C \____/ E D hexDiamter goes from F.x to C.x
 *
 * hexDiameter = terrainImageWidth / TERRAIN_IMAGE_COLS;
 *
 * A ____ B F / \ C \____/ E D
 *
 * hexSide goes from E.x to D.x
 *
 * hexSide ~= hexDiameter / 2 (approx)
 *
 * A ____ B F / \ C \____/ E D hexOffset goes from F.x to B.x
 *
 * offset = (Diameter - Side)/2 + Side
 *
 *
 * A ____ B F / \ C \____/ E D hexHeight goes from A.y to E.y (flat-to-flat distance) hexHeight = hexRadius * SQRT(3);
 *
 * A ____ B F / \ C \____/ E D
 *
 * The rise (gradient or slope) of CD To get the BC rise just change this value sign
 *
 * unit_image_offset
 *
 * the offset distance from the left and upper corners of the tile(same distance vertically and horizontally). The image
 * will be painted at Point(X+offset, Y+offset)
 *
 * Unit_stack_Offset the offset distance both horizontally and vertically between unit images painted in the same tile.
 * A stack of units is represented showing overlapping unit images. Each layer is shifted this number of pixels. For
 * example, say the first unit was painted at Point(X,Y), then the next layer will start at Point(X+offset,Y+offset),
 * and the third one at Point(X+2*offset, Y+2*offset) and so on.
 *
 * unit_max_stack the maximum numbers of units to be painted in a single tile
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public enum ProfiledGraphicProperty implements GraphicProperty {

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
