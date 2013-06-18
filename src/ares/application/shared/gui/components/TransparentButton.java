package ares.application.shared.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class TransparentButton extends JButton {
    private float alpha;

    public TransparentButton(Action action, float alpha) {
        super(action);
        this.alpha = alpha;
        setOpaque(false);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paint(g2);
        g2.dispose();
    }
}