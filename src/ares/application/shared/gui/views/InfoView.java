package ares.application.shared.gui.views;

import ares.application.shared.boundaries.viewers.InfoViewer;
import ares.application.shared.gui.components.ScenarioInfoPane;
import ares.application.shared.models.board.TileModel;
import ares.application.shared.models.forces.UnitModel;
import java.util.Calendar;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class InfoView extends AbstractView<JPanel> implements InfoViewer {

//    private JTextArea scenInfo;
    private JTextArea unitInfo;
    private JTextArea tileInfo;
    private ScenarioInfoPane scenInfo;
    
    @Override
    protected JPanel layout() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        scenInfo = new ScenarioInfoPane();
        tileInfo = new JTextArea();
        unitInfo = new JTextArea();
        tileInfo.setEditable(false);
        unitInfo.setEditable(false);
        panel.add(scenInfo);
        panel.add(tileInfo);
        panel.add(unitInfo);
        return panel;
    }

    @Override
    public void updateTileInfo(TileModel tile) {
        tileInfo.setText(tile.getDescription());
    }

    @Override
    public void updateScenarioInfo(Calendar calendar) {
        scenInfo.update(calendar);
    }

    @Override
    public void clear() {
        tileInfo.setText("");
        unitInfo.setText("");
    }

    @Override
    public void updateUnitInfo(UnitModel unitModel) {
        unitInfo.setText(unitModel.getDescription());
    }
}
