package ares.application.shared.boundaries.viewers;

import ares.application.shared.gui.views.View;
import ares.application.shared.models.board.TileModel;
import ares.application.shared.models.forces.UnitModel;

import javax.swing.*;
import java.util.Calendar;

/**
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface InfoViewer extends View<JPanel> {

    void updateUnitInfo(UnitModel unitModel);

    void updateTileInfo(TileModel tile);

    void updateScenarioInfo(Calendar calendar);

    void clear();
}
