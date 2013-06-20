package ares.application.shared.gui.views.layerviews;

import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.views.layerviews.AbstractLayeredImageView;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public abstract class AbstractBoardView extends AbstractLayeredImageView implements BoardViewer {

    @Override
    public void centerViewOn(Point location) {
        JScrollBar verticalScrollBar = contentPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = contentPane.getHorizontalScrollBar();
        Point pos = GraphicsModel.INSTANCE.tileToPixel(location, profile);
        Dimension viewportSize = contentPane.getViewport().getSize();
        Dimension boardSize = layeredPane.getSize();
        int x = pos.x - Math.min(viewportSize.width / 2, boardSize.width - pos.x);
        int y = pos.y - Math.min(viewportSize.height / 2, boardSize.height - pos.y);
        horizontalScrollBar.setValue(x);
        verticalScrollBar.setValue(y);
    }
}
