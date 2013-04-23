package ares.application.views;

import ares.application.boundaries.view.OOBViewer;
import ares.application.models.ScenarioModel;
import ares.application.models.forces.ForceModel;
import ares.application.models.forces.FormationModel;
import ares.platform.view.AbstractView;
import ares.platform.view.ComponentFactory;
import java.awt.Component;
import java.util.List;
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
            oobTree[index++] = ComponentFactory.tree(forceModel, null);
//            List<FormationModel> formations = forceModel.getFormationModels();
//            for (FormationModel formationModel : formations) {
//                
//            }
        }
    }

    @Override
    public void updateScenario(ScenarioModel scenario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
