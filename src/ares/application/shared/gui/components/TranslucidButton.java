package ares.application.shared.gui.components;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class TranslucidButton extends JButton {

    private BufferedImage buttonImage;
    private float alpha;

    public TranslucidButton(Action action, float alpha) {
        super(action);
        this.alpha = alpha;
        setOpaque(false);
    }

    @Override
    public void paint(Graphics g) {
        // Create an image for the button graphics if necessary
        if (buttonImage == null || buttonImage.getWidth() != getWidth()
                || buttonImage.getHeight() != getHeight()) {
            buttonImage = getGraphicsConfiguration().createCompatibleImage(getWidth(), getHeight());
        }
        Graphics gButton = buttonImage.getGraphics();
        gButton.setClip(g.getClip());

        // Have the superclass render the button for us
        super.paint(gButton);

        // Make the graphics object sent to this paint() method translucent
        Graphics2D g2d = (Graphics2D) g;
        AlphaComposite newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(newComposite);

        // Copy the button's image to the destination graphics, translucently
        g2d.drawImage(buttonImage, 0, 0, null);
    }
}
