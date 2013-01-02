package ares.application.commands;

import ares.platform.application.Command;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum UnitCommands implements Command {

    REST("Rest", "Rest", new Integer(KeyEvent.VK_R)),
    WAIT("Wait", "Wait", new Integer(KeyEvent.VK_W)),
    DEPLOY("Deploy", "Deploy", new Integer(KeyEvent.VK_D)),
    DIG_IN("Dig In", "Dig In", new Integer(KeyEvent.VK_F)),
    EMBARK("Embark", "Embark", new Integer(KeyEvent.VK_E)),
    DISEMBARK("Disembark", "Disembark", new Integer(KeyEvent.VK_I)),
    TRAVEL("Travel", "Travel", new Integer(KeyEvent.VK_T)),
    TACTICAL_MARCH("Move", "Move", new Integer(KeyEvent.VK_M)),
    APPROACH_MARCH("Move to contact", "Move to contact", new Integer(KeyEvent.VK_C)),
    STEALTH_MARCH("Move stealth", "Move stealth", new Integer(KeyEvent.VK_H)),
    DISENGAGE("Disengage", "Disengage", new Integer(KeyEvent.VK_G)),
    FORCED_MARCH("Move quickly", "Move quickly", new Integer(KeyEvent.VK_Q)),
    RECON("Recon", "Recon", new Integer(KeyEvent.VK_R)),
    ASSAULT("Assault", "Assault", new Integer(KeyEvent.VK_A)),
    ATTACK_BY_FIRE("Attack", "Attack", new Integer(KeyEvent.VK_F)),
    SUPPORT_BY_FIRE("Support", "Support", new Integer(KeyEvent.VK_Y)),
    BOMBARD("Bombard", "Bombard", new Integer(KeyEvent.VK_B)),
    BUILD("Build", "Build", new Integer(KeyEvent.VK_V)),
    DESTROY("Destroy", "Destroy", new Integer(KeyEvent.VK_Z)),
    REPAIR("Repair", "Repair", new Integer(KeyEvent.VK_P)),
    ASSEMBLE("Assemble", "Assemble", new Integer(KeyEvent.VK_L)),;
    private final String text;
    private final ImageIcon icon;
    private final String desc;
    private final Integer mnemonic;

    private UnitCommands(final String text, final String desc, final Integer mnemonic) {
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
