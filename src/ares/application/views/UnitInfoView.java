package ares.application.views;

import ares.application.boundaries.view.UnitInfoViewer;
import ares.application.models.board.TileModel;
import ares.platform.view.AbstractView;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class UnitInfoView extends AbstractView<JScrollPane> implements UnitInfoViewer {

    private JTextArea textArea;

    @Override
    protected JScrollPane layout() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        textArea = new JTextArea();
        textArea.setEditable(false);
        panel.add(textArea, BorderLayout.CENTER);
        return new JScrollPane(panel);
    }

    @Override
    public void updateInfo(TileModel tile) {
        if (tile.isEmpty()) {
            textArea.setText(tile.getDescription());
        } else {
            textArea.setText(tile.getDescription() + '\n' + tile.getTopUnit().getDescription());
        }
    }

    @Override
    public void clear() {
        textArea.setText("");
    }

}
