package ares.application.shared.boundaries.viewers;

import ares.application.shared.models.ScenarioModel;
import ares.application.shared.models.board.TileModel;
import ares.application.shared.models.forces.ForceModel;
import ares.application.shared.models.forces.FormationModel;
import ares.application.shared.models.forces.UnitModel;
import ares.platform.engine.algorithms.pathfinding.Path;
import ares.application.shared.gui.views.View;
import ares.platform.engine.algorithms.pathfinding.Node;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface BoardViewer extends View {

    final static int TERRAIN = 0;
    final static int GRID = 1;
    final static int SELECTION = 2;
    final static int ARROWS = 3;
    final static int UNITS = 4;
    final static int PATH_SEARCH=5;

    void loadScenario(ScenarioModel scenario);

    void updateScenario(ScenarioModel scenario);

    void flush();

    void updateUnitStack(TileModel tile);

    void addMouseListener(MouseListener listener);

    void addMouseMotionListener(MouseMotionListener listener);

    void updateCurrentOrders(Path path);

    void updateLastOrders(Path path);
    
    void updateLastPathSearch(Collection<Node> openSet, Collection<Node> closedSet);

    void updateSelectedUnit(UnitModel selectedUnit, FormationModel selectedFormation, ForceModel selectedForce);

    void centerViewOn(UnitModel selectedUnit, FormationModel selectedFormation);

    void setLayerVisible(int layer, boolean visible);

    boolean isLayerVisible(int layer);
    
    void switchLayerVisible(int layer);
    
    void setProfile(int profile);
}
