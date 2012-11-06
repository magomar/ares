package ares.application.commands;

import ares.platform.application.Command;
import java.awt.event.KeyEvent;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum FileCommands implements Command {
    OPEN_SCENARIO("Open Scenario", "Open a new scenario", new Integer(KeyEvent.VK_O)),
    OPEN_EQUIPMENT("Open Equipment", "Open an equipment database", new Integer(KeyEvent.VK_E)),
    CLOSE_SCENARIO("Close Scenario", "Close the current scenario", new Integer(KeyEvent.VK_U)),
    EXIT("Exit", "Exit the application", new Integer(KeyEvent.VK_X));
 
    private final String text;
//    private final ImageIcon icon;
    private final String desc;
    private final Integer mnemonic;


    private FileCommands(String text, String desc, Integer mnemonic) {
        this.text = text;
        this.desc = desc;
        this.mnemonic = mnemonic;
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
}
