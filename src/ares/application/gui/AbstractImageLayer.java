package ares.application.gui;

import ares.application.models.ScenarioModel;
import ares.application.models.board.BoardGraphicsModel;
import ares.application.models.board.TileModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public abstract class AbstractImageLayer extends javax.swing.JPanel {

    // Final image to be painted on the JComponent
    protected BufferedImage globalImage;
    // Nested layers
    protected AbstractImageLayer parentLayer;
    protected static final Logger LOG = Logger.getLogger(AbstractImageLayer.class.getName());

    public AbstractImageLayer() {
    }

    public AbstractImageLayer(AbstractImageLayer parentLayer) {
        this.parentLayer = parentLayer;
    }

    public void initialize(ScenarioModel s) {
        globalImage = new BufferedImage(BoardGraphicsModel.getImageWidth(), BoardGraphicsModel.getImageHeight(), BufferedImage.TYPE_INT_ARGB);
        createGlobalImage(s);
    }

    public void updateGlobalImage(ScenarioModel s) {
        globalImage = new BufferedImage(BoardGraphicsModel.getImageWidth(), BoardGraphicsModel.getImageHeight(), BufferedImage.TYPE_INT_ARGB);
        createGlobalImage(s);
        repaint();
    }

    protected abstract void createGlobalImage(ScenarioModel s);

    public abstract void paintTile(TileModel t);

    protected BufferedImage loadImage(File f) {
        BufferedImage i = null;
        try {
            i = ImageIO.read(f);
        } catch (IOException e) {
        }
        return i;
    }

    public void flush() {
        globalImage = null;
    }

    /**
     * Paints the globalImage if it's not null,
     * if it is then paints a black rectangle.
     * 
     * globalImages shouldn't be null unless you know what you're doing,
     * check your code!
     * 
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (globalImage != null) {
            g2.drawImage(globalImage, 0, 0, this);
        } else{
            g2.setBackground(Color.BLACK);
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    public AbstractImageLayer getParentLayer() {
        return parentLayer;
    }

    public BufferedImage getGlobalImage() {
        return globalImage;
    }
    
}
