package ares.application.shared.gui.components;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class OOBTreeCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        if (value instanceof OOBTreeNode) {
            OOBTreeNode node = (OOBTreeNode) value;
            if (node.getIcon() != null) {
                setIcon(((OOBTreeNode) value).getIcon());
            }
        }
        return this;
    }
}