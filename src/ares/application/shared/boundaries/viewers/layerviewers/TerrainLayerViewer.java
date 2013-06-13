package ares.application.shared.boundaries.viewers.layerviewers;

import ares.application.shared.models.ScenarioModel;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface TerrainLayerViewer extends ImageLayerViewer {

    public static final String NAME = "TERRAIN_LAYER";

    void updateScenario(ScenarioModel scenario);
}
