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

    private static final Properties GRAPHICS;
    private static final String filename = "config/graphics.properties";

    static {
        GRAPHICS = new Properties();
        try (InputStream is = GraphicProperties.class.getClassLoader().getResourceAsStream(filename)) {
            GRAPHICS.load(is);
        } catch (IOException ex) {
            Logger.getLogger(GraphicProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int getProperty(GraphicProperty property) {
        return Integer.parseInt(GRAPHICS.getProperty(property.getName()));
    }

    public static double getRealProperty(GraphicProperty property) {
        return Double.parseDouble(GRAPHICS.getProperty(property.getName()));
    }

    public static int getProperty(GraphicProperty property, int profile) {
        String[] values = GRAPHICS.getProperty(property.getName()).split(",");
        return Integer.parseInt(values[profile]);
    }

    public static double getRealProperty(GraphicProperty property, int profile) {
        String[] values = GRAPHICS.getProperty(property.getName()).split(",");
        return Double.parseDouble(values[profile]);
    }
//    private static boolean isInteger(String s) {
//        try {
//            Integer.parseInt(s);
//        } catch (NumberFormatException e) {
//            return false;
//        }
//        return true;
//    }
//
//    private static boolean isDouble(String s) {
//        try {
//            Double.parseDouble(s);
//        } catch (NumberFormatException e) {
//            return false;
//        }
//        return true;
//    }
}
