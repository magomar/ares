package ares.scenario.board;

import ares.application.graphics.AresGraphicsProfile;
import ares.application.graphics.AresMiscGraphics;
import ares.application.graphics.providers.GraphicsProvider;
import ares.io.FileIO;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * Tiles have special features besides common terrain such as weather or buildings that affect the tile * Features
 * images are on the Terrain.OPEN file here we specify on which row and column<p>
 *
 * (0,3) is an invisible image
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine Heine <heisncfr@inf.upv.es> *
 */
public enum Feature implements GraphicsProvider<AresGraphicsProfile> {

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
    EXCLUDED_2(5, 7)
    ;
    private final int imageRow;
    private final int imageColumn;

    private Feature(final int imageColumn, final int imageRow) {
        this.imageRow = imageRow;
        this.imageColumn = imageColumn;
    }

    public int getImageRow() {
        return imageRow;
    }

    public int getImageCol() {
        return imageColumn;
    }

    @Override
    public String getFilename() {
        return AresMiscGraphics.TERRAIN_MISCELANEOUS.getFilename();
    }

    @Override
    public BufferedImage getImage(AresGraphicsProfile profile, int row, int column, FileIO fileSystem) {
        return AresMiscGraphics.TERRAIN_MISCELANEOUS.getImage(profile, row, column, fileSystem);
    }

    @Override
    public BufferedImage getImage(AresGraphicsProfile profile, int index, FileIO fileSystem) {
        return AresMiscGraphics.TERRAIN_MISCELANEOUS.getImage(profile, index, fileSystem);
    }

    @Override
    public BufferedImage getImage(AresGraphicsProfile profile, FileIO fileSystem) {
        return AresMiscGraphics.TERRAIN_MISCELANEOUS.getImage(profile, fileSystem);
    }

    @Override
    public BufferedImage getFullImage(AresGraphicsProfile profile, FileIO fileSystem) {
        return AresMiscGraphics.TERRAIN_MISCELANEOUS.getFullImage(profile, fileSystem);
    }

    @Override
    public Dimension getFullImageDimension(AresGraphicsProfile profile) {
        return AresMiscGraphics.TERRAIN_MISCELANEOUS.getFullImageDimension(profile);
    }

    @Override
    public String getFilename(AresGraphicsProfile profile) {
        return AresMiscGraphics.TERRAIN_MISCELANEOUS.getFilename(profile);
    }
}
