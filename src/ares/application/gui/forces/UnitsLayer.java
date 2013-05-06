package ares.application.gui.forces;

import ares.application.gui.AresGraphicsModel;
import ares.application.gui.AbstractImageLayer;
import ares.application.models.ScenarioModel;
import ares.application.models.board.*;
import ares.application.models.forces.*;
import ares.engine.knowledge.KnowledgeCategory;
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

    /**
     * Offset distance from the upper left corner of the tile. The image will be painted at Point(X+offset, Y+offset)
     *
     */
    private final static int UNIT_IMAGE_OFFSET = 7;
    /**
     * Maximum numbers of units to be painted in a single tile
     *
     */
    private final static int MAX_STACK = 6;
    /**
     * The unit image can be composed of different layered images. Each layer is shifted {@code unitStackOffset} pixels.
     * For example, say the first layer was painted at Point(X,Y), then the next layer will start at
     * Point(X+offset,Y+offset), and the third one at Point(X+2*offset, Y+2*offset) and so on.
     *
     */
    private static int UNIT_STACK_OFFSET = 1;

//    private TileModel tile;
    @Override
    public void updateLayer() {
        initialize();
        Graphics2D g2 = globalImage.createGraphics();
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

        //Calculate unit position
        Point pos = AresGraphicsModel.tileToPixel(tile.getCoordinates());

        //If no units on the tile
        if (tile.isEmpty()) {
            //Empty image
//            g2.drawImage(AresGraphicsModel.EMPTY_TILE_IMAGE, pos.x, pos.y, this);
//            repaint(pos.x, pos.y, AresGraphicsModel.EMPTY_TILE_IMAGE.getWidth(),
//                    AresGraphicsModel.EMPTY_TILE_IMAGE.getHeight());
        } else {
            //Retrieve the single unit image
            UnitModel unit = tile.getTopUnit();
            BufferedImage unitImage = unit.getColor().getImage(AresGraphicsModel.getProfile(), unit.getIconId(), AresIO.ARES_IO);

            //Num units to be painted
            int max = Math.min(tile.getNumStackedUnits(), MAX_STACK);
            // Attributes are painted only on the last image
            max--;

            // Offset from the upper left corner of the tile
            pos.x += UNIT_IMAGE_OFFSET;
            pos.y += UNIT_IMAGE_OFFSET;

            // Offset from the upper left corner of the last painted unit
            // incremented by unitStackOffset
            int d = 0;

            //Paint the same top unit
            for (int i = 0; i < max; i++) {
                g2.drawImage(unitImage, pos.x + d, pos.y + d, this);
                d += UNIT_STACK_OFFSET;
            }

            //Adds attributes to the image such as Health, Attack, Defense, etc.
            addUnitAttributes(unitImage, tile.getTopUnit());
            g2.drawImage(unitImage, pos.x + d, pos.y + d, this);
            repaint(pos.x, pos.y, unitImage.getWidth() + d, unitImage.getHeight() + d);
        }

    }

    /**
     * Adds unit attributes to the image depending on what much do we know about the unit
     *
     *
     * @param unitImage <code>BufferedImage</code> with the unit color and icon
     * @param unit to get its information
     * @see UnitAttributes
     */
    private void addUnitAttributes(BufferedImage unitImage, UnitModel unit) {
        UnitAttributes ua = new UnitAttributes(unitImage);
        KnowledgeCategory kc = unit.getKnowledgeCategory();
        switch (kc) {
            case COMPLETE:
                ua.paintUnitAttributes((KnownUnitModel) unit);
                break;
            case GOOD:
                ua.paintUnitAttributes((IdentifiedUnitModel) unit);
                break;
            case POOR:
                ua.paintUnitAttributes((DetectedUnitModel) unit);
                break;
            case NONE:
                break;
            default:
                //We shouldn't get here
                throw new AssertionError("Assertion failed: unknown knowledge category " + unit.getKnowledgeCategory().toString());
        }
    }
}
