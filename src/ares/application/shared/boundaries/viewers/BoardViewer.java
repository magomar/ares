package ares.application.shared.boundaries.viewers;

import ares.application.shared.boundaries.viewers.layerviewers.LayeredImageViewer;
import ares.application.shared.models.ScenarioModel;
import ares.application.shared.models.forces.UnitModel;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface BoardViewer extends LayeredImageViewer {

    void loadScenario(ScenarioModel scenario);

    public void forgetScenario();

    public void centerViewOn(UnitModel selectedUnit);
}
