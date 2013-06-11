package ares.application.shared.gui.views.layerviews;

import ares.application.shared.boundaries.viewers.layerviewers.MiniMapNavigationLayerViewer;
import ares.application.shared.gui.profiles.GraphicsModel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JViewport;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class MiniMapNavigationLayerView extends AbstractImageLayerView implements MiniMapNavigationLayerViewer {

    private JViewport boardViewport;

    @Override
    public void update(JViewport boardViewport) {
        this.boardViewport = boardViewport;
        updateLayer();
    }

    @Override
    public void updateLayer() {
        initialize();
        if (boardViewport == null) {
            return;
        }
        Graphics2D g2 = globalImage.createGraphics();
        Rectangle rect = GraphicsModel.INSTANCE.getViewportBounds(boardViewport, profile);
        g2.setColor(Color.CYAN);
        g2.drawRect(rect.x, rect.y, rect.width, rect.height);
        g2.dispose();
    }

    @Override
    public String name() {
        return MiniMapNavigationLayerViewer.NAME;
    }
}
