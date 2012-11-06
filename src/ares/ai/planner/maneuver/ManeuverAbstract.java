package ares.ai.planner.maneuver;

import ares.engine.command.OperationForm;
import ares.ai.planner.FormationTaskNode;
import ares.scenario.board.Tile;
import ares.scenario.forces.Formation;
import java.util.LinkedList;

/**
 *
 * @author Saúl Esteban
 */
public abstract class ManeuverAbstract implements Maneuver{
    
    protected LinkedList<OperationForm> operations;
    protected FormationTaskNode node;
    
    @Override
    public void setData(FormationTaskNode node) {
        this.node = node;
    }
    
    @Override
    public LinkedList<OperationForm> getOperations() {
        return operations;
    }
}
