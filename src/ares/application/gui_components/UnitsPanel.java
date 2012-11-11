package ares.application.gui_components;

import ares.io.AresIO;
import ares.io.AresPaths;
import ares.platform.util.ImageUtils;
import ares.scenario.Scenario;
import ares.scenario.board.BoardInfo;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergio Musoles
 */
public final class UnitsPanel extends javax.swing.JPanel {

    private Scenario scenario;
    private BoardInfo boardInfo;
    private Image unitsImage;
    private Map<Unit, BufferedImage> unitGraphics;

    /**
     * Creates new form UnitsPanel
     */
    public UnitsPanel() {
        initComponents();
        setOpaque(false);
    }

    public void initialize(Scenario scenario) {
        this.scenario = scenario;
        this.boardInfo = scenario.getBoardInfo();
        loadGraphics();
        updateUnits();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        int hexRadius = boardInfo.getHexRadius();
        int hexHeight = boardInfo.getHexHeight();
        int ci = (int) Math.floor((double) evt.getX() / (double) boardInfo.getHexSide());
        int cx = evt.getX() - boardInfo.getHexSide() * ci;
        int ty = evt.getY() - ((ci + 1) % 2) * hexHeight / 2;
        int cj = (int) Math.floor((double) ty / (double) hexHeight);
        int cy = ty - hexHeight * cj;

        int i;
        int j;
        if (cx > Math.abs(hexRadius / 2 - hexRadius * cy / hexHeight)) {
            i = ci;
            j = cj;
        } else {
            i = ci - 1;
            j = cj + ((ci + 1) % 2) - ((cy < hexHeight / 2) ? 1 : 0);
        }
        if (boardInfo.validCoordinates(i, j)) {
            Logger.getLogger(UnitsPanel.class.getName()).log(Level.INFO, "Click en < {0},{1}>", new Object[]{i, j});
            Tile tile = scenario.getBoard().getTile(i, j);
            int numStackedUnits = tile.getNumStackedUnits();
            if (numStackedUnits > 0) {
                tile.nextTopUnit();
                Logger.getLogger(UnitsPanel.class.getName()).log(Level.INFO, "Point of Interest = {0}", tile.getTopUnit());
                updateUnits();
            }
        }
    }//GEN-LAST:event_formMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private void loadGraphics() {
        List<Unit> activeUnits = scenario.getActiveUnits();
        UnitColors[] unitColors = UnitColors.values();
        Map<UnitColors, BufferedImage[]> usedColors = new EnumMap<>(UnitColors.class);
        unitGraphics = new HashMap<>();
        for (Unit unit : activeUnits) {
            UnitColors color = unitColors[unit.getColor()];
            if (usedColors.containsKey(color)) {
                unitGraphics.put(unit, usedColors.get(color)[unit.getIconId()]);
            } else {
                BufferedImage[] colorGraphics = ImageUtils.splitImageUnits(ImageUtils.loadImage(AresIO.ARES_IO.getFile(AresPaths.GRAPHICS_MEDIUM.getPath(), color.getFileName() + ".png")), 8, 16);
                usedColors.put(color, colorGraphics);
                unitGraphics.put(unit, colorGraphics[unit.getIconId()]);
            }
        }
    }

    private BufferedImage[][] createUnitImages() {
        BufferedImage[][] buffUnitsImage = new BufferedImage[boardInfo.getWidth()][boardInfo.getHeight()];
        BufferedImage bufUnitInformation[] = ImageUtils.splitImageUnitInformation(ImageUtils.loadImage(AresIO.ARES_IO.getFile(AresPaths.GRAPHICS_MEDIUM.getPath(), "Numbers.png")), 115, 28);
        Tile[][] tiles = scenario.getBoard().getMap();
        for (int i = 0; i < boardInfo.getWidth(); i++) {
            for (int j = 0; j < boardInfo.getHeight(); j++) {
                BufferedImage bufImage = new BufferedImage(boardInfo.getHexDiameter(), boardInfo.getHexHeight(), BufferedImage.TYPE_INT_ARGB);
                Tile tile = tiles[i][j];
                int numStackedUnits = tile.getNumStackedUnits();

                if (numStackedUnits > 0) {
                    Graphics2D gbi = bufImage.createGraphics();
                    AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
                    gbi.setComposite(ac);
                    int sum = 0;
                    Unit visibleUnit = tile.getTopUnit();
                    for (int k = 0; k < numStackedUnits; k++) {
                        gbi.drawImage(unitGraphics.get(visibleUnit), sum + 10, sum + 7, this);
                        //AÃ‘ADIDO le he sumado a todo +10 y +7
                        int selectColorInformation = (visibleUnit.getColor() - 1) * 28;
                        int attack = visibleUnit.getAttackStrength();
                        int firstNumberAttack = (int) attack / 100;
                        int secondNumberAttack = (int) attack % 100;
                        gbi.drawImage(bufUnitInformation[selectColorInformation + firstNumberAttack], 15 + sum, 29 + sum, this);
                        gbi.drawImage(bufUnitInformation[selectColorInformation + secondNumberAttack], 19 + sum, 29 + sum, this);
                        int defense = visibleUnit.getDefenseStrength();
                        int firstNumberDefense = (int) defense / 10;
                        int secondNumberDefense = (int) defense % 10;
                        gbi.drawImage(bufUnitInformation[selectColorInformation + firstNumberDefense], 27 + sum, 29 + sum, this);
                        gbi.drawImage(bufUnitInformation[selectColorInformation + secondNumberDefense], 31 + sum, 29 + sum, this);
                        //health 0-100
                        int health = visibleUnit.getHealth() / 25;
                        gbi.drawImage(bufUnitInformation[3084 - health], 34 + sum, 9 + sum, this);
                        //el echelon el ordinal 
                        int echelon = visibleUnit.getEchelon().ordinal();
                        gbi.drawImage(bufUnitInformation[selectColorInformation + 10 + echelon], 20 + sum, 9 + sum, this);
                        int emphasis = visibleUnit.getEmphasis().ordinal() + 1;
                        int displacement = 0;
                        for (int p = 0; p < emphasis; p++) {
                            gbi.drawImage(bufUnitInformation[selectColorInformation + 19], 12 + sum, 13 + sum + displacement, this);
                            displacement += 6;
                        }

                        if (sum < 6) {
                            sum += 6 / numStackedUnits;
                        }
                    }
                    BufferedImage[] densityIcon = ImageUtils.splitImageHexagons(ImageUtils.loadImage(AresIO.ARES_IO.getFile(AresPaths.GRAPHICS_MEDIUM.getPath(), "tiles_misc.png")), 8, 10);
                    int stackingPenalty = tile.getStackingPenalty(scenario.getScale());
                    if (stackingPenalty > 0) {
                        gbi.drawImage(densityIcon[10 + stackingPenalty], 0, 1, this);
                    }
                }

                buffUnitsImage[i][j] = bufImage;
            }
        }

        return buffUnitsImage;
    }

    private void createGlobalUnitsImage(BufferedImage[][] images) {
        BufferedImage buffUnitsImage = new BufferedImage(boardInfo.getHexDiameter() + (boardInfo.getWidth() - 1) * boardInfo.getHexSide(), ((boardInfo.getHeight() * 2) + 1) * boardInfo.getHexHeight() / 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gIR = buffUnitsImage.createGraphics();
        int displacementX = 10;
        int displacementY = 7;
        for (int i = 0; i < boardInfo.getWidth(); i++) {
            int x = boardInfo.getHexSide() * i + displacementX;
            for (int j = 0; j < boardInfo.getHeight(); j++) {
                int y = (boardInfo.getHexHeight() * (2 * j + ((i + 1) % 2)) / 2) + displacementY;
                gIR.drawImage(images[i][j], x, y, this);
            }
        }
        unitsImage = buffUnitsImage;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Dimension x = new Dimension(unitsImage.getWidth(this), unitsImage.getHeight(this));
        this.setPreferredSize(x);
        g2.drawImage(unitsImage, 0, 0, unitsImage.getWidth(this), unitsImage.getHeight(this), this);
    }

    public void updateUnits() {
        BufferedImage[][] hexImages = createUnitImages();
        createGlobalUnitsImage(hexImages);
        repaint();
    }
}
