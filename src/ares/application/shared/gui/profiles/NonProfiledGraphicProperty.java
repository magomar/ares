package ares.application.shared.gui.profiles;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public enum NonProfiledGraphicProperty implements GraphicProperty {
    NUM_PROFILES,
    TILES_ROWS,
    TILES_COLUMNS,
    TILE_RISE,
    UNITS_ROWS,
    UNITS_COLUMNS,
    TERRAIN_INFO_WIDTH,
    TERRAIN_INFO_HEIGHT,
    TERRAIN_INFO_PREFIX,
    TERRAIN_INFO_PATH;
    private final String name;

    private NonProfiledGraphicProperty() {
        this.name = name().toLowerCase();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isProfiled() {
        return false;
    }
}
