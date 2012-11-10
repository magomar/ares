package ares.model.board;

import ares.model.forces.AirUnit;
import ares.model.forces.LandUnit;
import ares.model.forces.SurfaceUnit;
import ares.model.forces.Unit;
import ares.model.scenario.Scale;
import ares.platform.util.Ring;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class StackOfUnits {

    /**
     * Surface (Land and Naval) Units found in this location.
     */
    private Collection<SurfaceUnit> surfaceUnits;
    /**
     * Air Units found in this location.
     */
    private Collection<AirUnit> airUnits;
    /**
     * All units in the location. This uses a Ring, a circular linked list which allows cycling over all the units. This
     * is used by the GUI. We need this structure to remember the currently visible unit across turns.
     */
    private Ring<Unit> allUnits;
    

    public StackOfUnits(Tile location) {
        surfaceUnits = new ArrayList<>();
        airUnits = new ArrayList<>();
        allUnits = new Ring<>();
    }

    protected boolean isEmpty() {
        return allUnits.isEmpty();
    }

    protected int getStackingPenalty(Scale scale) {
        int criticalDensity = scale.getCriticalDensity();
        int numHorsesAndVehicles = 0;
            for (SurfaceUnit surfaceUnit : surfaceUnits) {
                if (surfaceUnit.getMovement().isMobileLandUnit()) {
                    numHorsesAndVehicles += ((LandUnit)surfaceUnit).getNumVehiclesAndHorses();
                }
            }
        return Math.min(4, numHorsesAndVehicles / criticalDensity);
    }
    
    protected Collection<SurfaceUnit> getSurfaceUnits() {
        return surfaceUnits;
    }

    protected Collection<AirUnit> getAirUnits() {
        return airUnits;
    }

//    public Ring<Unit> getAllUnits() {
//        return allUnits;
//    }
    protected void addSurfaceUnit(SurfaceUnit surfaceUnit) {
        surfaceUnits.add(surfaceUnit);
        allUnits.add(surfaceUnit);
    }

    protected void addAirUnit(AirUnit airUnit) {
        airUnits.add(airUnit);
        allUnits.add(airUnit);
    }

    protected boolean removeSurfaceUnit(SurfaceUnit surfaceUnit) {
        return (surfaceUnits.remove(surfaceUnit) && allUnits.remove(surfaceUnit));
    }

    protected boolean removeAirUnit(AirUnit airUnit) {
        return (airUnits.remove(airUnit) && allUnits.remove(airUnit));
    }

    protected void next() {
        allUnits.next();
    }

    protected Unit getPointOfInterest() {
        return allUnits.getPointOfInterest();
    }

    protected int size() {
        return allUnits.size();
    }
}

