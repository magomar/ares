package ares.application.gui.board;

import ares.application.gui.AbstractImageLayer;
import ares.application.gui.AresMiscGraphics;
import ares.application.gui.AresGraphicsModel;
import ares.io.AresIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Grid image layer
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class GridLayer extends AbstractImageLayer {

    private final AresMiscGraphics grid = AresMiscGraphics.GRID;

    @Override
    protected void updateLayer() {
        int w = AresGraphicsModel.getTileColumns();
        int y = AresGraphicsModel.getTileRows();
        Graphics2D g2 = globalImage.createGraphics();
        BufferedImage bi = grid.getImage(AresGraphicsModel.getProfile(), AresIO.ARES_IO);
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < y; ++j) {
                Point pos = AresGraphicsModel.tileToPixel(i,j);
                g2.drawImage(bi, pos.x, pos.y, this);
                repaint(pos.x, pos.y, bi.getWidth(), bi.getHeight());
                g2.dispose();
            }
        }
    }
}
