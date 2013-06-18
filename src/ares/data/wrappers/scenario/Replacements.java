
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Replacements complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Replacements">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Force" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Node" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Available" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="Rate" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="Start" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="End" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="Inventory" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
@XmlType(name = "Replacements", namespace = "ares", propOrder = {
    "force"
})
public class Replacements {

    @XmlElement(name = "Force", namespace = "ares", required = true)
    protected List<Replacements.Force> force;

    /**
     * Gets the value of the force property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the force property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getForce().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Replacements.Force }
     * 
     * 
     */
    public List<Replacements.Force> getForce() {
        if (force == null) {
            force = new ArrayList<Replacements.Force>();
        }
        return this.force;
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
     *         &lt;element name="Node" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Available" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="Rate" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="Start" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="End" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="Inventory" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    @XmlType(name = "", propOrder = {
        "node"
    })
    public static class Force {

        @XmlElement(name = "Node", namespace = "ares", required = true)
        protected List<Replacements.Force.Node> node;
        @XmlAttribute(name = "id", required = true)
        protected int id;

        /**
         * Gets the value of the node property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the node property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getNode().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Replacements.Force.Node }
         * 
         * 
         */
        public List<Replacements.Force.Node> getNode() {
            if (node == null) {
                node = new ArrayList<Replacements.Force.Node>();
            }
            return this.node;
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
         *         &lt;element name="Available" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="Rate" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="Start" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="End" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="Inventory" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
            "available",
            "rate",
            "start",
            "end",
            "inventory"
        })
        public static class Node {

            @XmlElement(name = "Available", namespace = "ares")
            protected int available;
            @XmlElement(name = "Rate", namespace = "ares")
            protected int rate;
            @XmlElement(name = "Start", namespace = "ares")
            protected int start;
            @XmlElement(name = "End", namespace = "ares")
            protected int end;
            @XmlElement(name = "Inventory", namespace = "ares")
            protected int inventory;
            @XmlAttribute(name = "id", required = true)
            protected int id;

            /**
             * Gets the value of the available property.
             * 
             */
            public int getAvailable() {
                return available;
            }

            /**
             * Sets the value of the available property.
             * 
             */
            public void setAvailable(int value) {
                this.available = value;
            }

            /**
             * Gets the value of the rate property.
             * 
             */
            public int getRate() {
                return rate;
            }

            /**
             * Sets the value of the rate property.
             * 
             */
            public void setRate(int value) {
                this.rate = value;
            }

            /**
             * Gets the value of the start property.
             * 
             */
            public int getStart() {
                return start;
            }

            /**
             * Sets the value of the start property.
             * 
             */
            public void setStart(int value) {
                this.start = value;
            }

            /**
             * Gets the value of the end property.
             * 
             */
            public int getEnd() {
                return end;
            }

            /**
             * Sets the value of the end property.
             * 
             */
            public void setEnd(int value) {
                this.end = value;
            }

            /**
             * Gets the value of the inventory property.
             * 
             */
            public int getInventory() {
                return inventory;
            }

            /**
             * Sets the value of the inventory property.
             * 
             */
            public void setInventory(int value) {
                this.inventory = value;
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

}
