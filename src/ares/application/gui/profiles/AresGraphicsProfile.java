package ares.application.gui.profiles;

import ares.platform.io.ResourcePath;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public enum AresGraphicsProfile implements GraphicsProfile {

    SMALL(ResourcePath.GRAPHICS_SMALL) {
        @Override
        public String getFilename(String filename) {
            return "s_" + filename;
        }
    },
    MEDIUM(ResourcePath.GRAPHICS_MEDIUM) {
        @Override
        public String getFilename(String filename) {
            return "m_" + filename;
        }
    },
    HIGH(ResourcePath.GRAPHICS_HIGH) {
        @Override
        public String getFilename(String filename) {
            return "h_" + filename;
        }
    };

    private final String path;

    private AresGraphicsProfile(final ResourcePath resourcePath) {
        this.path = resourcePath.getFolderPath().toString();
    }

    /**
     * Gets the relative path to the graphics folder
     *
     * @return the path
     */
    @Override
    public String getPath() {
        return path;
    }


    @Override
    public int getOrdinal() {
        return ordinal();
    }
    
    
}
