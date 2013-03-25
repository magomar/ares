package ares.application.gui;

import ares.application.gui.graphics.BoardGraphicsModel;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public abstract class AbstractImageLayer extends javax.swing.JPanel implements ImageLayer {

    // Final image to be painted on the JComponent
    protected BufferedImage globalImage;
    // Nested layers
    protected AbstractImageLayer parentLayer;

    public AbstractImageLayer() {
        setOpaque(false);
    }

    public AbstractImageLayer(AbstractImageLayer parentLayer) {
        setOpaque(false);
        this.parentLayer = parentLayer;
        globalImage = parentLayer.globalImage;
    }

    @Override
    public final void initialize() {
        globalImage = new BufferedImage(BoardGraphicsModel.getImageWidth(), BoardGraphicsModel.getImageHeight(), BufferedImage.TYPE_INT_ARGB);
        repaint();
    }

    protected abstract void updateLayer(); // TODO try to remove this method, move code where appropriate

    @Override
    public final void flush() {
        globalImage = null;
    }

    /**
     * Paints the globalImage if it's not null, otherwise paints a black rectangle.
     *
     * globalImages shouldn't be null unless you know what you're doing, check your code!
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        if (parentLayer != null) {
            parentLayer.paintComponent(g);
        }
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (globalImage != null) {
            g2.drawImage(globalImage, 0, 0, this);
        } else {
            g2.setBackground(Color.BLACK);
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    @Override
    public ImageLayer getParentLayer() {
        return parentLayer;
    }

    @Override
    public BufferedImage getGlobalImage() {
        return globalImage;
    }
}
