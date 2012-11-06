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
public class ManeuverFrontalAttack extends ManeuverAbstract{
    
    public ManeuverFrontalAttack() {
        
        operations = new LinkedList<>();
        operations.add(OperationForm.ASSAULT);
    }
    
    @Override
    public int checkPreconditions() {
        //TODO check if goal's nearby tiles house enemy units on flanks and the rear 
        
        int allies = 0, enemies = 0;
        
        if(node.getFormation().getLineUnits().size() > 0) {
            allies += node.getFormation().getLineUnits().size();
        }
        else {
//            return 0;
        }
        
        if(node.getFormation().getSupportUnits().size() > 0) {
            allies += node.getFormation().getSupportUnits().size();
        }
        
        if(node.getFormation().getServiceUnits().size() > 0) {
            allies += node.getFormation().getServiceUnits().size();
        }
        
        if(node.getFormation().getConditionalReinforcements().size() > 0) {
            allies += node.getFormation().getConditionalReinforcements().size();
        }
        
        if(node.getTask().getGoal().getSurfaceUnits().size() > 0) {
            if(!node.getTask().getGoal().getOwner().equals(node.getFormation().getForce())) {
                enemies += node.getTask().getGoal().getSurfaceUnits().size();

                for(Tile neighbour : node.getTask().getGoal().getNeighbors().values()) {
                    if(neighbour.getSurfaceUnits().size() > 0) {
                        if(!neighbour.getOwner().equals(node.getFormation().getForce())) {
                            enemies += node.getTask().getGoal().getSurfaceUnits().size();
                        }
                    }
                }
            }
        }
        
        return 7;// allies - enemies;
    }
    
    @Override
    public void plan() {
        //se asume de momento objetivo descubierto
        //buscar posiciones para el ataque
        Tile mainPosition = null;
        LinkedList<Tile> sidePositions = new LinkedList<>();
        LinkedList<Tile> enemies = new LinkedList<>();
        LinkedList<Tile> sideObjectives = new LinkedList<>();
        
        for(Tile neighbour : node.getTask().getGoal().getNeighbors().values()) {
            
            if(neighbour.getSurfaceUnits().isEmpty() ||
               (neighbour.getSurfaceUnits().size() > 0 &&
                neighbour.getOwner().equals(node.getFormation().getForce()))) {
                boolean main = true;
                for(Tile neighboursNeighbour : neighbour.getNeighbors().values()) {
                    if(!neighboursNeighbour.equals(node.getTask().getGoal()) && 
                        neighboursNeighbour.getSurfaceUnits().size() > 0 &&
                       !neighboursNeighbour.getOwner().equals(node.getFormation().getForce())) {
                        main = false;
                        break;
                    }
                }
                if(main) {
                    mainPosition = neighbour;
                }
            }
            else {
                enemies.add(neighbour);
            }
                    
        }
        for(Tile neighbour : mainPosition.getNeighbors().values()) {
            if(neighbour.getSurfaceUnits().isEmpty()) {
                for(Tile enemy : enemies) {
                    if(neighbour.getNeighbors().values().contains(enemy) && 
                       neighbour.getNeighbors().values().contains(mainPosition)) {
                        sidePositions.add(neighbour);
                    }
                }
            }
        }
        
        //comprobar cuantas unidades de combate hay
        int formationPower = 0;
        for(Unit unit : node.getFormation().getLineUnits()) {
            formationPower += unit.getAntiPersonnel() + unit.getAntiTank();
        }
        double part = formationPower / (sidePositions.size() + 1);
        //TODO choose soft units for mainposition
        int power = 0;
        int turn = 0;
        for(Unit unit : node.getFormation().getLineUnits()) {
            UnitTask task = null;
            if(turn == 0) {//power < part) {
                task = new UnitTask(TacticalMissionType.ASSAULT,mainPosition,node.getTask().getGoal());
            }
            else if (turn == 1) {//power > part && power < part*2) {
                for(Tile enemy : enemies){ 
                    if(sidePositions.getFirst().getNeighbors().values().contains(enemy)) {
                        task = new UnitTask(TacticalMissionType.ATTACK_BY_FIRE,sidePositions.getFirst(),enemy);
                        break;
                    }
                }
            }
            else {
                for(Tile enemy : enemies){ 
                    if(sidePositions.getLast().getNeighbors().values().contains(enemy)) {
                        task = new UnitTask(TacticalMissionType.ATTACK_BY_FIRE,sidePositions.getLast(),enemy);
                        break;
                    }
                }
            }
            UnitTaskNode unitNode = new UnitTaskNode(task,unit);
            node.addSubtask(unitNode);
            power += unit.getAntiPersonnel() + unit.getAntiTank();
            turn++;
            if(turn > 2) {
                turn = 0;
            } 
        }
        //para cada unidad de apoyo con capacidad de bombardeo
        //asignar mision de apoyo con posicion segun rango        
//        Unit unitForSupport = (Unit)node.getFormation().getLineUnits().toArray()[0];
//       
//        if(node.getFormation().getSupportUnits().size() > 0) {
//            for(Unit unit : node.getFormation().getSupportUnits()) {
//                if(unit.getType().getCapabilities().contains(Capability.BOMBARDMENT)) {
//                    UnitTask task = new UnitTask(TacticalMissionType.SUPPORT_BY_FIRE,mainPosition,node.getTask().getGoal());
//                    UnitTaskNode unitNode = new UnitTaskNode(task,unit);
//                    unitNode.setUnitForSupport(unitForSupport);
//                    node.addSubtask(unitNode);
//                }
//            }
//        }
    }
}
