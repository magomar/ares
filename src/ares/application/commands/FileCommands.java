package ares.application.commands;

import ares.platform.application.Command;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum FileCommands implements Command {

    NEW_SCENARIO("New Scenario", "Open a new scenario", new Integer(KeyEvent.VK_O)),
    LOAD_SCENARIO("Load Scenario", "Load a saved scenario", new Integer(KeyEvent.VK_L)),
    SETTINGS("Settings", "Game settings", new Integer(KeyEvent.VK_S)),
    OPEN_EQUIPMENT("Open Equipment", "Open an equipment database", new Integer(KeyEvent.VK_E)),
    CLOSE_SCENARIO("Close Scenario", "Close the current scenario", new Integer(KeyEvent.VK_U)),
    EXIT("Exit", "Exit the application", new Integer(KeyEvent.VK_X));
    private final String text;
    private final ImageIcon icon;
    private final String desc;
    private final Integer mnemonic;

    private FileCommands(String text, String desc, Integer mnemonic) {
        this.text = text;
        this.desc = desc;
        this.mnemonic = mnemonic;
        icon = null;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public Integer getMnemonic() {
        return mnemonic;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public ImageIcon getImageIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
