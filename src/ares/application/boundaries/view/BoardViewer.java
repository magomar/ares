package ares.application.boundaries.view;

import ares.application.models.ScenarioModel;
import ares.application.models.board.TileModel;
import ares.application.models.forces.ForceModel;
import ares.application.models.forces.FormationModel;
import ares.application.models.forces.UnitModel;
import ares.engine.algorithms.pathfinding.Path;
import ares.platform.view.View;
import ares.scenario.board.Tile;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface BoardViewer extends View {

    final static int TERRAIN = 0;
    final static int GRID = 1;
    final static int SELECTION = 2;
    final static int ARROWS = 3;
    final static int PATHFINDING = 4;
    final static int UNITS = 5;

    void loadScenario(ScenarioModel scenario);

    void updateScenario(ScenarioModel scenario);

    void closeScenario();

    void updateUnitStack(TileModel tile);

    void addMouseListener(MouseListener listener);

    void addMouseMotionListener(MouseMotionListener listener);

    void updateCurrentOrders(Path path);

    void updateLastOrders(Path path);
    
    void updateLastPathSearch(ArrayList<Tile> openSet, ArrayList<Tile> closedSet);

    void updateSelectedUnit(UnitModel selectedUnit, FormationModel selectedFormation, ForceModel selectedForce);

    void centerViewOn(UnitModel selectedUnit, FormationModel selectedFormation);

    void setLayerVisible(int layer, boolean visible);

    boolean isLayerVisible(int layer);
}
