package ares.application.shared.boundaries.viewers.layerviewers;

import ares.application.shared.gui.views.View;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface LayeredImageViewer extends View<JScrollPane> {

    LayeredImageViewer addLayerView(ImageLayerViewer imageLayerView);

    ImageLayerViewer getLayerView(String layerViewName);

    void setProfile(int profile);

    int getProfile();

    public void flush();

    void setLayerVisible(String layerName, boolean visible);

    boolean isLayerVisible(String layerName);

    void switchLayerVisible(String layerName);

    void addMouseListener(MouseListener listener);

    void addMouseMotionListener(MouseMotionListener listener);

}
