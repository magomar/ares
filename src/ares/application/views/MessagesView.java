package ares.application.views;

import ares.application.gui_components.MessagesPanel;
import ares.platform.view.AbstractView;
import java.beans.PropertyChangeEvent;
import javax.swing.JScrollPane;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class MessagesView extends AbstractView<JScrollPane> {

    @Override
    protected JScrollPane layout() {
        return new MessagesPanel();
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
//        Logger.getLogger(MessagesView.class.getName()).log(Level.INFO, evt.toString());
    }


}
