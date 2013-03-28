package ares.application.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public abstract class AbstractImageLayer extends javax.swing.JPanel implements ImageLayer {

    /**
     * Image to be rendered on this layer
     */
    protected BufferedImage globalImage;
    /**
     * A layer that shares his {@link #globalImage} with this layer
     * This attribute is null if the layer uses its own {@link #globalImage}
     */ 
    protected AbstractImageLayer parentLayer;

    /**
     * Creates a new instance of this class with independent graphics ({@code parentLayer == null})
     */
    public AbstractImageLayer() {
        setOpaque(false);
    }
   /**
     * Creates a new instance of this class sharing its graphics with {@code parentLayer}
     */
    public AbstractImageLayer(AbstractImageLayer parentLayer) {
        setOpaque(false);
        this.parentLayer = parentLayer;
        globalImage = parentLayer.globalImage;
    }

    @Override
    public final void initialize() {
        globalImage = new BufferedImage(AresGraphicsModel.getImageWidth(), AresGraphicsModel.getImageHeight(), BufferedImage.TYPE_INT_ARGB);
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
