package ares.application.gui.layers;

import ares.application.gui.profiles.GraphicsModel;
import ares.application.gui.layers.ImageLayer;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JViewport;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractImageLayer extends JPanel implements ImageLayer {

    /**
     * Image to be rendered on this layer
     */
    protected BufferedImage globalImage;
    /**
     * A layer that shares his {@link #globalImage} with this layer This attribute is null if the layer uses its own
     * {@link #globalImage}
     */
    protected AbstractImageLayer parentLayer;
    /**
     * Viewport where this layer is placed
     */
    protected JViewport viewport;

    /**
     * Creates a new instance of this class with independent graphics ({@code parentLayer == null})
     */
    public AbstractImageLayer(JViewport viewport) {
        this(viewport, null);
    }

    public AbstractImageLayer(JViewport viewport, AbstractImageLayer parentLayer) {
        setOpaque(false);
        this.viewport = viewport;
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
//
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
            Rectangle rect = viewport.getViewRect();
            int visibleImageWidth = Math.min(rect.width, globalImage.getWidth());
            int visibleImageHeight = Math.min(rect.height, globalImage.getHeight());
            BufferedImage viewImage = globalImage.getSubimage(rect.x, rect.y, visibleImageWidth, visibleImageHeight);
//            BufferedImage viewImage = globalImage.getSubimage(rect.x, rect.y, rect.width, rect.height);
            g2.drawImage(viewImage, rect.x, rect.y, this);
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
