package ares.application.shared.boundaries;

import java.awt.Container;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface Interactor {
    void registerLogger(Logger logger);
    Container getGUIContainer();
}
