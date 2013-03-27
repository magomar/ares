package ares.application.graphics.command;

import ares.application.graphics.BoardGraphicsModel;
import ares.application.graphics.AbstractImageLayer;
import ares.application.models.board.*;
import ares.application.models.forces.FormationModel;
import ares.application.models.forces.UnitModel;
import ares.application.graphics.ImageTools;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;

/**
 * Draws the movement arrows on a BufferedImage
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class SelectionLayer extends AbstractImageLayer {

    private SoftReference<BufferedImage> brassCursorImage = new SoftReference<>(null);
    private SoftReference<BufferedImage> steelCursorImage = new SoftReference<>(null);
    private UnitModel unit;
    private FormationModel formation;

    @Override
    protected void updateLayer() {
        initialize();
        if (unit == null) {
            return;
        }
        Graphics2D g2 = globalImage.createGraphics();
        if (brassCursorImage.get() == null) {
            brassCursorImage = new SoftReference<>(ImageTools.loadImage(BoardGraphicsModel.getImageProfile().getBrassCursorFile()));
        }
        if (steelCursorImage.get() == null) {
            steelCursorImage = new SoftReference<>(ImageTools.loadImage(BoardGraphicsModel.getImageProfile().getSteelCursorFile()));
        }
        for (UnitModel u : formation.getUnitModels()) {
            if (!u.equals(unit)) {
                TileModel t = u.getLocation();
                paintCursor(g2, u.getLocation(), steelCursorImage.get());
            }
        }
        paintCursor(g2, unit.getLocation(), brassCursorImage.get());
        g2.dispose();
    }

    private void paintCursor(Graphics2D g2, TileModel tile, BufferedImage image) {
        Point pos = BoardGraphicsModel.tileToPixel(tile.getCoordinates());
        g2.drawImage(image, pos.x, pos.y, null);
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
