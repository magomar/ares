
package ares.data.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Unit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Unit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Type" type="{ares}UnitType"/>
 *         &lt;element name="IconId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Color" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Size" type="{ares}Echelon"/>
 *         &lt;element name="Experience" type="{ares}Experience"/>
 *         &lt;element name="Proficiency" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Readiness" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Supply" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="X" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Y" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Emphasis" type="{ares}Emphasis"/>
 *         &lt;element name="Availability" type="{ares}Availability"/>
 *         &lt;element name="OpState" type="{ares}OpState"/>
 *         &lt;element name="ReplacementPriority" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Entry" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Parent" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Equipment" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="Max" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
@XmlType(name = "Unit", namespace = "ares", propOrder = {
    "name",
    "type",
    "iconId",
    "color",
    "size",
    "experience",
    "proficiency",
    "readiness",
    "supply",
    "x",
    "y",
    "emphasis",
    "availability",
    "opState",
    "replacementPriority",
    "entry",
    "parent",
    "equipment"
})
public class Unit {

    @XmlElement(name = "Name", namespace = "ares", required = true)
    protected String name;
    @XmlElement(name = "Type", namespace = "ares", required = true)
    protected UnitType type;
    @XmlElement(name = "IconId", namespace = "ares")
    protected int iconId;
    @XmlElement(name = "Color", namespace = "ares")
    protected int color;
    @XmlElement(name = "Size", namespace = "ares", required = true)
    protected Echelon size;
    @XmlElement(name = "Experience", namespace = "ares", required = true)
    protected Experience experience;
    @XmlElement(name = "Proficiency", namespace = "ares", defaultValue = "33")
    protected int proficiency;
    @XmlElement(name = "Readiness", namespace = "ares", defaultValue = "100")
    protected int readiness;
    @XmlElement(name = "Supply", namespace = "ares", defaultValue = "100")
    protected int supply;
    @XmlElement(name = "X", namespace = "ares")
    protected int x;
    @XmlElement(name = "Y", namespace = "ares")
    protected int y;
    @XmlElement(name = "Emphasis", namespace = "ares", required = true)
    protected Emphasis emphasis;
    @XmlElement(name = "Availability", namespace = "ares", required = true)
    protected Availability availability;
    @XmlElement(name = "OpState", namespace = "ares", required = true)
    protected OpState opState;
    @XmlElement(name = "ReplacementPriority", namespace = "ares")
    protected int replacementPriority;
    @XmlElement(name = "Entry", namespace = "ares")
    protected Integer entry;
    @XmlElement(name = "Parent", namespace = "ares")
    protected Integer parent;
    @XmlElement(name = "Equipment", namespace = "ares", required = true)
    protected List<Unit.Equipment> equipment;
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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link UnitType }
     *     
     */
    public UnitType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitType }
     *     
     */
    public void setType(UnitType value) {
        this.type = value;
    }

    /**
     * Gets the value of the iconId property.
     * 
     */
    public int getIconId() {
        return iconId;
    }

    /**
     * Sets the value of the iconId property.
     * 
     */
    public void setIconId(int value) {
        this.iconId = value;
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
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link Echelon }
     *     
     */
    public Echelon getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link Echelon }
     *     
     */
    public void setSize(Echelon value) {
        this.size = value;
    }

    /**
     * Gets the value of the experience property.
     * 
     * @return
     *     possible object is
     *     {@link Experience }
     *     
     */
    public Experience getExperience() {
        return experience;
    }

    /**
     * Sets the value of the experience property.
     * 
     * @param value
     *     allowed object is
     *     {@link Experience }
     *     
     */
    public void setExperience(Experience value) {
        this.experience = value;
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
     * Gets the value of the readiness property.
     * 
     */
    public int getReadiness() {
        return readiness;
    }

    /**
     * Sets the value of the readiness property.
     * 
     */
    public void setReadiness(int value) {
        this.readiness = value;
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
     * Gets the value of the x property.
     * 
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the value of the x property.
     * 
     */
    public void setX(int value) {
        this.x = value;
    }

    /**
     * Gets the value of the y property.
     * 
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the value of the y property.
     * 
     */
    public void setY(int value) {
        this.y = value;
    }

    /**
     * Gets the value of the emphasis property.
     * 
     * @return
     *     possible object is
     *     {@link Emphasis }
     *     
     */
    public Emphasis getEmphasis() {
        return emphasis;
    }

    /**
     * Sets the value of the emphasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link Emphasis }
     *     
     */
    public void setEmphasis(Emphasis value) {
        this.emphasis = value;
    }

    /**
     * Gets the value of the availability property.
     * 
     * @return
     *     possible object is
     *     {@link Availability }
     *     
     */
    public Availability getAvailability() {
        return availability;
    }

    /**
     * Sets the value of the availability property.
     * 
     * @param value
     *     allowed object is
     *     {@link Availability }
     *     
     */
    public void setAvailability(Availability value) {
        this.availability = value;
    }

    /**
     * Gets the value of the opState property.
     * 
     * @return
     *     possible object is
     *     {@link OpState }
     *     
     */
    public OpState getOpState() {
        return opState;
    }

    /**
     * Sets the value of the opState property.
     * 
     * @param value
     *     allowed object is
     *     {@link OpState }
     *     
     */
    public void setOpState(OpState value) {
        this.opState = value;
    }

    /**
     * Gets the value of the replacementPriority property.
     * 
     */
    public int getReplacementPriority() {
        return replacementPriority;
    }

    /**
     * Sets the value of the replacementPriority property.
     * 
     */
    public void setReplacementPriority(int value) {
        this.replacementPriority = value;
    }

    /**
     * Gets the value of the entry property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEntry() {
        return entry;
    }

    /**
     * Sets the value of the entry property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEntry(Integer value) {
        this.entry = value;
    }

    /**
     * Gets the value of the parent property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getParent() {
        return parent;
    }

    /**
     * Sets the value of the parent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setParent(Integer value) {
        this.parent = value;
    }

    /**
     * Gets the value of the equipment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the equipment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEquipment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Unit.Equipment }
     * 
     * 
     */
    public List<Unit.Equipment> getEquipment() {
        if (equipment == null) {
            equipment = new ArrayList<Unit.Equipment>();
        }
        return this.equipment;
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


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="Max" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    @XmlType(name = "", propOrder = {
        "name",
        "number",
        "max"
    })
    public static class Equipment {

        @XmlElement(name = "Name", namespace = "ares", required = true)
        protected String name;
        @XmlElement(name = "Number", namespace = "ares")
        protected int number;
        @XmlElement(name = "Max", namespace = "ares")
        protected int max;
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
         * Gets the value of the number property.
         * 
         */
        public int getNumber() {
            return number;
        }

        /**
         * Sets the value of the number property.
         * 
         */
        public void setNumber(int value) {
            this.number = value;
        }

        /**
         * Gets the value of the max property.
         * 
         */
        public int getMax() {
            return max;
        }

        /**
         * Sets the value of the max property.
         * 
         */
        public void setMax(int value) {
            this.max = value;
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

}
