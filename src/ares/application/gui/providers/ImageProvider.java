package ares.application.gui.providers;

import ares.application.gui.profiles.GraphicsProfile;
import ares.platform.io.FileIO;
import java.awt.Dimension;
import java.awt.Point;
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
     * Gets the sprite located in given {@code coordinates} of the graphics of this provider
     *
     * @param column
     * @param row
     * @return
     */
    BufferedImage getImage(Point coordinates);

    /**
     * Gets the sprite located in given {@code row} and {@code column} of the graphics of this provider
     *
     * @param column
     * @param row
     * @return
     */
    public BufferedImage getImage(int column, int row);

    /**
     * Gets the sprite in coordinates encoded by a single {@code index}
     * <p> {@code column = index / rows}
     * <p> {@code row = index % rows}
     *
     * @param profile
     * @param index
     * @return
     */
    BufferedImage getImage(int index);

    /**
     * Gets the first or only sprite in the graphics of this provider
     * <p> {@code column = 1}
     * <p> {@code row = 1}
     *
     * @param profile
     * @param index
     * @return
     */
    BufferedImage getImage();

    /**
     * Gets the complete image containing all the graphics for this provider
     *
     * @param profile
     * @return
     */
    BufferedImage getFullImage();

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
