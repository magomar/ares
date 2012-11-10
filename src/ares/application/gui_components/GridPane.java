package ares.application.gui_components;

import ares.io.AresIO;
import ares.scenario.Scenario;
import ares.scenario.board.BoardInfo;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Creates an hexagonal tiling image
 *
 * The grid is separated from the terrain level so it's faster to hide or show it
 * again.
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class GridPane extends javax.swing.JPanel {

    private Image gridImage;
    //The grid filename should be specified somwhere else (ImageProfile, Terrain?)
    //and set hexagonGridFileName according to the needed size
    private String hexagonGridFileName = "Hexoutline.png";
    private String lastScenario = null;

    public void initialize(Scenario scenario) {

        /*
         * Create the grid when opening new scenario
         */
        if (lastScenario == null || !lastScenario.equals(scenario.getName())) {
            BoardInfo boardInfo = scenario.getBoardInfo();            
            createGridImage(boardInfo, loadHexagonFile(boardInfo));
            lastScenario = scenario.getName();

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
    private void createGridImage(BoardInfo boardInfo, Image hexagonGrid) {

        gridImage = new BufferedImage(boardInfo.getImageWidth(), boardInfo.getImageHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics gridGraphics = gridImage.getGraphics();

        int x = 0, y;
        int dx = boardInfo.gexHexOffset();

        for (int i = 0; i < boardInfo.getWidth(); ++i) {
            for (int j = 0; j < boardInfo.getHeight(); ++j) {
                
                /* The height depends on the column index 'j'.
                 * It paints higher if 'j' is odd, lower if it's even
                 */
                
                y = boardInfo.getHexHeight() * (2 * j + ((i + 1) % 2)) / 2;

                gridGraphics.drawImage(hexagonGrid, x, y, null);
            }
            
            x += dx;
        }



    }

    private Image loadHexagonFile(BoardInfo boardInfo) {
        Image i;
        
        try {
            i = ImageIO.read(AresIO.ARES_IO.getFile(boardInfo.getImageProfile().getPath(), hexagonGridFileName));

        } catch (IOException e) {
            System.out.println(e.getMessage() + "File not found: " + hexagonGridFileName);
            i = null;
        }
        return i;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(gridImage, 0, 0, this);
    }
}
