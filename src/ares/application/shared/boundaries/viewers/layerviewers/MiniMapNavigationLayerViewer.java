package ares.application.shared.boundaries.viewers.layerviewers;

import javax.swing.JViewport;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface MiniMapNavigationLayerViewer extends ImageLayerViewer {

    public static final String NAME = "MINI_MAP_NAVIGATION_LAYER";

    public void update(JViewport boardViewport);
}
