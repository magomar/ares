package ares.platform.application;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface Command {

    public String name();
    
    public String getText();

    public String getDesc();

    public Integer getMnemonic();
    
//    public ImageIcon getImageIcon();
}
