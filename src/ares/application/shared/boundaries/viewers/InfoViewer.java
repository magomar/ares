package ares.application.shared.boundaries.viewers;

import ares.application.shared.models.board.TileModel;
import ares.application.shared.models.forces.UnitModel;
import ares.application.shared.gui.views.View;
import java.util.Calendar;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface InfoViewer extends View {

    void updateUnitInfo(UnitModel unitModel);

    void updateTileInfo(TileModel tile);

    void updateScenarioInfo(Calendar calendar);

    void clear();
}
