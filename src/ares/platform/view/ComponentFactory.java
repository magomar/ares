package ares.platform.view;

import ares.application.gui.menu.TranslucidButton;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.TableModel;
import javax.swing.tree.*;

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
    /**
     * Size of split dividers
     */
    public static final int SPLIT_DIVIDER_SIZE = 5;

    //------------------------------------------------
    // Factories for frames
    //--------------------------------------------------
    public static JFrame frame(String title, JComponent contentPane, JMenuBar menuBar, JToolBar toolBar) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (contentPane != null) {
            frame.setContentPane(contentPane);
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
            frame.setJMenuBar(menuBar);
        }
        if (toolBar != null) {
//            frame.getContentPane().add(toolBar, BorderLayout.PAGE_START);
            frame.add(toolBar, BorderLayout.NORTH);
        }
        return frame;
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

    //------------------------------------------------
    // Factories for panels
    //--------------------------------------------------
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

    public static JSplitPane verticalSplitPane(boolean continuosLayout, Component topComponent, Component bottomComponent, double resizeWeight) {
        JSplitPane pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, continuosLayout, topComponent, bottomComponent);
        pane.setResizeWeight(resizeWeight);
        pane.setDividerSize(SPLIT_DIVIDER_SIZE);
        return pane;
    }

    public static JSplitPane horizontalSplitPane(boolean continuosLayout, Component leftComponent, Component rightComponent, double resizeWeight) {
        JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, continuosLayout, leftComponent, rightComponent);
        pane.setResizeWeight(resizeWeight);
        pane.setDividerSize(SPLIT_DIVIDER_SIZE);
        return pane;
    }

    //------------------------------------------------
    // Factories for text components
    //--------------------------------------------------
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

    //------------------------------------------------
    // Factories for multi-data components (Tables, Trees, etc.)
    //--------------------------------------------------
    public static JTable table(TableModel tableModel) {
        JTable table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (tableModel != null) {
            table.setModel(tableModel);
        }
        return table;
    }

    public static JTree tree(TreeModel treeModel) {
        JTree tree = new JTree();
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        if (treeModel != null) {
            tree.setModel(treeModel);
        }
        return tree;
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

    //------------------------------------------------
    // Factories for buttons and tool bars
    //--------------------------------------------------
    public static JButton translucidButton(Action action) {
        JButton button = new TranslucidButton(action);
        button.setName((String) action.getValue(Action.ACTION_COMMAND_KEY));
        button.setIcon((Icon) action.getValue(Action.LARGE_ICON_KEY));
        return button;
    }

    public static JButton button(Action action) {
        JButton button = new JButton(action);
        button.setName((String) action.getValue(Action.ACTION_COMMAND_KEY));
        if (action.getValue(Action.LARGE_ICON_KEY) != null) {
            button.setHideActionText(true);
        }
        return button;
    }

//    public static JPanel buttonsPanel(JButton... buttons) {
//        JPanel panel = panel(new FlowLayout(), null);
//        for (JButton button : buttons) {
//            panel.add(button);
//        }
//        return panel;
//    }
    public static JToolBar toolBar(String name, JButton... buttons) {
        return toolBar(name, SwingConstants.HORIZONTAL, buttons);
    }

    public static JToolBar toolBar(String name, int orientation, JButton... buttons) {
        JToolBar toolBar = new JToolBar(name, orientation);
        for (JButton button : buttons) {
            toolBar.add(button);
        }
        return toolBar;
    }

    //------------------------------------------------
    // Factories for menus 
    //--------------------------------------------------
//    public static JMenu menu(String name, String text, int mnemonic, JMenuItem... items) {
//        JMenu menu = new JMenu(text);
//        menu.setName(name);
//        menu.setMnemonic(mnemonic);
//        for (JMenuItem item : items) {
//            menu.add(item);
//        }
//        return menu;
//    }
    public static JMenu menu(String name, String text, Integer mnemonic, Action... actions) {
        JMenu menu = new JMenu(text);
        menu.setName(name);
        menu.setMnemonic(mnemonic);
        for (Action action : actions) {
            menu.add(menuItem(action));
        }
        return menu;
    }

    public static JMenuBar menuBar(JMenu... menus) {
        JMenuBar menuBar = new JMenuBar();
        for (JMenu menu : menus) {
            menuBar.add(menu);
        }
        return menuBar;
    }

    public static JMenuItem menuItem(Action action) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setName((String) action.getValue(Action.ACTION_COMMAND_KEY));
//        menuItem.setIcon((Icon) action.getValue(Action.SMALL_ICON));
//        menuItem.setToolTipText((String) action.getValue(Action.SHORT_DESCRIPTION));
//        menuItem.setText((String) action.getValue(Action.NAME));
//        Icon icon = (Icon) action.getValue(Action.SMALL_ICON);
//        if (icon != null) {
//            menuItem.setIcon(icon);
//        }
        return menuItem;
    }

    public static JPopupMenu popupMenu(Action... actions) {
        JPopupMenu popupMenu = new JPopupMenu();
        for (Action action : actions) {
            popupMenu.add(action);
        }
        return popupMenu;
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
    public static JDialog newModalDialog(JFrame owner, String title, JPanel content, Action... actions) {
        JButton[] buttons = new JButton[actions.length];
        for (int i = 0; i < actions.length; i++) {
            buttons[i] = ComponentFactory.button(actions[i]);
        }
        return newModalDialog(owner, title, content, buttons);
    }
}
