
package ares.data.jaxb.graphics;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="NumProfiles" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TilesRows" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TilesColumns" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="UnitsRows" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="UnitsColumns" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FlagsRows" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FlagsColumns" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TileRise" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Profile" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TilesWidth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="TilesHeight" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="TileWidth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="TileHeight" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="TileSide" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="TileOffset" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="UnitsWidth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="UnitsHeight" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="UnitWidth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="UnitHeight" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="UnitOffset" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="UnitStackOffset" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="UnitsMaxStack" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="FlagsWidth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="FlagsHeight" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="UnitFontSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="UnitLedLength" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="UnitBarLength" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="ArrowFontSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
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
@XmlType(name = "", propOrder = {
    "numProfiles",
    "tilesRows",
    "tilesColumns",
    "unitsRows",
    "unitsColumns",
    "flagsRows",
    "flagsColumns",
    "tileRise",
    "profile"
})
@XmlRootElement(name = "Graphics", namespace = "graphics")
public class Graphics {

    @XmlElement(name = "NumProfiles", namespace = "graphics")
    protected int numProfiles;
    @XmlElement(name = "TilesRows", namespace = "graphics")
    protected int tilesRows;
    @XmlElement(name = "TilesColumns", namespace = "graphics")
    protected int tilesColumns;
    @XmlElement(name = "UnitsRows", namespace = "graphics")
    protected int unitsRows;
    @XmlElement(name = "UnitsColumns", namespace = "graphics")
    protected int unitsColumns;
    @XmlElement(name = "FlagsRows", namespace = "graphics")
    protected int flagsRows;
    @XmlElement(name = "FlagsColumns", namespace = "graphics")
    protected int flagsColumns;
    @XmlElement(name = "TileRise", namespace = "graphics")
    protected double tileRise;
    @XmlElement(name = "Profile", namespace = "graphics", required = true)
    protected List<Graphics.Profile> profile;

    /**
     * Gets the value of the numProfiles property.
     * 
     */
    public int getNumProfiles() {
        return numProfiles;
    }

    /**
     * Sets the value of the numProfiles property.
     * 
     */
    public void setNumProfiles(int value) {
        this.numProfiles = value;
    }

    /**
     * Gets the value of the tilesRows property.
     * 
     */
    public int getTilesRows() {
        return tilesRows;
    }

    /**
     * Sets the value of the tilesRows property.
     * 
     */
    public void setTilesRows(int value) {
        this.tilesRows = value;
    }

    /**
     * Gets the value of the tilesColumns property.
     * 
     */
    public int getTilesColumns() {
        return tilesColumns;
    }

    /**
     * Sets the value of the tilesColumns property.
     * 
     */
    public void setTilesColumns(int value) {
        this.tilesColumns = value;
    }

    /**
     * Gets the value of the unitsRows property.
     * 
     */
    public int getUnitsRows() {
        return unitsRows;
    }

    /**
     * Sets the value of the unitsRows property.
     * 
     */
    public void setUnitsRows(int value) {
        this.unitsRows = value;
    }

    /**
     * Gets the value of the unitsColumns property.
     * 
     */
    public int getUnitsColumns() {
        return unitsColumns;
    }

    /**
     * Sets the value of the unitsColumns property.
     * 
     */
    public void setUnitsColumns(int value) {
        this.unitsColumns = value;
    }

    /**
     * Gets the value of the flagsRows property.
     * 
     */
    public int getFlagsRows() {
        return flagsRows;
    }

    /**
     * Sets the value of the flagsRows property.
     * 
     */
    public void setFlagsRows(int value) {
        this.flagsRows = value;
    }

    /**
     * Gets the value of the flagsColumns property.
     * 
     */
    public int getFlagsColumns() {
        return flagsColumns;
    }

    /**
     * Sets the value of the flagsColumns property.
     * 
     */
    public void setFlagsColumns(int value) {
        this.flagsColumns = value;
    }

    /**
     * Gets the value of the tileRise property.
     * 
     */
    public double getTileRise() {
        return tileRise;
    }

    /**
     * Sets the value of the tileRise property.
     * 
     */
    public void setTileRise(double value) {
        this.tileRise = value;
    }

    /**
     * Gets the value of the profile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the profile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProfile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Graphics.Profile }
     * 
     * 
     */
    public List<Graphics.Profile> getProfile() {
        if (profile == null) {
            profile = new ArrayList<Graphics.Profile>();
        }
        return this.profile;
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
     *         &lt;element name="TilesWidth" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="TilesHeight" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="TileWidth" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="TileHeight" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="TileSide" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="TileOffset" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="UnitsWidth" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="UnitsHeight" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="UnitWidth" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="UnitHeight" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="UnitOffset" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="UnitStackOffset" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="UnitsMaxStack" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="FlagsWidth" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="FlagsHeight" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="UnitFontSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="UnitLedLength" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="UnitBarLength" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="ArrowFontSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *       &lt;/sequence>
     *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "tilesWidth",
        "tilesHeight",
        "tileWidth",
        "tileHeight",
        "tileSide",
        "tileOffset",
        "unitsWidth",
        "unitsHeight",
        "unitWidth",
        "unitHeight",
        "unitOffset",
        "unitStackOffset",
        "unitsMaxStack",
        "flagsWidth",
        "flagsHeight",
        "unitFontSize",
        "unitLedLength",
        "unitBarLength",
        "arrowFontSize"
    })
    public static class Profile {

        @XmlElement(name = "TilesWidth", namespace = "graphics")
        protected int tilesWidth;
        @XmlElement(name = "TilesHeight", namespace = "graphics")
        protected int tilesHeight;
        @XmlElement(name = "TileWidth", namespace = "graphics")
        protected int tileWidth;
        @XmlElement(name = "TileHeight", namespace = "graphics")
        protected int tileHeight;
        @XmlElement(name = "TileSide", namespace = "graphics")
        protected int tileSide;
        @XmlElement(name = "TileOffset", namespace = "graphics")
        protected int tileOffset;
        @XmlElement(name = "UnitsWidth", namespace = "graphics")
        protected int unitsWidth;
        @XmlElement(name = "UnitsHeight", namespace = "graphics")
        protected int unitsHeight;
        @XmlElement(name = "UnitWidth", namespace = "graphics")
        protected int unitWidth;
        @XmlElement(name = "UnitHeight", namespace = "graphics")
        protected int unitHeight;
        @XmlElement(name = "UnitOffset", namespace = "graphics")
        protected int unitOffset;
        @XmlElement(name = "UnitStackOffset", namespace = "graphics")
        protected int unitStackOffset;
        @XmlElement(name = "UnitsMaxStack", namespace = "graphics")
        protected int unitsMaxStack;
        @XmlElement(name = "FlagsWidth", namespace = "graphics")
        protected int flagsWidth;
        @XmlElement(name = "FlagsHeight", namespace = "graphics")
        protected int flagsHeight;
        @XmlElement(name = "UnitFontSize", namespace = "graphics")
        protected int unitFontSize;
        @XmlElement(name = "UnitLedLength", namespace = "graphics")
        protected int unitLedLength;
        @XmlElement(name = "UnitBarLength", namespace = "graphics")
        protected int unitBarLength;
        @XmlElement(name = "ArrowFontSize", namespace = "graphics")
        protected int arrowFontSize;
        @XmlAttribute(name = "id")
        protected Integer id;

        /**
         * Gets the value of the tilesWidth property.
         * 
         */
        public int getTilesWidth() {
            return tilesWidth;
        }

        /**
         * Sets the value of the tilesWidth property.
         * 
         */
        public void setTilesWidth(int value) {
            this.tilesWidth = value;
        }

        /**
         * Gets the value of the tilesHeight property.
         * 
         */
        public int getTilesHeight() {
            return tilesHeight;
        }

        /**
         * Sets the value of the tilesHeight property.
         * 
         */
        public void setTilesHeight(int value) {
            this.tilesHeight = value;
        }

        /**
         * Gets the value of the tileWidth property.
         * 
         */
        public int getTileWidth() {
            return tileWidth;
        }

        /**
         * Sets the value of the tileWidth property.
         * 
         */
        public void setTileWidth(int value) {
            this.tileWidth = value;
        }

        /**
         * Gets the value of the tileHeight property.
         * 
         */
        public int getTileHeight() {
            return tileHeight;
        }

        /**
         * Sets the value of the tileHeight property.
         * 
         */
        public void setTileHeight(int value) {
            this.tileHeight = value;
        }

        /**
         * Gets the value of the tileSide property.
         * 
         */
        public int getTileSide() {
            return tileSide;
        }

        /**
         * Sets the value of the tileSide property.
         * 
         */
        public void setTileSide(int value) {
            this.tileSide = value;
        }

        /**
         * Gets the value of the tileOffset property.
         * 
         */
        public int getTileOffset() {
            return tileOffset;
        }

        /**
         * Sets the value of the tileOffset property.
         * 
         */
        public void setTileOffset(int value) {
            this.tileOffset = value;
        }

        /**
         * Gets the value of the unitsWidth property.
         * 
         */
        public int getUnitsWidth() {
            return unitsWidth;
        }

        /**
         * Sets the value of the unitsWidth property.
         * 
         */
        public void setUnitsWidth(int value) {
            this.unitsWidth = value;
        }

        /**
         * Gets the value of the unitsHeight property.
         * 
         */
        public int getUnitsHeight() {
            return unitsHeight;
        }

        /**
         * Sets the value of the unitsHeight property.
         * 
         */
        public void setUnitsHeight(int value) {
            this.unitsHeight = value;
        }

        /**
         * Gets the value of the unitWidth property.
         * 
         */
        public int getUnitWidth() {
            return unitWidth;
        }

        /**
         * Sets the value of the unitWidth property.
         * 
         */
        public void setUnitWidth(int value) {
            this.unitWidth = value;
        }

        /**
         * Gets the value of the unitHeight property.
         * 
         */
        public int getUnitHeight() {
            return unitHeight;
        }

        /**
         * Sets the value of the unitHeight property.
         * 
         */
        public void setUnitHeight(int value) {
            this.unitHeight = value;
        }

        /**
         * Gets the value of the unitOffset property.
         * 
         */
        public int getUnitOffset() {
            return unitOffset;
        }

        /**
         * Sets the value of the unitOffset property.
         * 
         */
        public void setUnitOffset(int value) {
            this.unitOffset = value;
        }

        /**
         * Gets the value of the unitStackOffset property.
         * 
         */
        public int getUnitStackOffset() {
            return unitStackOffset;
        }

        /**
         * Sets the value of the unitStackOffset property.
         * 
         */
        public void setUnitStackOffset(int value) {
            this.unitStackOffset = value;
        }

        /**
         * Gets the value of the unitsMaxStack property.
         * 
         */
        public int getUnitsMaxStack() {
            return unitsMaxStack;
        }

        /**
         * Sets the value of the unitsMaxStack property.
         * 
         */
        public void setUnitsMaxStack(int value) {
            this.unitsMaxStack = value;
        }

        /**
         * Gets the value of the flagsWidth property.
         * 
         */
        public int getFlagsWidth() {
            return flagsWidth;
        }

        /**
         * Sets the value of the flagsWidth property.
         * 
         */
        public void setFlagsWidth(int value) {
            this.flagsWidth = value;
        }

        /**
         * Gets the value of the flagsHeight property.
         * 
         */
        public int getFlagsHeight() {
            return flagsHeight;
        }

        /**
         * Sets the value of the flagsHeight property.
         * 
         */
        public void setFlagsHeight(int value) {
            this.flagsHeight = value;
        }

        /**
         * Gets the value of the unitFontSize property.
         * 
         */
        public int getUnitFontSize() {
            return unitFontSize;
        }

        /**
         * Sets the value of the unitFontSize property.
         * 
         */
        public void setUnitFontSize(int value) {
            this.unitFontSize = value;
        }

        /**
         * Gets the value of the unitLedLength property.
         * 
         */
        public int getUnitLedLength() {
            return unitLedLength;
        }

        /**
         * Sets the value of the unitLedLength property.
         * 
         */
        public void setUnitLedLength(int value) {
            this.unitLedLength = value;
        }

        /**
         * Gets the value of the unitBarLength property.
         * 
         */
        public int getUnitBarLength() {
            return unitBarLength;
        }

        /**
         * Sets the value of the unitBarLength property.
         * 
         */
        public void setUnitBarLength(int value) {
            this.unitBarLength = value;
        }

        /**
         * Gets the value of the arrowFontSize property.
         * 
         */
        public int getArrowFontSize() {
            return arrowFontSize;
        }

        /**
         * Sets the value of the arrowFontSize property.
         * 
         */
        public void setArrowFontSize(int value) {
            this.arrowFontSize = value;
        }

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setId(Integer value) {
            this.id = value;
        }

    }

}
