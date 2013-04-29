package ares.application.gui.command;

import ares.application.gui.providers.AresMiscGraphics;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public enum ArrowType {

    ACTIVE(AresMiscGraphics.RED_ARROWS),
    LAST(AresMiscGraphics.PURPLE_ARROWS),
    PLANNED(AresMiscGraphics.BLUE_ARROWS);
    private final AresMiscGraphics provider;

    private ArrowType(final AresMiscGraphics provider) {
        this.provider = provider;
    }

    public AresMiscGraphics getProvider() {
        return provider;
    }

}
