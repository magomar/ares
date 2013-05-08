package ares.application.gui.providers;

import ares.application.gui.GraphicsProfile;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class TileImageProvider extends AbstractImageProvider {

    public TileImageProvider(String filename, int rows, int columns, GraphicsProfile profile) {
        super(filename, rows, columns, profile.getHexDiameter(), profile.getHexHeight(), profile);
    }
}
