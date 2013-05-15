package ares.scenario.board;

import ares.application.gui.profiles.AresGraphicsProfile;
import ares.application.gui.providers.GraphicsDescriptor;
import ares.application.gui.providers.ImageProviderType;
import ares.engine.movement.MovementCost;
import java.util.EnumSet;
import java.util.Set;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum Terrain implements GraphicsDescriptor {
//motor, amph, mixed, foot, AT, AP, vehicles, infantry, stationary, vision, directional
//    OPEN(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, Directionality.NONE),

    ARID(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false),
    SAND(1, 1, 1, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false),
    DUNES(MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, 3, 1.0, 1.0, 1.0, 3.0, 2.0, Vision.NORMAL, false),
    BADLANDS(MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, 2, 1.0, 1.0, 1.0, 4.0, 3.0, Vision.NORMAL, false),
    HILLS(2, 2, 2, 1, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.NORMAL, false),
    MOUNTAINS(3, 3, 3, 3, 3.5, 1.5, 1.0, 3.0, 2.0, Vision.NORMAL, false),
    ALPINE(MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, false),
    MARSH(3, 3, 3, 2, 2.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, false),
    FLOODED_MARS(MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, false),
    SHALLOW_WATER(MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false),
    DEEP_WATER(MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false),
    CROPLANDS(1, 1, 1, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, false),
    BOCAGE_HEDGEROW(2, 2, 2, 2, 2.0, 3.0, 1.0, 3.0, 1.5, Vision.RESTRICTED, false),
    URBAN(0, 0, 0, 0, 2.0, 1.0, 1.0, 3.0, 1.5, Vision.RESTRICTED, false),
    DENSE_URBAN(1, 1, 1, 1, 3.5, 1.0, 1.5, 4.0, 2.0, Vision.RESTRICTED, false),
    URBAN_RUIN(0, 0, 0, 0, 2.0, 1.0, 1.0, 3.0, 1.5, Vision.RESTRICTED, false),
    DENSE_URBAN_RUIN(1, 1, 1, 1, 3.5, 1.0, 1.5, 4.0, 2.0, Vision.RESTRICTED, false),
    ROCKY(2, 2, 2, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false),
    ESCARPMENT(3, 3, 3, 2, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, true),
    MAJOR_ESCARPMENT(MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, true),
    WADY(2, 2, 2, 1, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.NORMAL, false),
    RIVER(2, 0, 2, 2, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false),
    SUPER_RIVER(MovementCost.IMPASSABLE, 2, MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false),
    CANAL(2, 0, 2, 2, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false),
    SUPER_CANAL(MovementCost.IMPASSABLE, 2, MovementCost.IMPASSABLE, MovementCost.IMPASSABLE, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false),
    EVERGREEN_FOREST(2, 2, 2, 2, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.RESTRICTED, false),
    FOREST(2, 2, 2, 2, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.RESTRICTED, false),
    LIGHT_WOODS(1, 1, 1, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, false),
    JUNGLE(3, 3, 3, 3, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.RESTRICTED, false),
    FORTIFICATION(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false),
    ROAD(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, true),
    IMPROVED_ROAD(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, true),
    RAIL(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, true),
    BROKEN_RAIL(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, true),
    SHALLOW_WATER_DECORATOR(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false),
    DEEP_WATER_DECORATOR(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false),
    BORDER(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false);
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
    private final boolean directional;
    private final String filename;
    private final ImageProviderType imageProviderType = ImageProviderType.TILE;
    public static final Set<Terrain> ANY_WATER = EnumSet.of(SHALLOW_WATER, DEEP_WATER);
    public static final Set<Terrain> ANY_RIVER = EnumSet.of(RIVER, SUPER_RIVER, CANAL, SUPER_CANAL);
    public static final Set<Terrain> ANY_ROAD = EnumSet.of(ROAD, IMPROVED_ROAD);

    private Terrain(final int motor, final int amph, final int mixed, final int foot,
            final double antiTank, final double antiPersonnel,
            final double vehicles, final double infantry, final double stationary,
            final Vision vision, final boolean directional) {
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
        this.directional = directional;
        filename = "terrain_" + name().toLowerCase() + ".png";
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

    public boolean isDirectional() {
        return directional;
    }

    /**
     * Obtains the image index in the image file, given a bitmask representing directions.<p>
     * There are 6 standard directions, so 2^6=64 combinations are possible. In addition, there is a special direction,
     * {@link Direction#C} that excludes any of the other directions. That makes 65 values to represent. However, the
     * absence of any direction is not represented (it is assumed bitMask is never 0), so we actually have 64 values,
     * numbered from 1 to 64.
     *
     * @see Direction
     * @param bitMask
     * @return
     */
    public static int getImageIndex(int bitMask) {
        return bitMask - 1;
    }

    @Override
    public String getFilename() {
        return filename;
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
