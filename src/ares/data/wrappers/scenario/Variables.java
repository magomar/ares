
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Variables complex unitType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="Variables">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="scenarioIsOver" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ceaseFire" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="eventEngineVariable" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="riversAlongEdges" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="attritionDivider" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="maxRoundsPerBattle" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="AAALethalityRate" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="engineeringRate" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="hexConversionRate" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="entrenchmentRate" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="combatDensityRate" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="supplyMovementRate" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="supplyReadinessRate" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Variables", namespace = "ares", propOrder = {
        "scenarioIsOver",
        "ceaseFire",
        "eventEngineVariable",
        "riversAlongEdges",
        "attritionDivider",
        "maxRoundsPerBattle",
        "aaaLethalityRate",
        "engineeringRate",
        "hexConversionRate",
        "entrenchmentRate",
        "combatDensityRate",
        "supplyMovementRate",
        "supplyReadinessRate"
})
public class Variables {

    @XmlElement(namespace = "ares")
    protected int scenarioIsOver;
    @XmlElement(namespace = "ares")
    protected int ceaseFire;
    @XmlElement(namespace = "ares")
    protected int eventEngineVariable;
    @XmlElement(namespace = "ares")
    protected int riversAlongEdges;
    @XmlElement(namespace = "ares", defaultValue = "10")
    protected int attritionDivider;
    @XmlElement(namespace = "ares", defaultValue = "99")
    protected int maxRoundsPerBattle;
    @XmlElement(name = "AAALethalityRate", namespace = "ares", defaultValue = "100")
    protected int aaaLethalityRate;
    @XmlElement(namespace = "ares", defaultValue = "100")
    protected int engineeringRate;
    @XmlElement(namespace = "ares", defaultValue = "100")
    protected int hexConversionRate;
    @XmlElement(namespace = "ares", defaultValue = "100")
    protected int entrenchmentRate;
    @XmlElement(namespace = "ares", defaultValue = "100")
    protected int combatDensityRate;
    @XmlElement(namespace = "ares", defaultValue = "100")
    protected int supplyMovementRate;
    @XmlElement(namespace = "ares", defaultValue = "100")
    protected int supplyReadinessRate;

    /**
     * Gets the value of the scenarioIsOver property.
     */
    public int getScenarioIsOver() {
        return scenarioIsOver;
    }

    /**
     * Sets the value of the scenarioIsOver property.
     */
    public void setScenarioIsOver(int value) {
        this.scenarioIsOver = value;
    }

    /**
     * Gets the value of the ceaseFire property.
     */
    public int getCeaseFire() {
        return ceaseFire;
    }

    /**
     * Sets the value of the ceaseFire property.
     */
    public void setCeaseFire(int value) {
        this.ceaseFire = value;
    }

    /**
     * Gets the value of the eventEngineVariable property.
     */
    public int getEventEngineVariable() {
        return eventEngineVariable;
    }

    /**
     * Sets the value of the eventEngineVariable property.
     */
    public void setEventEngineVariable(int value) {
        this.eventEngineVariable = value;
    }

    /**
     * Gets the value of the riversAlongEdges property.
     */
    public int getRiversAlongEdges() {
        return riversAlongEdges;
    }

    /**
     * Sets the value of the riversAlongEdges property.
     */
    public void setRiversAlongEdges(int value) {
        this.riversAlongEdges = value;
    }

    /**
     * Gets the value of the attritionDivider property.
     */
    public int getAttritionDivider() {
        return attritionDivider;
    }

    /**
     * Sets the value of the attritionDivider property.
     */
    public void setAttritionDivider(int value) {
        this.attritionDivider = value;
    }

    /**
     * Gets the value of the maxRoundsPerBattle property.
     */
    public int getMaxRoundsPerBattle() {
        return maxRoundsPerBattle;
    }

    /**
     * Sets the value of the maxRoundsPerBattle property.
     */
    public void setMaxRoundsPerBattle(int value) {
        this.maxRoundsPerBattle = value;
    }

    /**
     * Gets the value of the aaaLethalityRate property.
     */
    public int getAAALethalityRate() {
        return aaaLethalityRate;
    }

    /**
     * Sets the value of the aaaLethalityRate property.
     */
    public void setAAALethalityRate(int value) {
        this.aaaLethalityRate = value;
    }

    /**
     * Gets the value of the engineeringRate property.
     */
    public int getEngineeringRate() {
        return engineeringRate;
    }

    /**
     * Sets the value of the engineeringRate property.
     */
    public void setEngineeringRate(int value) {
        this.engineeringRate = value;
    }

    /**
     * Gets the value of the hexConversionRate property.
     */
    public int getHexConversionRate() {
        return hexConversionRate;
    }

    /**
     * Sets the value of the hexConversionRate property.
     */
    public void setHexConversionRate(int value) {
        this.hexConversionRate = value;
    }

    /**
     * Gets the value of the entrenchmentRate property.
     */
    public int getEntrenchmentRate() {
        return entrenchmentRate;
    }

    /**
     * Sets the value of the entrenchmentRate property.
     */
    public void setEntrenchmentRate(int value) {
        this.entrenchmentRate = value;
    }

    /**
     * Gets the value of the combatDensityRate property.
     */
    public int getCombatDensityRate() {
        return combatDensityRate;
    }

    /**
     * Sets the value of the combatDensityRate property.
     */
    public void setCombatDensityRate(int value) {
        this.combatDensityRate = value;
    }

    /**
     * Gets the value of the supplyMovementRate property.
     */
    public int getSupplyMovementRate() {
        return supplyMovementRate;
    }

    /**
     * Sets the value of the supplyMovementRate property.
     */
    public void setSupplyMovementRate(int value) {
        this.supplyMovementRate = value;
    }

    /**
     * Gets the value of the supplyReadinessRate property.
     */
    public int getSupplyReadinessRate() {
        return supplyReadinessRate;
    }

    /**
     * Sets the value of the supplyReadinessRate property.
     */
    public void setSupplyReadinessRate(int value) {
        this.supplyReadinessRate = value;
    }

}
