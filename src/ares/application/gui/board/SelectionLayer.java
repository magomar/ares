package ares.application.gui.board;

import ares.application.gui.AbstractImageLayer;
import ares.application.models.ScenarioModel;
import ares.application.models.board.*;
import ares.application.models.forces.FormationModel;
import ares.application.models.forces.UnitModel;
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

    /**
     * Paints a brass cursor around the selected unit, and steel cursors around other units in the same formation
     *
     * @param unit
     * @param formation
     */
    public void paintSelectedUnit(UnitModel unit, FormationModel formation) {
        if (unit == null) {
            return;
        }
        if (brassCursorImage.get() == null) {
            brassCursorImage = new SoftReference<>(loadImage(BoardGraphicsModel.getImageProfile().getBrassCursorFilename()));
        }
        if (steelCursorImage.get() == null) {
            steelCursorImage = new SoftReference<>(loadImage(BoardGraphicsModel.getImageProfile().getSteelCursorFilename()));
        }

        Graphics2D g2 = globalImage.createGraphics();
        for (UnitModel u : formation.getUnitModels()) {
            if (!u.equals(unit)) {
                TileModel t = u.getLocation();
                Point p = BoardGraphicsModel.tileToPixel(t.getCoordinates());
                g2.drawImage(steelCursorImage.get(), p.x, p.y, null);
                repaint(p.x, p.y, BoardGraphicsModel.getHexDiameter(), BoardGraphicsModel.getHexHeight());
            }
        }
        TileModel tile = unit.getLocation();
        Point pos = BoardGraphicsModel.tileToPixel(tile.getCoordinates());
        g2.drawImage(brassCursorImage.get(), pos.x, pos.y, null);
        repaint(pos.x, pos.y, BoardGraphicsModel.getHexDiameter(), BoardGraphicsModel.getHexHeight());
    }

    @Override
    protected void createGlobalImage(ScenarioModel s) {
    }

    @Override
    public void paintTile(TileModel t) {
    }
}
