
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Header complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Header">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ForceName" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="OutcomesWin" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="OutcomesLose" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="OutcomesDraw" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="fileType" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="firstPlayer" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Header", namespace = "ares", propOrder = {
    "name",
    "description",
    "forceName",
    "outcomesWin",
    "outcomesLose",
    "outcomesDraw"
})
public class Header {

    @XmlElement(name = "Name", namespace = "ares", required = true)
    protected String name;
    @XmlElement(name = "Description", namespace = "ares", required = true)
    protected String description;
    @XmlElement(name = "ForceName", namespace = "ares", required = true)
    protected List<String> forceName;
    @XmlElement(name = "OutcomesWin", namespace = "ares", required = true)
    protected List<String> outcomesWin;
    @XmlElement(name = "OutcomesLose", namespace = "ares", required = true)
    protected List<String> outcomesLose;
    @XmlElement(name = "OutcomesDraw", namespace = "ares", required = true)
    protected List<String> outcomesDraw;
    @XmlAttribute(name = "version")
    protected Integer version;
    @XmlAttribute(name = "fileType")
    protected Integer fileType;
    @XmlAttribute(name = "firstPlayer")
    protected Integer firstPlayer;

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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the forceName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the forceName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getForceName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getForceName() {
        if (forceName == null) {
            forceName = new ArrayList<String>();
        }
        return this.forceName;
    }

    /**
     * Gets the value of the outcomesWin property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outcomesWin property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutcomesWin().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutcomesWin() {
        if (outcomesWin == null) {
            outcomesWin = new ArrayList<String>();
        }
        return this.outcomesWin;
    }

    /**
     * Gets the value of the outcomesLose property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outcomesLose property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutcomesLose().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutcomesLose() {
        if (outcomesLose == null) {
            outcomesLose = new ArrayList<String>();
        }
        return this.outcomesLose;
    }

    /**
     * Gets the value of the outcomesDraw property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outcomesDraw property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutcomesDraw().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutcomesDraw() {
        if (outcomesDraw == null) {
            outcomesDraw = new ArrayList<String>();
        }
        return this.outcomesDraw;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVersion(Integer value) {
        this.version = value;
    }

    /**
     * Gets the value of the fileType property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFileType() {
        return fileType;
    }

    /**
     * Sets the value of the fileType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFileType(Integer value) {
        this.fileType = value;
    }

    /**
     * Gets the value of the firstPlayer property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Sets the value of the firstPlayer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFirstPlayer(Integer value) {
        this.firstPlayer = value;
    }

}
