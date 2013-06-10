package ares.application.shared.boundaries.viewers;

import ares.application.shared.boundaries.viewers.layerviewers.LayeredImageViewer;
import ares.application.shared.models.forces.UnitModel;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface BoardViewer extends LayeredImageViewer {

    public void centerViewOn(UnitModel selectedUnit);
}
