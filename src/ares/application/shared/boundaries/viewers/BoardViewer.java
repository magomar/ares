package ares.application.shared.boundaries.viewers;

import ares.application.shared.gui.views.View;
import ares.application.shared.models.forces.ForceModel;
import ares.application.shared.models.forces.FormationModel;
import ares.application.shared.models.forces.UnitModel;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface BoardViewer extends View {

    void updateSelectedUnit(UnitModel selectedUnit, FormationModel selectedFormation, ForceModel selectedForce);

    void centerViewOn(UnitModel selectedUnit, FormationModel selectedFormation);

    void addMouseListener(MouseListener listener);

    void addMouseMotionListener(MouseMotionListener listener);

    void setLayerVisible(int layer, boolean visible);

    boolean isLayerVisible(int layer);

    void switchLayerVisible(int layer);
}
