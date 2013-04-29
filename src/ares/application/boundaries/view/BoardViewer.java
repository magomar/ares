package ares.application.boundaries.view;

import ares.application.models.ScenarioModel;
import ares.application.models.board.TileModel;
import ares.application.models.forces.FormationModel;
import ares.application.models.forces.UnitModel;
import ares.engine.algorithms.pathfinding.Path;
import ares.platform.view.View;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface BoardViewer extends View {

    public void loadScenario(ScenarioModel scenario);

    public void updateScenario(ScenarioModel scenario);

    public void closeScenario();

    public void updateUnitStack(TileModel tile);

    public void addMouseListener(MouseListener listener);

    public void addMouseMotionListener(MouseMotionListener listener);

    public void updateCurrentOrders(Path path);
    
    public void updateLastOrders(Path path);

    public void updateSelectedUnit(UnitModel selectedUnit, FormationModel formation);

    public void centerViewOn(UnitModel unit, FormationModel formation);
}
