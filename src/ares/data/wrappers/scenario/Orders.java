
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Orders complex unitType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="Orders">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OperationalStance" unitType="{ares}OperationalStance"/>
 *         &lt;element name="Activates" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Frontage" unitType="{ares}Frontage"/>
 *         &lt;element name="OnlyPO" unitType="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Emphasis" unitType="{ares}Emphasis"/>
 *         &lt;element name="Supportscope" unitType="{ares}SupportScope"/>
 *         &lt;element name="Track" unitType="{ares}Track" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Orders", namespace = "ares", propOrder = {
        "operationalStance",
        "activates",
        "frontage",
        "onlyPO",
        "emphasis",
        "supportscope",
        "track"
})
public class Orders {

    @XmlElement(name = "OperationalStance", namespace = "ares", required = true)
    protected OperationalStance operationalStance;
    @XmlElement(name = "Activates", namespace = "ares", defaultValue = "0")
    protected int activates;
    @XmlElement(name = "Frontage", namespace = "ares", required = true, defaultValue = "NORMAL")
    protected Frontage frontage;
    @XmlElement(name = "OnlyPO", namespace = "ares", defaultValue = "false")
    protected boolean onlyPO;
    @XmlElement(name = "Emphasis", namespace = "ares", required = true)
    protected Emphasis emphasis;
    @XmlElement(name = "Supportscope", namespace = "ares", required = true)
    protected SupportScope supportscope;
    @XmlElement(name = "Track", namespace = "ares", required = true)
    protected List<Track> track;

    /**
     * Gets the value of the operationalStance property.
     *
     * @return possible object is
     *         {@link OperationalStance }
     */
    public OperationalStance getOperationalStance() {
        return operationalStance;
    }

    /**
     * Sets the value of the operationalStance property.
     *
     * @param value allowed object is
     *              {@link OperationalStance }
     */
    public void setOperationalStance(OperationalStance value) {
        this.operationalStance = value;
    }

    /**
     * Gets the value of the activates property.
     */
    public int getActivates() {
        return activates;
    }

    /**
     * Sets the value of the activates property.
     */
    public void setActivates(int value) {
        this.activates = value;
    }

    /**
     * Gets the value of the frontage property.
     *
     * @return possible object is
     *         {@link Frontage }
     */
    public Frontage getFrontage() {
        return frontage;
    }

    /**
     * Sets the value of the frontage property.
     *
     * @param value allowed object is
     *              {@link Frontage }
     */
    public void setFrontage(Frontage value) {
        this.frontage = value;
    }

    /**
     * Gets the value of the onlyPO property.
     */
    public boolean isOnlyPO() {
        return onlyPO;
    }

    /**
     * Sets the value of the onlyPO property.
     */
    public void setOnlyPO(boolean value) {
        this.onlyPO = value;
    }

    /**
     * Gets the value of the emphasis property.
     *
     * @return possible object is
     *         {@link Emphasis }
     */
    public Emphasis getEmphasis() {
        return emphasis;
    }

    /**
     * Sets the value of the emphasis property.
     *
     * @param value allowed object is
     *              {@link Emphasis }
     */
    public void setEmphasis(Emphasis value) {
        this.emphasis = value;
    }

    /**
     * Gets the value of the supportscope property.
     *
     * @return possible object is
     *         {@link SupportScope }
     */
    public SupportScope getSupportscope() {
        return supportscope;
    }

    /**
     * Sets the value of the supportscope property.
     *
     * @param value allowed object is
     *              {@link SupportScope }
     */
    public void setSupportscope(SupportScope value) {
        this.supportscope = value;
    }

    /**
     * Gets the value of the track property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the track property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrack().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following unitType(s) are allowed in the list
     * {@link Track }
     */
    public List<Track> getTrack() {
        if (track == null) {
            track = new ArrayList<Track>();
        }
        return this.track;
    }

}
