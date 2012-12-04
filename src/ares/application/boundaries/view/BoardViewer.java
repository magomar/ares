package ares.application.boundaries.view;

import ares.application.models.ScenarioModel;
import ares.application.models.board.TileModel;
import ares.engine.algorithms.routing.Path;
import ares.platform.view.View;
import java.awt.event.MouseListener;


/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface BoardViewer extends View {
    public void loadScenario(ScenarioModel scenario);
    public void updateScenario(ScenarioModel scenario);
//    public void updateUnits(Collection<UnitModel> units);
    public void closeScenario();
    public void updateTile(TileModel tile);
    public void addMouseListener(MouseListener listener);
    public void updateArrowPath(ScenarioModel s,Path path);
}
