package ares.application.gui.providers;

import ares.application.gui.profiles.GraphicProperties;
import ares.application.gui.profiles.GraphicsModel;
import ares.application.gui.profiles.GraphicsProfile;
import ares.application.gui.profiles.ProfiledGraphicProperty;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public enum AresMiscGraphics implements ImageProviderFactory {

    TERRAIN_MISCELANEOUS(8, 8),
    TERRAIN_BORDER(8, 8),
    GRID(1, 1),
    GRID_GREEN(1, 1),
    GRID_YELLOW(1, 1),
    BRASS_CURSOR(1, 1),
    STEEL_CURSOR(1, 1),
    RED_ARROWS(8, 6),
    PURPLE_ARROWS(8, 6),
    BLUE_ARROWS(8, 6),
    DARK_BLUE_ARROWS(8, 6),
    GRAY_ARROWS(8, 6);
    private final String filename;
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
    public ImageProvider createImageProvider(GraphicsProfile profile) {
        int fullImageWidth = GraphicProperties.getProperty(ProfiledGraphicProperty.TILE_WIDTH, profile) * columns;
        int fullImageHeight = GraphicProperties.getProperty(ProfiledGraphicProperty.TILE_HEIGHT, profile) * rows;
        return ImageProviderFactoryMethods.createImageProvider(filename, rows, columns, fullImageWidth, fullImageHeight, profile);
    }
}
