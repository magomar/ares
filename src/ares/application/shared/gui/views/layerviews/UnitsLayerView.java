package ares.application.shared.gui.views.layerviews;

import ares.application.shared.boundaries.viewers.layerviewers.UnitsLayerViewer;
import ares.application.shared.gui.decorators.ImageDecorators;
import ares.application.shared.gui.profiles.GraphicProperties;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.profiles.ProfiledGraphicProperty;
import ares.application.shared.models.ScenarioModel;
import ares.application.shared.models.board.TileModel;
import ares.application.shared.models.forces.ForceModel;
import ares.application.shared.models.forces.UnitModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class UnitsLayerView extends AbstractImageLayerView implements UnitsLayerViewer {

    private ScenarioModel scenario;

    @Override
    public String name() {
        return UnitsLayerViewer.NAME;
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
     * Paints all the units visible in the {@code scenario} passed as a parameter
     *
     * @param scenario
     */
    @Override
    public void updateScenario(ScenarioModel scenario) {
        this.scenario = scenario;
        updateLayer();
    }

    /**
     * Paints all the units visible in a single {@code tile}. Method used to update just one stack (typically the one
     * selected by the user)
     *
     * @param scenario
     */
    @Override
    public void updateUnitStack(TileModel tile) {
        Graphics2D g2 = globalImage.createGraphics();
        paintUnitStack(g2, tile);
        g2.dispose();
    }

    /**
     * Paints all the units in a single tile. Method used to paint all the units in an scenario
     *
     * @param tile     TileModel where the units are
     * @param maxStack maximum units in the stack to be painted
     */
    private void paintUnitStack(Graphics2D g2, TileModel tile) {

        if (!tile.isEmpty()) {
            //Calculate unit location in board
            Point pos = GraphicsModel.INSTANCE.tileToPixel(tile.getCoordinates(), profile);
            //Retrieve the single unit image
            UnitModel unit = tile.getTopUnit();
            BufferedImage unitImage = GraphicsModel.INSTANCE.getProfiledImageProvider(unit.getColor(), profile).getImage(unit.getIconId());

            // Offset from the upper left corner of the tile
            int imageOffset = GraphicProperties.getProperty(ProfiledGraphicProperty.UNIT_OFFSET, profile);
            pos.x += imageOffset;
            pos.y += imageOffset;
            // Offset from the upper left corner of the last painted unit
            int stackOffset = GraphicProperties.getProperty(ProfiledGraphicProperty.UNIT_STACK_OFFSET, profile);
            //Num units to be painted
            int unitsToPaint = Math.min(tile.getNumStackedUnits(), GraphicProperties.getProperty(ProfiledGraphicProperty.UNIT_MAX_STACK, profile));
            // Max stack  offset
            int maxStackOffset = unitsToPaint * stackOffset - stackOffset;

            //Paint the same top unit
            for (int d = maxStackOffset; d >= 0; d -= stackOffset) {
                g2.drawImage(unitImage, pos.x + d, pos.y + d, contentPane);
            }
            BufferedImage subImage = globalImage.getSubimage(pos.x, pos.y, unitImage.getWidth(), unitImage.getHeight());

            //Paint additional unit info (Health, Stamina, Attack, Defense, etc.)

            Graphics2D unitG2 = subImage.createGraphics();
            unitG2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            ImageDecorators unitsProfile = GraphicsModel.INSTANCE.getImageDecorators(profile);
            unitsProfile.paintUnitAttributes(unitG2, unit);
//            unitsProfile.paintUnitAttributes(g2, unit);
            unitG2.dispose();
            contentPane.repaint(pos.x, pos.y, unitImage.getWidth() + maxStackOffset, unitImage.getHeight() + maxStackOffset);
        }

    }
}
