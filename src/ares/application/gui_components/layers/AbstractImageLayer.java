package ares.application.gui_components.layers;

import ares.application.models.ScenarioModel;
import ares.application.models.board.BoardGraphicsModel;
import ares.application.models.board.TileModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    public AbstractImageLayer() {
    }

    public AbstractImageLayer(AbstractImageLayer parentLayer) {
        this.parentLayer = parentLayer;
    }
    
    public void initialize(ScenarioModel s){
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
    
    protected BufferedImage loadImage(File f){
        BufferedImage i=null;
        try {
            i = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e.getMessage() + "File not found: " + f.toString());
        }
        return i;
    }
    
    public void flush(){
        globalImage = null;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(globalImage,0,0,this);
    }


}
