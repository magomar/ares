package ares.application.gui_components;

import ares.application.models.board.BoardGraphicsModel;
import ares.application.models.ScenarioModel;
import ares.application.models.board.ObservedTileModel;
import ares.application.models.board.TileModel;
import ares.application.models.forces.DetectedUnitModel;
import ares.application.models.forces.ForceModel;
import ares.application.models.forces.IdentifiedUnitModel;
import ares.application.models.forces.KnownUnitModel;
import ares.application.models.forces.UnitModel;
import ares.io.AresIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.*;
import java.util.Map.Entry;
import javax.imageio.ImageIO;

/**
 * Units image layer based on Sergio Musoles TerrainPanel
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class UnitsLayer extends javax.swing.JPanel {

    // Final image to be painted on the JComponent
    private Image unitsImage = null;
    // Map to save loaded images
    private SoftReference<HashMap<Integer, BufferedImage>> unitBufferMap;
    // BoardGraphicsModel provides hexagonal and image sizes
    private BoardGraphicsModel boardInfo;
    /**
     * Offset distance from the upper left corner of the tile. The image will be painted at Point(X+offset, Y+offset)
     *
     * @see refreshUnitsByPosition
     */
    private int unitImageOffset = 7;
    /**
     * Maximum numbers of units to be painted in a single tile
     *
     * @see refreshUnitsByPosition
     */
    private int maxStack = 6;
    /**
     * The unit image can be composed of different layered images. Each layer is shifted
     * <code>unitStackOffset</code> pixels For example, say the first layer was painted at Point(X,Y), the next layer
     * will start at Point(X+offset,Y+offset), and the third one at Point(X+2*offset, Y+2*offset) and so on.
     *
     * @see refreshUnitsByPosition
     */
    private static int unitStackOffset = 1;

    /**
     * Initializes the buffer map and creates the units image
     *
     * @param scenario
     */
    public void initialize(ScenarioModel scenario) {
        boardInfo = scenario.getBoardGraphicsModel();

        // If the image has not been created
        if (unitsImage == null) {

            // If the buffer map doesn't exist
            if (unitBufferMap == null) {
                unitBufferMap = new SoftReference<>(new HashMap<Integer, BufferedImage>());
            }

//            unitsImage = new BufferedImage(boardInfo.getImageWidth(), boardInfo.getImageHeight(), BufferedImage.TYPE_4BYTE_ABGR);
//            Collection<UnitModel> spottedUnits = new ArrayList<>();
//            for (ForceModel forceModel : scenario.getForceModel()) {
//                spottedUnits.addAll(forceModel.getUnitModels());
//            }
//            createAllUnitsImage(spottedUnits);
            updateScenario(scenario);
        }
//        repaint();

    }

    public void updateScenario(ScenarioModel scenario) {
        Collection<TileModel> tileModels = new ArrayList<>();
        for (ForceModel forceModel : scenario.getForceModel()) {
            for (UnitModel unitModel : forceModel.getUnitModels()) {
                TileModel tileModel =unitModel.getLocation();
                if (!tileModels.contains(tileModel)) {
                    tileModels.add(tileModel);
                }
            }
        }
        unitsImage = new BufferedImage(boardInfo.getImageWidth(), boardInfo.getImageHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        createAllUnitsImage(tileModels);
        repaint();
    }

    private void createAllUnitsImage(Collection<TileModel> tileModels) {
        for (TileModel tile : tileModels) {
            UnitModel unit = ((ObservedTileModel) tile).getTopUnit();
            int numStackedUnits = ((ObservedTileModel) tile).getNumStackedUnits();
            refreshUnitsbyPosition(unit, tile.getCoordinates(), numStackedUnits);
        }
    }

//    /**
//     * Iterates over a unit collection to paint all its elements
//     *
//     * @param spottedUnits Units with known location
//     */
//    private void createAllUnitsImage(Collection<UnitModel> spottedUnits) {
//        for (UnitModel unit : spottedUnits) {
//            refreshUnitsbyPosition(unit, unit.getCoordinates().x, unit.getCoordinates().y, 1);
//        }
//    }

    /**
     * Paints all the given units at the specified position Uses the default maxStack
     *
     * @param uCol collection of units to be painted
     * @param x tile row
     * @param y tile column
     * @see refreshUnitsByPosition(UnitModel, int, int, int)
     */
    public void refreshUnitsbyPosition(UnitModel unit, Point coord, int numStack) {

        refreshUnitsbyPosition(unit, coord.x, coord.y, numStack, maxStack);
    }

    /**
     * Paints all the given units at the specified position, the unit model to pass as a parameter has to be the top
     * unit in the stack of units at the uni's location
     *
     * @param uCol Collection of units to be painted
     * @param row Tile row
     * @param col Tile column
     * @param maxStack Maximum numbers of units to be painted in a single tile
     */
    public void refreshUnitsbyPosition(UnitModel unit, int row, int col, int numStack, int maxStack) {

        Graphics2D g2 = (Graphics2D) unitsImage.getGraphics();
        int x = boardInfo.getHexOffset() * row;
        int h = boardInfo.getHexHeight();
        int y = (row % 2 == 0 ? h * (col) + h / 2 : h * col);// * (2 * col + ((row + 1) % 2)) / 2;
        x += unitImageOffset;
        y += unitImageOffset;
        int d = 0;

        //if(unit.isTopUnit());
        //for int i=0, i<unitModel.getStackedUnits() && i<maxStack; ++i
        //
//        for (int numStack = 0; numStack < maxStack; numStack++) {
        for (int i = 0; i < numStack; i++) {
            g2.drawImage(getUnitImage(unit), x + d, y + d, this);
            d += unitStackOffset;
        }
        g2.dispose();
    }

    /**
     * Loads the unit image based on its color, IconId and KnowledgeLevel ______________ | E H | | ______ | | | | B| TD|
     * | | B| | |______| B| | | | AT DF| |____________|
     *
     *
     * Where 'E' stands for "Echelon", 'H' for "Health", 'TD' for "Tile density" 'AT' "Attack", 'DF' Defense and 'BBB'
     * "Stamina Bar" (a line)
     *
     * @param unit
     * @return
     * @see KnowledgeLevel
     */
    private Image getUnitImage(UnitModel unit) {

        BufferedImage unitImage;
        if (unitBufferMap.get().get(unit.getColor()) == null) {
            loadUnitGraphics(unit.getColor());
        }

        //TODO paint tile density
        //Retrieve color palette image and crop the unit we need from it
        int unitImgWidth = boardInfo.getImageProfile().getUnitsImageWidth() / boardInfo.getImageProfile().getUnitsImageCols();
        int unitImgHeight = boardInfo.getImageProfile().getUnitsImageHeight() / boardInfo.getImageProfile().getUnitsImageRows();
        int row = unit.getIconId() / boardInfo.getImageProfile().getUnitsImageCols();
        int col = unit.getIconId() % boardInfo.getImageProfile().getUnitsImageRows();
        unitImage = unitBufferMap.get().get(unit.getColor()).getSubimage(col * unitImgWidth, row * unitImgHeight, unitImgWidth, unitImgHeight);

        addUnitAttributes(unitImage, unit);
        return unitImage;
    }

    /**
     * Adds unitattributes to the image based on its information level
     *
     *
     * @param unitImage <code>BufferedImage</code>
     * @param unit
     */
    private void addUnitAttributes(BufferedImage unitImage, UnitModel unit) {

        int unitImgWidth = unitImage.getWidth();
        int unitImgHeight = unitImage.getHeight();

        Graphics2D g2 = unitImage.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN, 10);
        g2.setFont(font);

        //TODO paint name
        switch (unit.getKnowledgeLevel()) {
            case COMPLETE:
                KnownUnitModel ku = (KnownUnitModel) unit;
                //Echelon
                g2.drawString("-", 3, 6);
                // Health           
                g2.setColor(Color.BLACK);
                g2.drawRect(unitImgWidth - 9, 3, 5, 3);
                g2.setColor(Color.GREEN);
                g2.fillRect(unitImgWidth - 8, 4, 3, 2);
                // Stamina bar
                g2.setColor(Color.RED);
                g2.fillRect(27, 9, 2, 11);
                // Attack
                g2.setColor(Color.ORANGE);
                g2.drawString("00", 3, unitImgHeight - 2);
                // Defense
                g2.drawString("99", unitImgWidth - 13, unitImgHeight - 2);
                break;
            case GOOD:
                IdentifiedUnitModel id = (IdentifiedUnitModel) unit;

                //Echelon
                g2.drawString("-", 3, 6);
                // Health           
                g2.setColor(Color.BLACK);
                g2.drawRect(unitImgWidth - 9, 3, 5, 3);
                g2.setColor(Color.GREEN);
                g2.fillRect(unitImgWidth - 8, 4, 3, 2);
                // Stamina bar
                g2.setColor(Color.RED);
                g2.fillRect(27, 9, 2, 11);
                // Attack
                g2.setColor(Color.ORANGE);
                g2.drawString("00", 3, unitImgHeight - 2);
                // Defense
                g2.drawString("99", unitImgWidth - 13, unitImgHeight - 2);


                break;
            case POOR:
                DetectedUnitModel du = (DetectedUnitModel) unit;

                //Echelon
                g2.drawString("-", 3, 6);
                // Health           
                g2.setColor(Color.BLACK);
                g2.drawRect(unitImgWidth - 9, 3, 5, 3);
                g2.setColor(Color.GREEN);
                g2.fillRect(unitImgWidth - 8, 4, 3, 2);
                // Stamina bar
                g2.setColor(Color.RED);
                g2.fillRect(27, 9, 2, 11);
                // Attack
                g2.setColor(Color.ORANGE);
                g2.drawString("XX", 3, unitImgHeight - 2);
                // Defense
                g2.drawString("XX", unitImgWidth - 13, unitImgHeight - 2);

                break;
        }

        g2.dispose();

    }

    /**
     * Loads all the unit icons from disk and saves them in the buffer map
     *
     * @param colorIndex
     * @see UnitColors
     */
    private void loadUnitGraphics(int colorIndex) {
        BufferedImage imageAllUnits = null;
        String filename = UnitColors.values()[colorIndex].getFileName();
        try {

            imageAllUnits = ImageIO.read(AresIO.ARES_IO.getFile(boardInfo.getImageProfile().getPath(), filename));
            unitBufferMap.get().put(colorIndex, imageAllUnits);
        } catch (IOException e) {
            System.out.println(e.getMessage() + "File not found: " + filename);
        }
    }

    /**
     *
     */
    public void flushLayer() {
        unitsImage = null;
        unitBufferMap = null;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //Zoom cutre
        //g2.scale(2, 2);
        g2.drawImage(unitsImage, 0, 0, this);
    }
}
