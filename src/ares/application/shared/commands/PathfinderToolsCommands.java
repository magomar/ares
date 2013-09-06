package ares.application.shared.commands;

import ares.platform.io.ResourcePath;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 *
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public enum PathfinderToolsCommands implements Command {

    COMPARATOR_PERSPECTIVE("Comparator perspective", "Switch to comparator perspective"),
    BENCHMARK_PERSPECTIVE("Benchmark perspective", "Switch to benchmark perspective");
    private final String text;
    private final String iconFilename;
    private Icon icon;
    private final String desc;
    private final Integer mnemonic;
    private final KeyStroke accelerator;
    
    private PathfinderToolsCommands(final String text, final String desc) {
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
