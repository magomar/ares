package ares.io;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum AresPaths {
    DATA("Data"),
    EQUIPMENT("Data/Equipment"),
    SCENARIOS("Data/Scenarios"),
    GRAPHICS("Graphics"),
    GRAPHICS_SMALL("Graphics/Medium"),
    GRAPHICS_MEDIUM("Graphics/Medium"),
    GRAPHICS_HIGH("Graphics/Large");
    private final String path;

    private AresPaths(final String path) {
//        this.path = System.getProperty("user.dir") + path;
        this.path = path;
    }

    public String getPath() {
        return path;
    }
    
}
