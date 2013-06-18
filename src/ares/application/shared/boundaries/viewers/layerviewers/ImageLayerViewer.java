package ares.application.shared.boundaries.viewers.layerviewers;

import ares.application.shared.gui.views.View;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JViewport;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface ImageLayerViewer extends View<JComponent> {

    ImageLayerViewer setViewport(JViewport viewport);

    ImageLayerViewer setParenLayer(ImageLayerViewer parentLayer);

    void initialize();

    void updateLayer();

    void flush();
    
    boolean hasParentLayer();

    ImageLayerViewer getParentLayer();

    BufferedImage getGlobalImage();

    void setProfile(int profile);
    
    String name();
}