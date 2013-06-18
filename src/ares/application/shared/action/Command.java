package ares.application.shared.action;

import javax.swing.*;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface Command {

    String getName();

    String getText();

    String getDescription();

    Integer getMnemonic();
    
    KeyStroke getAccelerator();

    Icon getLargeIcon();
    
    Icon getSmallIcon();
    
}
