package ares.application.models.board;

import ares.application.models.forces.UnitModel;
import ares.engine.knowledge.KnowledgeCategory;
import ares.engine.movement.MovementCost;
import ares.platform.model.KnowledgeMediatedModel;
import ares.scenario.board.*;
import ares.scenario.forces.SurfaceUnit;
import java.awt.Point;
import java.util.*;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class TileModel extends KnowledgeMediatedModel {

    protected final Tile tile;

    public TileModel(Tile tile, KnowledgeCategory kLevel) {
        super(kLevel);
        this.tile = tile;
    }

    public Map<Direction, Set<Terrain>> getSideTerrain() {
        return Collections.unmodifiableMap(tile.getSideTerrain());
    }

    public Set<TerrainFeatures> getTerrainFeatures() {
        return Collections.unmodifiableSet(tile.getTerrainFeatures());
    }

    public Point getCoordinates() {
        return tile.getCoordinates();
    }
    
    public Map<Direction, TileModel> getNeighbors(){
        EnumMap<Direction,TileModel> map = new EnumMap<>(Direction.class);
        for(Map.Entry<Direction, Tile> entry :tile.getNeighbors().entrySet()){
            map.put(entry.getKey(),entry.getValue().getModel(kLevel));
        }
        return map;
    }

    public abstract UnitModel getTopUnit();
    
    public abstract String getOwner();

    public abstract int getNumStackedUnits();

    public abstract boolean isEmpty();
    
    public abstract String getDescription();

    public abstract Collection<SurfaceUnit> getSurfaceUnits();
    
    public abstract boolean isAlliedTerritory(String force);
    
    public abstract boolean hasEnemies(String force);
    
    public MovementCost getMoveCost(Direction d) {
        return tile.getMoveCost(d);
    }
    
    @Override
    public String toString() {
        return tile.toString();
    }
}
