package ares.application.shared.gui.views.layerviews;

import ares.application.shared.boundaries.viewers.layerviewers.LayeredImageViewer;
import ares.application.shared.boundaries.viewers.layerviewers.ImageLayerViewer;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.views.AbstractView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public abstract class AbstractLayeredImageView extends AbstractView<JScrollPane> implements LayeredImageViewer {

    protected int profile;
    protected JLayeredPane layeredPane;
    protected Map<String, ImageLayerViewer> layerViews;

    @Override
    public LayeredImageViewer addLayerView(ImageLayerViewer imageLayerView) {
        if (imageLayerView.getParentLayer() == null) {
            layeredPane.add(imageLayerView.getContentPane(), new Integer(layeredPane.getComponentCount()));
        }
        layerViews.put(imageLayerView.name(), imageLayerView);
//        imageLayerView.setProfile(profile);
        return this;
    }

    @Override
    public ImageLayerViewer getLayerView(String layerViewName) {
        return layerViews.get(layerViewName);
    }

    @Override
    protected JScrollPane layout() {
        layerViews = new HashMap<>();
        //Create layered pane to hold all the layers
        layeredPane = new JLayeredPane();
        layeredPane.setOpaque(true);
        layeredPane.setBackground(Color.BLACK);

        // Create and set up scroll pane as the content pane
        JScrollPane scrollPane = new JScrollPane(layeredPane);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setVisible(true);
        scrollPane.setOpaque(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(50);
        return scrollPane;
    }

    @Override
    public void setProfile(int profile) {
        this.profile = profile;
        Dimension imageSize = new Dimension(GraphicsModel.INSTANCE.getBoardWidth(profile), GraphicsModel.INSTANCE.getBoardHeight(profile));
        layeredPane.setPreferredSize(imageSize);
//        layeredPane.setSize(imageSize);
        for (ImageLayerViewer layerView : layerViews.values()) {
            layerView.setProfile(profile);
            layerView.initialize();
        }
    }

    @Override
    public void flush() {
        for (ImageLayerViewer layerView : layerViews.values()) {
            layerView.flush();
        }
    }

    @Override
    public void setLayerVisible(String layerName, boolean visible) {
        layerViews.get(layerName).setVisible(visible);
    }

    @Override
    public boolean isLayerVisible(String layerName) {
        return layerViews.get(layerName).isVisible();
    }

    @Override
    public void switchLayerVisible(String layerName) {
        ImageLayerViewer imageLayer = layerViews.get(layerName);
        imageLayer.setVisible(!imageLayer.isVisible());
    }

    @Override
    public void addMouseListener(MouseListener listener) {
        contentPane.getViewport().getView().addMouseListener(listener);
    }

    @Override
    public void addMouseMotionListener(MouseMotionListener listener) {
        contentPane.getViewport().getView().addMouseMotionListener(listener);
    }
}
