package ares.application.boundaries.view;

import ares.application.models.ScenarioModel;
import ares.application.models.board.TileModel;


/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface BoardViewer {
    public void loadScenario(ScenarioModel scenario);
    public void updateScenario(ScenarioModel scenario);
//    public void updateUnits(Collection<UnitModel> units);
    public void closeScenario();
    public void updateTile(TileModel tile);
}
