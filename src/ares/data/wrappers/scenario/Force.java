
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Force complex unitType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="Force">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" unitType="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Proficiency" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Supply" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Flag" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Formation" unitType="{ares}Formation" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" unitType="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Force", namespace = "ares", propOrder = {
        "name",
        "proficiency",
        "supply",
        "flag",
        "formation"
})
public class Force {

    @XmlElement(name = "Name", namespace = "ares", required = true)
    protected String name;
    @XmlElement(name = "Proficiency", namespace = "ares", defaultValue = "33")
    protected int proficiency;
    @XmlElement(name = "Supply", namespace = "ares", defaultValue = "100")
    protected int supply;
    @XmlElement(name = "Flag", namespace = "ares")
    protected int flag;
    @XmlElement(name = "Formation", namespace = "ares", required = true)
    protected List<Formation> formation;
    @XmlAttribute(name = "id", required = true)
    protected int id;

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the proficiency property.
     */
    public int getProficiency() {
        return proficiency;
    }

    /**
     * Sets the value of the proficiency property.
     */
    public void setProficiency(int value) {
        this.proficiency = value;
    }

    /**
     * Gets the value of the supply property.
     */
    public int getSupply() {
        return supply;
    }

    /**
     * Sets the value of the supply property.
     */
    public void setSupply(int value) {
        this.supply = value;
    }

    /**
     * Gets the value of the flag property.
     */
    public int getFlag() {
        return flag;
    }

    /**
     * Sets the value of the flag property.
     */
    public void setFlag(int value) {
        this.flag = value;
    }

    /**
     * Gets the value of the formation property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formation property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormation().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following unitType(s) are allowed in the list
     * {@link Formation }
     */
    public List<Formation> getFormation() {
        if (formation == null) {
            formation = new ArrayList<Formation>();
        }
        return this.formation;
    }

    /**
     * Gets the value of the id property.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     */
    public void setId(int value) {
        this.id = value;
    }

}
