package ares.application.shared.gui.views.layerviews;

import ares.application.shared.boundaries.viewers.layerviewers.ImageLayerViewer;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.views.AbstractView;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
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
     * Whether this image layer is sharing its global image with other layers
     */
    protected boolean sharingGlobalImage;
    /**
     * Viewport where this layer is placed
     */
    protected JViewport viewport;

    @Override
    protected JComponent layout() {
        JComponent component = new JComponent() {
            /**
             * Paints the globalImage if it's not null
             *
             * @param g      graphics of this component
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
                    g2.drawImage(visibleImage, viewRectangle.x, viewRectangle.y, this);
                }
            }
        };
        return component;
    }

    /**
     * Initializes the global image of this image layer
     */
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
    public boolean isSharingGlobalImage() {
        return sharingGlobalImage;
    }

    @Override
    public void setSharingGlobalImage(boolean sharingGlobalImage) {
        this.sharingGlobalImage = sharingGlobalImage;
    }

    /**
     * @param viewport the {@link JViewport} where this image layer is shown through
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
     * @param parentLayer the {@link ImageLayerViewer} this layer shares its {@link #globalImage} with
     */
    @Override
    public ImageLayerViewer setParenLayer(ImageLayerViewer parentLayer) {
        this.parentLayer = parentLayer;
        parentLayer.setSharingGlobalImage(true);
        return this;
    }

    /**
     * Frees memory resources
     */
    @Override
    public final void flush() {
        globalImage.flush();
        globalImage = null;
    }

    /**
     * Set the profile and change the size of the {@code #contentPane} accordingly
     * @param profile
     */
    @Override
    public void setProfile(int profile) {
        this.profile = profile;
        Dimension imageSize = new Dimension(GraphicsModel.INSTANCE.getBoardWidth(profile), GraphicsModel.INSTANCE.getBoardHeight(profile));
        contentPane.setPreferredSize(imageSize);
        contentPane.setSize(imageSize);
    }

    @Override
    public ImageLayerViewer getParentLayer() {
        return parentLayer;
    }

    @Override
    public BufferedImage getGlobalImage() {
        return globalImage;
    }
}
