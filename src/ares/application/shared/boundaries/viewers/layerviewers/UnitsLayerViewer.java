package ares.application.shared.boundaries.viewers.layerviewers;

import ares.application.shared.models.ScenarioModel;
import ares.application.shared.models.board.TileModel;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface UnitsLayerViewer extends ImageLayerViewer {

    void updateScenario(ScenarioModel scenario);

    void updateUnitStack(TileModel tile);
}
