package ares.ai.planner.maneuver;

import ares.engine.command.OperationForm;
import ares.ai.planner.FormationTaskNode;
import java.util.LinkedList;

/**
 *
 * @author Saúl Esteban
 */
public interface Maneuver {
    
    public void setData(FormationTaskNode node);
    
    public LinkedList<OperationForm> getOperations();
    
    public int checkPreconditions();
    
    public void plan();
}
