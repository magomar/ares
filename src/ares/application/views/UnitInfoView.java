package ares.application.views;

import ares.application.boundaries.view.UnitInfoViewer;
import ares.application.models.board.TileModel;
import ares.platform.view.AbstractView;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
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
        panel.add(textArea, BorderLayout.CENTER);
        return new JScrollPane(panel);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateInfo(TileModel tile) {
        if (tile.isEmpty()) {
            textArea.setText(tile.getDescription());
        } else {
            textArea.setText(tile.getDescription() + '\n' + tile.getTopUnit().getDescription());
        }
    }

    public void clear() {
        textArea.setText("");
    }
}
