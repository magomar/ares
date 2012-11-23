package ares.application.gui_components;

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
public abstract class AbstractImageLayer extends javax.swing.JPanel{

    // Final image to be painted on the JComponent
    protected BufferedImage globalImage;
    
    // BoardGraphicsModel provides hexagonal and image sizes
    protected BoardGraphicsModel bgm;
    
    public void initialize(ScenarioModel s){
        bgm = s.getBoardGraphicsModel();
        globalImage = new BufferedImage(bgm.getImageWidth(), bgm.getImageHeight(), BufferedImage.TYPE_INT_ARGB);
        createGlobalImage(s);
        repaint();
    }
    
    public void updateGlobalImage(ScenarioModel s) {
        globalImage = new BufferedImage(bgm.getImageWidth(), bgm.getImageHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        createGlobalImage(s);
    }
    
    protected abstract void createGlobalImage(ScenarioModel s);
    
    public abstract void paintByTile(TileModel t);
    
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
        bgm=null;
    }
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(globalImage,0,0,this);
    }


}
