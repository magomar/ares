package ares.application.boundaries.view;

import ares.application.models.board.TileModel;
import ares.application.models.forces.UnitModel;
import ares.platform.view.View;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface InfoViewer extends View {

    public void updateUnitInfo(UnitModel unitModel);

    public void updateTileInfo(TileModel tile);

    public void updateScenarioInfo(String text);

    public void clear();
}
