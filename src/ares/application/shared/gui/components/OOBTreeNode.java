package ares.application.shared.gui.components;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class OOBTreeNode extends DefaultMutableTreeNode {

    /**
     * The icon which is displayed on the JTree object. open, close, leaf icon.
     */
    private ImageIcon icon;

    public OOBTreeNode(Object userObject) {
        super(userObject);
    }

    public OOBTreeNode(ImageIcon icon) {
        this.icon = icon;
    }

    public OOBTreeNode(Object userObject, ImageIcon icon) {
        super(userObject);
        this.icon = icon;
    }

    public OOBTreeNode(Object userObject, ImageIcon icon, boolean allowsChildren) {
        super(userObject, allowsChildren);
        this.icon = icon;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
