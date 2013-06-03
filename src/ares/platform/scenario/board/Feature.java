package ares.platform.scenario.board;

import java.awt.Point;

/**
 * Tiles have special features besides common terrain such as weather or buildings that affect the tile * Features
 * images are on the Terrain.OPEN file here we specify on which row and column<p>
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine Heine <heisncfr@inf.upv.es> *
 */
public enum Feature {

    OPEN(0, 0),
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

    private Feature(final int imageColumn, final int imageRow) {
        this.coordinates = new Point(imageColumn, imageRow);
    }

    public Point getCoordinates() {
        return coordinates;
    }
}
