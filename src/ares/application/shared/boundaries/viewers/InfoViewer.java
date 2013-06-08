package ares.application.shared.boundaries.viewers;

import ares.application.shared.models.board.TileModel;
import ares.application.shared.models.forces.UnitModel;
import ares.application.shared.gui.views.View;
import java.util.Calendar;
import javax.swing.JPanel;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface InfoViewer extends View<JPanel> {

    void updateUnitInfo(UnitModel unitModel);

    void updateTileInfo(TileModel tile);

    void updateScenarioInfo(Calendar calendar);

    void clear();
}
