package ares.application.graphics.providers;

import ares.application.graphics.GraphicsProfile;
import ares.io.FileIO;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface ImageProvider {

    /**
     * Returns the base filename of a particular graphics provider.
     * <p>Note that the actual filename depends on the particular {@link GraphicsProfile}
     *
     * @return
     */
    String getFilename();

    /**
     * Gets the number of columns in the graphics image. This is the number of sprites per row
     *
     * @return
     */
    int getColumns();

    /**
     * Gets the number of rows in the graphics image. This is the number of sprites per column
     *
     * @return
     */
    int getRows();

    /**
     * Gets the sprite located in given {@code row} and {@code column} of the graphics of this provider
     *
     * @param row
     * @param column
     * @return
     */
    BufferedImage getImage(int row, int column, FileIO fileSystem);

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
    BufferedImage getImage(int index, FileIO fileSystem);

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
    BufferedImage getImage(FileIO fileSystem);
    
    /**
     * Gets the complete image containing all the graphics for this provider
     *
     * @param profile
     * @param fileSystem
     * @return
     */
    BufferedImage getFullImage(FileIO fileSystem);

    /**
     * Gets the dimension of the image containing the graphics of this provider
     *
     * @return
     */
    Dimension getFullImageDimension();

    /**
     * Gets the dimension of the sprite contained in the graphics of this provider
     * <p>Graphics are stored in files that may contain a matrix of sprites
     *
     * @return
     */
    Dimension getImageDimension();
}
