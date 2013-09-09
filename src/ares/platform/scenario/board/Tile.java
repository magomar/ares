package ares.platform.scenario.board;

import ares.application.shared.models.board.NonObservedTileModel;
import ares.application.shared.models.board.ObservedTileModel;
import ares.application.shared.models.board.TileModel;
import ares.platform.engine.combat.CombatModifier;
import ares.platform.engine.knowledge.KnowledgeCategory;
import ares.platform.engine.knowledge.KnowledgeLevel;
import ares.platform.engine.movement.MovementCost;
import ares.platform.engine.movement.MovementType;
import ares.platform.engine.time.Clock;
import ares.platform.model.ModelProvider;
import ares.platform.model.UserRole;
import ares.platform.scenario.Scenario;
import ares.platform.scenario.assets.AssetTrait;
import ares.platform.scenario.forces.*;

import java.awt.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * This class holds the state of a single tile in the board, and it is uniquely identified by coordinates X and Y.
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public final class Tile implements ModelProvider<TileModel> {

    /**
     * Terrain types and directions in which they apply
     */
    private final Map<Terrain, Directions> terrain;
    /**
     * Terrain features
     */
    private final Set<Feature> features;
    /**
     * Entrenchment (fortification) level, expressed as a percentage
     */
    private int entrenchment;
    /**
     * Distance in "tiles" from the map limits, this value is greater than zero for tiles separated from the main
     * playing area by some Non-playable tiles, which is typically used to represent remote units and facilities which
     * can impact the scenario, for example an airport and some air units or airborne-capable units.
     */
    private final int distance;
    /**
     * Victory points, represented by a positive integer
     */
    private final int victoryPoints;
    /**
     * Force in possession of this tile
     */
    private Force owner;
    /**
     * Coordinates of the tile in the board. {@link Point#x} holds the column (horizontal coordinate) and
     * {@link Point#y} holds the row (vertical coordinate). The origin of coordinates corresponds to the Top-Left corner
     * of the board.
     */
    private final Point coordinates;
    /**
     * Unique identifier obtained from coordinates: {@code index(x, y) = x * board.width + y}
     */
    private int index;
    /**
     * Level of visibility in this tile depending on the terrain (without considering the weather)
     */
    private Vision visibility;
    /*
     * Data structure containing all units in the location.
     */
    private final UnitsStack units;
    /**
     * Precomputed movement costs to enter the tile from every possible direction.
     */
    private final Map<Direction, MovementCost> enterCost;
    /**
     * Minimum movement cost to leave the tile for every possible movement type
     */
    private Map<MovementType, Integer> minExitCost;
    /**
     * Modifiers to combat due to terrain. This modifier applies to the unit defending this location
     */
    private final Map<Direction, CombatModifier> combatModifiers;
    /**
     * Neighbor tiles in all valid directions. If there is no neighbor in one direction (which happens at the edges of
     * the board), then there would be no entry for that direction.
     */
    private Map<Direction, Tile> neighbors;
    /**
     * Knowledge levels for every possible {@link UserRole}
     */
    private final Map<UserRole, KnowledgeLevel> knowledgeLevels;
    /**
     * Collection of TileModel, stored as a map that holds the
     * {@link TileModel} for every possible {@link KnowledgeCategory}
     */
    private final Map<KnowledgeCategory, TileModel> models;

    public Tile(ares.data.wrappers.scenario.Cell c) {
//        x = c.getX();
//        y = c.getY();
        coordinates = new Point(c.getX(), c.getY());
        Integer ent = c.getEntrenchment();
        entrenchment = (ent != null ? ent : 0);
        Integer dist = c.getDistance();
        distance = (dist != null ? dist : 0);
        Integer vp = c.getVP();
        victoryPoints = (vp != null ? vp : 0);
        units = new UnitsStack(this);

        // Initialize terrain information
        terrain = new EnumMap<>(Terrain.class);
        visibility = Vision.OPEN;
        for (ares.data.wrappers.scenario.Terrain ct : c.getTerrain()) {
            ares.data.wrappers.scenario.TerrainType type = ct.getType();
            Terrain terr = Terrain.valueOf(type.name());
            Directions multiDir = Directions.valueOf(ct.getDir().name());
            terrain.put(terr, multiDir);
            Vision vision = terr.getVision();
            if (vision.compareTo(visibility) < 0) {
                visibility = vision;
            }
        }
        features = EnumSet.noneOf(Feature.class);
        // Initialize terrain features
        for (ares.data.wrappers.scenario.TerrainFeature feature : c.getFeature()) {
            features.add(Enum.valueOf(Feature.class, feature.name()));
        }

        // Initialize models and knowledge levels
        models = new HashMap<>();
        knowledgeLevels = new HashMap<>();
        enterCost = new EnumMap<>(Direction.class);
        combatModifiers = new EnumMap<>(Direction.class);
        for (Direction direction : Direction.DIRECTIONS) {
            MovementCost cost = new MovementCost(this, direction);
            enterCost.put(direction, cost);
            CombatModifier combatModifier = new CombatModifier(this, direction);
            combatModifiers.put(direction, combatModifier);
        }
    }

    /**
     * Initialization method to be invoked when the board and forces have already been created It sets the owner and
     * neighbors. It also computes the movement costs induced by the terrain when moving from this tile to another tile,
     * and the combat modifiers for units located in this tile.
     *
     * @param neighbors the tiles adjacent to this tile
     * @param owner     the force owning this tile
     * @param scenario  the scenario
     */
    public void initialize(Map<Direction, Tile> neighbors, Force owner, Scenario scenario) {
        this.owner = owner;
        index = coordinates.x * scenario.getBoard().getWidth() + coordinates.y;
        this.neighbors = neighbors;
//        for (Direction direction : neighbors.keySet()) {
//            MovementCost cost = new MovementCost(this, direction);
//            enterCost.put(direction, cost);
//            CombatModifier combatModifier = new CombatModifier(this, direction);
//            combatModifiers.put(direction, combatModifier);
//        }
        minExitCost = computeMinExitCosts();
        knowledgeLevels.put(UserRole.GOD, new KnowledgeLevel(KnowledgeCategory.COMPLETE));
        for (Force force : scenario.getForces()) {
            if (owner.equals(force)) {
                knowledgeLevels.put(UserRole.getForceRole(force), new KnowledgeLevel(KnowledgeCategory.COMPLETE));
            } else {
                knowledgeLevels.put(UserRole.getForceRole(force), new KnowledgeLevel(KnowledgeCategory.POOR));
            }
        }
        models.put(KnowledgeCategory.NONE, new NonObservedTileModel(this, KnowledgeCategory.NONE));
        models.put(KnowledgeCategory.POOR, new ObservedTileModel(this, KnowledgeCategory.POOR));
        models.put(KnowledgeCategory.GOOD, new ObservedTileModel(this, KnowledgeCategory.GOOD));
        models.put(KnowledgeCategory.COMPLETE, new ObservedTileModel(this, KnowledgeCategory.COMPLETE));
    }

    public void add(Unit unit) {
        if (unit.isAircraft()) {
            units.addAirUnit((AirUnit) unit);
        } else {
            units.addSurfaceUnit((SurfaceUnit) unit);
            Force force = unit.getForce();
            if (!force.equals(owner)) {
                owner = force;
            }
            // TODO review this
            if (units.getSurfaceUnits().size() == 1) {
                knowledgeLevels.get(UserRole.getForceRole(force)).modify(KnowledgeCategory.COMPLETE.getLowerBound());
            }
        }
    }

    public boolean remove(Unit unit) {
        Set<Capability> capabilities = unit.getUnitType().getCapabilities();
        if (capabilities.contains(Capability.AIRCRAFT)) {
            return units.removeAirUnit((AirUnit) unit);
        } else {
            return units.removeSurfaceUnit((SurfaceUnit) unit);
        }
    }

    public void reconnoissance(Unit unit, double intensity) {
        int minutes = Clock.INSTANCE.getMINUTES_PER_TICK();
        double recon = unit.getTraitValue(AssetTrait.RECON) * AssetTrait.RECON.getFactor();
        if (recon == 0) {
            recon = AssetTrait.RECON.getFactor();
        }
        Force force = unit.getForce();
        knowledgeLevels.get(UserRole.getForceRole(force)).modify(minutes * recon / (24 * 60));
    }

    public void setEntrenchment(int entrenchment) {
        this.entrenchment = entrenchment;
    }

    public void updateKnowledge() {
        int minutes = Clock.INSTANCE.getMINUTES_PER_TICK();
        for (Entry<UserRole, KnowledgeLevel> entry : knowledgeLevels.entrySet()) {
            if (!entry.getKey().isGod()) {
                Force force = entry.getKey().getForce();
                if (owner.equals(force)) {
                    entry.getValue().modify(-minutes * 1.0 / (24 * 60));
                } else {
                    entry.getValue().modify(-minutes * 10.0 / (24 * 60));
                }
            }
        }
    }

    public Unit getTopUnit() {
        return units.getPointOfInterest();
    }

    public UnitsStack getUnitsStack() {
        return units;
    }

    public Collection<SurfaceUnit> getSurfaceUnits() {
        return units.getSurfaceUnits();
    }

    public Collection<AirUnit> getAirUnits() {
        return units.getAirUnits();
    }

    public Map<Direction, Tile> getNeighbors() {
        return neighbors;
    }

    public Tile getNeighbor(Direction direction) {
        return neighbors.get(direction);
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public Vision getVisibility() {
        return visibility;
    }

    public int getDistance() {
        return distance;
    }

    public int getEntrenchment() {
        return entrenchment;
    }

    public Force getOwner() {
        return owner;
    }

    public int getIndex() {
        return index;
    }

    public Map<Terrain, Directions> getTerrain() {
        return terrain;
    }

    public Vision getVision() {
        return visibility;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public Map<MovementType, Integer> getMinExitCosts() {
        return minExitCost;
    }

    public MovementCost getEnterCost(Direction fromDir) {
        return enterCost.get(fromDir);
    }

    public CombatModifier getCombatModifiers(Direction dir) {
        return combatModifiers.get(dir);
    }

    public boolean isAlliedTerritory(Force force) {
        return owner.equals(force);
    }

    public boolean hasEnemies(Force force) {
        if (!isAlliedTerritory(force) && !getSurfaceUnits().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasEnemiesNearby(Force force) {
        for (Tile neighbor : neighbors.values()) {
            if (neighbor.hasEnemies(force)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tile other = (Tile) obj;
        if (this.index != other.index) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
//        int hash = 5;
//        hash = 11 * hash + this.index;
//        return hash;
        return index;
    }

    public KnowledgeLevel getKnowledgeLevel(UserRole userRole) {
        return knowledgeLevels.get(userRole);
    }

    public TileModel getModel(KnowledgeCategory knowledgeCategory) {
        return models.get(knowledgeCategory);
    }

    @Override
    public TileModel getModel(UserRole userRole) {
        KnowledgeCategory category = knowledgeLevels.get(userRole).getCategory();
        return models.get(category);
    }

    @Override
    public String toString() {
        return "<" + coordinates.x + "," + coordinates.y + ">";
    }

    public String toStringMultiline() {
        StringBuilder sb = new StringBuilder(toString() + '\n');

        for (Entry<UserRole, KnowledgeLevel> entry : knowledgeLevels.entrySet()) {
            if (UserRole.GOD != entry.getKey()) {
                sb.append(entry.getKey()).append(entry.getValue()).append('\n');
            }
        }

        for (Terrain t : terrain.keySet()) {
            sb.append(t).append('\n');
        }
//        if (!terrain.isEmpty()) {
//            sb.append(terrain.keySet()).append('\n');
//        }
//        if (!tileTerrain.isEmpty()) {
//            sb.append("Terrain: ").append(tileTerrain).append('\n');
//        }
        for (Feature f : features) {
            sb.append(f).append('\n');
        }
//        if (!features.isEmpty()) {
//            sb.append(features).append('\n');
//        }
//        sb.append(owner).append('\n');

        return sb.toString();
    }

    private Map<MovementType, Integer> computeMinExitCosts() {
        Map<MovementType, Integer> minimumCosts = new HashMap<>();
        for (MovementType mt : MovementType.values()) {
            minimumCosts.put(mt, MovementCost.IMPASSABLE);
        }
        for (Map.Entry<MovementType, Integer> entry : minimumCosts.entrySet()) {
            MovementType mt = entry.getKey();
            int cost = MovementCost.IMPASSABLE;
            for (Map.Entry<Direction, Tile> neighborEntry : neighbors.entrySet()) {
                Direction dir = neighborEntry.getKey();
                Tile neighbor = neighborEntry.getValue();
                int neighborCost = neighbor.getEnterCost(dir.getOpposite()).getTerrainCost(mt);
                if (neighborCost < cost) {
                    cost = neighborCost;
                }
            }
            minimumCosts.put(mt, cost);
        }
        return minimumCosts;
    }

    public boolean isPlayable() {
        return !features.contains(Feature.NON_PLAYABLE);
    }
}
