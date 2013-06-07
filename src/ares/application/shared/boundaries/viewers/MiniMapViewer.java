package ares.application.shared.boundaries.viewers;

import ares.application.shared.models.ScenarioModel;
import ares.application.shared.models.forces.FormationModel;
import ares.application.shared.models.forces.UnitModel;
import ares.application.shared.gui.views.View;
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
