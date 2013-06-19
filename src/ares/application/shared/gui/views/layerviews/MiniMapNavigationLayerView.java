package ares.application.shared.gui.views.layerviews;

import ares.application.shared.boundaries.viewers.layerviewers.MiniMapNavigationLayerViewer;
import ares.application.shared.gui.profiles.GraphicsModel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class MiniMapNavigationLayerView extends AbstractImageLayerView implements MiniMapNavigationLayerViewer {

    private JViewport boardViewport;
    private int boardProfile;

    @Override
    public void update(JViewport boardViewport, int boardProfile) {
        this.boardViewport = boardViewport;
        this.boardProfile = boardProfile;
        updateLayer();
    }

    @Override
    public void updateLayer() {
        initialize();
        if (!isVisible()) return;
        if (boardViewport == null) {
            return;
        }
        Graphics2D g2 = globalImage.createGraphics();
        paintNavigationRectangle(g2);
        g2.dispose();
    }

    private void paintNavigationRectangle(Graphics2D g2) {
        Rectangle visibleTiles = GraphicsModel.INSTANCE.getVisibleTiles(boardViewport, boardProfile);
        Point topLeftCorner = GraphicsModel.INSTANCE.tileToPixel(visibleTiles.x, visibleTiles.y, profile);
        Point bottomRightCorner = GraphicsModel.INSTANCE.tileToPixel(visibleTiles.x + visibleTiles.width, visibleTiles.y + visibleTiles.height, profile);
        int width = bottomRightCorner.x - topLeftCorner.x;
        int height = bottomRightCorner.y - topLeftCorner.y;
        g2.setColor(Color.CYAN);
        g2.drawRect(topLeftCorner.x, topLeftCorner.y, width, height);
        contentPane.repaint(topLeftCorner.x, topLeftCorner.y, width, height);
    }

    @Override
    public String name() {
        return MiniMapNavigationLayerViewer.NAME;
    }
}
