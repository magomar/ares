package ares.application.graphics.providers;

import ares.application.graphics.GraphicsProfile;
import ares.io.FileIO;
import java.awt.Dimension;
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
     * Returns the actual filename of a particular graphics provider.
     * for the graphics {@code profile} passed as a parameter
     *
     * @see GraphicsProfile
     * @return the base filename
     */
    String getFilename(T profile);

    /**
     * Gets the sprite located in given {@code row} and {@code column} of the graphics of this provider
     *
     * @param row
     * @param column
     * @return
     */
    BufferedImage getImage(T profile, int row, int column, FileIO fileSystem);

    /**
     * Gets the sprite in coordinates encoded by a single {@code index}
     * <p> {@code column = index / rows}
     * <p> {@code row = index % rows}
     *
     * @param profile
     * @param index
     * @param fileSystem
     * @return
     */
    BufferedImage getImage(T profile, int index, FileIO fileSystem);

    /**
     * Gets the first or only sprite in the graphics of this provider
     * <p> {@code column = 1}
     * <p> {@code row = 1}
     *
     * @param profile
     * @param index
     * @param fileSystem
     * @return
     */
    BufferedImage getImage(T profile, FileIO fileSystem);

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
