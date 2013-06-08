package ares.application.shared.boundaries.viewers.layerviewers;

import ares.application.shared.models.forces.FormationModel;
import ares.application.shared.models.forces.UnitModel;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface SelectionLayerViewer {

    void updateSelectedUnit(UnitModel selectedUnit, FormationModel selectedFormation);
}
