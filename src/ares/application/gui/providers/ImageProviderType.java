package ares.application.gui.providers;

import ares.application.gui.GraphicsProfile;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public enum ImageProviderType {

    TILE {
        @Override
        public ImageProvider getImageProvider(String filename, GraphicsProfile profile) {
            return new TileImageProvider(filename, profile);
        }

        @Override
        public ImageProvider getImageProvider(String filename, int rows, int columns, GraphicsProfile profile) {
            return new TileImageProvider(filename, rows, columns, profile);
        }
    },
    UNIT {
        @Override
        public ImageProvider getImageProvider(String filename, GraphicsProfile profile) {
            return new UnitImageProvider(filename, profile);
        }

        @Override
        public ImageProvider getImageProvider(String filename, int rows, int columns, GraphicsProfile profile) {
            return new UnitImageProvider(filename, rows, columns, profile);
        }
    };

    public abstract ImageProvider getImageProvider(String filename, GraphicsProfile profile);

    public abstract ImageProvider getImageProvider(String filename, int rows, int columns, GraphicsProfile profile);
}
