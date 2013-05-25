package ares.application.gui.providers;

import ares.application.gui.profiles.GraphicsProfile;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class UnitImageProvider extends AbstractImageProvider {

    public UnitImageProvider(String filename, int rows, int columns, GraphicsProfile profile) {
        super(filename, rows, columns, profile.getUnitWidth(), profile.getUnitHeight(), profile);
    }
}
