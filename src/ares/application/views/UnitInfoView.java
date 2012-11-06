package ares.application.views;

import ares.application.gui_components.UnitInfoPanel;
import ares.platform.view.AbstractView;
import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class UnitInfoView extends AbstractView<JPanel> {

    @Override
    protected JPanel layout() {
        return new UnitInfoPanel();
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
//        Logger.getLogger(UnitInfoView.class.getName()).log(Level.INFO, evt.toString());
    }
    
}