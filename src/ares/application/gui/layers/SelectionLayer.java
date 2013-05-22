package ares.application.gui.layers;

import ares.application.gui.GraphicsModel;
import ares.application.gui.AbstractImageLayer;
import ares.application.gui.profiles.AresGraphicsProfile;
import ares.application.gui.providers.AresMiscGraphics;
import ares.application.models.board.*;
import ares.application.models.forces.FormationModel;
import ares.application.models.forces.UnitModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JViewport;

/**
 * Draws the movement arrows on a BufferedImage
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class SelectionLayer extends AbstractImageLayer {

    private final AresMiscGraphics brassCursor = AresMiscGraphics.BRASS_CURSOR;
    private final AresMiscGraphics steelCursor = AresMiscGraphics.STEEL_CURSOR;
    private UnitModel selectedUnit;
    private FormationModel formation;

    public SelectionLayer(JViewport viewport) {
        super(viewport);
    }

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
                paintCursor(g2, u.getLocation(), GraphicsModel.INSTANCE.getActiveProvider(steelCursor).getImage());
            }
        }
        paintCursor(g2, selectedUnit.getLocation(), GraphicsModel.INSTANCE.getActiveProvider(brassCursor).getImage());
        g2.dispose();
    }

    private void paintCursor(Graphics2D g2, TileModel tile, BufferedImage image) {
        Point pos = GraphicsModel.INSTANCE.tileToPixel(tile.getCoordinates());
        g2.drawImage(image, pos.x, pos.y, this);
        repaint(pos.x, pos.y, image.getWidth(), image.getHeight());
    }

    /**
     * Paints a brass cursor around the selected unit, and steel cursors around other units in the same formation
     *
     * @param unit
     * @param formation
     */
    public void paintSelectedUnit(UnitModel unit, FormationModel formation) {
        this.selectedUnit = unit;
        this.formation = formation;
        updateLayer();
    }
}
