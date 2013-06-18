package ares.platform.io;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum AresFileType implements FileType {

    SCENARIO(".scenario", "Ares Scenario File "),
    EQUIPMENT(".equipment", "Ares Equipment File "),
    MAP(".map", "Ares Map File");
    private final String fileExtension;
    private final String description;
    private final FileTypeFilter fileTypeFilter;
//    public static final String JAXB_CONTEXT_PATH = "ares.data.jaxb";
//    public static final String JAXB_NAMESPACE = "ares";

    private AresFileType(final String fileExtension, final String description) {
        this.fileExtension = fileExtension;
        this.description = description;
        fileTypeFilter = new FileTypeFilter(this);
    }

    @Override
    public String getFileExtension() {
        return fileExtension;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public FileTypeFilter getFileTypeFilter() {
        return fileTypeFilter;
    }

    public static AresFileType fromFileExtension(String extension) {
        if (extension != null) {
            for (AresFileType fileType : AresFileType.values()) {
                if (extension.equalsIgnoreCase(fileType.fileExtension)) {
                    return fileType;
                }
            }
        }
        return null;

    }
}
