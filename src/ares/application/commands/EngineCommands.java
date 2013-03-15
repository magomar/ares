package ares.application.commands;

import ares.platform.application.Command;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum EngineCommands implements Command {

//    RESUME("Resume", "Resume the engine", new Integer(KeyEvent.VK_R)),
    PAUSE("Pause", "Pause the engine", new Integer(KeyEvent.VK_P)),
    TURN("Next turn", "Play until new turn", new Integer(KeyEvent.VK_T)),
    STEP("Next step", "Play just one time tick", new Integer(KeyEvent.VK_S));
    private final String text;
//    private final ImageIcon icon;
    private final String desc;
    private final Integer mnemonic;

    private EngineCommands(final String text, final String desc, final Integer mnemonic) {
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
