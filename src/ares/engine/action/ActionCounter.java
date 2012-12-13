package ares.engine.action;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ActionCounter {

    private static ActionCounter INSTANCE = new ActionCounter();
    private static int counter = 0;

    private ActionCounter() {
    }

    
    public static int count() {
        return ++counter;
    }
    
}
