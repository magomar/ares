package ares.application.gui_components;

import ares.io.AresIO;
import ares.scenario.Scenario;
import ares.scenario.board.*;
import ares.scenario.forces.Unit;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.*;
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
    // BoardInfo provides hexagonal and image sizes
    private BoardInfo boardInfo;
    /**
     * Offset distance from the upper left corner of the tile. The image will be
     * painted at Point(X+offset, Y+offset)
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
     * The unit image can be composed of different layered images. Each layer is
     * shifted
     * <code>unitStackOffset</code> pixels For example, say the first layer was
     * painted at Point(X,Y), the next layer will start at
     * Point(X+offset,Y+offset), and the third one at Point(X+2*offset,
     * Y+2*offset) and so on.
     *
     * @see refreshUnitsByPosition
     */
    private int unitStackOffset = 1;

    /**
     * Initializes the buffer map and creates the units image
     *
     * @param scenario
     */
    public void initialize(Scenario scenario) {

        boardInfo = scenario.getBoardInfo();
        // If the image has not been created
        if (unitsImage == null) {

            // If the buffer doesn't exist
            if (unitBufferMap == null) {
                unitBufferMap = new SoftReference<>(new HashMap<Integer, BufferedImage>());
            }

            unitsImage = new BufferedImage(boardInfo.getImageWidth(), boardInfo.getImageHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            Map<Collection<Unit>, Integer> unitC = fillmap(scenario);
            createAllUnitsImage(unitC);
        }
        repaint();

    }

    /**
     * Iterates over a map to paint all entries
     *
     * @param m Map with a collection as key and the index as value
     */
    private void createAllUnitsImage(Map<Collection<Unit>, Integer> m) {

        for (Map.Entry<Collection<Unit>, Integer> e : m.entrySet()) {

            // Get the row and column from the index
            int index = e.getValue();
            int row = index / boardInfo.getWidth(), col = index % boardInfo.getHeight();
            // Paint the collection
            refreshUnitsbyPosition(e.getKey(), row, col);

        }
    }

    /**
     * Paints all the given units at the specified position Uses the default
     * maxStack
     *
     * @param uCol collection of units to be painted
     * @param x tile row
     * @param y tile column
     * @see refreshUnitsByPosition(Collection<Unit>, int, int, int)
     */
    public void refreshUnitsbyPosition(Collection<Unit> uCol, int x, int y) {

        refreshUnitsbyPosition(uCol, x, y, maxStack);
    }

    /**
     * Paints all the given units at the specified position
     *
     * @param uCol Collection of units to be painted
     * @param row Tile row
     * @param col Tile column
     * @param maxStack Maximum numbers of units to be painted in a single tile
     */
    public void refreshUnitsbyPosition(Collection<Unit> uCol, int row, int col, int maxStack) {

        Graphics2D g = (Graphics2D) unitsImage.getGraphics();
        int x = boardInfo.getHexOffset();
        int y = boardInfo.getHexHeight() * (2 * col + ((row + 1) % 2)) / 2;
        this.maxStack = maxStack;

        Unit topUnit = uCol.iterator().next();
        for (int stack = uCol.size(), d = unitImageOffset; stack < maxStack; ++stack) {

            g.drawImage(getUnitImage(topUnit), row * x + d, y + d, this);
            d += unitStackOffset;
        }
    }

    /**
     * Loads the unit image based on its color and IconId
     *
     * @param unit
     * @return
     */
    private Image getUnitImage(Unit unit) {
        BufferedImage imageUnit;
        if (unitBufferMap.get().get(unit.getColor()) == null) {
            loadUnitGraphics(unit.getColor());
        }

        int unitImgWidth = boardInfo.getImageProfile().getUnitsImageWidth() / boardInfo.getImageProfile().getUnitsImageCols();
        int unitImgHeight = boardInfo.getImageProfile().getUnitsImageHeight() / boardInfo.getImageProfile().getUnitsImageRows();
        int row = unit.getIconId() / boardInfo.getImageProfile().getUnitsImageCols();
        int col = unit.getIconId() % boardInfo.getImageProfile().getUnitsImageRows();
        imageUnit = unitBufferMap.get().get(unit.getColor()).getSubimage(col * unitImgWidth, row * unitImgHeight, unitImgWidth, unitImgHeight);
        return imageUnit;

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
     * This method goes through all the tiles and puts
     * in a map the collection of units in each tile with its position
     *
     * @param scenario
     * @return
     */
    private Map<Collection<Unit>, Integer> fillmap(Scenario scenario) {

        Map<Collection<Unit>, Integer> m = new HashMap<>();
        Tile[][] tmap = scenario.getBoard().getMap();
        for (Tile[] tv : tmap) {

            for (Tile t : tv) {
                Collection c = new ArrayList();
                for (int i = 0; i < t.getNumStackedUnits(); i++) {
                    c.add(t.getTopUnit());
                    t.nextTopUnit();
                }
                if (t.getNumStackedUnits() != 0) {
                    m.put(c, t.getX() * scenario.getBoardInfo().getWidth() + t.getY());
                }
            }

        }



        return m;
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
        g.drawImage(unitsImage, 0, 0, this);
    }
}
