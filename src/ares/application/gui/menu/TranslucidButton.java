package ares.application.gui.menu;

import java.awt.AlphaComposite;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Action;
import javax.swing.JButton;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class TranslucidButton extends JButton {

    private final static Dimension BUTTON_DIMENSION = new Dimension(250, 50);

//    public TranslucidButton(String string) {
//        super(string);
////        setBorder(null);
////        setBorderPainted(false);
////        setContentAreaFilled(false);
//        setOpaque(false);
//        setFont(getFont().deriveFont(Font.BOLD));
//        setAlignmentX(CENTER_ALIGNMENT);
//        setSize(BUTTON_DIMENSION);
//        setMaximumSize(BUTTON_DIMENSION);
//        setMinimumSize(BUTTON_DIMENSION);
//    }

    public TranslucidButton(Action action) {
        super(action);
        setOpaque(false);
        setFont(getFont().deriveFont(Font.BOLD));
        setAlignmentX(CENTER_ALIGNMENT);
        setSize(BUTTON_DIMENSION);
        setMaximumSize(BUTTON_DIMENSION);
        setMinimumSize(BUTTON_DIMENSION);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        super.paint(g2);
        g2.dispose();
    }
}
