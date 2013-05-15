package ares.application.boundaries.view;

import ares.application.models.board.TileModel;
import ares.application.models.forces.UnitModel;
import ares.platform.view.View;
import java.util.Calendar;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface InfoViewer extends View {

    void updateUnitInfo(UnitModel unitModel);

    void updateTileInfo(TileModel tile);

    void updateScenarioInfo(String text, Calendar calendar);

    void clear();
}
