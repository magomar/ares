package ares.scenario.forces;

import ares.application.models.forces.DetectedUnitModel;
import ares.application.models.forces.IdentifiedUnitModel;
import ares.application.models.forces.KnownUnitModel;
import ares.application.models.forces.UnitModel;
import ares.data.jaxb.Availability;
import ares.data.jaxb.Emphasis;
import ares.engine.action.ActionType;
import ares.engine.command.TacticalMission;
import ares.engine.command.TacticalMissionType;
import ares.engine.knowledge.KnowledgeCategory;
import ares.engine.movement.MovementType;
import ares.platform.model.ModelProvider;
import ares.platform.model.UserRole;
import ares.platform.util.MathUtils;
import ares.scenario.Clock;
import ares.scenario.Scale;
import ares.scenario.Scenario;
import ares.scenario.assets.Asset;
import ares.scenario.assets.AssetTrait;
import ares.scenario.assets.AssetType;
import ares.scenario.assets.AssetTypes;
import ares.scenario.board.Board;
import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Mario Gomez <margomez antiTank dsic.upv.es>
 */
public abstract class Unit implements ModelProvider<UnitModel> {

//    public static final Comparator<Unit> UNIT_ACTION_FINISH_COMPARATOR = new UnitActionFinishComparator();
    public static final Comparator<Unit> UNIT_ENTRY_COMPARATOR = new Unit.UnitEntryComparator();
    public static final int MAX_ENDURANCE = 18 * 60 * 60;
    /**
     * IMPORTANT: Currently the id of the unit is just a reference from Toaw, but it is not a unique identifier
     */
    protected int id;
    /**
     * The name which identifies the unit within a force's OOB
     */
    protected String name;
    /**
     * Identifies a graphic icon depicting the type of unit {@link type}
     */
    protected int iconId;
    /**
     * Identifies the combination of colors of the graphical icon {@link iconId}
     */
    protected int color;
    /**
     * The type of unit (infantry, mechanized infantry, artillery, etc.)
     *
     * @see UnitType
     */
    protected UnitType type;
    /**
     * The rank of a unit in its force OOB (battalion, division, etc.)
     *
     * @see Echelon;
     */
    protected Echelon echelon;
    /**
     * The formation the unit is attached to
     */
    protected Formation formation;
    /**
     * The formation the unit belongs to
     */
    protected Force force;
//    protected Experience experience;
    /**
     * Represents the quality of the unit, its ability to perform its duties. It captures both the training and the
     * experience of a unit. In general proficiency tend to increase as the unit executes actions. It is specified as a
     * percentage, where 100% is the best possible proficiency.
     */
    protected int proficiency;
    /**
     * Represents the pshysical condition of a unit due to the changeEndurance and tear of its personnel and equipment,
     * as well as its organization and cohesion state. It is specified as a percentage, where 100% is the ideal
     * condition, with the equipment in perfect conditions, all the personnel fit and rested
     */
    protected int readiness;
    /**
     * Represents the amount of supplies available. It is specified as a percentage, where 100% indicates that the unit
     * is able to fully feed its personnel, and has standard fuel and ammunition for sustained operations during a
     * single day
     */
    protected int supply;
    /**
     * Current location of the unit on the map board
     */
    protected Tile location;
    /**
     * Represents the level of losses acceptable before disengaging from combat It is represented by an Ordinal scale
     * (minimize losses, limit losses, ignore losses)
     */
    protected Emphasis emphasis;
    /**
     * Indicates whether a unit is available or not, and the reason for not being available Ir is represented by a
     * Nominal scale (available, disbanded, eliminated, expected as reinforcement, etc.)
     */
    protected Availability availability;
    /**
     * Represents the operational state of a unit, how it is deployed and what it is doing (deployed, embarked, mobile,
     * deploying, assembling, moving, attacking, routing, reorganizing, etc. )
     */
    protected OpState opState;
    /**
     * Priority to receive replacements
     */
    protected int replacementPriority;
    /**
     * It can represent two things. If a unit is expected to enter as a reinforcement, it represents the turn it is
     * received. However, if a unit is a conditional reinforcement, it represents the identifier of the event that fires
     * the reception of the unit.
     */
    protected int entry;
    /**
     * This attribute is used in units resulting of division to reference the unit that was divided.
     */
    protected Unit parent;
    /**
     * Collection of assets available in the unit. An asset includes information concerning the type of equipment, the
     * current amount of that equipment, and the maximum amount (the ideal condition). Assets are stored in a
     * <code>Map</code> having
     * <code>AssetaType</code> objects as keys, and
     * <code>Asset</code> objects as values.
     */
    protected Map<AssetType, Asset> assets;
    // ************ COMPUTED ATTRIBUTES ****************
    /**
     * Represents the special capabilities of a unit. It is specified as a map that links asset types to the number of
     * assets possesing each asset trait. Stored as a map Assets are stored in a
     * <code>Map</code> having
     * <code>AssetaTrait</code> objects as keys, and the amount of that trait as values (
     * <code>Integer</code>)
     */
    protected Map<AssetTrait, Integer> traits;
    /**
     * Fighting strenght against armored vehicles such as tanks, armored personnel carriers, etc. (hard targets)
     */
    protected int antiTank;
    /**
     * Fighting strenght against personnel, animals and non-armored equipment (soft targets)
     */
    protected int antiPersonnel;
    /**
     * Fighting strenght against aircrafts flying at high altitude
     */
    protected int highAntiAir;
    /**
     * Fighting strenght against aircrafts flying at low/medium altitude
     */
    protected int lowAntiAir;
    /**
     * Strenght that represents the capacity to sustain loses. In general defense is computed as the sum of personnel
     * and vehicles
     */
    protected int defense;
    /**
     * Fighting strenght against soft targets (people, animals, non-armored equipment) using long-range indirect-fires
     * (Bombardment) (soft targets)
     */
    protected int artillery;
    /**
     * Range for indirect fire, specified in Km *
     */
    protected int artilleryRange;
    /**
     * Weight in kilograms
     */
    protected int weight;
    /**
     * Movement type depends on the type of assets in the unit (air, naval, foot, motorized, etc.)
     */
    protected MovementType movement;
    /**
     * Standard average moving speed in ideal conditions, specified in meters per minute. Depends on the type of assets
     * in the unit
     */
    protected int speed;
    /**
     * The actual ability to perform actions, taking into account readiness and proficiency.
     *
     */
    protected int quality;
    /**
     * The actual efficacy when performing actions, considering quality and supplies
     */
    protected int efficacy;
    /**
     * The ability to explore the environment and obtain information
     */
    protected int reconnaissance;
    /**
     * Represents the remaining physical resistence of a unit before becoming exhausted, expressed in action points
     * (seconds of low-intensity activity). The execution of actions consumes endurance, but it can be replenished by
     * resting.
     */
    protected int endurance;
    /**
     * Represents the maximun physical resistence of a unit when fully rested, given the actual readiness and supplies.
     *
     * @see #endurance
     */
    protected int maxEndurance;
    /**
     * Distance in meters a unit is currently able to move antiTank standard average speed, before becoming exhausted.
     */
    protected int range;
    /**
     * Distance in meters a rested unit would be able to move in ideal conditions at standard average speed before
     * becoming exhausted.
     */
    protected int maxRange;
    private final Map<KnowledgeCategory, UnitModel> models;
    /**
     * The actor assigned to this unit
     */
    protected TacticalMission mission;

    protected Unit(ares.data.jaxb.Unit unit, Formation formation, Force force, Scenario scenario) {
        id = unit.getId();
        name = unit.getName();
        type = UnitType.valueOf(unit.getType().name());
        if (type.ordinal() > 114) {
            System.err.println(unit.getName() + " *** " + type);
        }
        iconId = unit.getIconId();
        color = unit.getColor();
        echelon = Echelon.valueOf(unit.getSize().name());
        this.formation = formation;
        this.force = force;
        proficiency = unit.getProficiency();
        readiness = unit.getReadiness();
        supply = unit.getSupply();
        emphasis = unit.getEmphasis();
        availability = unit.getAvailability();
        opState = OpState.valueOf(unit.getOpState().name());
        replacementPriority = unit.getReplacementPriority();
        entry = (availability == Availability.TURN || availability == Availability.EVENT ? entry = unit.getEntry() : 1);
        artilleryRange = 0;
        assets = new HashMap<>();
        traits = new EnumMap<>(AssetTrait.class);
        boolean providesIndirectFireSupport = type.getCapabilities().contains(Capability.BOMBARDMENT);
        AssetTypes assetTypes = scenario.getAssetTypes();
        for (ares.data.jaxb.Unit.Equipment equip : unit.getEquipment()) {
            Asset asset = new Asset(equip, assetTypes);
            AssetType assetType = asset.getType();
            assets.put(assetType, asset);
            int n = asset.getNumber();
            if (n > 0) {
                antiTank += n * assetType.getAt();
                antiPersonnel += n * assetType.getAp();
                defense += n * assetType.getDf();
                highAntiAir += n * assetType.getAah();
                lowAntiAir += n * assetType.getAal();
                if (providesIndirectFireSupport) {
                    int astRange = assetType.getArtyRange();
                    if (astRange > 0) {
                        artilleryRange = (artilleryRange == 0 ? astRange : Math.min(artilleryRange, astRange));
                        artillery += n * assetType.getAp() / 2;
                    }
                }
                weight += n * assetType.getWeight();
                Set<AssetTrait> assetTraits = assetType.getTraits();
                for (AssetTrait trait : assetTraits) {
                    if (traits.containsKey(trait)) {
                        traits.put(trait, traits.get(trait) + asset.getNumber());
                    } else {
                        traits.put(trait, asset.getNumber());
                    }
                }
            }
        }
        Board board = scenario.getBoard();
        int x = unit.getX();
        int y = unit.getY();
        location = board.getMap()[x][y];
        models = new HashMap<>();
//        models.put(KnowledgeCategory.NONE, null);
        models.put(KnowledgeCategory.POOR, new DetectedUnitModel(this));
        models.put(KnowledgeCategory.GOOD, new IdentifiedUnitModel(this));
        models.put(KnowledgeCategory.COMPLETE, new KnownUnitModel(this));

    }

    /**
     * This method makes the unit active, which implies initializing state attributes & placing the unit in the board
     *
     */
    public void activate() {
        updateMaxValues();
        endurance = maxEndurance;
        range = maxRange;
        updateDerivedValues();
        location.add(this);
        mission = new TacticalMission(TacticalMissionType.NULL);
    }

    /**
     * Changes the readiness of the unit by the specified
     * <code>amount</code>
     *
     * @param amount
     */
    public void changeReadiness(int amount) {
        readiness += amount;
        readiness = MathUtils.setBounds(readiness, 0, 100);
        updateDerivedValues();
    }

    /**
     * Changes the supply levels of the unit by the specified
     * <code>amount</code>
     *
     * @param amount
     */
    public void changeSupply(int amount) {
        supply += amount;
        supply = MathUtils.setBounds(supply, 0, 200);
        updateDerivedValues();
    }

    /**
     * Updates derived attributes (
     * <code>quality</code> and
     * <code>efficacy</code>)
     */
    protected void updateDerivedValues() {
        quality = (2 * proficiency + readiness) / 3;
        efficacy = (2 * proficiency + readiness + Math.min(100, supply)) / 4;
    }

    /**
     * Updates the maximum values permitted for endurance (
     * <code>maxEndurance</code> and
     * <code>maxRange</code>. This feature simmulates a loss of recovery capacity (which occurs for example after
     * incurring in excesive fatigue). A unit whis these values reduced are not able to recover 100% performance after
     * resting.
     */
    public void updateMaxValues() {
        maxEndurance = MAX_ENDURANCE * (200 + readiness + Math.min(100, supply)) / 400;
        maxRange = speed * maxEndurance / 90 / 1000;
    }

    /**
     * Changes the
     * <code>endurance</code> of the unit by the specified
     * <code>amount</code>
     *
     * @param amount
     */
    public void changeEndurance(int amount) {
        endurance += amount;
        endurance = MathUtils.setUpperBound(endurance, maxEndurance);
        range = speed * endurance / 90 / 1000;
    }

    public void addAssets() {
        throw new UnsupportedOperationException();
    }

    public void removeAssets() {
        throw new UnsupportedOperationException();
    }

    public void setParent(Unit parent) {
        this.parent = parent;
    }

    /**
     * Moves the unit one tile in the indicated
     * <code>direction</code>. This method simply changes the location of the unit, and updates the board accordingly
     * (removes unit from previous location and adds it to the new location)
     *
     * @param direction
     */
    public void move(Direction direction) {
        location.remove(this);
        this.location = location.getNeighbor(direction);
        location.add(this);
    }

    /**
     * Sets the operational state of the unit. This indicates the way the unit is deployed and/or which kind of activity
     * is performing
     *
     * @see OpState
     * @param opState
     */
    public void setOpState(OpState opState) {
        if (opState != null) {
            this.opState = opState;
        }
    }

    public Map<AssetType, Asset> getAssets() {
        return assets;
    }

    public Map<AssetTrait, Integer> getTraits() {
        return traits;
    }

    public int getTraitValue(AssetTrait trait) {
        if (traits.containsKey(trait)) {
            return traits.get(trait);
        } else {
            return 0;
        }
    }

    public Tile getLocation() {
        return location;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public int getRange() {
        return range;
    }

    public int getId() {
        return id;
    }

    public int getHighAntiAir() {
        return highAntiAir * efficacy;
    }

    public int getLowAntiAir() {
        return lowAntiAir * efficacy;
    }

    public int getAntiPersonnel() {
        return antiPersonnel * efficacy;
    }

    public int getArtillery() {
        return artillery * efficacy;
    }

    public int getAntiTank() {
        return antiTank * efficacy;
    }

    public int getColor() {
        return color;
    }

    public int getDefense() {
        return defense * efficacy;
    }

    public int getArtilleryRange() {
        return artilleryRange;
    }

    public int getEndurance() {
        return endurance;
    }

    public double getAttackStrength() {
        return efficacy * (double) (antiTank + antiPersonnel) / Scale.INSTANCE.getArea();
    }

    public double getDefenseStrength() {
        return efficacy * (double) defense / Scale.INSTANCE.getArea();
    }

    public int getEfficacy() {
        return efficacy;
    }

    public Emphasis getEmphasis() {
        return emphasis;
    }

    public int getEntry() {
        return entry;
    }

    public int getIconId() {
        return iconId;
    }

    public String getName() {
        return name;
    }

    public int getReconnaissance() {
        return reconnaissance;
    }

    public Unit getParent() {
        return parent;
    }

    public int getProficiency() {
        return proficiency;
    }

    public int getQuality() {
        return quality;
    }

    public int getReadiness() {
        return readiness;
    }

    public int getReplacementPriority() {
        return replacementPriority;
    }

    public Echelon getSize() {
        return echelon;
    }

    public int getSpeed() {
        return speed;
    }

    public Availability getAvailability() {
        return availability;
    }

    public int getSupply() {
        return supply;
    }

    public UnitType getType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }

    public MovementType getMovement() {
        return movement;
    }

    public Echelon getEchelon() {
        return echelon;
    }

    public Force getForce() {
        return force;
    }

    public Formation getFormation() {
        return formation;
    }

    public OpState getOpState() {
        return opState;
    }

//    public void recover() {
//        quality = (2 * proficiency + readiness) / 3;
//        efficacy = (2 * proficiency + readiness + supply) / 4;
//        int enduranceRestored = (MAX_ENDURANCE * 200 + MAX_ENDURANCE * readiness + MAX_ENDURANCE * supply) / 400;
//        endurance = Math.min(endurance + enduranceRestored, enduranceRestored);
//        maxRange = speed * MAX_ENDURANCE / 90 / 1000;
//        range = speed * endurance / 90 / 1000;
//    }
    /**
     * Compares two units in terms of the turn of entry
     */
    protected static class UnitEntryComparator implements Comparator<Unit> {

        @Override
        public int compare(Unit u1, Unit u2) {
            int entry1 = u1.getEntry();
            int entry2 = u2.getEntry();
            return entry1 - entry2;
        }
    }

//    protected static class UnitActionFinishComparator implements Comparator<Unit> {
//
//        @Override
//        public int compare(Unit u1, Unit u2) {
//            Action a1 = u1.getAction();
//            Action a2 = u2.getAction();
//            if (a1 == null) {
//                if (a2 == null) {
//                    return 0;
//                } else {
//                    return 1;
//                }
//            } else if (a2 == null) {
//                return -1;
//            } else {
//                return a1.getFinish() - a2.getFinish();
//            }
//        }
//    }
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.force);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Unit other = (Unit) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.force, other.force)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + "(" + type.name() + ")." + movement + "." + opState + " @ " + location;
    }

    public String toStringMultiline() {
        StringBuilder sb = new StringBuilder(name).append(" (").append(echelon).append(')').append('\n');
        sb.append("Belongs to ").append(formation).append(" (").append(force).append(")\n");
        sb.append("Unit type: ").append(type).append('\n');
        sb.append("Location: ").append(location).append('\n');
        sb.append("Movement: ").append(movement).append(" (").append(speed * 60.0 / 1000).append(" Km/h)\n");
        sb.append("OpState: ").append(opState).append('\n');
        sb.append("Stamina: ").append(endurance * 100 / MAX_ENDURANCE).append('\n');
        sb.append("Proficiency: ").append(proficiency).append('\n');
        sb.append("Readiness: ").append(readiness).append('\n');
        sb.append("Supply: ").append(supply).append('\n');
        sb.append("Range: ").append(range).append('/').append(maxRange).append('\n');
        sb.append("Quality: ").append(quality).append('\n');
        sb.append("Efficacy: ").append(efficacy).append('\n');
        sb.append("\n___Strenghts___\n");
        sb.append("Attack ").append(efficacy * (antiTank + antiPersonnel)).append('\n');
        sb.append("Defense: ").append(efficacy * defense).append("\n");
        sb.append("AT: ").append(efficacy * antiTank).append("  ");
        sb.append("AP: ").append(efficacy * antiPersonnel).append("\n");
        sb.append("HAA: ").append(efficacy * highAntiAir).append("   ");
        sb.append("LAA: ").append(efficacy * lowAntiAir).append("\n");
        if (artillery != 0) {
            sb.append("Art: ").append(efficacy * artillery).append(" (Range ").append(range).append(" Km.)\n");
        }
        if (!type.getCapabilities().isEmpty()) {
            sb.append("\n___Capabilities___\n");
            for (Capability capability : type.getCapabilities()) {
                sb.append(capability).append('\n');
            }
        }
        sb.append("\n___Assets___\n");
        for (Asset asset : assets.values()) {
            sb.append(asset).append('\n');
        }
        sb.append("\n___Traits___\n");
        for (Entry<AssetTrait, Integer> traitEntry : traits.entrySet()) {
            sb.append(traitEntry.getKey()).append(": ").append(traitEntry.getValue()).append('\n');
        }
        return sb.toString();
    }

    @Override
    public final UnitModel getModel(UserRole role) {
        KnowledgeCategory knowledgeCategory = location.getKnowledgeLevel(role).getCategory();
        return models.get(knowledgeCategory);
    }

    /**
     * Return
     *
     * @param knowledgeCategory
     * @return
     */
    public final UnitModel getModel(KnowledgeCategory knowledgeCategory) {
        return models.get(knowledgeCategory);
    }

    /**
     * Returns true if the
     * <code>unit</code> has enough endurance to perform the action. The answer depends of the current endurance of the
     * unit as well as the <type>type<type> of action.
     *
     * @param actionType
     * @return
     */
    public boolean canExecute(ActionType type) {
        return endurance > type.getRequiredEndurace(Clock.INSTANCE.getMINUTES_PER_TICK());
    }

    public TacticalMission getMission() {
        return mission;
    }

    /**
     * The unit gathers information on the surrounding environment. At this point only the adjacent tiles are considered
     */
    public void perceive() {
        location.reconnoissance(this, Clock.INSTANCE.getMINUTES_PER_TICK());
        for (Tile tile : location.getNeighbors().values()) {
            tile.reconnoissance(this, Clock.INSTANCE.getMINUTES_PER_TICK());
        }
    }
    
    public boolean isAircraft() {
        return movement == MovementType.AIRCRAFT;
    }
}
