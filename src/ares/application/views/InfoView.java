package ares.application.views;

import ares.application.boundaries.view.InfoViewer;
import ares.application.models.board.TileModel;
import ares.application.models.forces.UnitModel;
import ares.platform.view.AbstractView;
import java.util.Calendar;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import temp.AnalogClockDayNight;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class InfoView extends AbstractView<JScrollPane> implements InfoViewer {

    private JTextArea scenInfo;
    private JTextArea unitInfo;
    private JTextArea tileInfo;
    private AnalogClockDayNight clock;
    
    @Override
    protected JScrollPane layout() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        scenInfo = new JTextArea();
        tileInfo = new JTextArea();
        unitInfo = new JTextArea();
        clock = new AnalogClockDayNight();
        scenInfo.setEditable(false);
        tileInfo.setEditable(false);
        unitInfo.setEditable(false);
        panel.add(clock);
//        panel.add(scenInfo);
//        panel.add(tileInfo);
//        panel.add(unitInfo);
        return new JScrollPane(panel);
    }

    @Override
    public void updateTileInfo(TileModel tile) {
        tileInfo.setText(tile.getDescription());
    }

    @Override
    public void updateScenarioInfo(String text, Calendar calendar) {
        scenInfo.setText(text);
        clock.update(calendar);
        
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
