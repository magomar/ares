package ares.application.shared.gui.laf;

import java.awt.*;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */

public final class ColorUtil {
    private ColorUtil() {
    }

    public static Color getOpposite(Color c) {
        return isDark(c) ? Color.WHITE : Color.BLACK;
    }

    public static Color shade(Color c, double amount) {
        return blend(c, getOpposite(c), amount);
    }

    public static final Color mult(Color c, double amount) {
        return c == null ? null : new Color(Math.min(255, (int) (c.getRed() * amount)),
                Math.min(255, (int) (c.getGreen() * amount)),
                Math.min(255, (int) (c.getBlue() * amount)),
                c.getAlpha());
    }

    public static Color getTranslucentColor(Color c, float alpha) {
        return c == null ? null : new Color(c.getRed()/255f, c.getGreen()/255f, c.getBlue()/255f, alpha);
    }

    public static final Color add(Color c1, Color c2) {
        return c1 == null ? c2 :
                c2 == null ? c1 :
                        new Color(Math.min(255, c1.getRed() + c2.getRed()),
                                Math.min(255, c1.getGreen() + c2.getGreen()),
                                Math.min(255, c1.getBlue() + c2.getBlue()),
                                c1.getAlpha());
    }

    public static Color blend(Color c1, Color c2, double v) {
        double v2 = 1 - v;
        return c1 == null ? (c2 == null ? null : c2) :
                c2 == null ? c1 :
                        new Color(Math.min(255, (int) (c1.getRed() * v2 + c2.getRed() * v)),
                                Math.min(255, (int) (c1.getGreen() * v2 + c2.getGreen() * v)),
                                Math.min(255, (int) (c1.getBlue() * v2 + c2.getBlue() * v)),
                                Math.min(255, (int) (c1.getAlpha() * v2 + c2.getAlpha() * v)));
    }

    public static boolean isDark(Color c) {
        return c.getRed() + c.getGreen() + c.getBlue() < 3 * 180;
    }

    public static Color highlight(Color c) {
        return mult(c, isDark(c) ? 1.5F : 0.67F);
    }

    public static Color copy(Color c) {
        return c == null ? null : new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
    }
}

