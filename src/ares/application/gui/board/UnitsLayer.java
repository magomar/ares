package ares.application.gui.board;

import ares.application.graphics.BoardGraphicsModel;
import ares.application.gui.AbstractImageLayer;
import ares.application.gui.UnitIcons;
import ares.application.models.ScenarioModel;
import ares.application.models.board.*;
import ares.application.models.forces.*;
import ares.engine.knowledge.KnowledgeCategory;
import ares.io.AresIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;
import java.util.*;

/**
 * Units image layer based on Sergio Musoles TerrainPanel
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class UnitsLayer extends AbstractImageLayer {

    private ScenarioModel scenario;
    /**
     * Map to save loaded images
     */
    private EnumMap<UnitIcons, SoftReference<BufferedImage>> unitBufferMap = new EnumMap<>(UnitIcons.class);
    /**
     * Offset distance from the upper left corner of the tile. The image will be painted at Point(X+offset, Y+offset)
     *
     * @see paintTile(TileModel, int)
     */
    private final static int UNIT_IMAGE_OFFSET = 7;
    /**
     * Maximum numbers of units to be painted in a single tile
     *
     * @see paintTile(TileModel, int)
     */
    private final static int MAX_STACK = 6;
    /**
     * The unit image can be composed of different layered images. Each layer is shifted
     * <code>unitStackOffset</code> pixels. For example, say the first layer was painted at Point(X,Y), then the next
     * layer will start at Point(X+offset,Y+offset), and the third one at Point(X+2*offset, Y+2*offset) and so on.
     *
     * @see paintTile(TileModel, int)
     */
    private static int UNIT_STACK_OFFSET = 1;

    @Override
    protected void updateLayer() {
        initialize();
        Collection<TileModel> tileModels = new HashSet<>();
        for (ForceModel forceModel : scenario.getForceModel()) {
            for (UnitModel unitModel : forceModel.getUnitModels()) {
                TileModel tileModel = unitModel.getLocation();
                if (tileModels.add(tileModel)) {
                    paintUnitStack(tileModel);
                }
            }
        }
    }

    /**
     * Paints all the units visible in the  <code> scenario</code>
     *
     * @param scenario
     */
    public void paintUnits(ScenarioModel scenario) {
        this.scenario = scenario;
        updateLayer();
    }

    /**
     * Paints the units in a single tile
     *
     * @param tile TileModel where the units are
     * @param maxStack maximum units in the stack to be painted
     */
    public void paintUnitStack(TileModel tile) {
        //Graphics from the global image
        Graphics2D g2 = globalImage.createGraphics();

        //Where the single unit image will be painted
        BufferedImage unitImage;

        //Calculate unit position
        Point pos = BoardGraphicsModel.tileToPixel(tile.getCoordinates());

        //If no units on the tile
        if (tile.isEmpty()) {
            //Empty image
            unitImage = new BufferedImage(BoardGraphicsModel.getHexDiameter(), BoardGraphicsModel.getHexHeight(), BufferedImage.TYPE_INT_ARGB);
            g2.drawImage(unitImage, pos.x, pos.y, null);
            repaint(pos.x, pos.y, unitImage.getWidth(null), unitImage.getHeight(null));
        } else {
            //Retrieve the single unit image
            unitImage = getUnitImage(tile.getTopUnit());

            //Num units to be painted
            int max = (tile.getNumStackedUnits() > MAX_STACK) ? MAX_STACK : tile.getNumStackedUnits();
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
                g2.drawImage(unitImage, pos.x + d, pos.y + d, null);
                d += UNIT_STACK_OFFSET;
            }

            //Adds attributes to the image such as Health, Attack, Defense, etc.
            addUnitAttributes(unitImage, tile.getTopUnit());
            g2.drawImage(unitImage, pos.x + d, pos.y + d, null);
            repaint(pos.x, pos.y, unitImage.getWidth(null) + d, unitImage.getHeight(null) + d);
        }
        g2.dispose();
    }

    /**
     * Returns a unit image based on the unit color, IconId and KnowledgeCategory
     *
     * @param unit
     * @return
     * @see KnowledgeCategory
     */
    private BufferedImage getUnitImage(UnitModel unit) {
        
        // Color template
        UnitIcons uc = unit.getColor();
        //Make sure graphics are loaded
        loadUnitGraphics(uc);

        //TODO paint tile density
        //Retrieve color template image and crop the unit we need from it

        //Get the coordinates
        int size = BoardGraphicsModel.getImageProfile().getUnitSquareSide();
        int row = unit.getIconId() / BoardGraphicsModel.getImageProfile().getUnitsImageCols();
        int col = unit.getIconId() % BoardGraphicsModel.getImageProfile().getUnitsImageCols();

        return unitBufferMap.get(uc).get().getSubimage(col * size, row * size, size, size);
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

    /**
     * Loads the unit color template and saves it in the buffer map
     *
     * @param uc represents the UnitColor to be loaded
     * @see UnitIcons
     */
    private void loadUnitGraphics(UnitIcons uc) {
        SoftReference<BufferedImage> softImage = unitBufferMap.get(uc);
        //If image doesn't exist or has been GC'ed
        if (softImage == null || softImage.get() == null) {
            String filename = BoardGraphicsModel.getImageProfile().getUnitIconsFilename(uc);
            BufferedImage i = loadImage(AresIO.ARES_IO.getFile(BoardGraphicsModel.getImageProfile().getPath(), filename));
            unitBufferMap.put(uc, new SoftReference<>(i));
        }
    }
}
