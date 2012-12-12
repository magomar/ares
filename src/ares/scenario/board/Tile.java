package ares.scenario.board;

import ares.application.models.board.NonObservedTileModel;
import ares.application.models.board.ObservedTileModel;
import ares.application.models.board.TileModel;
import ares.data.jaxb.Map.Cell;
import ares.data.jaxb.TerrainFeature;
import ares.data.jaxb.TerrainType;
import ares.engine.combat.CombatModifier;
import ares.engine.knowledge.KnowledgeCategory;
import ares.engine.knowledge.KnowledgeLevel;
import ares.engine.movement.MovementCost;
import ares.engine.movement.MovementType;
import ares.engine.realtime.ClockEvent;
import ares.platform.model.ModelProvider;
import ares.platform.model.UserRole;
import ares.scenario.Scenario;
import ares.scenario.assets.AssetTrait;
import ares.scenario.forces.AirUnit;
import ares.scenario.forces.Capability;
import ares.scenario.forces.Force;
import ares.scenario.forces.SurfaceUnit;
import ares.scenario.forces.Unit;
import java.awt.Point;
import java.util.*;
import java.util.Map.Entry;

/**
 * This class holds the state of a single tile in the board, and it is uniquely identified by coordinates X and Y.
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public final class Tile implements ModelProvider<TileModel> {

    /**
     * Set of terrain types found in this location whose effect does not depend on direction
     */
    private Set<Terrain> tileTerrain;
    /**
     * Board containing the terrain types found in this location for each direction of the tile These terrain types may
     * either have and effect that depends on the direction (roads and escarpments), or need to be represented
     * graphically as having direction, although their effect would be global for the entire tile (rivers and wadis) or
     * it would have no effect at all (political boundaries [borders])
     */
    private Map<Direction, Set<Terrain>> sideTerrain;
    /**
     * Set of features found in the terrain that are not exactly "terrain types". This set includes both such airports
     * and harbours,
     *
     */
    private Set<TerrainFeatures> features;
    /**
     * Entrenchment (fortification) level, expressed as a percentage
     */
    private int entrechment;
    /**
     * Distance in "tiles" from the map limits, this value is greater than zero for tiles separated from the main
     * playing area by some Non-playable tiles, which is typically used to represent remote units and facilities which
     * can impact the scenario, for example an airport and some air units or airborne-capable units.
     */
    private int distance;
    /**
     * Victory points, represented by a positive integer
     */
    private int vp;
    /**
     * Force in possesion of this tile
     */
    private Force owner;
//    /**
//     * Column (horizontal coordinate)
//     */
//    private int x;
//    /**
//     * Row (vertical coordinate)
//     */
//    private int y;
    private Point coord;
    /**
     * Level of visibility in this tile depending on the terrain (withouth considering the weather)
     */
    private Vision visibility;
    /*
     * Data structure containing all units in the location.
     */
    private UnitsStack units;
    /**
     * Precomputed movement costs for all directions
     */
    private Map<Direction, MovementCost> moveCosts;
    /**
     * Minimun movement cost per each movement type
     */
    private Map<MovementType, Integer> minMoveCost;
    /**
     * Modifiers to combat due to terrain
     */
    private Map<Direction, CombatModifier> combatModifiers;
    /**
     * Neighbour tiles in all valid directions. If there is no neighbor in one direction (which happens at the edges of
     * the board), then there would be no entry for that direction.
     */
    private Map<Direction, Tile> neighbors;
//    /**
//     * Size of the tile in meters
//     */
//    private int size;
    private final Map<UserRole, KnowledgeLevel> knowledgeLevels;
    private final Map<KnowledgeCategory, TileModel> models;

    public Tile(Cell c) {
        // numeric attributes
//        x = c.getX();
//        y = c.getY();
        coord = new Point(c.getX(), c.getY());
        Integer ent = c.getEntrenchment();
        entrechment = (ent != null ? ent : 0);
        Integer dist = c.getDistance();
        distance = (dist != null ? dist : 0);
        Integer victPoints = c.getVP();
        vp = (victPoints != null ? victPoints : 0);
        units = new UnitsStack(this);

        // Initialize terrain information
        tileTerrain = EnumSet.noneOf(Terrain.class);
        sideTerrain = new EnumMap<>(Direction.class);
        for (Direction d : Direction.values()) {
            sideTerrain.put(d, EnumSet.noneOf(Terrain.class));
        }
        visibility = Vision.OPEN;
        for (Cell.Terrain ct : c.getTerrain()) {
            TerrainType type = ct.getType();
            Terrain terr = Terrain.valueOf(type.name());
            String[] dirStrArray = ct.getDir().split(" ");
            for (int i = 0; i < dirStrArray.length; i++) {
                Direction d = Direction.valueOf(dirStrArray[i]);
                sideTerrain.get(d).add(terr);
                if (terr.getDirectionality() != Directionality.LOGICAL) {
                    tileTerrain.add(terr);
                    if (terr.getVision().ordinal() < visibility.ordinal()) {
                        visibility = terr.getVision();
                    }
                }
            }

        }
        features = EnumSet.noneOf(TerrainFeatures.class);

        for (TerrainFeature feature : c.getFeature()) {
            features.add(Enum.valueOf(TerrainFeatures.class, feature.name()));
        }
        models = new HashMap<>();
        knowledgeLevels = new HashMap<>();
    }

    /**
     * Initialization method to be invoked when the board and forces have already been created It sets the owner and
     * neighbors. It also computes the movement costs induced by the terrain when moving from this tile to another tile,
     * and the combat modifiers for units located in this tile.
     *
     * @param board
     */
    public void initialize(Map<Direction, Tile> neighbors, Force owner, Scenario scenario) {
//        size = scenario.getScale().getDistance();
        moveCosts = new EnumMap<>(Direction.class);
        combatModifiers = new EnumMap<>(Direction.class);
        this.neighbors = neighbors;
        for (Map.Entry<Direction, Tile> neighbor : neighbors.entrySet()) {
            Direction fromDir = neighbor.getKey();
            Tile tile = neighbor.getValue();
            MovementCost cost = new MovementCost(fromDir, tile, scenario);
            moveCosts.put(fromDir, cost);
            CombatModifier combatModifier = new CombatModifier(tile, fromDir);
            combatModifiers.put(fromDir, combatModifier);
        }
        this.owner = owner;

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
        Set<Capability> capabilities = unit.getType().getCapabilities();
        if (capabilities.contains(Capability.AIRCRAFT)) {
            units.addAirUnit((AirUnit) unit);
        } else {
            units.addSurfaceUnit((SurfaceUnit) unit);
            Force force = unit.getForce();
            if (!force.equals(owner)) {
                owner = force;
            }
            if (units.getSurfaceUnits().size() == 1) {
                knowledgeLevels.get(UserRole.getForceRole(force)).modify(KnowledgeCategory.COMPLETE.getLowerBound());
            }
        }
    }

    public boolean remove(Unit unit) {
        Set<Capability> capabilities = unit.getType().getCapabilities();
        if (capabilities.contains(Capability.AIRCRAFT)) {
            return units.removeAirUnit((AirUnit) unit);
        } else {
            return units.removeSurfaceUnit((SurfaceUnit) unit);
        }
    }

    public void reconnoissance(Unit unit, int minutes) {
        double recon = unit.getTraitValue(AssetTrait.RECON) * AssetTrait.RECON.getFactor();
        if (recon == 0) {
            recon = AssetTrait.RECON.getFactor();
        }
        Force force = unit.getForce();
        knowledgeLevels.get(UserRole.getForceRole(force)).modify(minutes * recon / (24 * 60));
    }

    public void setEntrechment(int entrechment) {
        this.entrechment = entrechment;
    }

    public void updateKnowledge(ClockEvent ce) {
        int minutes = ce.getClock().MINUTES_PER_TICK;
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
    
    public Set<TerrainFeatures> getTerrainFeatures() {
        return features;
    }

    public Vision getVisibility() {
        return visibility;
    }

    public int getDistance() {
        return distance;
    }

    public int getEntrechment() {
        return entrechment;
    }

    public Force getOwner() {
        return owner;
    }

    public Map<Direction, Set<Terrain>> getSideTerrain() {
        return sideTerrain;
    }

    public Set<Terrain> getTileTerrain() {
        return tileTerrain;
    }

    public Vision getVision() {
        return visibility;
    }

    public int getVp() {
        return vp;
    }

    public Point getCoordinates() {
        return coord;
    }

//    public int getSize() {
//        return size;
//    }
//    
//    public int getX() {
//        return x;
//    }
//
//    public int getY() {
//        return y;
//    }
//    public Map<Direction, MovementCost> getMoveCosts() {
//        return moveCosts;
//    }
//
//    public Map<Direction, CombatModifier> getCombatModifiers() {
//        return combatModifiers;
//    }
    public MovementCost getMoveCost(Direction fromDir) {
        return moveCosts.get(fromDir);
    }

    public CombatModifier getCombatModifiers(Direction dir) {
        return combatModifiers.get(dir);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + coord.x;
        hash = 31 * hash + coord.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tile other = (Tile) obj;
        if (!this.coord.equals(other.getCoordinates())) {
            return false;
        }
        return true;
    }

    public KnowledgeLevel getKnowledgeLevel(UserRole role) {
        return knowledgeLevels.get(role);
    }

    public TileModel getModel(KnowledgeCategory kLevel) {
        return models.get(kLevel);
    }

    @Override
    public TileModel getModel(UserRole role) {
        KnowledgeCategory category = knowledgeLevels.get(role).getCategory();
        return models.get(category);
    }

    @Override
    public String toString() {
        return "<" + coord.x + "," + coord.y + ">";
    }

    public String toStringMultiline() {
        StringBuilder sb = new StringBuilder("Location: " + toString() + '\n');
        if (!tileTerrain.isEmpty()) {
            sb.append("Terrain: ").append(tileTerrain).append('\n');
        }
        if (!features.isEmpty()) {
            sb.append("Features: ").append(features).append('\n');
        }
        sb.append("Owner: ").append(owner).append('\n');
        for (Entry<UserRole, KnowledgeLevel> entry : knowledgeLevels.entrySet()) {
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append('\n');
        }

        return sb.toString();
    }
}
