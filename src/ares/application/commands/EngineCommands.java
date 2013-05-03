package ares.application.commands;

import ares.platform.commands.Command;
import ares.platform.io.ResourcePaths;
import java.awt.event.KeyEvent;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum EngineCommands implements Command {

    PAUSE("Pause", "Pause the engine", new Integer(KeyEvent.VK_P), "pause.png"),
    TURN("Next turn", "Play until new turn", new Integer(KeyEvent.VK_T), "next_turn.png"),
    STEP("Next step", "Play just one time tick", new Integer(KeyEvent.VK_S), "next_step.png");
    private final String text;
    private final String iconFilename;
    private Icon icon;
    private final String desc;
    private final Integer mnemonic;

    private EngineCommands(final String text, final String desc, final Integer mnemonic, final String iconFilename) {
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
            String filename = FileSystems.getDefault().getPath(ResourcePaths.ICONS.getPath(), iconFilename).toString();
            icon = new ImageIcon(filename);
        }
        return icon;
    }
}
