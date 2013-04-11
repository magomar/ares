package ares.application.gui;

import ares.application.gui.providers.GraphicsProvider;
import ares.application.gui.providers.MultiProfileImageProvider;
import ares.application.gui.providers.ImageProviderType;
import ares.io.FileIO;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public enum AresMiscGraphics implements GraphicsProvider<AresGraphicsProfile> {

    TERRAIN_MISCELANEOUS(8, 8),
    TERRAIN_BORDER(8, 8),
    GRID(1, 1),
    BRASS_CURSOR(1, 1),
    STEEL_CURSOR(1, 1),
    RED_ARROWS(8, 6),
    GRAY_ARROWS(8, 6);
    private final String filename;
    private final MultiProfileImageProvider<AresGraphicsProfile, AresMiscGraphics> provider;
    private static final Point ORIGIN_COORDINATES = new Point(0, 0);

    private AresMiscGraphics(final int rows, final int columns) {
        filename = name().toLowerCase() + ".png";
        provider = new MultiProfileImageProvider<>(this, ImageProviderType.TILE, AresGraphicsProfile.class,
                rows, columns);
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public BufferedImage getImage(AresGraphicsProfile profile, Point coordinates, FileIO fileSystem) {
        return provider.getImage(profile, coordinates, fileSystem);
    }

    public BufferedImage getImage(AresGraphicsProfile profile, FileIO fileSystem) {
        return provider.getImage(profile,ORIGIN_COORDINATES, fileSystem);
    }

    @Override
    public BufferedImage getFullImage(AresGraphicsProfile profile, FileIO fileSystem) {
        return provider.getFullImage(profile, fileSystem);
    }

    @Override
    public Dimension getFullImageDimension(AresGraphicsProfile profile) {
        return provider.getFullImageDimension(profile);
    }

    @Override
    public String getFilename(AresGraphicsProfile profile) {
        return provider.getFilename(profile);
    }
}
