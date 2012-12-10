package ares.application.gui_components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class TranslucidButton extends JButton {

    public TranslucidButton(String string) {
        super(string);
//        setBorder(null);
//        setBorderPainted(false);
//        setContentAreaFilled(false);
        setOpaque(false);
        setFont(getFont().deriveFont(Font.BOLD));
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        super.paint(g2);
        g2.dispose();
    }
}
