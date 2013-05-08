package ares.application.gui.providers;

/**
 * Classess implementing this interface are used to describe graphic files which are arranged as a matrix of independent
 * images.
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface GraphicsDescriptor {

    /**
     *
     * @return the base filename, which can later be used to build diferent filenames for diferent graphic profiles.
     */
    String getFilename();

    /**
     *
     * @return the type of images provided
     */
    ImageProviderType getImageProviderType();

    /**
     *
     * @return the number of rows in the image matrix
     */
    int getRows();

    /**
     *
     * @return the number of columns in the image matrix
     */
    int getColumns();
}
