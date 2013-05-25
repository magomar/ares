package ares.application.gui.profiles;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class GraphicProperties {
    public static final Properties GRAPHICS;
    private static final String filename = "config/graphics.properties";
    static {
        GRAPHICS = new Properties();
        try (InputStream is = GraphicProperties.class.getClassLoader().getResourceAsStream(filename)) {
            GRAPHICS.load(is);
        } catch (IOException ex) {
            Logger.getLogger(GraphicProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
