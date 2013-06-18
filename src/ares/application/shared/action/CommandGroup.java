package ares.application.shared.action;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface CommandGroup {

    Integer getMnemonic();

    String getName();

    String getText();

    Command[] getCommands();
}
