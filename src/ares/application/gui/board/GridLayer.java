package ares.application.gui.board;

import ares.application.gui.AbstractImageLayer;
import ares.application.gui.providers.AresMiscGraphics;
import ares.application.gui.GraphicsModel;
import ares.application.io.AresIO;
import ares.application.models.ScenarioModel;
import ares.application.models.board.TileModel;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Grid image layer
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class GridLayer extends AbstractImageLayer {

    private ScenarioModel scenario;

    @Override
    public void updateLayer() {
        initialize();
        if (scenario == null) {
            return;
        }
        int w = GraphicsModel.INSTANCE.getTileColumns();
        int y = GraphicsModel.INSTANCE.getTileRows();
        Graphics2D g2 = globalImage.createGraphics();
        BufferedImage bi = GraphicsModel.INSTANCE.getActiveProvider(AresMiscGraphics.GRID).getImage(AresIO.ARES_IO);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < y; j++) {
                TileModel tile = scenario.getBoardModel().getMapModel()[i][j];
                if (tile.isPlayable()) {
                    Point pos = GraphicsModel.INSTANCE.tileToPixel(i, j);
                    g2.drawImage(bi, pos.x, pos.y, this);
                    repaint(pos.x, pos.y, bi.getWidth(), bi.getHeight());
                }
            }
        }
        g2.dispose();
    }

    public void paintGrid(ScenarioModel scenario) {
        this.scenario = scenario;
        updateLayer();
    }
}
