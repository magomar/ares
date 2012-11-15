package ares.scenario.board;

import ares.application.models.board.TileModel;
import ares.data.jaxb.Map.Cell;
import ares.data.jaxb.TerrainFeature;
import ares.data.jaxb.TerrainType;
import ares.engine.combat.CombatModifier;
import ares.engine.movement.MovementCost;
import ares.platform.model.AbstractModelProvider;
import ares.platform.model.UserRole;
import ares.platform.model.UserRoleType;
import ares.scenario.Scale;
import ares.scenario.Scenario;
import ares.scenario.forces.AirUnit;
import ares.scenario.forces.Capability;
import ares.scenario.forces.Force;
import ares.scenario.forces.SurfaceUnit;
import ares.scenario.forces.Unit;
import java.util.*;

/**
 * This class holds the state of a single tile in the board, and it is uniquely identified by coordinates X and Y.
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public final class Tile extends AbstractModelProvider<TileModel> {

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
    private Set<TerrainFeature> features;
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
    /**
     * Column (horizontal coordinate)
     */
    private int x;
    /**
     * Row (vertical coordinate)
     */
    private int y;
    /**
     * Level of visibility in this tile depending on the terrain (withouth considering the weather)
     */
    private Vision visibility;
    /*
     * Data structure containing all units in the location.
     */
    private StackOfUnits units;
    /**
     * Precomputed movement costs for all directions
     */
    private Map<Direction, MovementCost> moveCosts;
    /**
     * Modifiers to combat due to terrain
     */
    private Map<Direction, CombatModifier> combatModifiers;
    /**
     * Neighbour tiles in all valid directions. If there is no neighbor in one direction (which happens at the edges of
     * the board), then there would be no entry for that direction.
     */
    private Map<Direction, Tile> neighbors;
    private Map<Force, InformationLevel> informationLevels;
    private TileModel tileModel;

    public Tile(Cell c) {
        // numeric attributes
        x = c.getX();
        y = c.getY();
        Integer ent = c.getEntrenchment();
        entrechment = (ent != null ? ent : 0);
        Integer dist = c.getDistance();
        distance = (dist != null ? dist : 0);
        Integer victPoints = c.getVP();
        vp = (victPoints != null ? victPoints : 0);
        units = new StackOfUnits(this);

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
        features = EnumSet.noneOf(TerrainFeature.class);
        for (TerrainFeature feature : c.getFeature()) {
            features.add(feature);
        }
        tileModel = new TileModel(this);
    }

    /**
     * Initialization method to be invoked when the board and forces have already been created It sets the owner and
     * neighbors. It also computes the movement costs induced by the terrain when moving from this tile to another tile,
     * and the combat modifiers for units located in this tile.
     *
     * @param board
     */
    public void initialize(Map<Direction, Tile> neighbors, Force owner, Scenario scenario) {
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
        informationLevels = new HashMap<>();
        Force[] forces = scenario.getForces();
        for (Force force : forces) {
            if (force == owner) {
                informationLevels.put(force, InformationLevel.COMPLETE);
            } else {
                informationLevels.put(force, InformationLevel.POOR);
            }
        }
    }

    public void add(Unit unit) {
        Set<Capability> capabilities = unit.getType().getCapabilities();
        if (capabilities.contains(Capability.AIRCRAFT)) {
            units.addAirUnit((AirUnit) unit);
        } else {
            units.addSurfaceUnit((SurfaceUnit) unit);
            Force force = unit.getForce();
            if (!force.equals(owner)) {
                owner = unit.getForce();
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

    public void setEntrechment(int entrechment) {
        this.entrechment = entrechment;
    }

    public void setInformationLevel(Force force, InformationLevel informationLevel) {
        informationLevels.put(force, informationLevel);
    }

// *** GETTERS ***
    public Unit getTopUnit() {
        return units.getPointOfInterest();
    }

    public void nextTopUnit() {
        units.next();
    }

//    public StackOfUnits getUnits() {
//        return units;
//    }
    public Map<Direction, Tile> getNeighbors() {
        return neighbors;
    }

    public Set<TerrainFeature> getFeatures() {
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

    public Collection<SurfaceUnit> getSurfaceUnits() {
        return units.getSurfaceUnits();
    }

    public Collection<AirUnit> getAirUnits() {
        return units.getAirUnits();
    }

    public int getStackingPenalty(Scale scale) {
        return units.getStackingPenalty(scale);
    }

    public int getNumStackedUnits() {
        return units.size();
    }

    public Vision getVision() {
        return visibility;
    }

    public int getVp() {
        return vp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

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

    public InformationLevel getInformationLevel(Force force) {
        return informationLevels.get(force);
    }

    @Override
    public String toString() {
        return "<" + x + "," + y + ">";
    }

    @Override
    public TileModel getModel(UserRole userRole) {
        if (userRole.getRoleType() == UserRoleType.GOD) {
            return getModel(InformationLevel.COMPLETE);
        }
        InformationLevel infoLevel = informationLevels.get(userRole.getForce());
        return getModel(infoLevel);
    }
}
