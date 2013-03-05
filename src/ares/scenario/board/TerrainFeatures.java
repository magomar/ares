/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ares.scenario.board;

/**
 * Tiles have special features besides common terrain such as weather or buildings that affect the tile
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine Heine <heisncfr@inf.upv.es> *
 */
public enum TerrainFeatures {

    /*
     * Features images are on the Terrain.OPEN file
     * here we specify on which row and column
     * 
     * (0,6) is an invisible image
     */
    OPEN(0, 0),
    ANCHORAGE(1, 0),
    AIRFIELD(2, 0),
    PEAK(3, 0),
    CONTAMINATED(4, 0),
    NON_PLAYABLE(5, 0),
    MUDDY(6, 2),
    SNOWY(0, 6),
    BRIDGE_DESTROYED(0, 6),
    FROZEN(0, 6),
    EXCLUDED_1(0, 6),
    EXCLUDED_2(0, 6);
    private int imageRow;
    private int imageColumn;

    private TerrainFeatures(int imageRow, int imageColumn) {
        this.imageRow = imageRow;
        this.imageColumn = imageColumn;
    }

    public int getRow() {
        return imageRow;
    }

    public int getCol() {
        return imageColumn;
    }
}
