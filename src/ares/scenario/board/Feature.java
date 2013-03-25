package ares.scenario.board;

/**
 * Tiles have special features besides common terrain such as weather or buildings that affect the tile * Features
 * images are on the Terrain.OPEN file here we specify on which row and column<p>
 *
 * (0,3) is an invisible image
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine Heine <heisncfr@inf.upv.es> *
 */
public enum Feature {
    OPEN(0, 0),
    ANCHORAGE(1, 0),
    AIRFIELD(2, 0),
    PEAK(3, 0),
    CONTAMINATED(4, 0),
    NON_PLAYABLE(5, 0),
    MUDDY(6, 0),
    SNOWY(7, 0),
    BRIDGE_DESTROYED(0, 3),
    FROZEN(0, 6),
    EXCLUDED_1(6, 5),
    EXCLUDED_2(7, 5);
    private int imageRow;
    private int imageColumn;

    private Feature(int imageRow, int imageColumn) {
        this.imageRow = imageRow;
        this.imageColumn = imageColumn;
    }

    public int getImageRow() {
        return imageRow;
    }

    public int getImageCol() {
        return imageColumn;
    }
}
