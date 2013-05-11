package ares.application.gui.providers;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public enum AresMiscGraphics implements GraphicsDescriptor {

    TERRAIN_MISCELANEOUS(8, 8),
    TERRAIN_BORDER(8, 8),
    GRID(1, 1),
    BRASS_CURSOR(1, 1),
    STEEL_CURSOR(1, 1),
    RED_ARROWS(8, 6),
    PURPLE_ARROWS(8, 6),
    BLUE_ARROWS(8, 6),
    GRAY_ARROWS(8, 6);
    private final String filename;
    private final ImageProviderType imageProviderType = ImageProviderType.TILE;
    private final int rows;
    private final int columns;

    private AresMiscGraphics(final int rows, final int columns) {
        this.rows = rows;
        this.columns = columns;
        filename = name().toLowerCase() + ".png";
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public ImageProviderType getImageProviderType() {
        return imageProviderType;
    }

    @Override
    public int getRows() {
        return rows;
    }
    
    @Override
    public int getColumns() {
        return columns;
    }
}
