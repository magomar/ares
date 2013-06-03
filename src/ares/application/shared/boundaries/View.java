package ares.application.shared.boundaries;

import java.awt.Container;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface View {

    public void setVisible(boolean visible);
    public Container getContentPane();
}
