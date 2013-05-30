package ares.scenario.forces;

import ares.application.gui.profiles.GraphicProperties;
import ares.application.gui.providers.ImageProvider;
import ares.application.gui.providers.ImageProviderFactory;
import ares.application.gui.profiles.NonProfiledGraphicProperty;
import ares.application.gui.profiles.ProfiledGraphicProperty;
import ares.application.gui.providers.MatrixImageProvider;
import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Sergio Musoles
 */
public enum UnitsColor implements ImageProviderFactory {

    UNITS_BLUE_0(Color.WHITE, "blue", "0"),
    UNITS_BLUE_1(Color.WHITE, "blue", "1"),
    UNITS_BLUE_2(Color.WHITE, "blue", "2"),
    UNITS_BLUE_3(Color.CYAN, "blue", "3"),
    UNITS_BLUE_4(Color.YELLOW, "blue", "4"),
    UNITS_BROWN_0(Color.WHITE, "brown", "0"),
    UNITS_BROWN_1(Color.WHITE, "brown", "1"),
    UNITS_BROWN_2(Color.WHITE, "brown", "2"),
    UNITS_BROWN_3(Color.WHITE, "brown", "3"),
    UNITS_BROWN_4(Color.BLACK, "brown", "4"),
    UNITS_GRAY_0(Color.BLACK, "gray", "0"),
    UNITS_GRAY_1(Color.BLACK, "gray", "1"),
    UNITS_GRAY_2(Color.BLACK, "gray", "2"),
    UNITS_GRAY_3(Color.BLACK, "gray", "3"),
    UNITS_GRAY_4(Color.BLACK, "gray", "4"),
    UNITS_GREEN_0(Color.BLACK, "green", "0"),
    UNITS_GREEN_1(Color.BLACK, "green", "1"),
    UNITS_GREEN_2(Color.WHITE, "green", "2"),
    UNITS_GREEN_3(Color.WHITE, "green", "3"),
    UNITS_GREEN_4(Color.WHITE, "green", "4"),
    UNITS_RED_0(Color.WHITE, "red", "0"),
    UNITS_RED_1(Color.WHITE, "red", "1"),
    UNITS_RED_2(Color.YELLOW, "red", "2"),
    UNITS_RED_3(Color.WHITE, "red", "3"),
    UNITS_RED_4(Color.WHITE, "red", "4"),
    UNITS_BLUELT_0(Color.BLACK, "bluelt", "0"),
    UNITS_BLUELT_1(Color.BLACK, "bluelt", "1"),
    UNITS_BLUELT_2(Color.BLACK, "bluelt", "2"),
    UNITS_BLUELT_3(Color.BLACK, "bluelt", "3"),
    UNITS_BLUELT_4(Color.BLACK, "bluelt", "4"),
    UNITS_WHITE_0(Color.BLACK, "white", "0"),
    UNITS_WHITE_1(Color.RED, "white", "1"),
    UNITS_WHITE_2(Color.BLACK, "white", "2"),
    UNITS_WHITE_3(Color.RED, "white", "3"),
    UNITS_WHITE_4(Color.BLACK, "white", "4"),
    UNITS_YELLOW_0(Color.RED, "yellow", "0"),
    UNITS_YELLOW_1(Color.BLACK, "yellow", "1"),
    UNITS_YELLOW_2(Color.BLACK, "yellow", "2"),
    UNITS_YELLOW_3(Color.BLUE, "yellow", "3"),
    UNITS_YELLOW_4(Color.RED, "yellow", "4"),
    UNITS_TAN_0(Color.BLACK, "tan", "0"),
    UNITS_TAN_1(Color.BLUE, "tan", "1"),
    UNITS_TAN_2(Color.BLACK, "tan", "2"),
    UNITS_TAN_3(Color.RED, "tan", "3"),
    UNITS_TAN_4(Color.RED, "tan", "4"),
    UNITS_GREENLT_0(Color.BLACK, "greenlt", "0"),
    UNITS_GREENLT_1(Color.BLACK, "greenlt", "1"),
    UNITS_GREENLT_2(Color.BLACK, "greenlt", "2"),
    UNITS_GREENLT_3(Color.YELLOW, "greenlt", "3"),
    UNITS_GREENLT_4(Color.BLACK, "greenlt", "4"),
    UNITS_GREENDK_0(Color.WHITE, "greendk", "0"),
    UNITS_GREENDK_1(Color.WHITE, "greendk", "1"),
    UNITS_GREENDK_2(Color.WHITE, "greendk", "2"),
    UNITS_GREENDK_3(Color.BLUE, "greendk", "3"),
    UNITS_GREENDK_4(Color.BLACK, "greendk", "4"),
    UNITS_012_0(Color.RED, "012", "0"),
    UNITS_012_1(Color.BLACK, "012", "1"),
    UNITS_012_2(Color.WHITE, "012", "2"),
    UNITS_012_3(Color.BLACK, "012", "3"),
    UNITS_012_4(Color.BLACK, "012", "4"),
    UNITS_013_0(Color.BLACK, "013", "0"),
    UNITS_013_1(Color.BLACK, "013", "1"),
    UNITS_013_2(Color.BLACK, "013", "2"),
    UNITS_013_3(Color.RED, "013", "3"),
    UNITS_013_4(Color.BLUE, "013", "4"),
    UNITS_014_0(Color.WHITE, "014", "0"),
    UNITS_014_1(Color.WHITE, "014", "1"),
    UNITS_014_2(Color.WHITE, "014", "2"),
    UNITS_014_3(Color.RED, "014", "3"),
    UNITS_014_4(Color.BLACK, "014", "4"),
    UNITS_015_0(Color.BLACK, "015", "0"),
    UNITS_015_1(Color.BLACK, "015", "1"),
    UNITS_015_2(Color.BLACK, "015", "2"),
    UNITS_015_3(Color.BLACK, "015", "3"),
    UNITS_015_4(Color.BLACK, "015", "4"),
    UNITS_016_0(Color.BLACK, "016", "0"),
    UNITS_016_1(Color.BLACK, "016", "1"),
    UNITS_016_2(Color.BLACK, "016", "2"),
    UNITS_016_3(Color.BLACK, "016", "3"),
    UNITS_016_4(Color.BLACK, "016", "4"),
    UNITS_017_0(Color.WHITE, "017", "0"),
    UNITS_017_1(Color.WHITE, "017", "1"),
    UNITS_017_2(Color.WHITE, "017", "2"),
    UNITS_017_3(Color.YELLOW, "017", "3"),
    UNITS_017_4(Color.BLACK, "017", "4"),
    UNITS_018_0(Color.BLACK, "018", "0"),
    UNITS_018_1(Color.BLACK, "018", "1"),
    UNITS_018_2(Color.RED, "018", "2"),
    UNITS_018_3(Color.RED, "018", "3"),
    UNITS_018_4(Color.BLACK, "018", "4"),
    UNITS_019_0(Color.YELLOW, "019", "0"),
    UNITS_019_1(Color.YELLOW, "019", "1"),
    UNITS_019_2(Color.WHITE, "019", "2"),
    UNITS_019_3(Color.WHITE, "019", "3"),
    UNITS_019_4(Color.WHITE, "019", "4"),
    UNITS_020_0(Color.BLACK, "020", "0"),
    UNITS_020_1(Color.BLACK, "020", "1"),
    UNITS_020_2(Color.BLACK, "020", "2"),
    UNITS_020_3(Color.RED, "020", "3"),
    UNITS_020_4(Color.BLACK, "020", "4"),
    UNITS_021_0(Color.WHITE, "021", "0"),
    UNITS_021_1(Color.WHITE, "021", "1"),
    UNITS_021_2(Color.ORANGE, "021", "2"),
    UNITS_021_3(Color.WHITE, "021", "3"),
    UNITS_021_4(Color.WHITE, "021", "4"),
    UNITS_022_0(Color.WHITE, "022", "0"),
    UNITS_022_1(Color.WHITE, "022", "1"),
    UNITS_022_2(Color.WHITE, "022", "2"),
    UNITS_022_3(Color.BLACK, "022", "3"),
    UNITS_022_4(Color.BLACK, "022", "4"),;
    private final Color foreground;
    private final String filename;
    private final String microFilename;
    private final Point[] coordinatesByIndex;

    private UnitsColor(Color foreground, String counterColor, String symbolColor) {
        this.foreground = foreground;
        this.filename = "units_" + counterColor + "_" + symbolColor + ".png";
        this.microFilename = "units_" + counterColor + ".png";
        int rows = GraphicProperties.getProperty(NonProfiledGraphicProperty.UNITS_ROWS);
        int columns = GraphicProperties.getProperty(NonProfiledGraphicProperty.UNITS_COLUMNS);
        int numIcons = rows * columns;
        coordinatesByIndex = new Point[numIcons];
        for (int i = 0; i < numIcons; i++) {
            int column = i / rows;
            int row = i % rows;
            coordinatesByIndex[i] = new Point(column, row);
        }
    }

    public Color getForeground() {
        return foreground;
    }

    @Override
    public String getFilename(int profile) {
        String prefix = GraphicProperties.getProfilePrefix(profile);
        if (profile == 0) {
            return prefix + "_" + microFilename;
        } else {
            return prefix + "_" + filename;
        }
    }

    @Override
    public ImageProvider createImageProvider(int profile) {
        int rows = GraphicProperties.getProperty(NonProfiledGraphicProperty.UNITS_ROWS);
        int columns = GraphicProperties.getProperty(NonProfiledGraphicProperty.UNITS_COLUMNS);
        int fullImageWidth = GraphicProperties.getProperty(ProfiledGraphicProperty.UNITS_WIDTH, profile);
        int fullImageHeight = GraphicProperties.getProperty(ProfiledGraphicProperty.UNITS_HEIGHT, profile);
        return new MatrixImageProvider(GraphicProperties.getProfilePath(profile), getFilename(profile), 
                rows, columns, fullImageWidth, fullImageHeight);
    }

    public Point getCoordinates(int index) {
        return coordinatesByIndex[index];
    }
}
