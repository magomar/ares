package ares.scenario.forces;

import ares.application.gui.profiles.GraphicProperties;
import ares.application.gui.profiles.GraphicsModel;
import ares.application.gui.profiles.GraphicsProfile;
import ares.application.gui.providers.ImageProvider;
import ares.application.gui.providers.ImageProviderFactory;
import ares.application.gui.providers.ImageProviderFactoryMethods;
import config.NonProfiledGraphicProperty;
import config.ProfiledGraphicProperty;
import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Sergio Musoles
 */
public enum UnitsColor implements ImageProviderFactory {

    UNITS_BLUE_0(Color.WHITE),
    UNITS_BLUE_1(Color.WHITE),
    UNITS_BLUE_2(Color.WHITE),
    UNITS_BLUE_3(Color.CYAN),
    UNITS_BLUE_4(Color.YELLOW),
    UNITS_BROWN_0(Color.WHITE),
    UNITS_BROWN_1(Color.WHITE),
    UNITS_BROWN_2(Color.WHITE),
    UNITS_BROWN_3(Color.WHITE),
    UNITS_BROWN_4(Color.BLACK),
    UNITS_GRAY_0(Color.BLACK),
    UNITS_GRAY_1(Color.BLACK),
    UNITS_GRAY_2(Color.BLACK),
    UNITS_GRAY_3(Color.BLACK),
    UNITS_GRAY_4(Color.BLACK),
    UNITS_GREEN_0(Color.BLACK),
    UNITS_GREEN_1(Color.BLACK),
    UNITS_GREEN_2(Color.WHITE),
    UNITS_GREEN_3(Color.WHITE),
    UNITS_GREEN_4(Color.WHITE),
    UNITS_RED_0(Color.WHITE),
    UNITS_RED_1(Color.WHITE),
    UNITS_RED_2(Color.YELLOW),
    UNITS_RED_3(Color.WHITE),
    UNITS_RED_4(Color.WHITE),
    UNITS_BLUELT_0(Color.BLACK),
    UNITS_BLUELT_1(Color.BLACK),
    UNITS_BLUELT_2(Color.BLACK),
    UNITS_BLUELT_3(Color.BLACK),
    UNITS_BLUELT_4(Color.BLACK),
    UNITS_WHITE_0(Color.BLACK),
    UNITS_WHITE_1(Color.RED),
    UNITS_WHITE_2(Color.BLACK),
    UNITS_WHITE_3(Color.RED),
    UNITS_WHITE_4(Color.BLACK),
    UNITS_YELLOW_0(Color.RED),
    UNITS_YELLOW_1(Color.BLACK),
    UNITS_YELLOW_2(Color.BLACK),
    UNITS_YELLOW_3(Color.BLUE),
    UNITS_YELLOW_4(Color.RED),
    UNITS_TAN_0(Color.BLACK),
    UNITS_TAN_1(Color.BLUE),
    UNITS_TAN_2(Color.BLACK),
    UNITS_TAN_3(Color.RED),
    UNITS_TAN_4(Color.RED),
    UNITS_GREENLT_0(Color.BLACK),
    UNITS_GREENLT_1(Color.BLACK),
    UNITS_GREENLT_2(Color.BLACK),
    UNITS_GREENLT_3(Color.YELLOW),
    UNITS_GREENLT_4(Color.BLACK),
    UNITS_GREENDK_0(Color.WHITE),
    UNITS_GREENDK_1(Color.WHITE),
    UNITS_GREENDK_2(Color.WHITE),
    UNITS_GREENDK_3(Color.BLUE),
    UNITS_GREENDK_4(Color.BLACK),
    UNITS_012_0(Color.RED),
    UNITS_012_1(Color.BLACK),
    UNITS_012_2(Color.WHITE),
    UNITS_012_3(Color.BLACK),
    UNITS_012_4(Color.BLACK),
    UNITS_013_0(Color.BLACK),
    UNITS_013_1(Color.BLACK),
    UNITS_013_2(Color.BLACK),
    UNITS_013_3(Color.RED),
    UNITS_013_4(Color.BLUE),
    UNITS_014_0(Color.WHITE),
    UNITS_014_1(Color.WHITE),
    UNITS_014_2(Color.WHITE),
    UNITS_014_3(Color.RED),
    UNITS_014_4(Color.BLACK),
    UNITS_015_0(Color.BLACK),
    UNITS_015_1(Color.BLACK),
    UNITS_015_2(Color.BLACK),
    UNITS_015_3(Color.BLACK),
    UNITS_015_4(Color.BLACK),
    UNITS_016_0(Color.BLACK),
    UNITS_016_1(Color.BLACK),
    UNITS_016_2(Color.BLACK),
    UNITS_016_3(Color.BLACK),
    UNITS_016_4(Color.BLACK),
    UNITS_017_0(Color.WHITE),
    UNITS_017_1(Color.WHITE),
    UNITS_017_2(Color.WHITE),
    UNITS_017_3(Color.YELLOW),
    UNITS_017_4(Color.BLACK),
    UNITS_018_0(Color.BLACK),
    UNITS_018_1(Color.BLACK),
    UNITS_018_2(Color.RED),
    UNITS_018_3(Color.RED),
    UNITS_018_4(Color.BLACK),
    UNITS_019_0(Color.YELLOW),
    UNITS_019_1(Color.YELLOW),
    UNITS_019_2(Color.WHITE),
    UNITS_019_3(Color.WHITE),
    UNITS_019_4(Color.WHITE),
    UNITS_020_0(Color.BLACK),
    UNITS_020_1(Color.BLACK),
    UNITS_020_2(Color.BLACK),
    UNITS_020_3(Color.RED),
    UNITS_020_4(Color.BLACK),
    UNITS_021_0(Color.WHITE),
    UNITS_021_1(Color.WHITE),
    UNITS_021_2(Color.ORANGE),
    UNITS_021_3(Color.WHITE),
    UNITS_021_4(Color.WHITE),
    UNITS_022_0(Color.WHITE),
    UNITS_022_1(Color.WHITE),
    UNITS_022_2(Color.WHITE),
    UNITS_022_3(Color.BLACK),
    UNITS_022_4(Color.BLACK);
    private final Color foreground;
    private final String filename;
    private final Point[] coordinatesByIndex;

    private UnitsColor(Color foreground) {
        this.foreground = foreground;
        filename = name().toLowerCase() + ".png";
//        int rows = GraphicsModel.getProperty(NonProfiledGraphicProperty.UNITS_ROWS);
//        int rows = GraphicProperties.getInt(NonProfiledGraphicProperty.UNITS_ROWS);
        int rows = 8;
//        int columns = GraphicsModel.getProperty(NonProfiledGraphicProperty.UNITS_COLUMNS);
//        int columns = GraphicProperties.getInt(NonProfiledGraphicProperty.UNITS_COLUMNS);
        int columns = 16;
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
    public String getFilename() {
        return filename;
    }

    @Override
    public ImageProvider createImageProvider(GraphicsProfile profile) {
        int rows = GraphicsModel.INSTANCE.getProperty(NonProfiledGraphicProperty.UNITS_ROWS);
        int columns = GraphicsModel.INSTANCE.getProperty(NonProfiledGraphicProperty.UNITS_COLUMNS);
        int fullImageWidth = GraphicsModel.INSTANCE.getProperty(ProfiledGraphicProperty.UNITS_WIDTH);
        int fullImageHeight = GraphicsModel.INSTANCE.getProperty(ProfiledGraphicProperty.UNITS_HEIGHT);
        return ImageProviderFactoryMethods.createImageProvider(filename, rows, columns, fullImageWidth, fullImageHeight, profile);
    }

//    public BufferedImage getImage(ImageProvider provider, int index) {
//        return provider.getImage(coordinatesByIndex[index]);
//    }
    public Point getCoordinates(int index) {
        return coordinatesByIndex[index];
    }
}
