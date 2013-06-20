package ares.application.shared.boundaries.viewers.layerviewers;

import ares.application.shared.models.ScenarioModel;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface PlacesLayerViewer extends ImageLayerViewer {

    public static final String NAME = "PLACES_LAYER";

    public void updateScenario(ScenarioModel scenarioModel);
}
