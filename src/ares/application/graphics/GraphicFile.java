package ares.application.graphics;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public enum GraphicFile {

    BORDER("borders.png"),
    GRID("hexoutline.png"),
    BRASS_CURSOR("brass_cursor.png"),
    STEEL_CURSOR("steel_cursor.png");
    private final String filename;

    private GraphicFile(final String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
