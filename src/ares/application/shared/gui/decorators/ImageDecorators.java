package ares.application.shared.gui.decorators;

import ares.application.shared.gui.profiles.GraphicProperties;
import ares.application.shared.gui.profiles.ProfiledGraphicProperty;
import ares.application.shared.models.forces.DetectedUnitModel;
import ares.application.shared.models.forces.IdentifiedUnitModel;
import ares.application.shared.models.forces.KnownUnitModel;
import ares.application.shared.models.forces.UnitModel;
import ares.platform.engine.knowledge.KnowledgeCategory;
import ares.platform.scenario.forces.Echelon;

import java.awt.*;
import java.awt.color.ICC_ProfileRGB;

/**
 * This class paints the relevant attributes of a unit over its image. Paint methods are overloaded for all the unit
 * models
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 * @see UnitModel
 */
public class ImageDecorators {

    private static final double HEADING_X = .3;
    private static final double HEADING_Y = .2;
    private static final double RIGHT_LED_X = .75;
    private static final double LED_Y = .05;
    private static final double RIGHT_BAR_X = .85;
    private static final double BAR_Y = .3;
    private static final double LEFT_INFO_X = .1;
    private static final double RIGHT_INFO_X = .6;
    private static final double INFO_Y = .98;
    private final Point headPos;
    private final Point rightLedPos;
    private final Point rightBarPos;
    private final Point leftInfoPos;
    private final Point rightInfoPos;
    private final Dimension ledSize;
    private final Dimension barSize;
    private final Font headFont;
    private final Font infoFont;
    private final Font arrowFont;
    private final Point arrowCostPos;
    private final Font placeFont;

    public ImageDecorators(int profile) {
        // compute attribute relative locations in pixels
        int fontSize = GraphicProperties.getProperty(ProfiledGraphicProperty.UNIT_FONT_SIZE, profile);
        infoFont = new Font(GraphicProperties.FONT_NAME, GraphicProperties.FONT_STYLE, fontSize);
        headFont = new Font(GraphicProperties.FONT_NAME, GraphicProperties.FONT_STYLE, fontSize * 2 / 3);
        int unitWidth = GraphicProperties.getProperty(ProfiledGraphicProperty.UNIT_WIDTH, profile);
        int unitHeight = GraphicProperties.getProperty(ProfiledGraphicProperty.UNIT_HEIGHT, profile);
        headPos = new Point((int) (HEADING_X * unitWidth), (int) (HEADING_Y * unitHeight));
        rightLedPos = new Point((int) (RIGHT_LED_X * unitWidth), (int) (LED_Y * unitHeight));
        rightBarPos = new Point((int) (RIGHT_BAR_X * unitWidth), (int) (BAR_Y * unitHeight));
        leftInfoPos = new Point((int) (LEFT_INFO_X * unitWidth), (int) (INFO_Y * unitHeight));
        rightInfoPos = new Point((int) (RIGHT_INFO_X * unitWidth), (int) (INFO_Y * unitHeight));
        int ledLenght = GraphicProperties.getProperty(ProfiledGraphicProperty.UNIT_LED_LENGHT, profile);
        ledSize = new Dimension(ledLenght * 3 / 2, ledLenght);
        int barLenght = GraphicProperties.getProperty(ProfiledGraphicProperty.UNIT_BAR_LENGHT, profile);
        barSize = new Dimension(Math.max(1, barLenght / 4), barLenght);
        int arrowFontSize = GraphicProperties.getProperty(ProfiledGraphicProperty.ARROW_FONT_SIZE, profile);
        arrowFont = new Font(GraphicProperties.FONT_NAME, GraphicProperties.FONT_STYLE, arrowFontSize);
        int tileWidth = GraphicProperties.getProperty(ProfiledGraphicProperty.TILE_WIDTH, profile);
        int tileHeight = GraphicProperties.getProperty(ProfiledGraphicProperty.TILE_HEIGHT, profile);
        arrowCostPos = new Point(tileWidth / 3, 2 * tileHeight / 3);
        placeFont = new Font(GraphicProperties.FONT_NAME, Font.BOLD, fontSize * 3 / 2);
    }

    public void paintUnitAttributes(Graphics2D g2, UnitModel unit) {
        KnowledgeCategory kc = unit.getKnowledgeCategory();
        switch (kc) {
            case COMPLETE:
                paintUnitAttributes(g2, (KnownUnitModel) unit);
                break;
            case GOOD:
                paintUnitAttributes(g2, (IdentifiedUnitModel) unit);
                break;
            case POOR:
                paintUnitAttributes(g2, (DetectedUnitModel) unit);
                break;
            case NONE:
                break;
            default:
                //We shouldn't get here
                throw new AssertionError("Assertion failed: unknown knowledge category " + unit.getKnowledgeCategory().toString());
        }
    }

    private void paintUnitAttributes(Graphics2D g2, KnownUnitModel unitModel) {
        g2.setFont(infoFont);
        Color color = unitModel.getColor().getForeground();
//        paintName(unitModel.getName());
        paintEchelon(g2, color, unitModel.getEchelon());
        paintHealth(g2, unitModel.getHealth());
        paintTileDensity(g2);
        paintStamina(g2, unitModel.getStamina());
        paintAttack(g2, color, unitModel.getAttackStrength());
        paintDeffense(g2, color, unitModel.getDefenseStrength());
    }

    private void paintUnitAttributes(Graphics2D g2, IdentifiedUnitModel unitModel) {
        g2.setFont(infoFont);
        Color color = unitModel.getColor().getForeground();
//        paintName(unitModel.getName());
        paintEchelon(g2, color, unitModel.getEchelon());
        paintHealth(g2, unitModel.getHealth());
        paintTileDensity(g2);
        paintAttack(g2, color, unitModel.getAttackStrength());
        paintDeffense(g2, color, unitModel.getDefenseStrength());
    }

    private void paintUnitAttributes(Graphics2D g2, DetectedUnitModel unitModel) {
    }

    //    private void paintName(String name) {
//        g2.drawString(name.substring(0, 4), 3, 6);
//    }
    private void paintEchelon(Graphics2D g2, Color color, Echelon echelon) {
        g2.setFont(headFont);
        g2.setColor(color);
        String echelonSymbol = echelon.getSymbol();
        g2.drawString(echelonSymbol, headPos.x - echelonSymbol.length() / 2, headPos.y);
        g2.setFont(infoFont);
    }

    private void paintHealth(Graphics2D g2, int health) {
        //Black rectangle (empty, b1 pixel
        g2.setColor(Color.BLACK);
        g2.drawRect(rightLedPos.x, rightLedPos.y, ledSize.width, ledSize.height);
        //Fill rectangle
        g2.setColor(colorLevel(health, ICC_ProfileRGB.REDCOMPONENT, ICC_ProfileRGB.GREENCOMPONENT));
        g2.fillRect(rightLedPos.x + 1, rightLedPos.y + 1, ledSize.width - 1, ledSize.height - 1);
    }

    private void paintTileDensity(Graphics2D g2) {
        //TODO paint Tile Density
    }

    private void paintStamina(Graphics2D g2, int stamina) {
        //Bar is smaller if stamine is low
        //+90% : 10px, +50% : 5px, 2px otherwise
//        int diff = (s > 90 ? 10 : (s > 50 ? 5 : 2));
//        int length = (int) (0.25 * barSize.height + 0.0075 * barSize.height * stamina);
        int length = barSize.height * stamina / 100;
//        g2.fillRect(26, 20 - diff, 2, diff);
        g2.setColor(Color.GREEN);
        g2.fillRect(rightBarPos.x, rightBarPos.y, barSize.width, length);
        g2.setColor(Color.BLACK);
        g2.fillRect(rightBarPos.x, rightBarPos.y + length, barSize.width, barSize.height - length);
    }

    private void paintAttack(Graphics2D g2, Color color, int value) {
        g2.setColor(color);
//        g2.drawString(att, 3, imageHeight - 2);
        g2.drawString(Integer.toString(value), leftInfoPos.x, leftInfoPos.y);
    }

    private void paintDeffense(Graphics2D g2, Color color, int value) {
        g2.setColor(color);
        g2.drawString(Integer.toString(value), rightInfoPos.x, rightInfoPos.y);
    }

//    /**
//     * Converts percentage to color in a scale from red to green
//     *
//     * @param percentage number between 0 and 100
//     * @return
//     */
//    private Color colorLevel(int percentage) {
//        int g = (255 * percentage) / 100;
//        int r = (255 * (100 - percentage)) / 100;
//        return new Color(r, g, 0);
//    }

    /**
     * Converts a percentage into a color using a scale between two pure RGB colors, identified as follows:
     * Red = 0
     * Green = 1
     * Blue = 2
     *
     * @param percentage    indicating the color level between the top and bottom colors
     * @param bottomRGBComponent  an int between 0 and 2 identifying the bottom color (R, G or B)
     * @param topRGBComponent     an int between 0 and 2 identifying the top color (R, G or B)
     * @return a color between bottom and top colors, correspoding to the {@code percentage} parameter
     * @see java.awt.color.ICC_ProfileRGB
     */
    private Color colorLevel(int percentage, int bottomRGBComponent, int topRGBComponent) {
        int[] rgb = new int[3];
        for (int i = 0; i < rgb.length; i++) {
            if (i == bottomRGBComponent) {
                rgb[i] = (255 * (100 - percentage)) / 100;
            } else if (i == topRGBComponent) {
                rgb[i] = (255 * percentage) / 100;
            } else {
                rgb[i] = 0;
            }
        }
        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    public void paintArrowCost(Graphics2D g2, int cost) {
        g2.setFont(arrowFont);
        g2.drawString(Integer.toString(cost), arrowCostPos.x, arrowCostPos.y);
    }

    public Font getPlaceFont() {
        return placeFont;
    }
}