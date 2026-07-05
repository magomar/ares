package ares.application.shared.commands;

import ares.platform.io.ResourcePath;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum UnitCommands implements Command {

    REST("Rest", "Rest", Integer.valueOf(KeyEvent.VK_R)),
    WAIT("Wait", "Wait", Integer.valueOf(KeyEvent.VK_W)),
    DEPLOY("Deploy", "Deploy", Integer.valueOf(KeyEvent.VK_D)),
    DIG_IN("Dig In", "Dig In", Integer.valueOf(KeyEvent.VK_F)),
    EMBARK("Embark", "Embark", Integer.valueOf(KeyEvent.VK_E)),
    DISEMBARK("Disembark", "Disembark", Integer.valueOf(KeyEvent.VK_I)),
    TRAVEL("Travel", "Travel", Integer.valueOf(KeyEvent.VK_T)),
    TACTICAL_MARCH("Move", "Move", Integer.valueOf(KeyEvent.VK_M)),
    APPROACH_MARCH("Move to contact", "Move to contact", Integer.valueOf(KeyEvent.VK_C)),
    STEALTH_MARCH("Move stealth", "Move stealth", Integer.valueOf(KeyEvent.VK_H)),
    DISENGAGE("Disengage", "Disengage", Integer.valueOf(KeyEvent.VK_G)),
    FORCED_MARCH("Move quickly", "Move quickly", Integer.valueOf(KeyEvent.VK_Q)),
    RECON("Recon", "Recon", Integer.valueOf(KeyEvent.VK_R)),
    ASSAULT("Assault", "Assault", Integer.valueOf(KeyEvent.VK_A)),
    ATTACK_BY_FIRE("Attack", "Attack", Integer.valueOf(KeyEvent.VK_F)),
    SUPPORT_BY_FIRE("Support", "Support", Integer.valueOf(KeyEvent.VK_Y)),
    BOMBARD("Bombard", "Bombard", Integer.valueOf(KeyEvent.VK_B)),
    BUILD("Build", "Build", Integer.valueOf(KeyEvent.VK_V)),
    DESTROY("Destroy", "Destroy", Integer.valueOf(KeyEvent.VK_Z)),
    REPAIR("Repair", "Repair", Integer.valueOf(KeyEvent.VK_P)),
    ASSEMBLE("Assemble", "Assemble", Integer.valueOf(KeyEvent.VK_L)),;
    private final String text;
    private final String iconFilename;
    private Icon icon;
    private final String desc;
    private final Integer mnemonic;
    private final KeyStroke accelerator;

    private UnitCommands(final String text, final String desc, final Integer mnemonic) {
        this.text = text;
        this.desc = desc;
        this.mnemonic = mnemonic;
        this.accelerator = null;
        this.iconFilename = name().toLowerCase() + ".png";
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public Integer getMnemonic() {
        return mnemonic;
    }

    @Override
    public KeyStroke getAccelerator() {
        return accelerator;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public Icon getLargeIcon() {
        if (icon == null) {
            icon = new ImageIcon(ResourcePath.ICONS_MEDIUM.getFilename(iconFilename));
        }
        return icon;
    }

    @Override
    public Icon getSmallIcon() {
        if (icon == null) {
            icon = new ImageIcon(ResourcePath.ICONS_SMALL.getFilename(iconFilename));
        }
        return icon;
    }
}
