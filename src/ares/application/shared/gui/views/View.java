package ares.application.shared.gui.views;

import java.awt.*;

/**
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface View<C extends Component> {

    void setVisible(boolean visible);

    boolean isVisible();

    void switchVisible();

    boolean isFocusable();

    C getContentPane();

    void setPreferredSize(Dimension size);

    Dimension getPreferredSize();
}
