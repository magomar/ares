package ares.application.commands;

import ares.platform.application.Command;
import java.awt.event.KeyEvent;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum ViewCommands implements Command {

    SHOW_GRID("Show Grid", "Show the hexagonal grid", new Integer(KeyEvent.VK_G)),
    HIDE_GRID("Hide Grid", "Hide the hexagonal grid", new Integer(KeyEvent.VK_G)),
    SHOW_UNITS("Show Units", "Show all the units", new Integer(KeyEvent.VK_U)),
    HIDE_UNITS("Hide Units", "Hide all the units", new Integer(KeyEvent.VK_U)),;
    private final String text;
//    private final ImageIcon icon;
    private final String desc;
    private final Integer mnemonic;

    private ViewCommands(String text, String desc, Integer mnemonic) {
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
