package ares.application.views;

import ares.application.boundaries.view.CommandBarViewer;
import ares.application.commands.FileCommands;
import ares.application.gui_components.layers.WelcomeScreen;
import ares.platform.view.AbstractView;
import ares.platform.view.ComponentFactory;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class WelcomeScreenView extends AbstractView<JPanel> implements CommandBarViewer {

    // Buttons in the main menu
//    private LinkedList<TranslucidButton> buttons = new LinkedList<>();
    protected Map<String, EventListenerList> actionListeners = new HashMap<>();

    @Override
    protected JPanel layout() {
        JPanel buttonsPanel = new WelcomeScreen();
        buttonsPanel.add(ComponentFactory.translucidButton(FileCommands.NEW_SCENARIO, this));
        buttonsPanel.add(ComponentFactory.translucidButton(FileCommands.LOAD_SCENARIO, this));
        buttonsPanel.add(ComponentFactory.translucidButton(FileCommands.SETTINGS, this));
        buttonsPanel.add(ComponentFactory.translucidButton(FileCommands.EXIT, this));
        return buttonsPanel;
    }

//    public void setMenuButtons(ArrayList<Pair<Command, ActionListener>> buttonListener) {
//
//        // Button dimension
//
//        // WelcomeScreen panel is inside a LayeredPane, which is inside a JRootPane
////        JFrame frame = (JFrame) this.getParent().getParent().getParent();
//        // Where the first button will be placed
////        Point buttonPos = new Point(frame.getSize().width / 2, frame.getSize().height / 2);
//        // Vertical gap between buttons
//        int vGap = 60;
//
//        int i = 0;
//        for (Pair<Command, ActionListener> pair : buttonListener) {
//            TranslucidButton b = new TranslucidButton(pair.getLeft().getText());
//            b.setAlignmentX(Component.CENTER_ALIGNMENT);
//            b.addActionListener(pair.getRight());
////            b.setPreferredSize(dim);
//            b.setMaximumSize(BUTTON_DIMENSION);
//            b.setMinimumSize(BUTTON_DIMENSION);
////            b.setSize(dim);
////            b.setLocation(buttonPos.x, buttonPos.y + vGap * i);
//            i++;
//            contentPane.add(b);
//        }
//
//    }
    protected Component getMenuElement(String elementName) {
        Component found = null;
        for (Component button : contentPane.getComponents()) {
            if (elementName.equals(button.getName())) {
                return button;
            }
        }
        return null;
    }

    @Override
    public void setCommandEnabled(String name, boolean enabled) {
        getMenuElement(name).setEnabled(enabled);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ActionListener commandListeners[] = actionListeners.get(ae.getActionCommand()).getListeners(ActionListener.class);
        for (ActionListener actionListener : commandListeners) {
            actionListener.actionPerformed(ae);
        }
    }

    @Override
    public void addActionListener(String actionCommand, ActionListener actionListener) {
        if (actionListeners.containsKey(actionCommand)) {
            actionListeners.get(actionCommand).add(ActionListener.class, actionListener);
        } else {
            EventListenerList newEventListenerList = new EventListenerList();
            newEventListenerList.add(ActionListener.class, actionListener);
            actionListeners.put(actionCommand, newEventListenerList);
        }
    }

    @Override
    public void removeActionListener(String actionCommand, ActionListener actionListener) {
        if (actionListeners.containsKey(actionCommand)) {
            actionListeners.get(actionCommand).remove(ActionListener.class, actionListener);
        }
    }
}
