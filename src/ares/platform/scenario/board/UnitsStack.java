package ares.platform.scenario.board;

import ares.platform.scenario.Scale;
import ares.platform.scenario.forces.AirUnit;
import ares.platform.scenario.forces.LandUnit;
import ares.platform.scenario.forces.SurfaceUnit;
import ares.platform.scenario.forces.Unit;
import ares.platform.util.Ring;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class UnitsStack {

    /**
     * Surface (Land and Naval) Units found in this location.
     */
    private final Collection<SurfaceUnit> surfaceUnits;
    /**
     * Air Units found in this location.
     */
    private final Collection<AirUnit> airUnits;
    /**
     * All units in the location. This uses a Ring, a circular linked list which allows cycling over all the units. This
     * is used by the GUI. We need this structure to remember the currently visible unit across turns.
     */
    private final Ring<Unit> allUnits;

    public UnitsStack(Tile location) {
        surfaceUnits = new ArrayList<>();
        airUnits = new ArrayList<>();
        allUnits = new Ring<>();
    }

    public boolean isEmpty() {
        return allUnits.isEmpty();
    }

    public int getStackingPenalty(Scale scale) {
        int criticalDensity = scale.getCriticalDensity();
        int numHorsesAndVehicles = 0;
        for (SurfaceUnit surfaceUnit : surfaceUnits) {
            if (surfaceUnit.getMovementType().isMobileLandUnit()) {
                numHorsesAndVehicles += ((LandUnit) surfaceUnit).getNumVehiclesAndHorses();
            }
        }
        return Math.min(4, numHorsesAndVehicles / criticalDensity);
    }

    public Collection<SurfaceUnit> getSurfaceUnits() {
        return surfaceUnits;
    }

    public Collection<AirUnit> getAirUnits() {
        return airUnits;
    }

    //    public Ring<Unit> getAllUnits() {
//        return allUnits;
//    }
    public void addSurfaceUnit(SurfaceUnit surfaceUnit) {
        surfaceUnits.add(surfaceUnit);
        allUnits.add(surfaceUnit);
    }

    public void addAirUnit(AirUnit airUnit) {
        airUnits.add(airUnit);
        allUnits.add(airUnit);
    }

    public boolean removeSurfaceUnit(SurfaceUnit surfaceUnit) {
        return (surfaceUnits.remove(surfaceUnit) && allUnits.remove(surfaceUnit));
    }

    public boolean removeAirUnit(AirUnit airUnit) {
        return (airUnits.remove(airUnit) && allUnits.remove(airUnit));
    }

    public void next() {
        allUnits.next();
    }

    public Unit getPointOfInterest() {
        return allUnits.getPointOfInterest();
    }

    public int size() {
        return allUnits.size();
    }

    @Override
    public String toString() {
        return allUnits.toString();
    }
}
