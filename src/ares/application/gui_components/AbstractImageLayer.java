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
public abstract class AbstractImageLayer extends JComponent {

    // Final image to be painted on the JComponent
    protected BufferedImage globalImage;
    
    // BoardGraphicsModel provides hexagonal and image sizes
    protected BoardGraphicsModel bgm;
    
    // Tile map
    protected TileModel[][] tileMap;
    
    public AbstractImageLayer(){
        
    }
    
    public AbstractImageLayer(AbstractImageLayer ail){
        this.globalImage = ail.globalImage;
        this.bgm = ail.bgm;
        this.tileMap = ail.tileMap;
    }
    
    public void initialize(ScenarioModel s){
        bgm = s.getBoardGraphicsModel();
        tileMap = s.getBoardModel().getMapModel();
        globalImage = new BufferedImage(bgm.getImageWidth(), bgm.getImageHeight(), BufferedImage.TYPE_INT_ARGB);
        createGlobalImage(s);
    }
    
    public void updateGlobalImage(ScenarioModel s) {
        globalImage = new BufferedImage(bgm.getImageWidth(), bgm.getImageHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        createGlobalImage(s);
        repaint();
    }
    
    protected void createGlobalImage(ScenarioModel s){
        Rectangle visibleTiles = bgm.getViewportTiles();
        Graphics2D g2 = globalImage.createGraphics();
        // Paint it black!
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, bgm.getImageWidth(), bgm.getImageHeight());
        g2.dispose();
        for(int i = visibleTiles.x; i<=visibleTiles.width;i++){
            for(int j = visibleTiles.y; j<=visibleTiles.height;j++){
                paintTile(tileMap[i][j]);
            }
        }
        bgm.setLastPaintedTileBoundary(visibleTiles.width,visibleTiles.height);
    }
    
    //public abstract void paintTileRow();
    
    public abstract void paintTile(TileModel t);
    
    public void paintTileInViewport(TileModel t){

        //Calculate tile position
        Point pos = bgm.tileToPixel(t.getCoordinates());
        
        //Viewport
        Rectangle r = bgm.getViewport();
        
        if(pos.x <= r.getMaxX() && pos.x >= r.getMinX() && pos.y <= r.getMaxY() && pos.y >= r.getMinY()){
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
        tileMap=null;
    }
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(globalImage,0,0,null);
    }

    public void paintViewportChanges(Integer oldValue, String action) {
        switch (action) {
            case "V_ROW":
                for (int i = 0; i <= bgm.getLastPaintedTileBoundary().x; i++) {
                    for (int j = oldValue; j <= bgm.getLastPaintedTileBoundary().y; j++) {
                        paintTile(tileMap[i][j]);
                    }
                }
                break;
            case "V_COL":
                for (int i = oldValue; i <= bgm.getLastPaintedTileBoundary().x; i++) {
                    for (int j = 0; j <= bgm.getLastPaintedTileBoundary().y; j++) {
                        paintTile(tileMap[i][j]);
                    }
                }                
                break;
        }
    }
}
