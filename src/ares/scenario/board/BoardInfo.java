package ares.scenario.board;

import ares.io.AresIO;
import ares.io.AresPaths;
import ares.platform.util.ImageUtils;
import java.awt.image.BufferedImage;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class BoardInfo {

    final private int hexRadius;
    final private int hexDiameter;
    final private int hexHeight;
    final private int hexSide;
    final private int width;
    final private int height;
    final private int imageWidth;
    final private int imageHeight;

    public BoardInfo(Board board) {
        width = board.getWidth();
        height = board.getHeight();
        BufferedImage image = ImageUtils.loadImage(AresIO.ARES_IO.getFile(AresPaths.GRAPHICS_MEDIUM.getPath(), "tiles_misc.png"));
        hexDiameter = image.getWidth() / 10;
        hexRadius = hexDiameter / 2;
        hexSide = hexRadius * 3 / 2 + 2;
        hexHeight = (int) (hexRadius * Math.sqrt(3));
        imageWidth = hexDiameter + (width - 1) * hexSide;
        imageHeight = (int) (((height * 2) + 1) * hexHeight) / 2;
    }

    public int getHexRadius() {
        return hexRadius;
    }

    public int getHexDiameter() {
        return hexDiameter;
    }

    public int getHexHeight() {
        return hexHeight;
    }

    public int getHexSide() {
        return hexSide;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public boolean validCoordinates(int i, int j) {
        return i >= 0 && i < width && j >= 0 && j < height;
    }
}
