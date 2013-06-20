package ares.application.shared.gui.providers;

import ares.application.shared.gui.profiles.GraphicProperties;
import ares.application.shared.gui.profiles.ProfiledGraphicProperty;

/**
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public enum AresMiscTerrainGraphics implements ProfiledImageProviderFactory {

    TERRAIN_MISCELANEOUS(8, 8, true),
    TERRAIN_BORDER(8, 8, false),
    GRID(1, 1, false),
    GRID_GREEN(1, 1, false),
    GRID_YELLOW(1, 1, false),
    BRASS_CURSOR(1, 1, false),
    STEEL_CURSOR(1, 1, false),
    RED_ARROWS(8, 6, false),
    PURPLE_ARROWS(8, 6, false),
    BLUE_ARROWS(8, 6, false),
    DARK_BLUE_ARROWS(8, 6, false),
    GRAY_ARROWS(8, 6, false);
    private final String filename;
    private final boolean microProfile;
    private final int rows;
    private final int columns;

    private AresMiscTerrainGraphics(final int rows, final int columns, final boolean microProfile) {
        this.rows = rows;
        this.columns = columns;
        filename = name().toLowerCase() + ".png";
        this.microProfile = microProfile;
    }

    @Override
    public String getFilename(int profile) {
        String prefix = GraphicProperties.getProfilePrefix(profile);
        if (profile == 0 && !microProfile) {
            return prefix + "_terrain_null.png";
        } else {
            return prefix + "_" + filename;
        }
    }

    @Override
    public ImageProvider createImageProvider(int profile) {
        int fullImageWidth = GraphicProperties.getProperty(ProfiledGraphicProperty.TILE_WIDTH, profile) * columns;
        int fullImageHeight = GraphicProperties.getProperty(ProfiledGraphicProperty.TILE_HEIGHT, profile) * rows;
        return new MatrixImageProvider(GraphicProperties.getProfilePath(profile), getFilename(profile), rows, columns, fullImageWidth, fullImageHeight);
    }
}
