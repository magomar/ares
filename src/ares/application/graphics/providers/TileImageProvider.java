package ares.application.graphics.providers;

import ares.application.graphics.GraphicsProfile;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class TileImageProvider extends AbstractImageProvider {

    public TileImageProvider(String filename, GraphicsProfile profile) {
        this(filename, 1, 1, profile);
    }

    public TileImageProvider(String filename, int rows, int columns, GraphicsProfile profile) {
        super(filename, rows, columns, profile.getHexDiameter(), profile.getHexHeight(), profile);
    }
}
