package ares.application.gui_components;

import ares.application.models.ScenarioModel;
import ares.application.models.board.TileModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;
import javax.swing.JScrollPane;

/**
 * Grid image layer
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class GridLayer extends AbstractImageLayer {
    
    private SoftReference<BufferedImage> hexImage =  new SoftReference<>(null);

    public GridLayer(JScrollPane contentPane){
        super(contentPane);
    }
    
    @Override
    protected void createGlobalImage(ScenarioModel s) {
        TileModel[][] tMap = s.getBoardModel().getMapModel();
        int w = tMap.length, y = tMap[0].length;
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < y; ++j) {
                paintTile(tMap[i][j]);
            }
        }

    }

    @Override
    public void paintTile(TileModel t) {
        Graphics2D g2 = globalImage.createGraphics();
        if (hexImage.get() == null) {
            hexImage = new SoftReference<>(loadImage(bgm.getGridHex()));
        }
        Point pos = bgm.tileToPixel(t.getCoordinates());
        g2.drawImage(hexImage.get(), pos.x, pos.y, null);
        repaint(pos.x, pos.y, hexImage.get().getWidth(), hexImage.get().getHeight());
    }
}
