package ares.application.boundaries.view;

import ares.application.models.ScenarioModel;
import ares.application.models.forces.FormationModel;
import ares.application.models.forces.UnitModel;
import ares.platform.view.View;
import java.awt.event.MouseListener;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface MiniMapViewer extends View {

    void loadScenario(ScenarioModel scenario);

    void updateScenario(ScenarioModel scenario);

    void flush();

    void addMouseListener(MouseListener listener);

    void centerViewOn(UnitModel selectedUnit, FormationModel selectedFormation);
    
    public void setProfile(int profile);
}
