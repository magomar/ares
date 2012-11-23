package ares.application.gui_components;

import ares.application.models.ScenarioModel;
import ares.application.models.board.BoardGraphicsModel;
import ares.application.models.board.TileModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public abstract class AbstractImageLayer extends JPanel{

    // Final image to be painted on the JComponent
    protected BufferedImage globalImage;
    
    // BoardGraphicsModel provides hexagonal and image sizes
    protected BoardGraphicsModel bgm;
    
    // Parent container
    private final JScrollPane contentPane;
    
    public AbstractImageLayer(JScrollPane contentPane){
        this.contentPane = contentPane;
    }
    
    public AbstractImageLayer(AbstractImageLayer ail){
        this.contentPane = ail.contentPane;
        this.globalImage = ail.globalImage;
        this.bgm = ail.bgm;
    }
    
    public void initialize(ScenarioModel s){
        bgm = s.getBoardGraphicsModel();
        globalImage = new BufferedImage(bgm.getImageWidth(), bgm.getImageHeight(), BufferedImage.TYPE_INT_ARGB);
        createGlobalImage(s);
    }
    
    public void updateGlobalImage(ScenarioModel s) {
        globalImage = new BufferedImage(bgm.getImageWidth(), bgm.getImageHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        createGlobalImage(s);
        repaint();
    }
    
    protected abstract void createGlobalImage(ScenarioModel s);
    
    public abstract void paintTile(TileModel t);
    
    public void paintTileInViewport(TileModel t){

        //Calculate tile position
        Point pos = bgm.tileToPixel(t.getCoordinates());
        
        //Viewport
        Rectangle r = contentPane.getVisibleRect();
        
        if(pos.x < r.getMaxX() && pos.x > r.getMinX() && pos.y < r.getMaxY() && pos.y > r.getMinY()){
            paintTile(t);
        }
    }
    
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
        globalImage=null;
        bgm=null;
    }
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(globalImage,0,0,this);
    }


}
