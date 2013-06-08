package ares.application.shared.gui.views.layerviews;

import ares.application.shared.gui.providers.AresMiscTerrainGraphics;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.models.ScenarioModel;
import ares.application.shared.models.board.TileModel;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Grid image layer
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class GridLayerView extends AbstractImageLayerView {

    private ScenarioModel scenario;

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
        TileModel[][] tiles = scenario.getBoardModel().getMapModel();
        for (int i = 0; i < w; i++) {
            TileModel[] tileColumn = tiles[i];
            for (int j = 0; j < y; j++) {
                TileModel tile = tileColumn[j];
                if (tile.isPlayable()) {
                    Point pos = GraphicsModel.INSTANCE.tileToPixel(i, j, profile);
                    g2.drawImage(bi, pos.x, pos.y, contentPane);
                    contentPane.repaint(pos.x, pos.y, bi.getWidth(), bi.getHeight());
                }
            }
        }
        g2.dispose();
    }

    public void update(ScenarioModel scenario) {
        this.scenario = scenario;
        updateLayer();
    }
}
