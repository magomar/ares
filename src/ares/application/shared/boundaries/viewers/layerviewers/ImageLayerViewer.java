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

    void setViewport(JViewport viewport);

    void setParenLayer(ImageLayerViewer parentLayer);

    void initialize();

    void updateLayer();

    void flush();

    ImageLayerViewer getParentLayer();

    BufferedImage getGlobalImage();

    void setProfile(int profile);
}
