package ares.application.gui.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class ImageTools {

    /**
     * Loads an image from file
     *
     * @param file
     * @return the image
     */
    public static BufferedImage loadImage(File file) {
        BufferedImage i = null;
        try {
            i = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(ImageTools.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }

    /**
     * Saves image into file
     *
     * @param image
     * @param file
     */
    public static void saveImage(RenderedImage image, File file) {
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException ex) {
            Logger.getLogger(ImageTools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
