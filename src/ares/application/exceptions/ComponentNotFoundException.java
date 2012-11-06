package ares.application.exceptions;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ComponentNotFoundException extends Exception {

    public ComponentNotFoundException(String componentName) {
        super(componentName + " not found !");
    }
    
}
