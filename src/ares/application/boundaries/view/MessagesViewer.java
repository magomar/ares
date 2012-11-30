package ares.application.boundaries.view;

import ares.engine.messages.EngineMessage;
import ares.platform.view.View;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface MessagesViewer extends View {

    public void addMessage(EngineMessage message);

    public void append(String str);

    public void clear();
}
