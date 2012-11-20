package ares.application.gui_components;

import ares.application.models.ScenarioModel;
import ares.io.AresIO;
import ares.scenario.Scenario;
import ares.application.models.board.BoardGraphicsModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Grid image layer
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class GridLayer extends javax.swing.JPanel {

    private Image gridImage;
    //The grid filename should be specified somwhere else (ImageProfile, Terrain?)
    //and set hexagonGridFileName according to the needed size
    private String hexagonGridFileName = "Hexoutline.png";

    public void initialize(ScenarioModel scenario) {

        /*
         * Create the grid when opening new scenario
         */
        if (gridImage == null) {
            BoardGraphicsModel boardInfo = scenario.getBoardGraphicsModel();            
            createGridImage(boardInfo, loadHexagonFile(boardInfo));
        } else {
            /*
             * Grid already created
             */
        }

        repaint();

    }

    /**
     * Paints the grid within the board range
     * using the gridImage graphics
     * 
     * @param boardInfo to get the board size
     * @param hexagonGrid the image to be drawn
     * 
     */
    private void createGridImage(BoardGraphicsModel boardInfo, Image hexagonGrid) {

        gridImage = new BufferedImage(boardInfo.getImageWidth(), boardInfo.getImageHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) gridImage.getGraphics();

        int x = 0, y;
        int dx = boardInfo.getHexOffset();

        for (int i = 0; i < boardInfo.getWidth(); ++i) {
            for (int j = 0; j < boardInfo.getHeight(); ++j) {
                
                /* The height depends on the column index 'j'.
                 * It paints higher if 'j' is odd, lower if it's even
                 */
                
                y = boardInfo.getHexHeight() * (2 * j + ((i + 1) % 2)) / 2;

                g2.drawImage(hexagonGrid, x, y, null);
            }
            
            x += dx;
        }
        g2.dispose();



    }

    /**
     * Loads the hexagon image
     * 
     * @param boardInfo
     * @return 
     */
    private Image loadHexagonFile(BoardGraphicsModel boardInfo) {
        Image i;
        
        try {
            i = ImageIO.read(AresIO.ARES_IO.getFile(boardInfo.getImageProfile().getPath(), hexagonGridFileName));

        } catch (IOException e) {
            System.out.println(e.getMessage() + "File not found: " + hexagonGridFileName);
            i = null;
        }
        return i;
    }

    /**
     * 
     */
    public void flushLayer(){
        gridImage = null;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(gridImage, 0, 0, this);
    }
}
