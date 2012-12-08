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
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class WelcomeScreen extends AbstractImageLayer {

    private SoftReference<BufferedImage> backgroundImage = new SoftReference<>(null);
    
    // Folder with wallpapers
    private final static File wallpapers = new File(AresPaths.GRAPHICS.getPath(), "Background");

    // Buttons in the main menu
    private LinkedList<JButton> buttons = new LinkedList<>();
    
    public WelcomeScreen() {
        setBorderLayout();
        setWelcomeBackground();
    }

    public void setWelcomeBackground() {
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

    public void setBorderLayout() {
        setLayout(new BorderLayout() {
            @Override
            public void addLayoutComponent(Component comp, Object constraints) {
                if (constraints == null) {
                    constraints = BorderLayout.CENTER;
                }
                super.addLayoutComponent(comp, constraints);
            }
        });
    }

    @Override
    protected void createGlobalImage(ScenarioModel s) {
    }

    @Override
    public void paintTile(TileModel t) {
    }

    public void setMenuButtons(ArrayList<Pair<Command, ActionListener>> buttonListener) {
        
        int buttonWidth = 250;
        int buttonHeight = 50;
        Dimension dim = new Dimension(buttonWidth, buttonHeight);

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
            this.add(b, BorderLayout.CENTER);
            buttons.add(b);
        }
        // FIXME BorderLayout problems....
        JButton empty = new JButton();
        empty.setVisible(false);
        this.add(empty);
    }

    /**
     * This function executes when opening a new scenario
     */
    public void openingScenario() {
        
        if(!buttons.isEmpty() && buttons.getFirst().isVisible()){
            for(JButton b : buttons){
                b.setVisible(false);
            }
        }
        
    }

}
