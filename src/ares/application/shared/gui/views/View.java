package ares.application.shared.gui.views;

import java.awt.Dimension;
import javax.swing.JComponent;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface View<C extends JComponent> {

    void setVisible(boolean visible);

    boolean isVisible();
    
    boolean isFocusable();

    C getContentPane();

    void setPreferredSize(Dimension size);
}
