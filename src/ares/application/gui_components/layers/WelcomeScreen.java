package ares.application.gui_components.layers;

import ares.application.models.ScenarioModel;
import ares.application.models.board.TileModel;
import ares.io.*;
import ares.platform.application.Command;
import ares.platform.util.Pair;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.ref.SoftReference;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;

/**
 * Main menu and welcome screen.
 * Sets a background picture and handles the main menu buttons.
 * 
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class WelcomeScreen extends AbstractImageLayer {

    // Background image to be loaded 
    private SoftReference<BufferedImage> backgroundImage = new SoftReference<>(null);
    
    // Folder with wallpapers
    private final static File wallpapers = new File(AresPaths.GRAPHICS.getPath(), "Background");

    // Buttons in the main menu
    private LinkedList<JButton> buttons = new LinkedList<>();
    
    // True if the welcome screen should be visible
    private static Boolean activated = true;
    
    
    public WelcomeScreen() {
        setBackgroundImage();
    }

    /**
     * Loads a random image and sets it as the background image
     */
    public void setBackgroundImage() {
        if (backgroundImage.get() == null) {
            try {
                backgroundImage = new SoftReference<>(loadImage(randomImageFile()));
                globalImage = backgroundImage.get();
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    private File randomImageFile() {
        File[] backgrounds = wallpapers.listFiles();
        if (backgrounds == null)
            return null;
        Integer index = new Random().nextInt(backgrounds.length);
        return backgrounds[index];
    }

    @Override
    protected void createGlobalImage(ScenarioModel s) {
    }

    @Override
    public void paintTile(TileModel t) {
    }

    public void setMenuButtons(ArrayList<Pair<Command, ActionListener>> buttonListener) {
        
        // Button dimension
        Dimension dim = new Dimension(250, 50);
        // WelcomeScreen panel is inside a LayeredPane, which is inside a JRootPane
        JFrame frame = (JFrame) this.getParent().getParent().getParent();
        // Where the first button will be placed
        Point buttonPos = new Point(frame.getSize().width / 2, frame.getSize().height / 2);
        // Vertical gap between buttons
        int vGap = 60;
        
        int i = 0;
        for(Pair<Command, ActionListener> pair : buttonListener){
            JButton b = new JButton(pair.getLeft().getText());
            b.addActionListener(pair.getRight());
            b.setPreferredSize(dim);
            b.setMaximumSize(dim);
            b.setMinimumSize(dim);
            b.setSize(dim);
            b.setLocation(buttonPos.x, buttonPos.y + vGap * i);
            i++;
            this.add(b, BorderLayout.SOUTH);
            buttons.add(b);
        }
        // FIXME BorderLayout problems....
        // + last button is out of place unless we add an empty button
        JButton empty = new JButton();
        empty.setVisible(false);
        this.add(empty, BorderLayout.SOUTH);
    }

    /**
     * This function executes when opening a new scenario
     */
    
    public void removeButtons(){
        
        for(Component c : this.getComponents()){
            if(c instanceof JButton){
                this.remove(c);
            }
        }
        this.revalidate();
    }
    
    public void hideButtons() {
        
        if(!buttons.isEmpty() && buttons.getFirst().isVisible()){
            for(JButton b : buttons){
                b.setVisible(false);
            }
        }
        
    }

    public void showButtons() {
        if(!buttons.isEmpty() && !buttons.getFirst().isVisible()){
            for(JButton b : buttons){
                b.setVisible(true);
            }
        }
    }
    
    public Boolean isActivated(){
        return activated;
    }
    public void setActivated(boolean a){
        WelcomeScreen.activated = a;
    }

    public void closeScenario() {
        for(Component c : this.getComponents()){
            if(c instanceof JButton){
                if (!"".equals(((JButton)c).getText()))
                    c.setVisible(true);
            } else{
                c.setVisible(false);
            }
        }
    }
    
}
