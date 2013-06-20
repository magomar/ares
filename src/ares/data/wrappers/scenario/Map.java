
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Map complex unitType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="Map">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MaxX" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MaxY" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Cell" unitType="{ares}Cell" maxOccurs="unbounded"/>
 *         &lt;element name="Place" unitType="{ares}Place" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" unitType="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Map", namespace = "ares", propOrder = {
        "maxX",
        "maxY",
        "cell",
        "place"
})
public class Map {

    @XmlElement(name = "MaxX", namespace = "ares")
    protected int maxX;
    @XmlElement(name = "MaxY", namespace = "ares")
    protected int maxY;
    @XmlElement(name = "Cell", namespace = "ares", required = true)
    protected List<Cell> cell;
    @XmlElement(name = "Place", namespace = "ares")
    protected List<Place> place;
    @XmlAttribute(name = "version")
    protected String version;

    /**
     * Gets the value of the maxX property.
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * Sets the value of the maxX property.
     */
    public void setMaxX(int value) {
        this.maxX = value;
    }

    /**
     * Gets the value of the maxY property.
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Sets the value of the maxY property.
     */
    public void setMaxY(int value) {
        this.maxY = value;
    }

    /**
     * Gets the value of the cell property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cell property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCell().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following unitType(s) are allowed in the list
     * {@link Cell }
     */
    public List<Cell> getCell() {
        if (cell == null) {
            cell = new ArrayList<Cell>();
        }
        return this.cell;
    }

    /**
     * Gets the value of the place property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the place property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlace().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following unitType(s) are allowed in the list
     * {@link Place }
     */
    public List<Place> getPlace() {
        if (place == null) {
            place = new ArrayList<Place>();
        }
        return this.place;
    }

    /**
     * Gets the value of the version property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
