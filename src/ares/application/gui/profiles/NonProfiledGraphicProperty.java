package ares.application.gui.profiles;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public enum NonProfiledGraphicProperty implements GraphicProperty {

    TILES_ROWS,
    TILES_COLUMNS,
    TILE_RISE,
    UNITS_ROWS,
    UNITS_COLUMNS;
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
