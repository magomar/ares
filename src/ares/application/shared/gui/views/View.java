package ares.application.shared.gui.views;

import java.awt.Container;
import java.awt.Dimension;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface View {

    void setVisible(boolean visible);
    Container getContentPane();
    void setPreferredSize(Dimension size); 
}
