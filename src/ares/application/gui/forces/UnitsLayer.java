package ares.application.gui.forces;

import ares.application.gui.GraphicsModel;
import ares.application.gui.AbstractImageLayer;
import ares.application.gui.GraphicsProfile;
import ares.application.models.ScenarioModel;
import ares.application.models.board.*;
import ares.application.models.forces.*;
import ares.application.io.AresIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Units image layer based on Sergio Musoles TerrainPanel
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class UnitsLayer extends AbstractImageLayer {

    private ScenarioModel scenario;

//    private TileModel tile;
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
     * Paints all the units visible in a single {@code tile}
     *
     * @param scenario
     */
    public void paintUnitStack(TileModel tile) {
        Graphics2D g2 = globalImage.createGraphics();
        paintUnitStack(g2, tile);
        g2.dispose();
    }

    /**
     * Paints the units in a single tile
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
            BufferedImage unitImage = GraphicsModel.INSTANCE.getActiveProvider(unit.getColor()).getImage(unit.getIconId(), AresIO.ARES_IO);
            GraphicsProfile graphicsProfile = GraphicsModel.INSTANCE.getActiveProfile();
            //Num units to be painted
            int unitsToPaint = Math.min(tile.getNumStackedUnits(), graphicsProfile.getMaxUnitsStack());
            // Attributes are painted only on the last image
            unitsToPaint--;

            // Offset from the upper left corner of the tile
            int imageOffset = graphicsProfile.getUnitImageOffset();
            pos.x += imageOffset;
            pos.y += imageOffset;

            int stackOffset = graphicsProfile.getUnitStackOffset();
            // Offset from the upper left corner of the last painted unit
            int maxOffset = unitsToPaint * stackOffset;

            //Paint the same top unit
            for (int d = maxOffset; d >= 0; d -= stackOffset) {
                g2.drawImage(unitImage, pos.x + d, pos.y + d, this);
            }
            BufferedImage subImage = globalImage.getSubimage(pos.x, pos.y, graphicsProfile.getUnitWidth(), graphicsProfile.getUnitHeight());

            //Paint additional unit info (Health, Stamina, Attack, Defense, etc.)
            
            Graphics2D unitG2 = subImage.createGraphics();
            unitG2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            UnitsInfographicProfile unitsProfile = graphicsProfile.getUnitsProfile();
            unitsProfile.paintUnitAttributes(unitG2, unit);
//            unitsProfile.paintUnitAttributes(g2, unit);
            unitG2.dispose();
            repaint(pos.x, pos.y, unitImage.getWidth() + maxOffset, unitImage.getHeight() + maxOffset);
        }

    }
}
