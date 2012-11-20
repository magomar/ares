package ares.application.gui_components;

import ares.application.models.forces.*;
import ares.scenario.forces.Echelon;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class paints the relevant attributes of a unit
 * over its image.
 * Paint methods are overloaded to all the unit models
 * 
 * @author Heine <heisncfr@inf.upv.es>
 * @see UnitModel
 */
public class UnitAttributes {

    //Unit image width and height
    private int unitImgWidth, unitImgHeight;
    //Image graphics
    private Graphics2D g2;
    
    private static String infinity = "∞";

    UnitAttributes(BufferedImage i) {
        unitImgWidth = i.getWidth();
        unitImgHeight = i.getHeight();
        g2 = (Graphics2D) i.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Unicode font for the infinity symbol
        Font font = new Font("Serif", Font.PLAIN, 10);
        g2.setFont(font);
    }

    
    void paintUnitAttributes(KnownUnitModel kum) {
        paintName(kum.getName());
        paintEchelon(kum.getEchelon());
        paintHealth(kum.getHealth());
        paintTileDensity();
        paintStamina(kum.getStamina());
        paintAttack(kum.getColor().getAttributeColor(),kum.getAttackStrength());
        paintDeffense(kum.getColor().getAttributeColor(),kum.getDefenseStrength());
    }

    void paintUnitAttributes(IdentifiedUnitModel ium) {
        paintName(ium.getName());
        paintEchelon(ium.getEchelon());
        paintHealth(ium.getHealth());
        paintTileDensity();
        paintAttack(ium.getColor().getAttributeColor(),ium.getAttackStrength());
        paintDeffense(ium.getColor().getAttributeColor(),ium.getDefenseStrength());
    }
    
    void paintUnitAttributes(DetectedUnitModel dum){
        
    }

    
    private void paintName(String name) {
        //TODO paint name above unit
    }
    
    private void paintEchelon(Echelon e) {
        g2.setColor(Color.WHITE);
        g2.drawString("-", 3, 6);
    }

    private void paintHealth(int health) {
        //Empty rectangle with black border
        g2.setColor(Color.BLACK);
        g2.drawRect(unitImgWidth - 9, 3, 5, 3);
        //Fill rectangle
        g2.setColor(colorLevel(health));
        g2.fillRect(unitImgWidth - 8, 4, 4, 2);
    }

    private void paintTileDensity() {
        //TODO paint Tile Density
    }

    private void paintStamina(int s) {
        g2.setColor(colorLevel(s));
        //Bar is smaller if stamine is low
        //+90% : 10px, +50% : 5px, 2px otherwise
        int diff = (s > 90 ? 10 : (s>50 ? 5 : 2) );
        g2.fillRect(26, 20-diff, 2, diff);
    }

    private void paintAttack(Color c, int a) {
        g2.setColor(c);
        String att = (a < 99 ? Integer.toString(a) : infinity);
        g2.drawString(att, 3, unitImgHeight - 2);
    }

    private void paintDeffense(Color c, int d) {
        g2.setColor(c);
        String def = (d < 99 ? Integer.toString(d) : infinity);
        g2.drawString(def, unitImgWidth - 13, unitImgHeight - 2);
    }

    /**
     * Converts percentage to color in a scale from red to green
     * 
     * @param percent number between 0 and 100
     * @return
     */
    private Color colorLevel(int percent) {
        //Just to make sure it's in range
        if(percent > 100 || percent < 0) return Color.BLACK;
        int r = (255*percent)/100;
        int g = (255*(100-percent))/100;
        return new Color(r,g,0);
    }


}
