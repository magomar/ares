package ares.ai.planner;

import ares.engine.command.OperationForm;
import ares.scenario.board.Tile;

/**
 *
 * @author Saúl Esteban
 */
public class FormationTask extends Task{
    
    private OperationForm operationForm;
    
    public FormationTask(OperationForm of, Tile g) {
        super(g);
        operationForm = of;
    }
    
    public OperationForm getOperationForm() {
        return operationForm;
    }
}
