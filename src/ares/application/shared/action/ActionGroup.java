package ares.application.shared.action;

import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.components.TranslucidButton;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenu;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class ActionGroup {

    private final String name;
    private final String text;
    private final Integer mnemonic;
    private final Action[] actions;

    public ActionGroup(String name, String text, Integer mnemonic, Action[] actions) {
        this.name = name;
        this.text = text;
        this.mnemonic = mnemonic;
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public Integer getMnemonic() {
        return mnemonic;
    }

    public Action[] getActions() {
        return actions;
    }

    public JMenu createMenu() {
        return ComponentFactory.menu(name, text, mnemonic, actions);
    }

    public JButton[] createToolBarButtons() {
        JButton[] buttons = new JButton[actions.length];
        for (int i = 0; i < actions.length; i++) {
            buttons[i] = ComponentFactory.button(actions[i]);
        }
        return buttons;
    }

    public JButton[] createMainMenuButtons() {
        List<JButton> buttons = new ArrayList<>();
        for (int i = 0; i < actions.length; i++) {
            Action action = actions[i];
            if (action.isEnabled())
            buttons.add(ComponentFactory.translucidButton(action));
        }
        return buttons.toArray(new TranslucidButton[buttons.size()]);
    }
}
