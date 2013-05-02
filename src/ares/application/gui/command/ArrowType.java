package ares.application.gui.command;

import ares.application.gui.providers.AresMiscGraphics;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public enum ArrowType {

    ACTIVE(AresMiscGraphics.RED_ARROWS),
    UNIT(AresMiscGraphics.PURPLE_ARROWS),
    FORMATION(AresMiscGraphics.BLUE_ARROWS),
    FORCE(AresMiscGraphics.GRAY_ARROWS);
    private final AresMiscGraphics provider;

    private ArrowType(final AresMiscGraphics provider) {
        this.provider = provider;
    }

    public AresMiscGraphics getProvider() {
        return provider;
    }

}
