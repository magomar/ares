package ares.platform.io;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum ResourcePath {

    TEMP("Temp"),
    DATA("Data"),
    EQUIPMENT("Data/Equipment"),
    SCENARIOS("Data/Scenarios"),
    GRAPHICS("Graphics"),
    ICONS_SMALL("Graphics/Icons/Small"),
    ICONS_MEDIUM("Graphics/Icons/Medium"),
    ICONS_LARGE("Graphics/Icons/Large"),
    GRAPHICS_BACKGROUND("Graphics/Background"),
    GRAPHICS_SMALL("Graphics/Small"),
    GRAPHICS_MEDIUM("Graphics/Medium"),
    GRAPHICS_HIGH("Graphics/High"),
    OTHER("Graphics/Other");
    private final Path folderPath;

    private ResourcePath(final String relativePath) {
//        this.path = ResourcePath.class.getResource(relativePath);
        this.folderPath = FileSystems.getDefault().getPath(System.getProperty("user.dir"), relativePath);
    }

    public Path getFolderPath() {
        return folderPath;
    }

    public Path getFilePath(String filename) {
        return FileSystems.getDefault().getPath(folderPath.toString(), filename);
    }

    public File getFile(String filename) {
        return getFilePath(filename).toFile();
    }

    public String getFilename(String filename) {
        return getFilePath(filename).toString();
    }
}
