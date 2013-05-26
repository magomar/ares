package ares.platform.io;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class FileIO {

    private static final Logger LOG = Logger.getLogger(FileIO.class.getName());

    /**
     * Unmarshalls Json element from file into java object
     *
     * @param xmlFile the XML file to be unmarshalled
     * @return
     */
    public static <T> T unmarshallJson(File file, Class<T> c) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        T object = null;
        try {
            object = mapper.readValue(file, c);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return object;
    }

    /**
     * Marshalls Java object into JSON file
     *
     * @param object object to be marshalled
     * @param file file to save the marshalled object
     * @return
     */
    public File marshallJson(Object object, File file) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        try {
            writer.writeValue(file, object);
            return file;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Loads an image from file
     *
     * @param file
     * @return the image
     */
    public static BufferedImage loadImage(File file) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error loading " + file.getPath(), ex);
        }
        return bi;
    }

    /**
     * Saves image into file
     *
     * @param image
     * @param file
     */
    public static void saveImage(RenderedImage image, File file, String imageFormat) {
        try {
            ImageIO.write(image, imageFormat, file);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
