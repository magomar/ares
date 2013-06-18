package ares.application.shared.gui.views;

import java.awt.Component;
import java.awt.Dimension;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface View<C extends Component> {

    void setVisible(boolean visible);

    boolean isVisible();
    
    boolean isFocusable();

    C getContentPane();

    void setPreferredSize(Dimension size);
}