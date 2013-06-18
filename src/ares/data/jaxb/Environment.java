
package ares.data.jaxb;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Environment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Environment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Scale" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Zone" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Boundary" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="Precipitation" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="Temperature" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="Visibility" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="DeltaT" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="Direction" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="OlddeltaT" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="Olddirection" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="NextClouds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Environment", namespace = "ares", propOrder = {
    "scale",
    "zone"
})
public class Environment {

    @XmlElement(name = "Scale", namespace = "ares")
    protected double scale;
    @XmlElement(name = "Zone", namespace = "ares", required = true)
    protected List<Environment.Zone> zone;

    /**
     * Gets the value of the scale property.
     * 
     */
    public double getScale() {
        return scale;
    }

    /**
     * Sets the value of the scale property.
     * 
     */
    public void setScale(double value) {
        this.scale = value;
    }

    /**
     * Gets the value of the zone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the zone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZone().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Environment.Zone }
     * 
     * 
     */
    public List<Environment.Zone> getZone() {
        if (zone == null) {
            zone = new ArrayList<Environment.Zone>();
        }
        return this.zone;
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
     *         &lt;element name="Boundary" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="Precipitation" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="Temperature" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="Visibility" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="DeltaT" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="Direction" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="OlddeltaT" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="Olddirection" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="NextClouds" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
        "boundary",
        "precipitation",
        "temperature",
        "visibility",
        "deltaT",
        "direction",
        "olddeltaT",
        "olddirection",
        "nextClouds"
    })
    public static class Zone {

        @XmlElement(name = "Boundary", namespace = "ares")
        protected int boundary;
        @XmlElement(name = "Precipitation", namespace = "ares")
        protected int precipitation;
        @XmlElement(name = "Temperature", namespace = "ares")
        protected int temperature;
        @XmlElement(name = "Visibility", namespace = "ares")
        protected int visibility;
        @XmlElement(name = "DeltaT", namespace = "ares")
        protected int deltaT;
        @XmlElement(name = "Direction", namespace = "ares")
        protected int direction;
        @XmlElement(name = "OlddeltaT", namespace = "ares")
        protected int olddeltaT;
        @XmlElement(name = "Olddirection", namespace = "ares")
        protected int olddirection;
        @XmlElement(name = "NextClouds", namespace = "ares")
        protected int nextClouds;
        @XmlAttribute(name = "id", required = true)
        protected int id;

        /**
         * Gets the value of the boundary property.
         * 
         */
        public int getBoundary() {
            return boundary;
        }

        /**
         * Sets the value of the boundary property.
         * 
         */
        public void setBoundary(int value) {
            this.boundary = value;
        }

        /**
         * Gets the value of the precipitation property.
         * 
         */
        public int getPrecipitation() {
            return precipitation;
        }

        /**
         * Sets the value of the precipitation property.
         * 
         */
        public void setPrecipitation(int value) {
            this.precipitation = value;
        }

        /**
         * Gets the value of the temperature property.
         * 
         */
        public int getTemperature() {
            return temperature;
        }

        /**
         * Sets the value of the temperature property.
         * 
         */
        public void setTemperature(int value) {
            this.temperature = value;
        }

        /**
         * Gets the value of the visibility property.
         * 
         */
        public int getVisibility() {
            return visibility;
        }

        /**
         * Sets the value of the visibility property.
         * 
         */
        public void setVisibility(int value) {
            this.visibility = value;
        }

        /**
         * Gets the value of the deltaT property.
         * 
         */
        public int getDeltaT() {
            return deltaT;
        }

        /**
         * Sets the value of the deltaT property.
         * 
         */
        public void setDeltaT(int value) {
            this.deltaT = value;
        }

        /**
         * Gets the value of the direction property.
         * 
         */
        public int getDirection() {
            return direction;
        }

        /**
         * Sets the value of the direction property.
         * 
         */
        public void setDirection(int value) {
            this.direction = value;
        }

        /**
         * Gets the value of the olddeltaT property.
         * 
         */
        public int getOlddeltaT() {
            return olddeltaT;
        }

        /**
         * Sets the value of the olddeltaT property.
         * 
         */
        public void setOlddeltaT(int value) {
            this.olddeltaT = value;
        }

        /**
         * Gets the value of the olddirection property.
         * 
         */
        public int getOlddirection() {
            return olddirection;
        }

        /**
         * Sets the value of the olddirection property.
         * 
         */
        public void setOlddirection(int value) {
            this.olddirection = value;
        }

        /**
         * Gets the value of the nextClouds property.
         * 
         */
        public int getNextClouds() {
            return nextClouds;
        }

        /**
         * Sets the value of the nextClouds property.
         * 
         */
        public void setNextClouds(int value) {
            this.nextClouds = value;
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
