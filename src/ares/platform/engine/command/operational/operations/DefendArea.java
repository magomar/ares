package ares.platform.engine.command.operational.operations;

import ares.platform.engine.command.Objective;
import ares.platform.engine.command.operational.Operation;
import ares.platform.scenario.forces.Unit;

import java.util.List;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class DefendArea extends Operation {

    public DefendArea(OperationType type, OperationForm form, List<Unit> units, Objective goal) {
        super(type, form, units, goal);
    }

    @Override
    public void plan() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
