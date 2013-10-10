package ares.application.shared.gui.components;

import ares.application.shared.gui.laf.ColorPalette;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class TranslucentButton extends JButton {
    private static final Color TL = new Color(1f, 1f, 1f, .2f);
    private static final Color BR = new Color(0f, 0f, 0f, .4f);
    private static final Color ST = new Color(1f, 1f, 1f, .2f);
    private static final Color SB = new Color(1f, 1f, 1f, .1f);
    private Color ssc;
    private Color bgc;
    private int r = 8;

    public TranslucentButton(String text) {
        super(text);
    }

    public TranslucentButton(String text, Icon icon) {
        super(text, icon);
    }

    public TranslucentButton(Action a) {
        super(a);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        this.setForeground(ColorPalette.ARMY_YELLOW);
        this.setFont(getFont().deriveFont(Font.BOLD, 16));
    }

    @Override
    protected void paintComponent(Graphics g) {
        int x = 0;
        int y = 0;
        int w = getWidth();
        int h = getHeight();
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Shape area = new RoundRectangle2D.Float(x, y, w - 1, h - 1, r, r);
        ssc = TL;
        bgc = BR;
        ButtonModel buttonModel = getModel();
        if (buttonModel.isPressed()) {
            ssc = SB;
            bgc = ST;
        } else if (buttonModel.isRollover()) {
            ssc = ST;
            bgc = SB;
        }
//        if (getModel().isArmed()) {
//            g2.setColor(select);
//        } else {
//            if (this.hasFocus()) {
//                g2.setColor(focus);
//            } else {
//                g2.setColor(background);
//            }
//        }
        g2.setPaint(new GradientPaint(x, y, ssc, x, y + h, bgc, true));
        g2.fill(area);
        g2.setPaint(BR);
        g2.draw(area);
        g2.dispose();
        super.paintComponent(g);
    }
}
