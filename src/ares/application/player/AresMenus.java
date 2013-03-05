package ares.application.player;

import ares.platform.application.MenuEntry;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum AresMenus implements MenuEntry {

    FILE_MENU("File", 'f'),
    VIEW_MENU("View", 'v'),
    ENGINE_MENU("Engine", 'e');
    private final char mnemonic;
    private final String text;

    private AresMenus(final String text, final char mnemonic) {
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
