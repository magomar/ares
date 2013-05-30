package ares.application.gui.providers;

/**
 * Classess implementing this interface are used to describe graphic files which are arranged as a matrix of independent
 * images.
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface ImageProviderFactory {

    /**
     *
     * @return the filename, which can later be used to build diferent filenames for diferent graphic profiles.
     */
    String getFilename(int profile);

    /**
     * 
     * @return an image provider
     */
    ImageProvider createImageProvider(int profile);

}
