package ares.platform.application;

import javax.swing.ImageIcon;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface Command {

    public String getName();

    public String getText();

    public String getDesc();

    public Integer getMnemonic();

    public ImageIcon getImageIcon();
}
