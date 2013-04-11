package ares.application.gui.providers;

import ares.application.gui.GraphicsProfile;
import ares.io.FileIO;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface GraphicsProvider<T extends Enum<T> & GraphicsProfile> {

    /**
     * Returns the base filename of a particular graphics provider.
     * <p>Note that the actual filename depends on the particular graphics profile
     *
     * @see GraphicsProfile
     * @return the base filename
     */
    String getFilename();

    /**
     * Returns the actual filename of a particular graphics provider. for the graphics {@code profile} passed as a
     * parameter
     *
     * @see GraphicsProfile
     * @return the base filename
     */
    String getFilename(T profile);

    /**
     * Gets the image located in given {@code row} and {@code column} of the graphics of this provider
     *
     * @param column
     * @param row
     * @return
     */
    BufferedImage getImage(T profile, Point coordinates, FileIO fileSystem);

    /**
     * Gets the complete image containing all the graphics for this provider
     *
     * @param profile
     * @param fileSystem
     * @return
     */
    BufferedImage getFullImage(T profile, FileIO fileSystem);

    /**
     * Gets the dimension of the image containing the graphics of this provider
     *
     * @return
     */
    Dimension getFullImageDimension(T profile);
}
