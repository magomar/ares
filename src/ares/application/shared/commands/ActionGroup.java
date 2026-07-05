package ares.application.shared.commands;

import ares.application.shared.gui.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class ActionGroup {
    private final static Dimension MAIN_MENU_BUTTON_SIZE = new Dimension(250, 50);
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
            if (action.isEnabled()) {
//                JButton newButton = ComponentFactory.transparentButton(action, 1f);
                JButton newButton = ComponentFactory.translucentButton(action);
                newButton.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
                newButton.setSize(MAIN_MENU_BUTTON_SIZE);
                newButton.setMinimumSize(MAIN_MENU_BUTTON_SIZE);
                newButton.setMaximumSize(MAIN_MENU_BUTTON_SIZE);
//                newButton.setFont(newButton.getFont().deriveFont(Font.BOLD, 16));
//                newButton.setForeground(Color.BLACK);
//                newButton.setBorder(new RoundedBorder(10));
                buttons.add(newButton);
            }
        }
        return buttons.toArray(new JButton[buttons.size()]);
    }

}
