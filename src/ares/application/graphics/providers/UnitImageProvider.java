package ares.application.graphics.providers;

import ares.application.graphics.GraphicsProfile;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class UnitImageProvider extends AbstractImageProvider {

    public UnitImageProvider(String filename, GraphicsProfile profile) {
        this(filename, 1, 1, profile);
    }

    public UnitImageProvider(String filename, int rows, int columns, GraphicsProfile profile) {
        super(filename, rows, columns, profile.getUnitWidth(), profile.getUnitHeight(), profile);
    }
}
