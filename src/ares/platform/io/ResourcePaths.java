package ares.platform.io;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum ResourcePaths {

    TEMP("Temp"),
    DATA("Data"),
    EQUIPMENT("Data/Equipment"),
    SCENARIOS("Data/Scenarios"),
    GRAPHICS("Graphics"),
    ICONS("Graphics/Icons"),
    GRAPHICS_BACKGROUND("Graphics/Background"),
    GRAPHICS_SMALL("Graphics/Small"),
    GRAPHICS_MEDIUM("Graphics/Medium"),
    GRAPHICS_HIGH("Graphics/High");
    private final String path;

    private ResourcePaths(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
