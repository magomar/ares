package ares.application.gui.profiles;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface GraphicsProfile {

    /**
     * Gets the actual filename based on the base filename passed as parameter
     *
     * @param baseFilename
     * @return the actual name of the file containing the graphics of a particular provider
     */
    String getFilename(String baseFilename);

    /**
     * Gets the relative path to the graphics folder
     *
     * @return the relative path to the graphics folder
     */
    String getPath();

    int getOrdinal();
   

}
