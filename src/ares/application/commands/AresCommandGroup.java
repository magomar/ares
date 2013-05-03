package ares.application.commands;

import ares.platform.commands.Command;
import ares.platform.commands.CommandGroup;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum AresCommandGroup implements CommandGroup {

    FILE("File", 'f'){
    @Override
    public Command[] getCommands() {
        return FileCommands.values();
    }},
    VIEW("View", 'v'){
    @Override
    public Command[] getCommands() {
        return ViewCommands.values();
    }},
    ENGINE("Engine", 'e'){
    @Override
    public Command[] getCommands() {
        return EngineCommands.values();
    }};
    private final char mnemonic;
    private final String text;

    private AresCommandGroup(final String text, final char mnemonic) {
        this.text = text;
        this.mnemonic = mnemonic;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public char getMnemonic() {
        return mnemonic;
    }

    @Override
    public String getName() {
        return name();
    }

    
}
