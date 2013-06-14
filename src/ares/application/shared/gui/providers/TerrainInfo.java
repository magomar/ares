package ares.application.shared.gui.providers;

import ares.application.shared.gui.profiles.GraphicProperties;
import ares.application.shared.gui.profiles.NonProfiledGraphicProperty;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public enum TerrainInfo implements NonProfiledImageProviderFactory {

    OPEN,
    ARID,
    SAND,
    DUNES,
    BADLANDS,
    HILLS,
    MOUNTAINS,
    ALPINE,
    MARSH,
    FLOODED_MARSH,
    SHALLOW_WATER,
    DEEP_WATER,
    CROPLANDS,
    BOCAGE_HEDGEROW,
    URBAN,
    DENSE_URBAN,
    URBAN_RUIN,
    DENSE_URBAN_RUIN,
    ROCKY,
    ESCARPMENT,
    MAJOR_ESCARPMENT,
    WADY,
    RIVER,
    SUPER_RIVER,
    CANAL,
    SUPER_CANAL,
    EVERGREEN_FOREST,
    FOREST,
    LIGHT_WOODS,
    JUNGLE,
    FORTIFICATION,
    ROAD,
    IMPROVED_ROAD,
    RAIL,
    BROKEN_RAIL;
    private final String filename;

    private TerrainInfo() {
        this.filename = name().toLowerCase() + ".png";
    }

    @Override
    public String getFilename() {
        return GraphicProperties.getTerrainInfoPrefix() + "_" + filename;
    }

    @Override
    public ImageProvider createImageProvider() {
        int fullImageWidth = GraphicProperties.getProperty(NonProfiledGraphicProperty.TERRAIN_INFO_WIDTH);
        int fullImageHeight = GraphicProperties.getProperty(NonProfiledGraphicProperty.TERRAIN_INFO_HEIGHT);
        return new MatrixImageProvider(GraphicProperties.getTerrainInfoPath(), getFilename(), 1, 1, fullImageWidth, fullImageHeight);
    }
}
