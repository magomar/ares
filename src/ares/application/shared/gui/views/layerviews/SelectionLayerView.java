package ares.application.shared.gui.views.layerviews;

import ares.application.shared.boundaries.viewers.layerviewers.SelectionLayerViewer;
import ares.application.shared.models.board.TileModel;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.providers.AresMiscTerrainGraphics;
import ares.application.shared.models.forces.FormationModel;
import ares.application.shared.models.forces.UnitModel;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Draws the movement arrows on a BufferedImage
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class SelectionLayerView extends AbstractImageLayerView implements SelectionLayerViewer {

    private UnitModel selectedUnit;
    private FormationModel formation;

    @Override
    public void updateLayer() {
        initialize();
        if (selectedUnit == null) {
            return;
        }
        Graphics2D g2 = globalImage.createGraphics();
        for (UnitModel u : formation.getUnitModels()) {
            if (!u.equals(selectedUnit)) {
                TileModel t = u.getLocation();
                paintCursor(g2, u.getLocation(), GraphicsModel.INSTANCE.getImageProvider(AresMiscTerrainGraphics.STEEL_CURSOR, profile).getImage(0, 0));
            }
        }
        paintCursor(g2, selectedUnit.getLocation(), GraphicsModel.INSTANCE.getImageProvider(AresMiscTerrainGraphics.BRASS_CURSOR, profile).getImage(0, 0));
        g2.dispose();
    }

    private void paintCursor(Graphics2D g2, TileModel tile, BufferedImage image) {
        Point pos = GraphicsModel.INSTANCE.tileToPixel(tile.getCoordinates(), profile);
        g2.drawImage(image, pos.x, pos.y, contentPane);
        contentPane.repaint(pos.x, pos.y, image.getWidth(), image.getHeight());
    }

    /**
     * Paints a brass cursor around the selected unit, and steel cursors around other units in the same formation
     *
     * @param unit
     * @param formation
     */
    @Override
    public void updateSelectedUnit(UnitModel unit, FormationModel formation) {
        this.selectedUnit = unit;
        this.formation = formation;
        updateLayer();
    }

   
}
