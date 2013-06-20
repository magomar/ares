package ares.platform.scenario.board;

import ares.application.shared.gui.profiles.GraphicProperties;
import ares.application.shared.gui.profiles.NonProfiledGraphicProperty;
import ares.application.shared.gui.profiles.ProfiledGraphicProperty;
import ares.application.shared.gui.providers.ImageProvider;
import ares.application.shared.gui.providers.MatrixImageProvider;
import ares.application.shared.gui.providers.ProfiledImageProviderFactory;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum Terrain implements ProfiledImageProviderFactory {
    //motor, amph, mixed, foot, AT, AP, vehicles, infantry, stationary, vision, directional, microProfile
    OPEN(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false, true),
    ARID(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false, true),
    SAND(1, 1, 1, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false, true),
    DUNES(9999, 9999, 9999, 3, 1.0, 1.0, 1.0, 3.0, 2.0, Vision.NORMAL, false, true),
    BADLANDS(9999, 9999, 9999, 2, 1.0, 1.0, 1.0, 4.0, 3.0, Vision.NORMAL, false, false),
    HILLS(2, 2, 2, 1, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.NORMAL, false, true),
    MOUNTAINS(3, 3, 3, 3, 3.5, 1.5, 1.0, 3.0, 2.0, Vision.NORMAL, false, true),
    ALPINE(9999, 9999, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, false, true),
    MARSH(3, 3, 3, 2, 2.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, false, true),
    FLOODED_MARSH(9999, 9999, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, false, true),
    SHALLOW_WATER(9999, 9999, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false, true),
    DEEP_WATER(9999, 9999, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false, true),
    CROPLANDS(1, 1, 1, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, false, false),
    BOCAGE_HEDGEROW(2, 2, 2, 2, 2.0, 3.0, 1.0, 3.0, 1.5, Vision.RESTRICTED, false, false),
    URBAN(0, 0, 0, 0, 2.0, 1.0, 1.0, 3.0, 1.5, Vision.RESTRICTED, false, true),
    DENSE_URBAN(1, 1, 1, 1, 3.5, 1.0, 1.5, 4.0, 2.0, Vision.RESTRICTED, false, true),
    URBAN_RUIN(0, 0, 0, 0, 2.0, 1.0, 1.0, 3.0, 1.5, Vision.RESTRICTED, false, true),
    DENSE_URBAN_RUIN(1, 1, 1, 1, 3.5, 1.0, 1.5, 4.0, 2.0, Vision.RESTRICTED, false, true),
    ROCKY(2, 2, 2, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false, false),
    ESCARPMENT(3, 3, 3, 2, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, true, false),
    MAJOR_ESCARPMENT(9999, 9999, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, true, false),
    WADY(2, 2, 2, 1, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.NORMAL, false, false),
    RIVER(2, 0, 2, 2, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false, true),
    SUPER_RIVER(9999, 2, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false, true),
    CANAL(2, 0, 2, 2, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false, true),
    SUPER_CANAL(9999, 2, 9999, 9999, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false, true),
    EVERGREEN_FOREST(2, 2, 2, 2, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.RESTRICTED, false, false),
    FOREST(2, 2, 2, 2, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.RESTRICTED, false, false),
    LIGHT_WOODS(1, 1, 1, 1, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.NORMAL, false, false),
    JUNGLE(3, 3, 3, 3, 1.0, 1.0, 1.0, 2.0, 1.5, Vision.RESTRICTED, false, false),
    FORTIFICATION(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false, false),
    ROAD(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, true, false),
    IMPROVED_ROAD(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, true, false),
    RAIL(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, true, false),
    BROKEN_RAIL(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, true, false),
    SHALLOW_WATER_DECORATOR(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false, false),
    DEEP_WATER_DECORATOR(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false, false),
    BORDER(0, 0, 0, 0, 1.0, 1.0, 1.0, 1.0, 1.0, Vision.OPEN, false, false),;
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
    private final boolean microProfile;
    public static final Set<Terrain> ANY_WATER = EnumSet.of(SHALLOW_WATER, DEEP_WATER);
    public static final Set<Terrain> ANY_RIVER = EnumSet.of(RIVER, SUPER_RIVER, CANAL, SUPER_CANAL);
    public static final Set<Terrain> ANY_ROAD = EnumSet.of(ROAD, IMPROVED_ROAD);

    private Terrain(final int motor, final int amph, final int mixed, final int foot,
                    final double antiTank, final double antiPersonnel,
                    final double vehicles, final double infantry, final double stationary,
                    final Vision vision, final boolean directional, final boolean microProfile) {
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
        this.microProfile = microProfile;

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
     * @param bitMask
     * @return
     */
    public static int getImageIndex(int bitMask) {
        return bitMask - 1;
    }

    @Override
    public String getFilename(int profile) {
        String prefix = GraphicProperties.getProfilePrefix(profile);
        if (profile == 0 && !microProfile) {
            return prefix + "_terrain_null.png";
        } else {
            return prefix + "_" + filename;
        }
    }

    @Override
    public ImageProvider createImageProvider(int profile) {
        int rows = GraphicProperties.getProperty(NonProfiledGraphicProperty.TILES_ROWS);
        int columns = GraphicProperties.getProperty(NonProfiledGraphicProperty.TILES_COLUMNS);
        int fullImageWidth = GraphicProperties.getProperty(ProfiledGraphicProperty.TILES_WIDTH, profile);
        int fullImageHeight = GraphicProperties.getProperty(ProfiledGraphicProperty.TILES_HEIGHT, profile);
        return new MatrixImageProvider(GraphicProperties.getProfilePath(profile), getFilename(profile), rows, columns, fullImageWidth, fullImageHeight);
    }
}
