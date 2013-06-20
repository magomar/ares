
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Cell complex unitType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="Cell">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Terrain" unitType="{ares}Terrain" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Feature" unitType="{ares}TerrainFeature" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Entrenchment" unitType="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Distance" unitType="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Owner" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="VP" unitType="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="x" unitType="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="y" unitType="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cell", namespace = "ares", propOrder = {
        "terrain",
        "feature",
        "entrenchment",
        "distance",
        "owner",
        "vp"
})
public class Cell {

    @XmlElement(name = "Terrain", namespace = "ares")
    protected List<Terrain> terrain;
    @XmlElement(name = "Feature", namespace = "ares")
    protected List<TerrainFeature> feature;
    @XmlElement(name = "Entrenchment", namespace = "ares")
    protected Integer entrenchment;
    @XmlElement(name = "Distance", namespace = "ares")
    protected Integer distance;
    @XmlElement(name = "Owner", namespace = "ares")
    protected int owner;
    @XmlElement(name = "VP", namespace = "ares")
    protected Integer vp;
    @XmlAttribute(name = "x")
    protected Integer x;
    @XmlAttribute(name = "y")
    protected Integer y;

    /**
     * Gets the value of the terrain property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the terrain property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTerrain().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following unitType(s) are allowed in the list
     * {@link Terrain }
     */
    public List<Terrain> getTerrain() {
        if (terrain == null) {
            terrain = new ArrayList<Terrain>();
        }
        return this.terrain;
    }

    /**
     * Gets the value of the feature property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the feature property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFeature().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following unitType(s) are allowed in the list
     * {@link TerrainFeature }
     */
    public List<TerrainFeature> getFeature() {
        if (feature == null) {
            feature = new ArrayList<TerrainFeature>();
        }
        return this.feature;
    }

    /**
     * Gets the value of the entrenchment property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getEntrenchment() {
        return entrenchment;
    }

    /**
     * Sets the value of the entrenchment property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setEntrenchment(Integer value) {
        this.entrenchment = value;
    }

    /**
     * Gets the value of the distance property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * Sets the value of the distance property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setDistance(Integer value) {
        this.distance = value;
    }

    /**
     * Gets the value of the owner property.
     */
    public int getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     */
    public void setOwner(int value) {
        this.owner = value;
    }

    /**
     * Gets the value of the vp property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getVP() {
        return vp;
    }

    /**
     * Sets the value of the vp property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setVP(Integer value) {
        this.vp = value;
    }

    /**
     * Gets the value of the x property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getX() {
        return x;
    }

    /**
     * Sets the value of the x property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setX(Integer value) {
        this.x = value;
    }

    /**
     * Gets the value of the y property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getY() {
        return y;
    }

    /**
     * Sets the value of the y property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setY(Integer value) {
        this.y = value;
    }

}
