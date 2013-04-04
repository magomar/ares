package ares.application.graphics.command;

import ares.application.graphics.AresMiscGraphics;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public enum ArrowType {

    GIVING_ORDERS(AresMiscGraphics.RED_ARROWS),
    CURRENT_ORDERS(AresMiscGraphics.GRAY_ARROWS);
    private final AresMiscGraphics provider;

    private ArrowType(final AresMiscGraphics provider) {
        this.provider = provider;
    }

    public AresMiscGraphics getProvider() {
        return provider;
    }

}