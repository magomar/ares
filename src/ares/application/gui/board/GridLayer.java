package ares.application.gui.board;

import ares.application.gui.AbstractImageLayer;
import ares.application.models.board.BoardGraphicsModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;

/**
 * Grid image layer
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class GridLayer extends AbstractImageLayer {

    private SoftReference<BufferedImage> hexImage = new SoftReference<>(null);
    
    @Override
    protected void updateLayer() {
        int w = BoardGraphicsModel.getTileColumns();
        int y = BoardGraphicsModel.getTileRows();
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < y; ++j) {
                paintTile(i, j);
            }
        }
    }

    private void paintTile(int x, int y) {
        Graphics2D g2 = globalImage.createGraphics();
        if (hexImage.get() == null) {
            hexImage = new SoftReference<>(loadImage(BoardGraphicsModel.getImageProfile().getGridHexFilename()));
        }
        g2.drawImage(hexImage.get(), x, y, null);
        repaint(x, y, hexImage.get().getWidth(), hexImage.get().getHeight());
        g2.dispose();
    }
}
