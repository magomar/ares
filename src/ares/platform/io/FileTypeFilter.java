package ares.platform.io;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class FileTypeFilter extends FileFilter implements java.io.FileFilter, FilenameFilter {

    private String description;
    private String extensions[];

    public FileTypeFilter(String description, String extension) {
        this(description, new String[]{extension});
    }

    public FileTypeFilter(String description, String extensions[]) {
        if (description == null) {
            this.description = extensions[0];
        } else {
            this.description = description;
        }
        this.extensions = (String[]) extensions.clone();
        toLower(this.extensions);
    }

    public FileTypeFilter(FileType fileType) {
        this(fileType.getDescription(), fileType.getFileExtension());
    }

    private void toLower(String array[]) {
        for (int i = 0, n = array.length; i < n; i++) {
            array[i] = array[i].toLowerCase();
        }
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        } else {
            String path = file.getAbsolutePath().toLowerCase();
            for (int i = 0, n = extensions.length; i < n; i++) {
                String extension = extensions[i];
                if (path.endsWith(extension)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean accept(File dir, String name) {
        Path path = FileSystems.getDefault().getPath(dir.getPath().toString(), name);
        return accept(path.toFile());
    }
}
