package ares.application.boundaries.view;

import ares.application.models.ScenarioModel;
import ares.platform.scenario.forces.Unit;
import javax.swing.event.TreeSelectionListener;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface OOBViewer {

    void loadScenario(ScenarioModel scenario);

    void updateScenario(ScenarioModel scenario);
    
    void addTreeSelectionListener(TreeSelectionListener listener);
    
    void select(Unit unit);

    public void flush();
}
