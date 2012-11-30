package ares.platform.view;

import ares.platform.application.Command;
import ares.platform.application.MenuEntry;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class ComponentFactory {

    /**
     * An "inter-component" horizontal space, also used as the standard inset from a frame to its content. This is the
     * nominal "Em" space.
     */
    public final static int STANDARD_SPACE = 12;
    /**
     * The highlight color for invalid fields.
     */
    public final static Color HIGHLIGHT_COLOR = new Color(255, 240, 240);

    public static JFrame frame(String title, JComponent contentPane, JMenuBar menuBar, JToolBar toolBar) {
        JFrame frame = frame(title, menuBar, toolBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (contentPane != null) {
            frame.getContentPane().add(contentPane, BorderLayout.CENTER);
        }
        return frame;
    }

    public static JFrame frame(String title, JMenuBar menuBar, JToolBar toolBar) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (title != null) {
            frame.setTitle(title);
        }
        if (menuBar != null) {
            frame.getContentPane().add(menuBar, BorderLayout.NORTH);
        }
        if (toolBar != null) {
            frame.getContentPane().add(toolBar, BorderLayout.NORTH);
        }

        frame.setPreferredSize(new Dimension(800, 600));
        return frame;
    }

    public static JPanel panel(LayoutManager layoutManager, String title) {
        JPanel panel = new JPanel();
        if (layoutManager != null) {
            panel.setLayout(layoutManager);
        }
        if (title != null) {
            panel.setBorder(new TitledBorder(title));
        }
        return panel;
    }

    public static JLabel label(String text, Font font) {
        JLabel label = new JLabel();
        if (text != null) {
            label.setText(text);
        }
        if (font != null) {
            label.setFont(font);
        }
        return label;
    }

    public static JTextField textField(int columnSize, boolean editable) {
        JTextField textField = new JTextField();
        if (columnSize > -1) {
            textField.setColumns(columnSize);
        }
        textField.setEditable(editable);
        return textField;
    }

    public static JButton button(String label, Action action) {
        JButton button = new JButton();
        if (label != null) {
            button.setText(label);
        }
        if (action != null) {
            button.setAction(action);
        }
        return button;
    }

    public static JPanel buttons(JButton... buttons) {
        JPanel panel = panel(new FlowLayout(), null);
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    public static JTable table(TableModel tableModel) {
        JTable table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (tableModel != null) {
            table.setModel(tableModel);
        }
        return table;
    }

    public static JInternalFrame internalFrame(Container contentPane, String title) {
        JInternalFrame internalFrame = new JInternalFrame();
        internalFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        internalFrame.setResizable(true);
        internalFrame.setClosable(true);
        internalFrame.setMaximizable(true);
        internalFrame.setIconifiable(true);
        internalFrame.setPreferredSize(new Dimension(600, 400));
        if (contentPane != null) {
            internalFrame.setContentPane(contentPane);
        }
        if (title != null) {
            internalFrame.setTitle(title);
        }
        return internalFrame;
    }

    public static JTree tree(TreeModel treeModel, MouseListener mouseListener) {
        JTree tree = new JTree();
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        if (treeModel != null) {
            tree.setModel(treeModel);
        }
        if (mouseListener != null) {
            tree.addMouseListener(mouseListener);
        }
        return tree;
    }

    public static JPopupMenu popupMenu(Action... actions) {
        JPopupMenu popupMenu = new JPopupMenu();
        for (Action action : actions) {
            popupMenu.add(action);
        }
        return popupMenu;
    }

    public static JMenu menu(MenuEntry menuEntry, boolean enabled, JMenuItem... items) {
        JMenu menu = new JMenu(menuEntry.getText());
        menu.setName(menuEntry.getName());
        menu.setMnemonic(menuEntry.getMnemonic());
        for (JMenuItem item : items) {
            menu.add(item);
        }
        menu.setEnabled(enabled);
        return menu;
    }

    public static JMenu menu(MenuEntry menuEntry, JMenuItem... items) {
        return menu(menuEntry, true, items);
    }

    public static JMenuBar menuBar(JMenu... menus) {
        JMenuBar menuBar = new JMenuBar();
        for (JMenu menu : menus) {
            menuBar.add(menu);
        }
        return menuBar;
    }

    public static JMenuItem menuItem(Command command, ActionListener listener, boolean enabled) {
        JMenuItem menuItem = new JMenuItem(command.getText(), command.getMnemonic());
//        menuItem.getAction().putValue(AbstractAction.SHORT_DESCRIPTION,command.getDesc());
        menuItem.setName(command.getName());
        menuItem.setActionCommand(command.getName());
        menuItem.addActionListener(listener);
        menuItem.setEnabled(enabled);
        return menuItem;
    }

    public static JMenuItem menuItem(Command command, ActionListener listener) {
        return menuItem(command, listener, true);
    }

    //----------------------------------------------------------------------------
//  Factories for standard spacing objects
//----------------------------------------------------------------------------
    /**
     * The border to be used around a dialog's content.
     */
    public static Border dialogBorder() {
        return BorderFactory.createEmptyBorder(
                STANDARD_SPACE, STANDARD_SPACE,
                STANDARD_SPACE, STANDARD_SPACE);
    }

    /**
     * The border to be used around a group of components in a dialog. Assumes that there will be a label above the
     * group, and that there won't be decoration between groups.
     */
    public static Border dialogGroupBorder() {
        return BorderFactory.createEmptyBorder(
                STANDARD_SPACE / 2,
                STANDARD_SPACE,
                STANDARD_SPACE * 3 / 2,
                0);
    }

    /**
     * A horizontal strut between buttons on the same line.
     */
    public static Component interButtonSpace() {
        return Box.createHorizontalStrut(STANDARD_SPACE);
    }

//----------------------------------------------------------------------------
//  Factories for consistent GUI objects
//----------------------------------------------------------------------------
    /**
     * Builds a standard modal input dialog, with content and buttons to accept or cancel that content.
     */
    public static JDialog newModalDialog(
            JFrame owner, String title,
            JPanel content, JButton... buttons) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(0.0f);
        buttonPanel.add(Box.createHorizontalGlue());
        for (int ii = 0; ii < buttons.length; ii++) {
            if (ii > 0) {
                buttonPanel.add(interButtonSpace());
            }
            buttonPanel.add(buttons[ii]);
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(dialogBorder());
        panel.add(content);
        panel.add(Box.createVerticalStrut(18));
        panel.add(buttonPanel);

        JDialog theDialog = new JDialog(owner, title, true);
        theDialog.setContentPane(panel);
        theDialog.pack();
        return theDialog;
    }

    /**
     * Builds a standard modal input dialog, with content and buttons to accept or cancel that content.
     */
    public static JDialog newModalDialog(
            JFrame owner, String title,
            JPanel content, Action... actions) {
        JButton[] buttons = new JButton[actions.length];
        for (int ii = 0; ii < actions.length; ii++) {
            buttons[ii] = new JButton(actions[ii]);
        }
        return newModalDialog(owner, title, content, buttons);
    }
}
