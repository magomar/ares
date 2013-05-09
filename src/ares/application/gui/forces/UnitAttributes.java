package ares.application.gui.forces;import ares.application.models.forces.*;import ares.scenario.forces.Echelon;import java.awt.*;import java.awt.image.BufferedImage;/** * This class paints the relevant attributes of a unit over its image. Paint methods are overloaded for all the unit * models * * @author Heine <heisncfr@inf.upv.es> * @see UnitModel */public class UnitAttributes {    /**     * Unit image width     */    private int unitImgWidth;     /**     * Unit image height     */    private int unitImgHeight;    /**    * Image graphics    */    private Graphics2D g2;    private static final String MAX_VALUE = Integer.toString(99);    public UnitAttributes(BufferedImage i) {        unitImgWidth = i.getWidth();        unitImgHeight = i.getHeight();        g2 = i.createGraphics();//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);        //Unicode font for the infinity symbol        Font font = new Font("Arial Narrow", Font.PLAIN, 10);        g2.setFont(font);    }    protected void paintUnitAttributes(KnownUnitModel unitModel) {        paintName(unitModel.getName());        paintEchelon(unitModel.getEchelon());        paintHealth(unitModel.getHealth());        paintTileDensity();        paintStamina(unitModel.getStamina());        paintAttack(unitModel.getColor().getForeground(), unitModel.getAttackStrength());        paintDeffense(unitModel.getColor().getForeground(), unitModel.getDefenseStrength());    }    protected void paintUnitAttributes(IdentifiedUnitModel unitModel) {        paintName(unitModel.getName());        paintEchelon(unitModel.getEchelon());        paintHealth(unitModel.getHealth());        paintTileDensity();        paintAttack(unitModel.getColor().getForeground(), unitModel.getAttackStrength());        paintDeffense(unitModel.getColor().getForeground(), unitModel.getDefenseStrength());    }    void paintUnitAttributes(DetectedUnitModel unitModel) {    }    private void paintName(String name) {        // Too much information        // g2.drawString(name.substring(0, 4), 3, 6);    }    private void paintEchelon(Echelon e) {        g2.setColor(Color.WHITE);        g2.drawString("-", 3, 6);    }    private void paintHealth(int health) {        //Empty rectangle with black border        g2.setColor(Color.BLACK);        g2.drawRect(unitImgWidth - 9, 3, 5, 3);        //Fill rectangle        g2.setColor(colorLevel(health));        g2.fillRect(unitImgWidth - 8, 4, 4, 2);    }    private void paintTileDensity() {        //TODO paint Tile Density    }    private void paintStamina(int s) {        g2.setColor(colorLevel(s));        //Bar is smaller if stamine is low        //+90% : 10px, +50% : 5px, 2px otherwise//        int diff = (s > 90 ? 10 : (s > 50 ? 5 : 2));        int diff = s/10;        g2.fillRect(26, 20 - diff, 2, diff);    }    private void paintAttack(Color c, int a) {        g2.setColor(c);        String att = (a < 99 ? Integer.toString(a) : MAX_VALUE);        g2.drawString(att, 3, unitImgHeight - 2);    }    private void paintDeffense(Color c, int d) {        g2.setColor(c);        String def = (d < 99 ? Integer.toString(d) : MAX_VALUE);        g2.drawString(def, unitImgWidth - 13, unitImgHeight - 2);    }    /**     * Converts percentage to color in a scale from red to green     *     * @param percent number between 0 and 100     * @return     */    private Color colorLevel(int percent) {        int g = (255 * percent) / 100;        int r = (255 * (100 - percent)) / 100;        return new Color(r, g, 0);    }}