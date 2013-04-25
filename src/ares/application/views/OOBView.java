package ares.application.views;

import ares.application.boundaries.view.OOBViewer;
import ares.application.models.ScenarioModel;
import ares.application.models.forces.ForceModel;
import ares.platform.view.AbstractView;
import ares.platform.view.ComponentFactory;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class OOBView extends AbstractView<JScrollPane> implements OOBViewer {

    private JTree[] oobTree;
    private JTabbedPane tabbedPane;

    @Override
    protected JScrollPane layout() {
        tabbedPane = new JTabbedPane();
        return new JScrollPane(tabbedPane);
    }

    @Override
    public void loadScenario(ScenarioModel scenario) {
        ForceModel[] force = scenario.getForceModel();
        oobTree = new JTree[force.length];
        int index = 0;
        for (ForceModel forceModel : force) {
            JTree tree = ComponentFactory.tree(forceModel.getTreeModel());
            tree.setName(forceModel.getName());
            tabbedPane.add(tree);
            oobTree[index++] = tree;
        }
    }

    @Override
    public void updateScenario(ScenarioModel scenario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
