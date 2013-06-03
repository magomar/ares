package ares.application.boundaries.interactor;

import java.awt.Container;
import java.util.logging.Logger;
import javax.swing.Action;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface Interactor {
    void addMenu(String name, String text, Integer mnemonic, Action[] actions);
    void addMainActions(Action[] actions);
    void addActions(Action[] actions);
    void registerLogger(Logger logger);
    Container getGUIContainer();
}
