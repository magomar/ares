package ares.io;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface FileType  {
    public String getFileExtension();
    public FileTypeFilter getFileTypeFilter();
    public String getDescription();
//    public FileType fromFileExtension(String fileExtension);
}
