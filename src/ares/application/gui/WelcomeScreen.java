package ares.application.gui;

import ares.application.gui.AbstractImageLayer;
import ares.application.models.ScenarioModel;
import ares.application.models.board.TileModel;
import ares.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.ref.SoftReference;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;

/**
 * Main menu and welcome screen. Sets a background picture and handles the main menu buttons.
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class WelcomeScreen extends AbstractImageLayer {

    // Background image to be loaded 
    private SoftReference<BufferedImage> backgroundImage = new SoftReference<>(null);
    // Folder with wallpapers
    private final static File wallpapers = new File(AresPaths.GRAPHICS.getPath(), "Background");

    public WelcomeScreen() {
        BoxLayout bl = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(bl);
        add(Box.createRigidArea(new Dimension(0, 300)));
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
        if (backgrounds == null) {
            return null;
        }
        Integer index = new Random().nextInt(backgrounds.length);
        return backgrounds[index];
    }

    @Override
    protected void createGlobalImage(ScenarioModel s) {
    }

    @Override
    public void paintTile(TileModel t) {
    }

  
}
