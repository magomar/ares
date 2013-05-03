package ares.application.commands;

import ares.platform.commands.Command;
import ares.platform.io.ResourcePaths;
import java.awt.event.KeyEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum ViewCommands implements Command {

    SHOW_GRID("Show Grid", "Show the hexagonal grid", new Integer(KeyEvent.VK_G), "show_grid.png"),
    HIDE_GRID("Hide Grid", "Hide the hexagonal grid", new Integer(KeyEvent.VK_G), "show_grid.png"),
    SHOW_UNITS("Show Units", "Show all the units", new Integer(KeyEvent.VK_U), "show_units.png"),
    HIDE_UNITS("Hide Units", "Hide all the units", new Integer(KeyEvent.VK_U), "show_units.png"),;
    private final String text;
    private final String iconFilename;
    private Icon icon;
    private final String desc;
    private final Integer mnemonic;

    private ViewCommands(final String text, final String desc, final Integer mnemonic, final String iconFilename) {
        this.text = text;
        this.desc = desc;
        this.mnemonic = mnemonic;
        this.iconFilename = iconFilename;
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
    public String getName() {
        return name();
    }

    @Override
    public Icon getIcon() {
        if (icon == null) {
            icon = new ImageIcon(getClass().getResource(ResourcePaths.ICONS + iconFilename));
        }
        return icon;
    }
}
