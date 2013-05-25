package ares.application.gui.layers;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface ImageLayer {

    void initialize();
    
    void updateLayer();

    void flush();

    ImageLayer getParentLayer();

    BufferedImage getGlobalImage();
    
    void setPreferredSize(Dimension dim);
    
    void setSize(Dimension dim);
    
    void setVisible(boolean visible);
    
    boolean isVisible();
}
