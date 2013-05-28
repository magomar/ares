package ares.application.gui.providers;

import ares.platform.io.FileIO;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.ref.SoftReference;
import java.nio.file.FileSystems;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class MatrixImageProvider implements ImageProvider {

    private static final Logger LOG = Logger.getLogger(MatrixImageProvider.class.getName());
    private final String filename;
    /**
     * The dimension of the sprite image in pixels
     */
    private final Dimension imageDimension;
    private final int rows;
    private final int columns;
    /**
     * The dimension of the image where the sprites are stored
     */
    private final Dimension fullImageDimension;
    private SoftReference<BufferedImage> image;
    private final String path;

    public MatrixImageProvider(String path, String filename, int rows, int columns, int fullImageWidth, int fullImageHeight) {
        this.filename = filename;
        this.path = path;
        this.rows = rows;
        this.columns = columns;
        fullImageDimension = new Dimension(fullImageWidth, fullImageHeight);
        imageDimension = new Dimension(fullImageWidth / columns, fullImageHeight / rows);
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public BufferedImage getImage(int index) {
        int column = index / rows;
        int row = index % rows;
        return getImage(column, row);
    }

    @Override
    public BufferedImage getImage(int column, int row) {
        BufferedImage bi;
        if (image == null || image.get() == null) {
            bi = loadGraphics();
            image = new SoftReference<>(bi);
        } else {
            bi = image.get();
        }
        try {
            BufferedImage result = bi.getSubimage(column * imageDimension.width, row * imageDimension.height,
                    imageDimension.width, imageDimension.height);
            return result;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, " Error getting subimage from {0}", filename);
        }
        return null;
    }

    @Override
    public BufferedImage getImage(Point coordinates) {
        return getImage(coordinates.x, coordinates.y);
    }

//    @Override
//    public BufferedImage getImage() {
//        return getImage(0, 0);
//
//    }

    @Override
    public BufferedImage getFullImage() {
        BufferedImage bi;
        if (image == null || image.get() == null) {
            bi = loadGraphics();
            image = new SoftReference<>(bi);
        } else {
            bi = image.get();
        }
        return bi;
    }

    @Override
    public Dimension getImageDimension() {
        return imageDimension;
    }

    @Override
    public Dimension getFullImageDimension() {
        return fullImageDimension;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public int getRows() {
        return rows;
    }

    private BufferedImage loadGraphics() {
        File file = FileSystems.getDefault().getPath(path, filename).toFile();
//        if (!file.exists()) {
//            String alternateFilename = filename.substring(0, filename.lastIndexOf('.')) + ".bmp";
//            file = FileSystems.getDefault().getPath(path, alternateFilename).toFile();
//        }
        if (!file.exists()) {
            LOG.log(Level.SEVERE, " Image file not found {0}", filename);
            return new BufferedImage(fullImageDimension.width, fullImageDimension.height, BufferedImage.TYPE_INT_ARGB);
        }
        BufferedImage bi = FileIO.loadImage(file);
        return bi;
    }
//    public void saveGraphics(RenderedImage image, String path, FileIO fileSystem) {
//        File file = fileSystem.getFile(path, filename);
//        FileIO.saveImage(image, file);
//    }
}
