package ares.scenario.board;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum Terrain {

    OPEN(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.NONE),
    ARID(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.NONE),
    SAND(1, 1, 1, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.NONE),
    DUNES(9999, 9999, 9999, 3, 1.0, 1.0, 1.0, 3.0, 2.0, Vision.NORMAL, Directionality.NONE),
    BADLANDS(9999, 9999, 9999, 2, 1.0, 1.0, 1.0, 4.0, 3.0, Vision.NORMAL, Directionality.NONE),
    HILLS(2, 2, 2, 1, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.NORMAL, Directionality.NONE),
    MOUNTAINS(3, 3, 3, 3, 3.5, 1.5, 1.0, 3.0, 2.0, Vision.NORMAL, Directionality.NONE),
    ALPINE(9999, 9999, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, Directionality.NONE),
    MARSH(3, 3, 3, 2, 2.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, Directionality.NONE),
    FLOODED_MARS(9999, 9999, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, Directionality.NONE),
    SHALLOW_WATER(9999, 9999, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.NONE),
    DEEP_WATER(9999, 9999, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.NONE),
    CROPLANDS(1, 1, 1, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, Directionality.NONE),
    BOCAGE_HEDGEROW(2, 2, 2, 2, 2.0, 3.0, 1.0, 3.0, 1.5, Vision.RESTRICTED, Directionality.NONE),
    URBAN(0, 0, 0, 0, 2.0, 1.0, 1.0, 3.0, 1.5, Vision.RESTRICTED, Directionality.NONE),
    DENSE_URBAN(1, 1, 1, 1, 3.5, 1.0, 1.5, 4.0, 2.0, Vision.RESTRICTED, Directionality.NONE),
    URBAN_RUIN(0, 0, 0, 0, 2.0, 1.0, 1.0, 3.0, 1.5, Vision.RESTRICTED, Directionality.NONE),
    DENSE_URBAN_RUIN(1, 1, 1, 1, 3.5, 1.0, 1.5, 4.0, 2.0, Vision.RESTRICTED, Directionality.NONE),
    ROCKY(2, 2, 2, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.NONE),
    ESCARPMENT(3, 3, 3, 2, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.LOGICAL),
    MAJOR_ESCARPMENT(9999, 9999, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.LOGICAL),
    WADY(2, 2, 2, 1, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.NORMAL, Directionality.GRAPHICAL),
    RIVER(2, 0, 2, 2, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.GRAPHICAL),
    SUPER_RIVER(9999, 2, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.GRAPHICAL),
    CANAL(2, 0, 2, 2, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.GRAPHICAL),
    SUPER_CANAL(9999, 2, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.GRAPHICAL),
    EVERGREEN_FOREST(2, 2, 2, 2, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.RESTRICTED, Directionality.NONE),
    FOREST(2, 2, 2, 2, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.RESTRICTED, Directionality.NONE),
    LIGHT_WOODS(1, 1, 1, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, Directionality.NONE),
    JUNGLE(3, 3, 3, 3, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.RESTRICTED, Directionality.NONE),
    FORTIFICATION(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.NONE),
    ROAD(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.LOGICAL),
    IMPROVED_ROAD(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.LOGICAL),
    RAIL(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.LOGICAL),
    BROKEN_RAIL(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.LOGICAL),
    SHALLOW_WATER_DECORATOR(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.GRAPHICAL),
    DEEP_WATER_DECORATOR(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.GRAPHICAL),
    BORDER(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.GRAPHICAL);
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
    private final String filename;
    public final static Terrain[] ALL_TERRAINS = Terrain.values();

    private Terrain(final int motor, final int amph, final int mixed, final int foot,
            final double antiTank, final double antiPersonnel,
            final double vehicles, final double infantry, final double stationary,
            final Vision vision, final Directionality directionality) {
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
        this.filename = name().toLowerCase()+".png";
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

    public String getFilename() {
        return filename;
    }

    public static int getImageIndex(int bitMask) {
//        return 63 & bitMask; // not neccessary if bitMask is never 0
        return bitMask - 1;
    }
}
