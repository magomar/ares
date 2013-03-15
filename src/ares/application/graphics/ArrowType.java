package ares.application.graphics;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public enum ArrowType {

    GIVING_ORDERS("Movement_arrows_Red.png"),
    CURRENT_ORDERS("Movement_arrows_Gray.png");
    private final String filename;

    private ArrowType(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
