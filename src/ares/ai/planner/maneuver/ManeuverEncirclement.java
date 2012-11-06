package ares.ai.planner.maneuver;

import ares.engine.command.OperationForm;
import ares.engine.command.TacticalMissionType;
import ares.ai.planner.UnitTask;
import ares.ai.planner.UnitTaskNode;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;
import java.util.LinkedList;

/**
 *
 * @author Saúl Esteban
 */
public class ManeuverEncirclement extends ManeuverAbstract{
    
    public ManeuverEncirclement() {
        operations = new LinkedList<>();
        operations.add(OperationForm.ASSAULT);
    }
    
    @Override
    public int checkPreconditions() {
        
//        boolean possible = true;
//        
//        for(Tile neighbour : node.getTask().getGoal().getNeighbors().values()) {
//            if(neighbour.getSurfaceUnits().size() > 0) {
//                if(!neighbour.getOwner().equals(node.getFormation().getForce())) {
//                    possible = false;
//                }
//            }
//        }
//        
//        if(possible) {
//            int allies = 0, enemies = 0;
//        
//            if(node.getFormation().getLineUnits().size() > 0) {
//                allies += node.getFormation().getLineUnits().size();
//            }
//            if(node.getFormation().getSupportUnits().size() > 0) {
//                allies += node.getFormation().getSupportUnits().size();
//            }
//            if(node.getFormation().getServiceUnits().size() > 0) {
//                allies += node.getFormation().getServiceUnits().size();
//            }
//            if(node.getFormation().getConditionalReinforcements().size() > 0) {
//                allies += node.getFormation().getConditionalReinforcements().size();
//            }
//            if(node.getFormation().getHeadquarters().getSupply() > 0) {
//                allies += node.getFormation().getHeadquarters().getSupply();
//            }
//
//            if(node.getTask().getGoal().getSurfaceUnits().size() > 0) {
//                if(!node.getTask().getGoal().getOwner().equals(node.getFormation().getForce())) {
//                    enemies += node.getTask().getGoal().getSurfaceUnits().size();
//                }
//            }
//
//            return allies - enemies;
//        }
//        else {
//          return 0;
//        }
        return 0;
    }
    
    @Override
    public void plan() {
        
        boolean inappropiate = false;
        Tile position;
        LinkedList<Tile> positions = new LinkedList<>();
        
//        for(Tile neighbour : node.getTask().getGoal().getNeighbors().values()) {
//            if(neighbour.getUnits().size() == 0) {
//                for(Tile neighboursNeigbour : neighbour.getNeighbors().values()) {
//                    if(neighboursNeigbour.getUnits().getFirst().getForce().equals(node.getFormation().getForce())) {
//                        inappropiate = true;
//                    }
//                }
//                if(!inappropiate) {
//                    positions.add(neighbour);
//                }
//            }
//        }
        
        for(Unit unit : node.getFormation().getLineUnits()) {
            UnitTask task;
            UnitTaskNode unitNode;
            
//            task = new UnitTask(TacticalMissionType.BYPASS,node.getTask().getGoal());
//            unitNode = new UnitTaskNode(task,unit);
//            node.addSubtask(unitNode);
            
            if(node.getTask().getOperationForm() == OperationForm.ASSAULT) {
//                task = new UnitTask(TacticalMissionType.ASSAULT,node.getTask().getGoal());
            }
            else {
//                task = new UnitTask(TacticalMissionType.REDUCE,node.getTask().getGoal());
            }
//            unitNode = new UnitTaskNode(task,unit);
//            node.addSubtask(unitNode);
        }
        
        Unit unitForSupport = (Unit)node.getFormation().getLineUnits().toArray()[0];
        
        if(node.getFormation().getSupportUnits().size() > 0) {
            for(Unit unit : node.getFormation().getLineUnits()) {
//                UnitTask task = new UnitTask(TacticalMissionType.FIX,node.getTask().getGoal());
//                UnitTaskNode unitNode = new UnitTaskNode(task,unit);
//                node.addSubtask(unitNode);
            }
        }
        
//        if(formation.getServiceUnits().size() > 0) {
//            allies += node.getFormation().getServiceUnits().size();
//        }
//        
//        if(formation.getConditionalReinforcements().size() > 0) {
//            allies += node.getFormation().getConditionalReinforcements().size();
//        }
        
//        UnitTask task = new UnitTask(TacticalMissionType.FOLLOW_AND_SUPPORT,node.getTask().getGoal());
//        UnitTaskNode unitNode = new UnitTaskNode(task,node.getFormation().getHeadquarters());
//        unitNode.setUnitForSupport(unitForSupport);
//        node.addSubtask(unitNode);
    }
}
