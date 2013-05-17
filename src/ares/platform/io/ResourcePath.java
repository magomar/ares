package ares.platform.io;

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
    private final String path;

    private ResourcePath(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}