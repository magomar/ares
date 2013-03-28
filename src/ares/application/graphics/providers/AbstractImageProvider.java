package ares.application.graphics.providers;

import ares.application.graphics.GraphicsProfile;
import ares.io.FileIO;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.ref.SoftReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public abstract class AbstractImageProvider implements ImageProvider {

    private static final Logger LOG = Logger.getLogger(AbstractImageProvider.class.getName());
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

    public AbstractImageProvider(String filename, int imageWidth, int imageHeight, GraphicsProfile profile) {
        this(filename, 1, 1, imageWidth, imageHeight, profile);
    }

    public AbstractImageProvider(String filename, int rows, int columns, int imageWidth, int imageHeight, GraphicsProfile profile) {
        this.filename = profile.getFilename(filename);
        this.rows = rows;
        this.columns = columns;
        fullImageDimension = new Dimension(imageWidth * columns, imageHeight * rows);
        imageDimension = new Dimension(imageWidth, imageHeight);
        path = profile.getPath();
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public BufferedImage getImage(int row, int column, FileIO fileSystem) {
        BufferedImage bi;
        if (image == null || image.get() == null) {
            bi = loadGraphics(path, fileSystem);
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
    public BufferedImage getImage(int index, FileIO fileSystem) {
        int column = index / rows;
        int row = index % rows;
        return getImage(row, column, fileSystem);

    }

    @Override
    public BufferedImage getFullImage(FileIO fileSystem) {
        BufferedImage bi;
        if (image == null || image.get() == null) {
            bi = loadGraphics(path, fileSystem);
            image = new SoftReference<>(bi);
        } else {
            bi = image.get();
        }
        return bi;
    }

    @Override
    public BufferedImage getImage(FileIO fileSystem) {
        return getImage(0, 0, fileSystem);

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

    private BufferedImage loadGraphics(String path, FileIO fileSystem) {
        File file = fileSystem.getFile(path, filename);
        if (!file.exists()) {
            String alternateFilename = filename.substring(0, filename.lastIndexOf('.')) + ".bmp";
            file = fileSystem.getFile(path, alternateFilename);
        }
        if (!file.exists()) {
            LOG.log(Level.SEVERE, " Image file not found {0}", filename);
            return null;
        }
        BufferedImage bi = FileIO.loadImage(file);
        return bi;
    }
//    public void saveGraphics(RenderedImage image, String path, FileIO fileSystem) {
//        File file = fileSystem.getFile(path, filename);
//        FileIO.saveImage(image, file);
//    }
}
