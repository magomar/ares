package ares.ai.planner;

import ares.engine.action.AbstractAction;
import ares.scenario.forces.Unit;
import java.util.LinkedList;

/**
 *
 * @author Saúl Esteban
 */
public class UnitTaskNode {
    private UnitTask task;
    private Unit unit;
    private Unit unitForSupport;
    private LinkedList<AbstractAction> actions;
    
    public UnitTaskNode(UnitTask ut, Unit u) {
        task = ut;
        unit = u;
        unitForSupport = null;
        actions = new LinkedList<>();
    }
    
    public void setUnitForSupport(Unit u) {
        unitForSupport = u;
    }
    
    public UnitTask getTask() {
        return task;
    }
    
    public Unit getUnit() {
        return unit;
    }
    
    public Unit getUnitForSupport() {
        return unitForSupport;
    }
    
    public void addAction(AbstractAction a) {
        actions.add(a);
    }
    
    public LinkedList<AbstractAction> getActions() {
        return actions;
    }
}
