
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Track complex unitType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="Track">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Objective" unitType="{ares}Objective" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" unitType="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Track", namespace = "ares", propOrder = {
        "objective"
})
public class Track {

    @XmlElement(name = "Objective", namespace = "ares")
    protected List<Objective> objective;
    @XmlAttribute(name = "id", required = true)
    protected int id;

    /**
     * Gets the value of the objective property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the objective property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjective().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following unitType(s) are allowed in the list
     * {@link Objective }
     */
    public List<Objective> getObjective() {
        if (objective == null) {
            objective = new ArrayList<Objective>();
        }
        return this.objective;
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
