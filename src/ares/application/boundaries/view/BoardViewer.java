package ares.application.boundaries.view;

import ares.application.models.ScenarioModel;
import ares.application.models.forces.UnitModel;
import java.util.Collection;


/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface BoardViewer {
    public void initializeBoard(ScenarioModel scenario);
    public void updateUnits(Collection<UnitModel> units);
}
