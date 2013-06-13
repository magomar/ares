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
public enum ViewCommands implements Command {

    VIEW_GRID("Show Grid", "Show/Hide the hexagonal grid", 'G'),
    VIEW_UNITS("Show Units", "Show all the units", 'U'),
    VIEW_ZOOM_IN("Zoom In", "Zoom in the board view", 'Z'),
    VIEW_ZOOM_OUT("Zoom Out", "Zoom out the board view", 'X');
    private final String text;
    private final String iconFilename;
    private Icon icon;
    private final String desc;
    private final Integer mnemonic;
    private final KeyStroke accelerator;

    private ViewCommands(final String text, final String desc, final char keyChar) {
        this.text = text;
        this.desc = desc;
        this.mnemonic = new Integer(keyChar);
        this.accelerator = KeyStroke.getKeyStroke(keyChar);
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
