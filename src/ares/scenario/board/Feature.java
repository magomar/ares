package ares.scenario.board;

import ares.application.gui.AresGraphicsProfile;
import ares.application.gui.providers.AresMiscGraphics;
import ares.application.gui.providers.GraphicsDescriptor;
import ares.application.gui.providers.ImageProviderType;
import java.awt.Point;

/**
 * Tiles have special features besides common terrain such as weather or buildings that affect the tile * Features
 * images are on the Terrain.OPEN file here we specify on which row and column<p>
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine Heine <heisncfr@inf.upv.es> *
 */
public enum Feature implements GraphicsDescriptor {

//    OPEN(0, 0),
    ANCHORAGE(0, 1),
    AIRFIELD(0, 2),
    PEAK(0, 3),
    CONTAMINATED(0, 4),
    NON_PLAYABLE(0, 5),
    MUDDY(0, 6),
    SNOWY(0, 7),
    BRIDGE_DESTROYED(3, 0),
    FROZEN(2, 6),
    EXCLUDED_1(5, 6),
    EXCLUDED_2(5, 7);
    private final Point coordinates;
    private final ImageProviderType imageProviderType = ImageProviderType.TILE;

    private Feature(final int imageColumn, final int imageRow) {
        this.coordinates = new Point(imageColumn, imageRow);
    }

    public Point getCoordinates() {
        return coordinates;
    }

    @Override
    public String getFilename() {
        return AresMiscGraphics.TERRAIN_MISCELANEOUS.getFilename();
    }

    @Override
    public ImageProviderType getImageProviderType() {
        return imageProviderType;
    }

    @Override
    public int getRows() {
        return AresGraphicsProfile.TERRAIN_IMAGE_ROWS;
    }

    @Override
    public int getColumns() {
        return AresGraphicsProfile.TERRAIN_IMAGE_COLS;
    }
}
