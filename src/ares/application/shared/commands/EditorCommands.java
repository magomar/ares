package ares.application.shared.commands;

import ares.platform.action.Command;
import ares.platform.io.ResourcePath;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum EditorCommands implements Command {

    OPEN_EQUIPMENT("Open Equipment", "Open an equipment database"),
    CLOSE_SCENARIO("Close Scenario", "Close current scenario"),;
    private final String text;
    private final String iconFilename;
    private Icon icon;
    private final String desc;
    private final Integer mnemonic;
    private final KeyStroke accelerator;

    private EditorCommands(final String text, final String desc) {
        this.text = text;
        this.desc = desc;
        this.mnemonic = null;
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
