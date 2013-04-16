package ares.application.boundaries.view;

import ares.application.models.ScenarioModel;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface OOBViewer {

    public void loadScenario(ScenarioModel scenario);

    public void updateScenario(ScenarioModel scenario);
}
