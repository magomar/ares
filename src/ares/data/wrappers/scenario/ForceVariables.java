
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ForceVariables complex unitType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ForceVariables">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Force" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="microUnitIcon" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="iconTints" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="chemicalsUsed" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="chemicalsAvailable" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="nukesUsed" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="nukesAvailableInitial" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="nukesAvailableCurrent" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="globalRecce" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="globalGuerillas" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="globalRailcapInitial" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="globalRailcapCurrent" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="globalRailcapLast" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="globalAircapInitial" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="globalAircapCurrent" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="globalAircapLast" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="globalSeacapInitial" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="globalSeacapCurrent" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="globalSeacapLast" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="newReinforcements" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="interdiction" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="victoryModifications" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="globalHandicap" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="globalRailRepair" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="globalRailDestruction" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="reconstitutionPointX" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="reconstitutionPointY" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="reconstitutionPointValue" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="roadSupplyRadius" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="globalAirHandicap" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="forceNBCReadiness" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="forcePGWMultiplier" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="forceElectronicSupport" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="forceAirRefuel" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="forceNightProficiency" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="externalPOBias" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="currentTrack" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="forceMoveBias" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="forcePestilence" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="forceCommunication" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="forceLossIntolerance" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="ZOCCost" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="recentAirLosses" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="id" use="required" unitType="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ForceVariables", namespace = "ares", propOrder = {
        "force"
})
public class ForceVariables {

    @XmlElement(name = "Force", namespace = "ares", required = true)
    protected List<ForceVariables.Force> force;

    /**
     * Gets the value of the force property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the force property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getForce().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following unitType(s) are allowed in the list
     * {@link ForceVariables.Force }
     */
    public List<ForceVariables.Force> getForce() {
        if (force == null) {
            force = new ArrayList<ForceVariables.Force>();
        }
        return this.force;
    }


    /**
     * <p>Java class for anonymous complex unitType.
     * <p/>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p/>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="microUnitIcon" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="iconTints" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="chemicalsUsed" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="chemicalsAvailable" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="nukesUsed" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="nukesAvailableInitial" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="nukesAvailableCurrent" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="globalRecce" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="globalGuerillas" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="globalRailcapInitial" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="globalRailcapCurrent" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="globalRailcapLast" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="globalAircapInitial" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="globalAircapCurrent" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="globalAircapLast" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="globalSeacapInitial" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="globalSeacapCurrent" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="globalSeacapLast" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="newReinforcements" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="interdiction" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="victoryModifications" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="globalHandicap" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="globalRailRepair" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="globalRailDestruction" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="reconstitutionPointX" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="reconstitutionPointY" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="reconstitutionPointValue" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="roadSupplyRadius" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="globalAirHandicap" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="forceNBCReadiness" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="forcePGWMultiplier" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="forceElectronicSupport" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="forceAirRefuel" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="forceNightProficiency" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="externalPOBias" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="currentTrack" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="forceMoveBias" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="forcePestilence" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="forceCommunication" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="forceLossIntolerance" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="ZOCCost" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="recentAirLosses" unitType="{http://www.w3.org/2001/XMLSchema}int"/>
     *       &lt;/sequence>
     *       &lt;attribute name="id" use="required" unitType="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "microUnitIcon",
            "iconTints",
            "chemicalsUsed",
            "chemicalsAvailable",
            "nukesUsed",
            "nukesAvailableInitial",
            "nukesAvailableCurrent",
            "globalRecce",
            "globalGuerillas",
            "globalRailcapInitial",
            "globalRailcapCurrent",
            "globalRailcapLast",
            "globalAircapInitial",
            "globalAircapCurrent",
            "globalAircapLast",
            "globalSeacapInitial",
            "globalSeacapCurrent",
            "globalSeacapLast",
            "newReinforcements",
            "interdiction",
            "victoryModifications",
            "globalHandicap",
            "globalRailRepair",
            "globalRailDestruction",
            "reconstitutionPointX",
            "reconstitutionPointY",
            "reconstitutionPointValue",
            "roadSupplyRadius",
            "globalAirHandicap",
            "forceNBCReadiness",
            "forcePGWMultiplier",
            "forceElectronicSupport",
            "forceAirRefuel",
            "forceNightProficiency",
            "externalPOBias",
            "currentTrack",
            "forceMoveBias",
            "forcePestilence",
            "forceCommunication",
            "forceLossIntolerance",
            "zocCost",
            "recentAirLosses"
    })
    public static class Force {

        @XmlElement(namespace = "ares")
        protected int microUnitIcon;
        @XmlElement(namespace = "ares")
        protected int iconTints;
        @XmlElement(namespace = "ares")
        protected int chemicalsUsed;
        @XmlElement(namespace = "ares")
        protected int chemicalsAvailable;
        @XmlElement(namespace = "ares")
        protected int nukesUsed;
        @XmlElement(namespace = "ares")
        protected int nukesAvailableInitial;
        @XmlElement(namespace = "ares")
        protected int nukesAvailableCurrent;
        @XmlElement(namespace = "ares")
        protected int globalRecce;
        @XmlElement(namespace = "ares")
        protected int globalGuerillas;
        @XmlElement(namespace = "ares")
        protected int globalRailcapInitial;
        @XmlElement(namespace = "ares")
        protected int globalRailcapCurrent;
        @XmlElement(namespace = "ares")
        protected int globalRailcapLast;
        @XmlElement(namespace = "ares")
        protected int globalAircapInitial;
        @XmlElement(namespace = "ares")
        protected int globalAircapCurrent;
        @XmlElement(namespace = "ares")
        protected int globalAircapLast;
        @XmlElement(namespace = "ares")
        protected int globalSeacapInitial;
        @XmlElement(namespace = "ares")
        protected int globalSeacapCurrent;
        @XmlElement(namespace = "ares")
        protected int globalSeacapLast;
        @XmlElement(namespace = "ares")
        protected int newReinforcements;
        @XmlElement(namespace = "ares")
        protected int interdiction;
        @XmlElement(namespace = "ares")
        protected int victoryModifications;
        @XmlElement(namespace = "ares", defaultValue = "100")
        protected int globalHandicap;
        @XmlElement(namespace = "ares", defaultValue = "1")
        protected int globalRailRepair;
        @XmlElement(namespace = "ares", defaultValue = "100")
        protected int globalRailDestruction;
        @XmlElement(namespace = "ares", defaultValue = "999")
        protected int reconstitutionPointX;
        @XmlElement(namespace = "ares", defaultValue = "999")
        protected int reconstitutionPointY;
        @XmlElement(namespace = "ares")
        protected int reconstitutionPointValue;
        @XmlElement(namespace = "ares", defaultValue = "4")
        protected int roadSupplyRadius;
        @XmlElement(namespace = "ares", defaultValue = "100")
        protected int globalAirHandicap;
        @XmlElement(namespace = "ares", defaultValue = "50")
        protected int forceNBCReadiness;
        @XmlElement(namespace = "ares", defaultValue = "20")
        protected int forcePGWMultiplier;
        @XmlElement(namespace = "ares", defaultValue = "33")
        protected int forceElectronicSupport;
        @XmlElement(namespace = "ares")
        protected int forceAirRefuel;
        @XmlElement(namespace = "ares", defaultValue = "33")
        protected int forceNightProficiency;
        @XmlElement(namespace = "ares", defaultValue = "2")
        protected int externalPOBias;
        @XmlElement(namespace = "ares")
        protected int currentTrack;
        @XmlElement(namespace = "ares", defaultValue = "100")
        protected int forceMoveBias;
        @XmlElement(namespace = "ares")
        protected int forcePestilence;
        @XmlElement(namespace = "ares", defaultValue = "100")
        protected int forceCommunication;
        @XmlElement(namespace = "ares", defaultValue = "100")
        protected int forceLossIntolerance;
        @XmlElement(name = "ZOCCost", namespace = "ares", defaultValue = "100")
        protected int zocCost;
        @XmlElement(namespace = "ares")
        protected int recentAirLosses;
        @XmlAttribute(name = "id", required = true)
        protected int id;

        /**
         * Gets the value of the microUnitIcon property.
         */
        public int getMicroUnitIcon() {
            return microUnitIcon;
        }

        /**
         * Sets the value of the microUnitIcon property.
         */
        public void setMicroUnitIcon(int value) {
            this.microUnitIcon = value;
        }

        /**
         * Gets the value of the iconTints property.
         */
        public int getIconTints() {
            return iconTints;
        }

        /**
         * Sets the value of the iconTints property.
         */
        public void setIconTints(int value) {
            this.iconTints = value;
        }

        /**
         * Gets the value of the chemicalsUsed property.
         */
        public int getChemicalsUsed() {
            return chemicalsUsed;
        }

        /**
         * Sets the value of the chemicalsUsed property.
         */
        public void setChemicalsUsed(int value) {
            this.chemicalsUsed = value;
        }

        /**
         * Gets the value of the chemicalsAvailable property.
         */
        public int getChemicalsAvailable() {
            return chemicalsAvailable;
        }

        /**
         * Sets the value of the chemicalsAvailable property.
         */
        public void setChemicalsAvailable(int value) {
            this.chemicalsAvailable = value;
        }

        /**
         * Gets the value of the nukesUsed property.
         */
        public int getNukesUsed() {
            return nukesUsed;
        }

        /**
         * Sets the value of the nukesUsed property.
         */
        public void setNukesUsed(int value) {
            this.nukesUsed = value;
        }

        /**
         * Gets the value of the nukesAvailableInitial property.
         */
        public int getNukesAvailableInitial() {
            return nukesAvailableInitial;
        }

        /**
         * Sets the value of the nukesAvailableInitial property.
         */
        public void setNukesAvailableInitial(int value) {
            this.nukesAvailableInitial = value;
        }

        /**
         * Gets the value of the nukesAvailableCurrent property.
         */
        public int getNukesAvailableCurrent() {
            return nukesAvailableCurrent;
        }

        /**
         * Sets the value of the nukesAvailableCurrent property.
         */
        public void setNukesAvailableCurrent(int value) {
            this.nukesAvailableCurrent = value;
        }

        /**
         * Gets the value of the globalRecce property.
         */
        public int getGlobalRecce() {
            return globalRecce;
        }

        /**
         * Sets the value of the globalRecce property.
         */
        public void setGlobalRecce(int value) {
            this.globalRecce = value;
        }

        /**
         * Gets the value of the globalGuerillas property.
         */
        public int getGlobalGuerillas() {
            return globalGuerillas;
        }

        /**
         * Sets the value of the globalGuerillas property.
         */
        public void setGlobalGuerillas(int value) {
            this.globalGuerillas = value;
        }

        /**
         * Gets the value of the globalRailcapInitial property.
         */
        public int getGlobalRailcapInitial() {
            return globalRailcapInitial;
        }

        /**
         * Sets the value of the globalRailcapInitial property.
         */
        public void setGlobalRailcapInitial(int value) {
            this.globalRailcapInitial = value;
        }

        /**
         * Gets the value of the globalRailcapCurrent property.
         */
        public int getGlobalRailcapCurrent() {
            return globalRailcapCurrent;
        }

        /**
         * Sets the value of the globalRailcapCurrent property.
         */
        public void setGlobalRailcapCurrent(int value) {
            this.globalRailcapCurrent = value;
        }

        /**
         * Gets the value of the globalRailcapLast property.
         */
        public int getGlobalRailcapLast() {
            return globalRailcapLast;
        }

        /**
         * Sets the value of the globalRailcapLast property.
         */
        public void setGlobalRailcapLast(int value) {
            this.globalRailcapLast = value;
        }

        /**
         * Gets the value of the globalAircapInitial property.
         */
        public int getGlobalAircapInitial() {
            return globalAircapInitial;
        }

        /**
         * Sets the value of the globalAircapInitial property.
         */
        public void setGlobalAircapInitial(int value) {
            this.globalAircapInitial = value;
        }

        /**
         * Gets the value of the globalAircapCurrent property.
         */
        public int getGlobalAircapCurrent() {
            return globalAircapCurrent;
        }

        /**
         * Sets the value of the globalAircapCurrent property.
         */
        public void setGlobalAircapCurrent(int value) {
            this.globalAircapCurrent = value;
        }

        /**
         * Gets the value of the globalAircapLast property.
         */
        public int getGlobalAircapLast() {
            return globalAircapLast;
        }

        /**
         * Sets the value of the globalAircapLast property.
         */
        public void setGlobalAircapLast(int value) {
            this.globalAircapLast = value;
        }

        /**
         * Gets the value of the globalSeacapInitial property.
         */
        public int getGlobalSeacapInitial() {
            return globalSeacapInitial;
        }

        /**
         * Sets the value of the globalSeacapInitial property.
         */
        public void setGlobalSeacapInitial(int value) {
            this.globalSeacapInitial = value;
        }

        /**
         * Gets the value of the globalSeacapCurrent property.
         */
        public int getGlobalSeacapCurrent() {
            return globalSeacapCurrent;
        }

        /**
         * Sets the value of the globalSeacapCurrent property.
         */
        public void setGlobalSeacapCurrent(int value) {
            this.globalSeacapCurrent = value;
        }

        /**
         * Gets the value of the globalSeacapLast property.
         */
        public int getGlobalSeacapLast() {
            return globalSeacapLast;
        }

        /**
         * Sets the value of the globalSeacapLast property.
         */
        public void setGlobalSeacapLast(int value) {
            this.globalSeacapLast = value;
        }

        /**
         * Gets the value of the newReinforcements property.
         */
        public int getNewReinforcements() {
            return newReinforcements;
        }

        /**
         * Sets the value of the newReinforcements property.
         */
        public void setNewReinforcements(int value) {
            this.newReinforcements = value;
        }

        /**
         * Gets the value of the interdiction property.
         */
        public int getInterdiction() {
            return interdiction;
        }

        /**
         * Sets the value of the interdiction property.
         */
        public void setInterdiction(int value) {
            this.interdiction = value;
        }

        /**
         * Gets the value of the victoryModifications property.
         */
        public int getVictoryModifications() {
            return victoryModifications;
        }

        /**
         * Sets the value of the victoryModifications property.
         */
        public void setVictoryModifications(int value) {
            this.victoryModifications = value;
        }

        /**
         * Gets the value of the globalHandicap property.
         */
        public int getGlobalHandicap() {
            return globalHandicap;
        }

        /**
         * Sets the value of the globalHandicap property.
         */
        public void setGlobalHandicap(int value) {
            this.globalHandicap = value;
        }

        /**
         * Gets the value of the globalRailRepair property.
         */
        public int getGlobalRailRepair() {
            return globalRailRepair;
        }

        /**
         * Sets the value of the globalRailRepair property.
         */
        public void setGlobalRailRepair(int value) {
            this.globalRailRepair = value;
        }

        /**
         * Gets the value of the globalRailDestruction property.
         */
        public int getGlobalRailDestruction() {
            return globalRailDestruction;
        }

        /**
         * Sets the value of the globalRailDestruction property.
         */
        public void setGlobalRailDestruction(int value) {
            this.globalRailDestruction = value;
        }

        /**
         * Gets the value of the reconstitutionPointX property.
         */
        public int getReconstitutionPointX() {
            return reconstitutionPointX;
        }

        /**
         * Sets the value of the reconstitutionPointX property.
         */
        public void setReconstitutionPointX(int value) {
            this.reconstitutionPointX = value;
        }

        /**
         * Gets the value of the reconstitutionPointY property.
         */
        public int getReconstitutionPointY() {
            return reconstitutionPointY;
        }

        /**
         * Sets the value of the reconstitutionPointY property.
         */
        public void setReconstitutionPointY(int value) {
            this.reconstitutionPointY = value;
        }

        /**
         * Gets the value of the reconstitutionPointValue property.
         */
        public int getReconstitutionPointValue() {
            return reconstitutionPointValue;
        }

        /**
         * Sets the value of the reconstitutionPointValue property.
         */
        public void setReconstitutionPointValue(int value) {
            this.reconstitutionPointValue = value;
        }

        /**
         * Gets the value of the roadSupplyRadius property.
         */
        public int getRoadSupplyRadius() {
            return roadSupplyRadius;
        }

        /**
         * Sets the value of the roadSupplyRadius property.
         */
        public void setRoadSupplyRadius(int value) {
            this.roadSupplyRadius = value;
        }

        /**
         * Gets the value of the globalAirHandicap property.
         */
        public int getGlobalAirHandicap() {
            return globalAirHandicap;
        }

        /**
         * Sets the value of the globalAirHandicap property.
         */
        public void setGlobalAirHandicap(int value) {
            this.globalAirHandicap = value;
        }

        /**
         * Gets the value of the forceNBCReadiness property.
         */
        public int getForceNBCReadiness() {
            return forceNBCReadiness;
        }

        /**
         * Sets the value of the forceNBCReadiness property.
         */
        public void setForceNBCReadiness(int value) {
            this.forceNBCReadiness = value;
        }

        /**
         * Gets the value of the forcePGWMultiplier property.
         */
        public int getForcePGWMultiplier() {
            return forcePGWMultiplier;
        }

        /**
         * Sets the value of the forcePGWMultiplier property.
         */
        public void setForcePGWMultiplier(int value) {
            this.forcePGWMultiplier = value;
        }

        /**
         * Gets the value of the forceElectronicSupport property.
         */
        public int getForceElectronicSupport() {
            return forceElectronicSupport;
        }

        /**
         * Sets the value of the forceElectronicSupport property.
         */
        public void setForceElectronicSupport(int value) {
            this.forceElectronicSupport = value;
        }

        /**
         * Gets the value of the forceAirRefuel property.
         */
        public int getForceAirRefuel() {
            return forceAirRefuel;
        }

        /**
         * Sets the value of the forceAirRefuel property.
         */
        public void setForceAirRefuel(int value) {
            this.forceAirRefuel = value;
        }

        /**
         * Gets the value of the forceNightProficiency property.
         */
        public int getForceNightProficiency() {
            return forceNightProficiency;
        }

        /**
         * Sets the value of the forceNightProficiency property.
         */
        public void setForceNightProficiency(int value) {
            this.forceNightProficiency = value;
        }

        /**
         * Gets the value of the externalPOBias property.
         */
        public int getExternalPOBias() {
            return externalPOBias;
        }

        /**
         * Sets the value of the externalPOBias property.
         */
        public void setExternalPOBias(int value) {
            this.externalPOBias = value;
        }

        /**
         * Gets the value of the currentTrack property.
         */
        public int getCurrentTrack() {
            return currentTrack;
        }

        /**
         * Sets the value of the currentTrack property.
         */
        public void setCurrentTrack(int value) {
            this.currentTrack = value;
        }

        /**
         * Gets the value of the forceMoveBias property.
         */
        public int getForceMoveBias() {
            return forceMoveBias;
        }

        /**
         * Sets the value of the forceMoveBias property.
         */
        public void setForceMoveBias(int value) {
            this.forceMoveBias = value;
        }

        /**
         * Gets the value of the forcePestilence property.
         */
        public int getForcePestilence() {
            return forcePestilence;
        }

        /**
         * Sets the value of the forcePestilence property.
         */
        public void setForcePestilence(int value) {
            this.forcePestilence = value;
        }

        /**
         * Gets the value of the forceCommunication property.
         */
        public int getForceCommunication() {
            return forceCommunication;
        }

        /**
         * Sets the value of the forceCommunication property.
         */
        public void setForceCommunication(int value) {
            this.forceCommunication = value;
        }

        /**
         * Gets the value of the forceLossIntolerance property.
         */
        public int getForceLossIntolerance() {
            return forceLossIntolerance;
        }

        /**
         * Sets the value of the forceLossIntolerance property.
         */
        public void setForceLossIntolerance(int value) {
            this.forceLossIntolerance = value;
        }

        /**
         * Gets the value of the zocCost property.
         */
        public int getZOCCost() {
            return zocCost;
        }

        /**
         * Sets the value of the zocCost property.
         */
        public void setZOCCost(int value) {
            this.zocCost = value;
        }

        /**
         * Gets the value of the recentAirLosses property.
         */
        public int getRecentAirLosses() {
            return recentAirLosses;
        }

        /**
         * Sets the value of the recentAirLosses property.
         */
        public void setRecentAirLosses(int value) {
            this.recentAirLosses = value;
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

}
