package ares.application.gui.providers;

import ares.application.gui.profiles.GraphicsProfile;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class ImageProviderFactoryMethods {
    public static ImageProvider createImageProvider(String filename, int rows, int columns, int fullImageWidth, int fullImageHeight, GraphicsProfile profile){
        return new ArrayImageProvider(profile.getPath(), profile.getFilename(filename), rows, columns, fullImageWidth, fullImageHeight);
    }
    
}
