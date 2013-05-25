package ares.application.gui.providers;

import ares.application.gui.profiles.GraphicsProfile;

/**
 * Classess implementing this interface are used to describe graphic files which are arranged as a matrix of independent
 * images.
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface ImageProviderFactory {

    /**
     *
     * @return the base filename, which can later be used to build diferent filenames for diferent graphic profiles.
     */
    String getFilename();

    /**
     * 
     * @return an image provider
     */
    ImageProvider createImageProvider(GraphicsProfile profile);

}
