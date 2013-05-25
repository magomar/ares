package ares.application.gui.providers;

import ares.application.gui.profiles.GraphicsProfile;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public enum ImageProviderType {

    TILE {
        @Override
        public ImageProvider createImageProvider(String filename, int rows, int columns, GraphicsProfile profile) {
            return new TileImageProvider(filename, rows, columns, profile);
        }
    },
    UNIT {
        @Override
        public ImageProvider createImageProvider(String filename, int rows, int columns, GraphicsProfile profile) {
            return new UnitImageProvider(filename, rows, columns, profile);
        }
    };

    public abstract ImageProvider createImageProvider(String filename, int rows, int columns, GraphicsProfile profile);
}
