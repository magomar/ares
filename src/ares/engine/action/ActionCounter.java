package ares.engine.action;

import ares.engine.realtime.Clock;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ActionCounter {

    private static ActionCounter INSTANCE = new ActionCounter();
    private static int counter = 0;

    // El constructor privado no permite que se genere un constructor por defecto
    // (con mismo modificador de acceso que la definición de la clase) 
    private ActionCounter() {
    }

//    public static ActionCounter getInstance() {
//        return INSTANCE;
//    }
    
    public static int count() {
        return ++counter;
    }
    
}
