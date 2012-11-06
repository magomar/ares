package ares.platform.view;

import ares.platform.application.Command;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class ComponentFactory {

    public static JFrame frame(String title, JComponent contentPane, JMenuBar menuBar, JToolBar toolBar) {
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
        if (contentPane != null) {
            frame.getContentPane().add(contentPane, BorderLayout.CENTER);
        }
        frame.setPreferredSize(new Dimension(800, 600));
        return frame;
    }

    public static JFrame showFrame(JFrame frame) {
        frame.pack();
        centerFrame(frame);
        frame.setVisible(true);
        return frame;
    }

    public static JFrame centerFrame(JFrame frame) {
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
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
            internalFrame.setContentPane(new JScrollPane(contentPane));
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

    public static JMenu menu(String text, char mnemonic, boolean enabled, JMenuItem... items) {
        JMenu menu = new JMenu(text);
        menu.setMnemonic(mnemonic);
        for (JMenuItem item : items) {
            menu.add(item);
        }
        menu.setEnabled(enabled);
        return menu;
    }

    public static JMenu menu(String text, char mnemonic, JMenuItem... items) {
        return menu(text, mnemonic, true, items);
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
        menuItem.setName(command.name());
        menuItem.setActionCommand(command.name());
        menuItem.addActionListener(listener);
        menuItem.setEnabled(enabled);
        return menuItem;
    }

    public static JMenuItem menuItem(Command command, ActionListener listener) {
        return menuItem(command, listener, true);
    }
}
