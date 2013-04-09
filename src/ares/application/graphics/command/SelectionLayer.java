package ares.application.graphics.command;

import ares.application.graphics.AresGraphicsModel;
import ares.application.graphics.AbstractImageLayer;
import ares.application.graphics.AresGraphicsProfile;
import ares.application.graphics.AresMiscGraphics;
import ares.application.models.board.*;
import ares.application.models.forces.FormationModel;
import ares.application.models.forces.UnitModel;
import ares.io.AresIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Draws the movement arrows on a BufferedImage
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class SelectionLayer extends AbstractImageLayer {

    private final AresMiscGraphics brassCursor = AresMiscGraphics.BRASS_CURSOR;
    private final AresMiscGraphics steelCursor = AresMiscGraphics.STEEL_CURSOR;
    private UnitModel unit;
    private FormationModel formation;

    @Override
    protected void updateLayer() {
        initialize();
        if (unit == null) {
            return;
        }
        Graphics2D g2 = globalImage.createGraphics();
        AresGraphicsProfile profile = AresGraphicsModel.getProfile();
        for (UnitModel u : formation.getUnitModels()) {
            if (!u.equals(unit)) {
                TileModel t = u.getLocation();
                paintCursor(g2, u.getLocation(), steelCursor.getImage(profile, AresIO.ARES_IO));
            }
        }
        paintCursor(g2, unit.getLocation(), brassCursor.getImage(profile, AresIO.ARES_IO));
        g2.dispose();
    }

    private void paintCursor(Graphics2D g2, TileModel tile, BufferedImage image) {
        Point pos = AresGraphicsModel.tileToPixel(tile.getCoordinates());
        g2.drawImage(image, pos.x, pos.y, this);
        repaint(pos.x, pos.y, image.getWidth(), image.getHeight());
//        paintImmediately(pos.x, pos.y, brassCursorImage.get().getWidth(), brassCursorImage.get().getHeight());
    }

    /**
     * Paints a brass cursor around the selected unit, and steel cursors around other units in the same formation
     *
     * @param unit
     * @param formation
     */
    public void paintSelectedUnit(UnitModel unit, FormationModel formation) {
        this.unit = unit;
        this.formation = formation;
        updateLayer();
    }
}
