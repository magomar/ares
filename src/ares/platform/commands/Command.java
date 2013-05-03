package ares.platform.commands;

import javax.swing.Icon;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface Command {

    public String getName();

    public String getText();

    public String getDescription();

    public Integer getMnemonic();

    public Icon getIcon();
    
}
