package ares.io;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum ToawFileType implements FileType {

    GAME(".gam", "TOAW Scenario File (XML)", AresFileType.SCENARIO),
    EQUIPMENT(".eqp", "TOAW Equipment File (XML)", AresFileType.EQUIPMENT),
    MAP(".mml", "TOAW Map File (XML)", AresFileType.MAP);
    private final String fileExtension;
    private String description;
    private final FileTypeFilter fileTypeFilter;
    private final AresFileType aresFileType;
    public static final String JAXB_CONTEXT_PATH = "toaw.data.jaxb";
    public static final String JAXB_NAMESPACE = "toaw";

    private ToawFileType(final String fileExtension, final String description, final AresFileType aresFileType) {
        this.fileExtension = fileExtension;
        this.description = description;
        this.aresFileType = aresFileType;
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

    public AresFileType getAresFileType() {
        return aresFileType;
    }

    public static ToawFileType fromFileExtension(String extension) {
        if (extension != null) {
            for (ToawFileType fileType : ToawFileType.values()) {
                if (extension.equalsIgnoreCase(fileType.fileExtension)) {
                    return fileType;
                }
            }
        }
        return null;

    }
}
