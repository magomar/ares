package ares.application.shared.gui.providers;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface ImageProvider {

    /**
     * Returns filename of a graphics image file
     *
     * @return the filename
     */
    String getFilename();

    /**
     * Gets the number of columns in the graphics image. This is the number of images per row
     *
     * @return the number of columns
     */
    int getColumns();

    /**
     * Gets the number of rows in the graphics image. This is the number of images per column
     *
     * @return the number of rows
     */
    int getRows();

    /**
     * Gets the image located in the indicated {@code coordinates} of the graphics of this provider
     *
     * @param coordinates
     * @return the sprite image
     */
    BufferedImage getImage(Point coordinates);

    /**
     * Gets the image located in given {@code row} and {@code column} of the graphics of this provider
     *
     * @param column
     * @param row
     * @return the sprite image
     */
    public BufferedImage getImage(int column, int row);

    /**
     * Gets the image in coordinates encoded by a single {@code index}
     * <p>{@code column = index / rows}
     * <p>{@code row = index % rows}
     *
     * @param index
     * @return the image
     */
    BufferedImage getImage(int index);

    /**
     * Gets the complete image containing all the graphics for this provider
     *
     * @return
     */
    BufferedImage getFullImage();

    /**
     * Gets the dimension of the image containing the graphics of this provider
     *
     * @return the size of the full image
     */
    Dimension getFullImageSize();

    /**
     * Gets the dimension of the single images contained in the graphics of this provider. Graphics are stored in image files
     * that may contain multiple images distributed in an array
     *
     * @return the size of the individual images
     */
    Dimension getImageSize();
}
