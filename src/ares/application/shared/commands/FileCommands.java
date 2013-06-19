package ares.application.shared.commands;

import ares.platform.io.ResourcePath;

import javax.swing.*;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum FileCommands implements Command {

    GAME_NEW("New Game", "Start a new scenario"),
    GAME_LOAD("Load Game", "Resum a saved scenario"),
    GAME_SAVE("Save Game", "Save scenario into file"),
    GAME_CLOSE("Close Game", "Close scenario"),
    SETTINGS("Settings", "Game settings"),
    EXIT("Exit", "Exit");
    private final String text;
    private final String iconFilename;
    private Icon icon;
    private final String desc;
    private final Integer mnemonic;
    private final KeyStroke accelerator;

    private FileCommands(final String text, final String desc) {
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
