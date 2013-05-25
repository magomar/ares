package ares.application.gui.layers;

import ares.application.gui.profiles.GraphicsModel;
import ares.application.gui.profiles.GraphicsProfile;
import ares.application.gui.profiles.UnitDecorator;
import ares.application.models.ScenarioModel;
import ares.application.models.board.*;
import ares.application.models.forces.*;
import config.ProfiledGraphicProperty;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.JViewport;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class UnitsLayer extends AbstractImageLayer {

    private ScenarioModel scenario;

    public UnitsLayer(JViewport viewport) {
        super(viewport);
    }

    @Override
    public void updateLayer() {
        initialize();
        Graphics2D g2 = globalImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Collection<TileModel> tileModels = new HashSet<>();
        for (ForceModel forceModel : scenario.getForceModel()) {
            for (UnitModel unitModel : forceModel.getUnitModels()) {
                TileModel tileModel = unitModel.getLocation();
                if (tileModels.add(tileModel)) {
                    paintUnitStack(g2, tileModel);
                }
            }
        }
        g2.dispose();
    }

    /**
     * Paints all the units visible in the {@code scenario}
     *
     * @param scenario
     */
    public void paintAllUnits(ScenarioModel scenario) {
        this.scenario = scenario;
        updateLayer();
    }

    /**
     * Paints all the units visible in a single {@code tile}. Method used to update just one stack (typically the one
     * selected by the user)
     *
     * @param scenario
     */
    public void paintUnitStack(TileModel tile) {
        Graphics2D g2 = globalImage.createGraphics();
        paintUnitStack(g2, tile);
        g2.dispose();
    }

    /**
     * Paints all the units in a single tile. Method used to paint all the units in an scenario
     *
     * @param tile TileModel where the units are
     * @param maxStack maximum units in the stack to be painted
     */
    private void paintUnitStack(Graphics2D g2, TileModel tile) {

        if (!tile.isEmpty()) {
            //Calculate unit location in board
            Point pos = GraphicsModel.INSTANCE.tileToPixel(tile.getCoordinates());
            //Retrieve the single unit image
            UnitModel unit = tile.getTopUnit();
            BufferedImage unitImage = GraphicsModel.INSTANCE.getActiveProvider(unit.getColor()).getImage(unit.getIconId());
            GraphicsProfile graphicsProfile = GraphicsModel.INSTANCE.getActiveProfile();

            // Offset from the upper left corner of the tile
            int imageOffset = GraphicsModel.INSTANCE.getProperty(ProfiledGraphicProperty.UNIT_OFFSET);
            pos.x += imageOffset;
            pos.y += imageOffset;
            // Offset from the upper left corner of the last painted unit
            int stackOffset = GraphicsModel.INSTANCE.getProperty(ProfiledGraphicProperty.UNIT_STACK_OFFSET);
            //Num units to be painted
            int unitsToPaint = Math.min(tile.getNumStackedUnits(), GraphicsModel.INSTANCE.getProperty(ProfiledGraphicProperty.UNIT_MAX_STACK));
            // Max stack  offset
            int maxStackOffset = unitsToPaint * stackOffset - stackOffset;

            //Paint the same top unit
            for (int d = maxStackOffset; d >= 0; d -= stackOffset) {
                g2.drawImage(unitImage, pos.x + d, pos.y + d, this);
            }
            BufferedImage subImage = globalImage.getSubimage(pos.x, pos.y, unitImage.getWidth(), unitImage.getHeight());

            //Paint additional unit info (Health, Stamina, Attack, Defense, etc.)

            Graphics2D unitG2 = subImage.createGraphics();
            unitG2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            UnitDecorator unitsProfile = GraphicsModel.INSTANCE.getUnitDecorator();
            unitsProfile.paintUnitAttributes(unitG2, unit);
//            unitsProfile.paintUnitAttributes(g2, unit);
            unitG2.dispose();
            repaint(pos.x, pos.y, unitImage.getWidth() + maxStackOffset, unitImage.getHeight() + maxStackOffset);
        }

    }
}
