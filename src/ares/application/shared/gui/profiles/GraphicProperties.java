package ares.application.shared.gui.profiles;

import java.awt.Font;
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
    public static final String FONT_NAME = "Arial";
    public static final int FONT_STYLE = Font.PLAIN;

    static {
        GRAPHICS = new Properties();
        try (InputStream is = GraphicProperties.class.getClassLoader().getResourceAsStream(filename)) {
            GRAPHICS.load(is);
        } catch (IOException ex) {
            Logger.getLogger(GraphicProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int getNumProfiles() {
        return Integer.parseInt(GRAPHICS.getProperty("num_profiles"));
    }

    public static String getProfilePath(int profile) {
        String[] values = GRAPHICS.getProperty("paths").split(",");
        return values[profile];
    }

    public static String getProfilePrefix(int profile) {
        String[] values = GRAPHICS.getProperty("filename_prefix").split(",");
        return values[profile];
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