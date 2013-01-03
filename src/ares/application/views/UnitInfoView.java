package ares.application.views;

import ares.application.boundaries.view.UnitInfoViewer;
import ares.application.models.board.TileModel;
import ares.platform.view.AbstractView;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class UnitInfoView extends AbstractView<JScrollPane> implements UnitInfoViewer {

    private JTextArea scenInfo;
    private JTextArea unitInfo;
    private JTextArea tileInfo;

    @Override
    protected JScrollPane layout() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        scenInfo = new JTextArea();
        tileInfo = new JTextArea();
        unitInfo = new JTextArea();
        scenInfo.setEditable(false);
        tileInfo.setEditable(false);
        unitInfo.setEditable(false);
        panel.add(scenInfo);
        panel.add(tileInfo);
        panel.add(unitInfo);
        return new JScrollPane(panel);
    }

    @Override
    public void updateInfo(TileModel tile) {
        if (tile.isEmpty()) {
            tileInfo.setText(tile.getDescription());
        } else {
            tileInfo.setText(tile.getDescription());
            unitInfo.setText(tile.getTopUnit().getDescription());
        }
    }

    @Override
    public void updateScenInfo(String text) {
        scenInfo.setText(text);
    }

    @Override
    public void clear() {
        tileInfo.setText("");
    }
}
