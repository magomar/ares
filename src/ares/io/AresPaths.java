package ares.io;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum AresPaths {

    TEMP("Temp"),
    DATA("Data"),
    EQUIPMENT("Data/Equipment"),
    SCENARIOS("Data/Scenarios"),
    GRAPHICS("Graphics"),
    GRAPHICS_BACKGROUND("Graphics/Background"),
    GRAPHICS_SMALL("Graphics/Medium"),
    GRAPHICS_MEDIUM("Graphics/Medium"),
    GRAPHICS_HIGH("Graphics/High");
    private final String path;

    private AresPaths(final String path) {
//        this.path = System.getProperty("user.dir") + path;
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
