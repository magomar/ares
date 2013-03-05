package ares.scenario.board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum Terrain {

    OPEN(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.NONE, "h_tiles_misc.png", "tiles_misc.png", "s_tiles_misc.png"),
    ARID(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.NONE, "h_tiles_arid.png", "tiles_arid.png", "s_tiles_arid.png"),
    SAND(1, 1, 1, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.NONE, "h_tiles_sandy.png", "tiles_sandy.png", "s_tiles_sandy.png"),
    DUNES(9999, 9999, 9999, 3, 1.0, 1.0, 1.0, 3.0, 2.0, Vision.NORMAL, Directionality.NONE, "h_tiles_r_sandy.png", "tiles_r_sandy.png", "s_tiles_r_sandy.png"),
    BADLANDS(9999, 9999, 9999, 2, 1.0, 1.0, 1.0, 4.0, 3.0, Vision.NORMAL, Directionality.NONE, "h_tiles_badlands.png", "tiles_badlands.png", "s_tiles_badlands.png"),
    HILLS(2, 2, 2, 1, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.NORMAL, Directionality.NONE, "h_tiles_hills.png", "tiles_hills.png", "s_tiles_hills.png"),
    MOUNTAINS(3, 3, 3, 3, 3.5, 1.5, 1.0, 3.0, 2.0, Vision.NORMAL, Directionality.NONE, "h_tiles_mountainous.png", "tiles_mountainous.png", "s_tiles_mountainous.png"),
    ALPINE(9999, 9999, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, Directionality.NONE, "h_tiles_impassable.png", "tiles_impassable.png", "s_tiles_impassable.png"),
    MARSH(3, 3, 3, 2, 2.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, Directionality.NONE, "h_tiles_marsh.png", "tiles_marsh.png", "s_tiles_marsh.png"),
    FLOODED_MARS(9999, 9999, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, Directionality.NONE, "h_tiles_floodedmarsh.png", "tiles_floodedmarsh.png", "s_tiles_floodedmarsh.png"),
    CROPLANDS(1, 1, 1, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, Directionality.NONE, "h_tiles_l_cultivated.png", "tiles_l_cultivated.png", "s_tiles_l_cultivated.png"),
    BOCAGE_HEDGEROW(2, 2, 2, 2, 2.0, 3.0, 1.0, 3.0, 1.5, Vision.RESTRICTED, Directionality.NONE, "h_tiles_h_cultivated.png", "tiles_h_cultivated.png", "s_tiles_h_cultivated.png"),
    URBAN(0, 0, 0, 0, 2.0, 1.0, 1.0, 3.0, 1.5, Vision.RESTRICTED, Directionality.NONE, "h_tiles_l_urban.png", "tiles_l_urban.png", "s_tiles_l_urban.png"),
    DENSE_URBAN(1, 1, 1, 1, 3.5, 1.0, 1.5, 4.0, 2.0, Vision.RESTRICTED, Directionality.NONE, "h_tiles_h_urban.png", "tiles_h_urban.png", "s_tiles_h_urban.png"),
    URBAN_RUIN(0, 0, 0, 0, 2.0, 1.0, 1.0, 3.0, 1.5, Vision.RESTRICTED, Directionality.NONE, "h_tiles_rl_urban.png", "tiles_rl_urban.png", "s_tiles_rl_urban.png"),
    DENSE_URBAN_RUIN(1, 1, 1, 1, 3.5, 1.0, 1.5, 4.0, 2.0, Vision.RESTRICTED, Directionality.NONE, "h_tiles_rh_urban.png", "tiles_rh_urban.png", "s_tiles_rh_urban.png"),
    ROCKY(2, 2, 2, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.NONE, "h_tiles_rocky.png", "tiles_rocky.png", "s_tiles_rocky.png"),
    ESCARPMENT(3, 3, 3, 2, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.LOGICAL, "h_tiles_escarpment.png", "tiles_escarpment.png", "s_tiles_escarpment.png"),
    MAJOR_ESCARPMENT(9999, 9999, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.LOGICAL, "h_tiles_major_escarpment.png", "tiles_major_escarpment.png", "s_tiles_major_escarpment.png"),
    EVERGREEN_FOREST(2, 2, 2, 2, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.RESTRICTED, Directionality.NONE, "h_tiles_c_forest.png", "tiles_c_forest.png", "s_tiles_c_forest.png"),
    FOREST(2, 2, 2, 2, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.RESTRICTED, Directionality.NONE, "h_tiles_d_forest.png", "tiles_d_forest.png", "s_tiles_d_forest.png"),
    LIGHT_WOODS(1, 1, 1, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, Directionality.NONE, "h_tiles_M_forest.png", "tiles_M_forest.png", "s_tiles_M_forest.png"),
    JUNGLE(3, 3, 3, 3, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.RESTRICTED, Directionality.NONE, "h_tiles_t_forest.png", "tiles_t_forest.png", "s_tiles_t_forest.png"),
    FORTIFICATION(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.NONE, "h_tiles_fortifiedline.png", "tiles_fortifiedline.png", "s_tiles_fortifiedline.png"),
    ROAD(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.LOGICAL, "h_Tiles_road.png", "Tiles_road.png", "s_Tiles_road.png"),
    IMPROVED_ROAD(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.LOGICAL, "h_Tiles_improvedroad.png", "Tiles_improvedroad.png", "s_Tiles_improvedroad.png"),
    RAIL(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.LOGICAL, "h_Tiles_railroad.png", "Tiles_railroad.png", "s_Tiles_railroad.png"),
    BROKEN_RAIL(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.LOGICAL, "h_Tiles_railroad_damaged_bridge.png", "Tiles_railroad_damaged_bridge.png", "s_Tiles_railroad_damaged_bridge.png"),
    WADY(2, 2, 2, 1, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.NORMAL, Directionality.GRAPHICAL, "h_Tiles_dry_river.png", "Tiles_dry_river.png", "s_Tiles_dry_river.png"),
    SUPER_RIVER(9999, 2, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.GRAPHICAL, "h_Tiles_major_river.png", "Tiles_major_river.png", "s_Tiles_major_river.png"),
    CANAL(2, 0, 2, 2, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.GRAPHICAL, "h_Tiles_canal.png", "Tiles_canal.png", "s_Tiles_canal.png"),
    SUPER_CANAL(9999, 2, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.GRAPHICAL, "h_Tiles_major_canal.png", "Tiles_major_canal.png", "s_Tiles_major_canal.png"),
    RIVER(2, 0, 2, 2, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.GRAPHICAL, "h_tiles_river.png", "tiles_river.png", "s_tiles_river.png"),
    SHALLOW_WATER(9999, 9999, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.NONE, "h_tiles_s_water.png", "tiles_s_water.png", "s_tiles_s_water.png"),
    DEEP_WATER(9999, 9999, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.NONE, "h_tiles_d_water.png", "tiles_d_water.png", "s_tiles_d_water.png"),
    BORDER(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.GRAPHICAL, "h_Borders.png", "Borders.png", "s_Borders.png"),
    SHALLOW_WATER_DECORATOR(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.GRAPHICAL, "h_tiles_s_water.png", "tiles_s_water.png", "s_tiles_s_water.png"),
    DEEP_WATER_DECORATOR(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.GRAPHICAL, "h_tiles_d_water.png", "tiles_d_water.png", "s_tiles_d_water.png"),;
    private final int motorized;
    private final int amphibious;
    private final int mixed;
    private final int foot;
    private final double antiTank;
    private final double antiPersonnel;
    private final double vehicles;
    private final double infantry;
    private final double stationary;
    private final Vision vision;
    private final Directionality directionality;
    private final String graphicFileHigh, graphicFileMedium, graphicFileSmall;
    public final static Terrain[] ALL_TERRAINS = Terrain.values();
    private static final int[] subImageIndex = new int[]{
        64, 72, 68, 76, 66, 74, 70, 78, 127, 127,
        32, 40, 36, 44, 34, 42, 38, 46, 127, 127,
        96, 104, 100, 108, 98, 106, 102, 110, 127, 127,
        16, 24, 20, 28, 18, 26, 22, 30, 127, 127,
        80, 88, 84, 92, 82, 90, 86, 94, 127, 127,
        48, 56, 52, 60, 50, 58, 54, 62, 127, 127,
        112, 120, 116, 124, 114, 122, 118, 126, 127, 127,
        8, 4, 12, 2, 10, 6, 14, 1, 127, 127};
    private static final Map<Integer, Integer> indexDirectionMap = fillIndexMap();

    private Terrain(final int motor, final int amph, final int mixed, final int foot,
            final double antiTank, final double antiPersonnel,
            final double vehicles, final double infantry, final double stationary,
            final Vision vision, final Directionality directionality, final String graphicFileHigh,
            final String graphicFileMedium, final String graphicFileSmall) {
        this.motorized = motor;
        this.amphibious = amph;
        this.mixed = mixed;
        this.foot = foot;
        this.antiTank = antiTank;
        this.antiPersonnel = antiPersonnel;
        this.vehicles = vehicles;
        this.infantry = infantry;
        this.stationary = stationary;
        this.vision = vision;
        this.directionality = directionality;
        this.graphicFileHigh = graphicFileHigh;
        this.graphicFileMedium = graphicFileMedium;
        this.graphicFileSmall = graphicFileSmall;
    }

    public int getMotorized() {
        return motorized;
    }

    public int getMixed() {
        return mixed;
    }

    public int getFoot() {
        return foot;
    }

    public int getAmphibious() {
        return amphibious;
    }

    public double getAntiPersonnel() {
        return antiPersonnel;
    }

    public double getAntiTank() {
        return antiTank;
    }

    public double getInfantry() {
        return infantry;
    }

    public double getStationary() {
        return stationary;
    }

    public double getVehicles() {
        return vehicles;
    }

    public Vision getVision() {
        return vision;
    }

    public Directionality getDirectionality() {
        return directionality;
    }

    public String getGraphicFileHigh() {
        return graphicFileHigh;
    }

    public String getGraphicFileMedium() {
        return graphicFileMedium;
    }

    public String getGraphicFileSmall() {
        return graphicFileSmall;
    }

    /**
     * Get the index of subimage in terrain image files Integer bits are used as flags for directions "N, NE, SE, S, SW,
     * NW, C"
     *
     * For example, a tile with "N NE SE" = bin("1110000") = 112 would be in the first column, seventh row
     *
     * @param directions Integer with the bits as directions
     * @return the associated value of the terrain subimage position
     */
    public int getIndexByDirections(int directions) {
        return indexDirectionMap.get(directions);
    }

    /**
     * Fills the map with the static array
     *
     * @return an unmodifiable map<subimageposition,indexinstaticarray>
     */
    private static Map<Integer, Integer> fillIndexMap() {

        Map<Integer, Integer> m = new HashMap<>();
        int i = 0;
        for (int b : subImageIndex) {
            m.put(b, i++);
        }

        return Collections.unmodifiableMap(m);
    }
}
