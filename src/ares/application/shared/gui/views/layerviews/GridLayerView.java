package ares.application.shared.gui.views.layerviews;

import ares.application.shared.boundaries.viewers.layerviewers.GridLayerViewer;
import ares.application.shared.gui.decorators.ImageDecorators;
import ares.application.shared.gui.providers.AresMiscTerrainGraphics;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.models.ScenarioModel;
import ares.application.shared.models.board.TileModel;
import ares.data.jaxb.Place;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Grid image layer
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class GridLayerView extends AbstractImageLayerView implements GridLayerViewer {

    private ScenarioModel scenario;

    @Override
    public String name() {
        return GridLayerViewer.NAME;
    }

    @Override
    public void updateLayer() {
        initialize();
        if (scenario == null) {
            return;
        }
        int width = GraphicsModel.INSTANCE.getBoardColumns();
        int height = GraphicsModel.INSTANCE.getBoardRows();
        Graphics2D g2 = globalImage.createGraphics();
        BufferedImage gridImage = GraphicsModel.INSTANCE.getProfiledImageProvider(AresMiscTerrainGraphics.GRID, profile).getImage(0, 0);
        TileModel[][] tiles = scenario.getBoardModel().getMapModel();
        for (int i = 0; i < width; i++) {
            TileModel[] tileColumn = tiles[i];
            for (int j = 0; j < height; j++) {
                TileModel tile = tileColumn[j];
                if (tile.isPlayable()) {
                    Point pos = GraphicsModel.INSTANCE.tileToPixel(i, j, profile);
                    g2.drawImage(gridImage, pos.x, pos.y, contentPane);
                    contentPane.repaint(pos.x, pos.y, gridImage.getWidth(), gridImage.getHeight());
                }
            }
        }
        ImageDecorators imageDec = GraphicsModel.INSTANCE.getImageDecorators(profile);
        Font placeFont = imageDec.getPlaceFont();
        g2.setFont(placeFont);
        g2.setColor(Color.black);
        FontMetrics fm = g2.getFontMetrics(placeFont);
        for (Place place : scenario.getBoardModel().getPlaces()) {
            Point pos = GraphicsModel.INSTANCE.tileToPixel(place.getX(), place.getY(), profile);
            String label = place.getName();
            int labelWidth = fm.stringWidth(label);
            int x = pos.x - (labelWidth - gridImage.getWidth()) / 2;
            if (x + labelWidth > globalImage.getWidth()) {
                x = globalImage.getWidth() - labelWidth;
            }
            int y = pos.y;
            if (y - placeFont.getSize() < 0) {
                y = placeFont.getSize();
            }
            g2.drawString(label, x, y);
        }
        g2.dispose();
    }

    @Override
    public void updateScenario(ScenarioModel scenario) {
        this.scenario = scenario;
        updateLayer();
    }
}
