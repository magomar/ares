package ares.application.views;

import ares.application.boundaries.view.UnitInfoViewer;
import ares.application.gui_components.UnitInfoPanel;
import ares.application.models.board.TileModel;
import ares.platform.view.AbstractView;
import java.beans.PropertyChangeEvent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class UnitInfoView extends AbstractView<JPanel> implements UnitInfoViewer {
    private JTextArea textArea;

    @Override
    protected JPanel layout() {
        JPanel panel = new JPanel();
        textArea = new JTextArea();
        panel.add(textArea);
        return panel;
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
//        Logger.getLogger(UnitInfoView.class.getName()).log(Level.INFO, evt.toString());
    }

    @Override
    public void updateTopUnit(TileModel tile) {
//        UnitInfoPanel panel = (UnitInfoPanel) getContentPane();
        textArea.setText(tile.getCoordinates().toString());
        
    }
    
}