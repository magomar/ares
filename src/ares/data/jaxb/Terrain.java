
package ares.data.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Terrain complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Terrain">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Type" type="{ares}TerrainType" />
 *       &lt;attribute name="Dir" type="{ares}MultiDirection" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Terrain", namespace = "ares")
public class Terrain {

    @XmlAttribute(name = "Type")
    protected TerrainType type;
    @XmlAttribute(name = "Dir")
    protected MultiDirection dir;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link TerrainType }
     *     
     */
    public TerrainType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link TerrainType }
     *     
     */
    public void setType(TerrainType value) {
        this.type = value;
    }

    /**
     * Gets the value of the dir property.
     * 
     * @return
     *     possible object is
     *     {@link MultiDirection }
     *     
     */
    public MultiDirection getDir() {
        return dir;
    }

    /**
     * Sets the value of the dir property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiDirection }
     *     
     */
    public void setDir(MultiDirection value) {
        this.dir = value;
    }

}
