package config;

/**
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
    UNIT_LED_SIZE,
    UNIT_BAR_SIZE;
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
