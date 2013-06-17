
package ares.data.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Formation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Formation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Echelon" type="{ares}Echelon"/>
 *         &lt;element name="Parent" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Color" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Commander" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Details" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Proficiency" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Supply" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Orders" type="{ares}Orders"/>
 *         &lt;element name="Unit" type="{ares}Unit" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Formation", namespace = "ares", propOrder = {
    "name",
    "echelon",
    "parent",
    "color",
    "commander",
    "details",
    "proficiency",
    "supply",
    "orders",
    "unit"
})
public class Formation {

    @XmlElement(name = "Name", namespace = "ares", required = true)
    protected String name;
    @XmlElement(name = "Echelon", namespace = "ares", required = true)
    protected Echelon echelon;
    @XmlElement(name = "Parent", namespace = "ares")
    protected int parent;
    @XmlElement(name = "Color", namespace = "ares")
    protected int color;
    @XmlElement(name = "Commander", namespace = "ares", required = true)
    protected String commander;
    @XmlElement(name = "Details", namespace = "ares", required = true)
    protected String details;
    @XmlElement(name = "Proficiency", namespace = "ares", defaultValue = "33")
    protected int proficiency;
    @XmlElement(name = "Supply", namespace = "ares", defaultValue = "100")
    protected int supply;
    @XmlElement(name = "Orders", namespace = "ares", required = true)
    protected Orders orders;
    @XmlElement(name = "Unit", namespace = "ares", required = true)
    protected List<Unit> unit;
    @XmlAttribute(name = "id", required = true)
    protected int id;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the echelon property.
     * 
     * @return
     *     possible object is
     *     {@link Echelon }
     *     
     */
    public Echelon getEchelon() {
        return echelon;
    }

    /**
     * Sets the value of the echelon property.
     * 
     * @param value
     *     allowed object is
     *     {@link Echelon }
     *     
     */
    public void setEchelon(Echelon value) {
        this.echelon = value;
    }

    /**
     * Gets the value of the parent property.
     * 
     */
    public int getParent() {
        return parent;
    }

    /**
     * Sets the value of the parent property.
     * 
     */
    public void setParent(int value) {
        this.parent = value;
    }

    /**
     * Gets the value of the color property.
     * 
     */
    public int getColor() {
        return color;
    }

    /**
     * Sets the value of the color property.
     * 
     */
    public void setColor(int value) {
        this.color = value;
    }

    /**
     * Gets the value of the commander property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommander() {
        return commander;
    }

    /**
     * Sets the value of the commander property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommander(String value) {
        this.commander = value;
    }

    /**
     * Gets the value of the details property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets the value of the details property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetails(String value) {
        this.details = value;
    }

    /**
     * Gets the value of the proficiency property.
     * 
     */
    public int getProficiency() {
        return proficiency;
    }

    /**
     * Sets the value of the proficiency property.
     * 
     */
    public void setProficiency(int value) {
        this.proficiency = value;
    }

    /**
     * Gets the value of the supply property.
     * 
     */
    public int getSupply() {
        return supply;
    }

    /**
     * Sets the value of the supply property.
     * 
     */
    public void setSupply(int value) {
        this.supply = value;
    }

    /**
     * Gets the value of the orders property.
     * 
     * @return
     *     possible object is
     *     {@link Orders }
     *     
     */
    public Orders getOrders() {
        return orders;
    }

    /**
     * Sets the value of the orders property.
     * 
     * @param value
     *     allowed object is
     *     {@link Orders }
     *     
     */
    public void setOrders(Orders value) {
        this.orders = value;
    }

    /**
     * Gets the value of the unit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Unit }
     * 
     * 
     */
    public List<Unit> getUnit() {
        if (unit == null) {
            unit = new ArrayList<Unit>();
        }
        return this.unit;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

}
