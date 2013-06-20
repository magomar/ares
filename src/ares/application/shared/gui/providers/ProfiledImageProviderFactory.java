package ares.application.shared.gui.providers;

/**
 * Classess implementing this interface are used to describe graphic files which are arranged as a matrix of independent
 * images, and have various sized-versions depending on a particular graphic {@code profile}, which is passed as a
 * parameter to the methods of this class
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface ProfiledImageProviderFactory {

    /**
     * @return the filename, which can later be used to build diferent filenames for diferent graphic profiles.
     */
    String getFilename(int profile);

    /**
     * @return an image provider
     */
    ImageProvider createImageProvider(int profile);
}
