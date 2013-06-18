
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Header" type="{ares}Header"/>
 *         &lt;element name="Calendar" type="{ares}Calendar"/>
 *         &lt;element name="Environment" type="{ares}Environment"/>
 *         &lt;element name="ForceVariables" type="{ares}ForceVariables"/>
 *         &lt;element name="Variables" type="{ares}Variables"/>
 *         &lt;element name="Map" type="{ares}Map"/>
 *         &lt;element name="OOB" type="{ares}OOB"/>
 *         &lt;element name="Events" type="{ares}Events"/>
 *         &lt;element name="Supplies" type="{ares}Supplies"/>
 *         &lt;element name="Replacements" type="{ares}Replacements"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "header",
        "calendar",
        "environment",
        "forceVariables",
        "variables",
        "map",
        "oob",
        "events",
        "supplies",
        "replacements"
})
@XmlRootElement(name = "Scenario", namespace = "ares")
public class Scenario {

    @XmlElement(name = "Header", namespace = "ares", required = true)
    protected Header header;
    @XmlElement(name = "Calendar", namespace = "ares", required = true)
    protected Calendar calendar;
    @XmlElement(name = "Environment", namespace = "ares", required = true)
    protected Environment environment;
    @XmlElement(name = "ForceVariables", namespace = "ares", required = true)
    protected ForceVariables forceVariables;
    @XmlElement(name = "Variables", namespace = "ares", required = true)
    protected Variables variables;
    @XmlElement(name = "Map", namespace = "ares", required = true)
    protected Map map;
    @XmlElement(name = "OOB", namespace = "ares", required = true)
    protected OOB oob;
    @XmlElement(name = "Events", namespace = "ares", required = true)
    protected Events events;
    @XmlElement(name = "Supplies", namespace = "ares", required = true)
    protected Supplies supplies;
    @XmlElement(name = "Replacements", namespace = "ares", required = true)
    protected Replacements replacements;

    /**
     * Gets the value of the header property.
     *
     * @return possible object is
     *         {@link Header }
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     *
     * @param value allowed object is
     *              {@link Header }
     */
    public void setHeader(Header value) {
        this.header = value;
    }

    /**
     * Gets the value of the calendar property.
     *
     * @return possible object is
     *         {@link Calendar }
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * Sets the value of the calendar property.
     *
     * @param value allowed object is
     *              {@link Calendar }
     */
    public void setCalendar(Calendar value) {
        this.calendar = value;
    }

    /**
     * Gets the value of the environment property.
     *
     * @return possible object is
     *         {@link Environment }
     */
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * Sets the value of the environment property.
     *
     * @param value allowed object is
     *              {@link Environment }
     */
    public void setEnvironment(Environment value) {
        this.environment = value;
    }

    /**
     * Gets the value of the forceVariables property.
     *
     * @return possible object is
     *         {@link ForceVariables }
     */
    public ForceVariables getForceVariables() {
        return forceVariables;
    }

    /**
     * Sets the value of the forceVariables property.
     *
     * @param value allowed object is
     *              {@link ForceVariables }
     */
    public void setForceVariables(ForceVariables value) {
        this.forceVariables = value;
    }

    /**
     * Gets the value of the variables property.
     *
     * @return possible object is
     *         {@link Variables }
     */
    public Variables getVariables() {
        return variables;
    }

    /**
     * Sets the value of the variables property.
     *
     * @param value allowed object is
     *              {@link Variables }
     */
    public void setVariables(Variables value) {
        this.variables = value;
    }

    /**
     * Gets the value of the map property.
     *
     * @return possible object is
     *         {@link Map }
     */
    public Map getMap() {
        return map;
    }

    /**
     * Sets the value of the map property.
     *
     * @param value allowed object is
     *              {@link Map }
     */
    public void setMap(Map value) {
        this.map = value;
    }

    /**
     * Gets the value of the oob property.
     *
     * @return possible object is
     *         {@link OOB }
     */
    public OOB getOOB() {
        return oob;
    }

    /**
     * Sets the value of the oob property.
     *
     * @param value allowed object is
     *              {@link OOB }
     */
    public void setOOB(OOB value) {
        this.oob = value;
    }

    /**
     * Gets the value of the events property.
     *
     * @return possible object is
     *         {@link Events }
     */
    public Events getEvents() {
        return events;
    }

    /**
     * Sets the value of the events property.
     *
     * @param value allowed object is
     *              {@link Events }
     */
    public void setEvents(Events value) {
        this.events = value;
    }

    /**
     * Gets the value of the supplies property.
     *
     * @return possible object is
     *         {@link Supplies }
     */
    public Supplies getSupplies() {
        return supplies;
    }

    /**
     * Sets the value of the supplies property.
     *
     * @param value allowed object is
     *              {@link Supplies }
     */
    public void setSupplies(Supplies value) {
        this.supplies = value;
    }

    /**
     * Gets the value of the replacements property.
     *
     * @return possible object is
     *         {@link Replacements }
     */
    public Replacements getReplacements() {
        return replacements;
    }

    /**
     * Sets the value of the replacements property.
     *
     * @param value allowed object is
     *              {@link Replacements }
     */
    public void setReplacements(Replacements value) {
        this.replacements = value;
    }

}
