package ares.application.gui_components;

import ares.application.models.ScenarioModel;
import ares.application.models.board.*;
import ares.application.models.forces.*;
import ares.io.AresIO;
import ares.scenario.board.KnowledgeLevel;
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

    //Map to save loaded images
    private EnumMap<UnitColors, SoftReference<BufferedImage>> unitBufferMap = new EnumMap<>(UnitColors.class);
    
    /**
     * Offset distance from the upper left corner of the tile.
     * The image will be painted at Point(X+offset, Y+offset)
     *
     * @see paintByTile(TileModel, int)
     */
    private int unitImageOffset = 7;
    /**
     * Maximum numbers of units to be painted in a single tile
     *
     * @see paintByTile(TileModel, int)
     */
    private int defaultMaxStack = 6;
    /**
     * The unit image can be composed of different layered images. Each layer is
     * shifted <code>unitStackOffset</code> pixels.
     * For example, say the first layer was painted at Point(X,Y),
     * then the next layer will start at Point(X+offset,Y+offset),
     * and the third one at Point(X+2*offset, Y+2*offset) and so on.
     *
     * @see paintByTile(TileModel, int)
     */
    private static int unitStackOffset = 1;

    @Override
    public void createGlobalImage(ScenarioModel s) {
        Collection<TileModel> tileModels = new ArrayDeque<> ();
        for (ForceModel forceModel : s.getForceModel()) {
            for (UnitModel unitModel : forceModel.getUnitModels()) {
                TileModel tileModel = unitModel.getLocation();
                if (!tileModels.contains(tileModel)) {
                    tileModels.add(tileModel);
                    paintByTile(tileModel);
                }
            }
        }
    }

    @Override
    public void paintByTile(TileModel t){
        paintByTile(t,defaultMaxStack);
    }
    
    /**
     * Paints the units in a single tile
     * 
     * @param t TileModel where the units are
     * @param maxStack maximum units in the stack to be painted
     */
    public void paintByTile(TileModel t, int maxStack) {
        //Graphics from the global image
        Graphics2D g2 = (Graphics2D) globalImage.getGraphics();
        
        //Where the single unit image will be painted
        Image unitImage;
        
        //Calculate unit position
        Point pos = bgm.tileToPixel(t.getCoordinates());
        
        //If no units on the tile
        if (((ObservedTileModel) t).getTopUnit() == null) {
            //Empty image
            unitImage = new BufferedImage(bgm.getHexDiameter(), bgm.getHeight(), BufferedImage.TYPE_INT_ARGB);
            g2.drawImage(unitImage, pos.x, pos.y,this);
            repaint(pos.x, pos.y, unitImage.getWidth(this), unitImage.getHeight(this));
            
        } else {

            //Retrieve the single unit image
            unitImage = getUnitImage(((ObservedTileModel) t).getTopUnit());

            //Max units to be painted
            int numStackedUnits = ((ObservedTileModel) t).getNumStackedUnits();
            
            // Offset from the upper left corner of the tile
            pos.x += unitImageOffset;
            pos.y += unitImageOffset;

            // Offset from the upper left corner of the last painted unit
            // incremented by unitStackOffset
            int d = 0;
            
            //Paint the same top unit
            for (int i = 0; i < numStackedUnits && i < maxStack; i++) {
                g2.drawImage(unitImage, pos.x + d, pos.y + d, this);
                d += unitStackOffset;
            }
            repaint(pos.x, pos.y, unitImage.getWidth(this) + d, unitImage.getHeight(this) + d);
        }
        g2.dispose();
    }

    /**
     * Returns a unit image based on the unit color, IconId and KnowledgeLevel
     * 
     * @param unit
     * @return
     * @see KnowledgeLevel
     */
    private Image getUnitImage(UnitModel unit) {

        // Where the units will be painted
        BufferedImage unitImage;
        // Color template
        UnitColors uc = unit.getColor();
        // If it doesn't exists
        if (unitBufferMap.get(uc) == null) {
            loadUnitGraphics(uc);
        }

        //TODO paint tile density
        //Retrieve color template image and crop the unit we need from it
        
        //Get the coordinates
        int unitImgWidth = bgm.getImageProfile().getUnitsImageWidth() / bgm.getImageProfile().getUnitsImageCols();
        int unitImgHeight = bgm.getImageProfile().getUnitsImageHeight() / bgm.getImageProfile().getUnitsImageRows();
        int row = unit.getIconId() / bgm.getImageProfile().getUnitsImageCols();
        int col = unit.getIconId() % bgm.getImageProfile().getUnitsImageRows();
        
        //Get the unit image
        unitImage = unitBufferMap.get(uc).get().getSubimage(col * unitImgWidth, row * unitImgHeight, unitImgWidth, unitImgHeight);

        //Adds attributes to the image such as Health, Attack, Defense, etc.
        addUnitAttributes(unitImage, unit);
        
        return unitImage;
    }

    /**
     * Adds unit attributes to the image depending on what
     * much do we know about the unit
     *
     *
     * @param unitImage <code>BufferedImage</code> with the unit color and icon
     * @param unit to get its information
     * @see UnitAttributes
     */
    private void addUnitAttributes(BufferedImage unitImage, UnitModel unit) {

        UnitAttributes ua = new UnitAttributes(unitImage);        
        switch (unit.getKnowledgeLevel()) {
            case COMPLETE:
                ua.paintUnitAttributes((KnownUnitModel) unit);
                break;
            case GOOD:
                ua.paintUnitAttributes((IdentifiedUnitModel) unit);
                break;
            case POOR:
                ua.paintUnitAttributes((DetectedUnitModel) unit);
                break;
            default:
                //UnknownUnit or something Exception
                break;
        }
    }
    
    /**
     * Loads the unit color template and saves it in the buffer map
     *
     * @param uc represents the UnitColor to be loaded
     * @see UnitColors
     */
    private void loadUnitGraphics(UnitColors uc) {
        String filename = uc.getFileName();
        Image i = loadImage(AresIO.ARES_IO.getFile(bgm.getImageProfile().getPath(), filename));
        unitBufferMap.put(uc, new SoftReference(i));
    }
}
