package ares.ai.planner;

import ares.engine.realtime.Clock;
import ares.engine.action.AbstractAction;
import ares.engine.command.OperationForm;
import ares.scenario.board.Board;
import ares.scenario.board.Tile;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;


/**
 * The planner obtains a formation and the obtainPlan method
 * assigns a collection of actions to the units belonging to
 * the formation.
 * 
 * @author SaÃºl Esteban
 */
public class TaskPlanner {
    
    private Formation formation;
    private Clock clock;
    private FormationTaskAnalyzer formationTaskAnalyzer;
    private UnitTaskAnalyzer unitTaskAnalyzer;
    
    public TaskPlanner(Formation f, Clock c) {
        formation = f;
        clock = c;
        formationTaskAnalyzer = new FormationTaskAnalyzer();
        unitTaskAnalyzer = new UnitTaskAnalyzer(clock);
    }
    
    /**
     * Analyzes the board and builds a task network according
     * to the data obtained.
     * 
     * @param m : the board in a certain instant
     * @param c : the clock
     */
    public void obtainPlan() {
        if(formation.getObjectives().size() > 0 && formation.getLineUnits().size() > 0) {
            Tile currentObjective = null;
            double objectiveDistance, currentObjectiveDistance;
            Unit[] units = new Unit[formation.getActiveUnits().size()];
            formation.getActiveUnits().toArray(units);
        
            currentObjectiveDistance = 1000;
            
            for(Tile objective : formation.getObjectives()) {
                objectiveDistance = Board.getDistanceInTilesBetween(units[0].getLocation(), objective);
                if(currentObjectiveDistance > objectiveDistance) {
                    currentObjectiveDistance = objectiveDistance;
                    currentObjective = objective;
                }
            }
            
            buildTaskNetwork(currentObjective,OperationForm.ASSAULT);
        }
    }
    
    /**
     * Builds the task network according to the OperationalStance
     * selected previously using the analyzeTask method.
     * 
     * @param u : the unit which is going to receive the orders
     * @param g : the objective position of the tile
     * @param os : the selected OperationalStance
     */
    public void buildTaskNetwork(Tile g, OperationForm of) {
        
        FormationTaskNode top = new FormationTaskNode(new FormationTask(of, g), formation);
        
        formationTaskAnalyzer.analyzeTask(top);
        
        //top.getManeuver().useManeuver(top);
        
        for(UnitTaskNode node : top.getSubtasks()) {
            unitTaskAnalyzer.analyzeTask(node);
        }
        
        synchronizeMissions();
            
        assignMissions(top);
    }
    
    public void synchronizeMissions() {
        
    }
    
    public void assignMissions(FormationTaskNode tn) {
        
        for(UnitTaskNode node : tn.getSubtasks()) {
            for(AbstractAction action : node.getActions()) {
                node.getUnit().getPendingActions().add(action);
            }
        }
    }
}
