package ares.application.boundaries.view;

import ares.platform.application.Command;
import ares.platform.util.Pair;
import ares.platform.view.View;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface ToolBarViewer extends View, ActionListener {

    public void setMenuButtons(ArrayList<Pair<Command, ActionListener>> buttonListener);

    public void removeButtons();

    public void hideButtons();

    public void showButtons();

    public Boolean isActivated();

    public void setActivated(boolean a);

    public void closeScenario();
}
