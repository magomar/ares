package ares.application.shared.gui.layers;

import ares.application.shared.gui.providers.AresMiscTerrainGraphics;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.models.ScenarioModel;
import ares.application.shared.models.board.TileModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JViewport;

/**
 * Grid image layer
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class GridLayer extends AbstractImageLayer {

    private ScenarioModel scenario;

    public GridLayer(JViewport viewport) {
        super(viewport);
    }

    @Override
    public void updateLayer() {
        initialize();
        if (scenario == null) {
            return;
        }
        int w = GraphicsModel.INSTANCE.getBoardColumns();
        int y = GraphicsModel.INSTANCE.getBoardRows();
        Graphics2D g2 = globalImage.createGraphics();
        BufferedImage bi = GraphicsModel.INSTANCE.getImageProvider(AresMiscTerrainGraphics.GRID, profile).getImage(0,0);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < y; j++) {
                TileModel tile = scenario.getBoardModel().getMapModel()[i][j];
                if (tile.isPlayable()) {
                    Point pos = GraphicsModel.INSTANCE.tileToPixel(i, j, profile);
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