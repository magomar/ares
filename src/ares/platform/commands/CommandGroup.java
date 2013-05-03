package ares.platform.commands;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface CommandGroup {

    char getMnemonic();

    String getName();

    String getText();
    
    Command[] getCommands();
}
