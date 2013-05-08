package ares.application.gui;

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
     * A layer that shares his {@link #globalImage} with this layer This
     * attribute is null if the layer uses its own {@link #globalImage}
     */
    protected AbstractImageLayer parentLayer;

    /**
     * Creates a new instance of this class with independent graphics
     * ({@code parentLayer == null})
     */
    public AbstractImageLayer() {
        setOpaque(false);
    }

    /**
     * Creates a new instance of this class sharing its graphics with
     * {@code parentLayer}
     */
    public AbstractImageLayer(AbstractImageLayer parentLayer) {
        setOpaque(false);
        this.parentLayer = parentLayer;
    }

    @Override
    public final void initialize() {
        if (parentLayer == null) {
            globalImage = new BufferedImage(GraphicsModel.INSTANCE.getImageWidth(), GraphicsModel.INSTANCE.getImageHeight(), BufferedImage.TYPE_INT_ARGB);
        } else {
            globalImage = parentLayer.globalImage;
        }
        repaint();
    }

//    @Override
//    public abstract void updateLayer();

    @Override
    public final void flush() {
        globalImage = null;
    }

    /**
     * Paints the globalImage if it's not null
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (parentLayer != null) {
            parentLayer.updateLayer();
        }
        Graphics2D g2 = (Graphics2D) g;
        if (globalImage != null) {
            g2.drawImage(globalImage, 0, 0, this);
        }
//        else {
//            g2.setBackground(Color.BLACK);
//            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
//        }
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
