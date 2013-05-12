package ares.application.gui.command;

import ares.application.gui.providers.AresMiscGraphics;

/**
 *
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public enum TileType {

    OPEN_SET(AresMiscGraphics.GRID_GREEN),
    CLOSED_SET(AresMiscGraphics.GRID_YELLOW);
    private final AresMiscGraphics provider;

    private TileType(final AresMiscGraphics provider) {
        this.provider = provider;
    }

    public AresMiscGraphics getProvider() {
        return provider;
    }

}
