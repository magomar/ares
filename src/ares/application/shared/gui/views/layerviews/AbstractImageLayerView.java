package ares.application.shared.gui.views.layerviews;

import ares.application.shared.boundaries.viewers.layerviewers.ImageLayerViewer;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.views.AbstractView;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JViewport;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractImageLayerView extends AbstractView<JComponent> implements ImageLayerViewer {

    /**
     * The index of the current graphical profile
     */
    protected int profile;
    /**
     * Image to be rendered on this layer
     */
    protected BufferedImage globalImage;
    /**
     * A layer that shares his {@link #globalImage} with this layer This attribute is null if the layer uses its own
     * {@link #globalImage}
     */
    protected ImageLayerViewer parentLayer;
    /**
     * Viewport where this layer is placed
     */
    protected JViewport viewport;
    /**
     * Layer index of this image layer inside the layered image view.
     */
    protected Integer layer;

    @Override
    protected JComponent layout() {
        JComponent component = new JComponent() {
            /**
             * Paints the globalImage if it's not null
             *
             * @param g graphics object associated to this {@link JComponent}
             */
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (parentLayer != null) {
                    parentLayer.updateLayer();
                }
                Graphics2D g2 = (Graphics2D) g;
                if (globalImage != null) {
                    Rectangle viewRectangle = viewport.getViewRect();
                    int visibleImageWidth = Math.min(viewRectangle.width, globalImage.getWidth());
                    int visibleImageHeight = Math.min(viewRectangle.height, globalImage.getHeight());
                    BufferedImage visibleImage = globalImage.getSubimage(viewRectangle.x, viewRectangle.y, visibleImageWidth, visibleImageHeight);
//                    BufferedImage viewImage = globalImage.getSubimage(rect.x, rect.y, rect.width, rect.height);
                    g2.drawImage(visibleImage, viewRectangle.x, viewRectangle.y, this);
                }
            }
        };
        return component;
    }

    /**
     * the viewport where this image layer is shown
     *
     * @param viewport
     */
    @Override
    public ImageLayerViewer setViewport(JViewport viewport) {
        this.viewport = viewport;
        return this;
    }

    @Override
    public boolean hasParentLayer() {
        return (parentLayer != null);
    }

    /**
     *
     * @param layer the {@link tImageLayer} this layer shares its {@link #globalImage} with
     */
    @Override
    public ImageLayerViewer setParenLayer(ImageLayerViewer parentLayer) {
        this.parentLayer = parentLayer;
        return this;
    }

    @Override
    public final void initialize() {
        if (parentLayer == null) {
            globalImage = new BufferedImage(GraphicsModel.INSTANCE.getBoardWidth(profile),
                    GraphicsModel.INSTANCE.getBoardHeight(profile), BufferedImage.TYPE_INT_ARGB);
//            globalImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        } else {
            globalImage = parentLayer.getGlobalImage();
        }
        contentPane.repaint();
    }

    @Override
    public final void flush() {
        globalImage.flush();
        globalImage = null;
    }

    @Override
    public ImageLayerViewer getParentLayer() {
        return parentLayer;
    }

    @Override
    public BufferedImage getGlobalImage() {
        return globalImage;
    }

    @Override
    public void setProfile(int profile) {
        this.profile = profile;
        Dimension imageSize = new Dimension(GraphicsModel.INSTANCE.getBoardWidth(profile), GraphicsModel.INSTANCE.getBoardHeight(profile));
        contentPane.setPreferredSize(imageSize);
        contentPane.setSize(imageSize);
//        contentPane.setBounds(new Rectangle(imageSize));
    }
}
