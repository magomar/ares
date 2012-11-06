package ares.ai.planner;

import ares.scenario.forces.Unit;
import java.util.List;

/**
 *
 * @author Saúl Esteban
 */
public class TaskNode {
    
    private Task task;
    private TaskNode parent;
    private List<TaskNode> subtasks;
    private Unit unit;
    
    public TaskNode(Task t, Unit u) {
        task = t;
        unit = u;
    }
    
    public TaskNode(Task t, TaskNode p, Unit u) {
        task = t;
        parent = p;
        unit = u;
    }
    
    public Task getTask() {
        return task;
    }
    
    public TaskNode getParent() {
        return parent;
    }
    
    public void addSubtask(TaskNode t) {
        subtasks.add(t);
    }
    
    public List<TaskNode> getSubtasks() {
        return subtasks;
    }
    
    public Unit getUnit() {
        return unit;
    }
}