package ares.application.commands;

import ares.platform.application.Command;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum EngineCommands implements Command {

    START("Start", "Start the engine", new Integer(KeyEvent.VK_S)),
    PAUSE("Pause", "Pause the engine", new Integer(KeyEvent.VK_S)),
    NEXT("Next", "Execute this turn", new Integer(KeyEvent.VK_N));
    private final String text;
//    private final ImageIcon icon;
    private final String desc;
    private final Integer mnemonic;

    private EngineCommands(String text, String desc, Integer mnemonic) {
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

    @Override
    public String getName() {
        return name();
    }

    @Override
    public ImageIcon getImageIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
