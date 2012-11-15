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
        // // True boolean is a temporary fix to repaint
        // // the unit layer. Units should be refreshed tile by tile
        if (true || unitsImage == null) {

            // If the buffer doesn't exist
            if (unitBufferMap == null) {
                unitBufferMap = new SoftReference<>(new HashMap<Integer, BufferedImage>());
            }

            unitsImage = new BufferedImage(boardInfo.getImageWidth(), boardInfo.getImageHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            //This map will be replaced with Collection<unitModel>
            //where unitModel will have attributes int color, iconid;Point position; int stackedunits(tile density), stamina, health attack, defense; Echelon ech;
            Map<Collection<Unit>, Point> unitC = fillmap(scenario);
            createAllUnitsImage(unitC);
        }
        repaint();

    }

    /**
     * Iterates over a map to paint all entries
     *
     * @param m Map with a collection as key and the index as value
     */
    private void createAllUnitsImage(Map<Collection<Unit>, Point> m) {

        for (Map.Entry<Collection<Unit>, Point> e : m.entrySet()) {

            // Get the row and column from the index
            
            // Paint the collection
            if(e.getKey().size()!=0)refreshUnitsbyPosition(e.getKey(), e.getValue().x, e.getValue().y);

        }
    }

    /**
     * Paints all the given units at the specified position Uses the default
     * maxStack
     *
     * @param uCol collection of units to be painted
     * @param x tile row
     * @param y tile column
     * @see refreshUnitsByPosition(Unit, int, int, int)
     */
    public void refreshUnitsbyPosition(Collection<Unit> unit, int x, int y) {

        refreshUnitsbyPosition(unit, x, y, maxStack);
    }

    /**
     * Paints all the given units at the specified position
     *
     * @param uCol Collection of units to be painted
     * @param row Tile row
     * @param col Tile column
     * @param maxStack Maximum numbers of units to be painted in a single tile
     */
    public void refreshUnitsbyPosition(Collection<Unit> unit, int row, int col, int maxStack) {

        Graphics2D g2 = (Graphics2D) unitsImage.getGraphics();
        int x = boardInfo.getHexOffset()*row;
        int h = boardInfo.getHexHeight(); int y = (row%2 == 0 ? h*(col)+h/2 : h*col);// * (2 * col + ((row + 1) % 2)) / 2;
        x+=unitImageOffset; y+=unitImageOffset;
        int d = 0;

        //for int i=0, i<unitModel.stackedUnits || i<maxStack; ++i
        Unit u = unit.iterator().next();
        for(Unit varNotUsed : unit){
            g2.drawImage(getUnitImage(u), x+d , y+d , this);
            d += unitStackOffset;
        }
        g2.dispose();
    }

    /**
     * Loads the unit image based on its color and IconId
     * and writes its attributes.
     * 
     * The result will look like this:
     *    |--------------|
     *    | E          H |
     *    |              |
     * TD |   ........ B |
     *    |   |      | B |
     *    |   |......| B |
     *    |              |
     *    |__AT_____DF___|
     * 
     * Where 'E' stands for "Echelon", 'H' for "Health", 'TD' for "Tile density"
     * 'AT' "Attack", 'DF' Defense and 'BBB' "Stamina Bar" (a line)
     * @param unit
     * @return
     */
    private Image getUnitImage(Unit unit) {
        BufferedImage imageUnit;
        if (unitBufferMap.get().get(unit.getColor()) == null) {
            loadUnitGraphics(unit.getColor());
        }
        
        //TODO paint tile density
        
        //Retrieve image and crop the unit we need
        int unitImgWidth = boardInfo.getImageProfile().getUnitsImageWidth() / boardInfo.getImageProfile().getUnitsImageCols();
        int unitImgHeight = boardInfo.getImageProfile().getUnitsImageHeight() / boardInfo.getImageProfile().getUnitsImageRows();
        int row = unit.getIconId() / boardInfo.getImageProfile().getUnitsImageCols();
        int col = unit.getIconId() % boardInfo.getImageProfile().getUnitsImageRows();
        imageUnit = unitBufferMap.get().get(unit.getColor()).getSubimage(col * unitImgWidth, row * unitImgHeight, unitImgWidth, unitImgHeight);
        
        //TODO Set text dinamycally
        //Write text
        Graphics2D g2 = imageUnit.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN,10);
        g2.setFont(font);
        //Echelon
        g2.drawString("-",3,6);
        // Health            //g2.drawString("99", unitImgWidth-13, 9);
        g2.setColor(Color.BLACK);
        g2.drawRect(unitImgWidth-9, 3, 5, 3);
        g2.setColor(Color.GREEN);
        g2.fillRect(unitImgWidth-8, 4, 3, 2);
        // Stamina bar
        g2.setColor(Color.RED);
        g2.fillRect(27,9, 2,11);
        // Attack
        g2.setColor(Color.ORANGE);
        g2.drawString("00",3,unitImgHeight-2);
        // Defense
        g2.drawString("99",unitImgWidth-13,unitImgHeight-2);
        
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
     * This method goes through all the tiles and puts in a map the collection
     * of units in each tile with its position
     *
     * @param scenario
     * @return
     */
    private Map<Collection<Unit>, Point> fillmap(Scenario scenario) {

        Map<Collection<Unit>, Point> m = new HashMap<>();
        Tile[][] tmap = scenario.getBoard().getMap();
        for (Tile[] tv : tmap) {
            for (Tile t : tv) {
                Collection<Unit> uc = new ArrayList();
                if (t.getNumStackedUnits() != 0) {
                    for(int i = 0; i < t.getNumStackedUnits(); i++){
                        uc.add(t.getTopUnit());t.nextTopUnit();
                    }
                }
                m.put(uc, new Point(t.getX(),t.getY()));
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
        Graphics2D g2 = (Graphics2D) g;
        //Zoom cutre
        //g2.scale(2, 2);
        g2.drawImage(unitsImage, 0, 0, this);
    }
}
