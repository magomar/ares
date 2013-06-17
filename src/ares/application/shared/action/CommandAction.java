package ares.application.shared.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class CommandAction extends AbstractAction {

    private final ActionListener listener;

    public CommandAction(Command command, ActionListener listener) {
        this(command, listener, true);
    }

    public CommandAction(Command command, ActionListener listener, boolean enabled) {
        super(command.getText());
        this.listener = listener;
//        putValue(Action.SMALL_ICON, command.getSmallIcon());
        putValue(Action.LARGE_ICON_KEY, command.getLargeIcon());
        putValue(Action.ACCELERATOR_KEY, command.getAccelerator());
        putValue(Action.MNEMONIC_KEY, command.getMnemonic());
        putValue(Action.ACTION_COMMAND_KEY, command.getName());
        putValue(Action.SHORT_DESCRIPTION, command.getText());
        putValue(Action.LONG_DESCRIPTION, command.getDescription());
        setEnabled(enabled);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listener.actionPerformed(e);
    }
}
