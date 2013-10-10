package ares.application.shared.gui.components;

import ares.application.shared.gui.laf.ColorPalette;
import ares.application.shared.gui.laf.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class TranslucentButton extends JButton {
    private final Color focus;
    private final Color background;
    private final Color select;
    private int r = 8;

//    public TranslucentButton(String text) {
//        super(text);
//    }
//
//    public TranslucentButton(String text, Icon icon) {
//        super(text, icon);
//    }

    public TranslucentButton(Action a) {
        super(a);
        this.setForeground(UIManager.getColor("Button.background"));
        this.setFont(getFont().deriveFont(Font.BOLD, 16));
        focus = ColorUtil.getTranslucentColor(ColorPalette.ARMY_YELLOW, 0.5f);
        background = ColorUtil.getTranslucentColor(UIManager.getColor("Button.foreground"), 0.5f);
        select = ColorUtil.shade(focus, 0.5);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
//        this.setForeground(ColorPalette.ARMY_YELLOW);
//        this.setFont(getFont().deriveFont(Font.BOLD, 16));
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
        ButtonModel buttonModel = getModel();
        if (buttonModel.isArmed()) {
            g2.setColor(select);
        } else {
            if (this.hasFocus() || buttonModel.isRollover()) {
                g2.setColor(focus);
            } else {
                g2.setColor(background);
            }
        }
//        if (buttonModel.isPressed()) {
//            ssc = SB;
//            bgc = ST;
//        } else if (buttonModel.isRollover()) {
//            ssc = ST;
//            bgc = SB;
//        }
        g2.fill(area);
        g2.draw(area);
        g2.dispose();
        super.paintComponent(g);
    }
}
