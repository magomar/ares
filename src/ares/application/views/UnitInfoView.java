package ares.application.views;

import ares.application.boundaries.view.UnitInfoViewer;
import ares.application.models.forces.UnitModel;
import ares.platform.view.AbstractView;
import java.awt.BorderLayout;
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
        panel.setLayout(new BorderLayout());
        textArea = new JTextArea();
        panel.add(textArea, BorderLayout.CENTER);
        return panel;
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
//        Logger.getLogger(UnitInfoView.class.getName()).log(Level.INFO, evt.toString());
    }

    @Override
    public void selectUnit(UnitModel unit) {
        textArea.setText(unit.getName());
    }
    
}