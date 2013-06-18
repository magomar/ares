package ares.application.shared.boundaries.viewers;

import ares.application.shared.gui.views.MessagesHandler;
import ares.application.shared.gui.views.View;
import ares.platform.engine.messages.EngineMessage;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface MessagesViewer extends View<JPanel> {

    public void addMessage(EngineMessage message);

    public void append(String str);

    public void clear();

    public void setLogCheckBoxes(ActionListener logCheckBoxListener);

    public MessagesHandler getHandler();
}
