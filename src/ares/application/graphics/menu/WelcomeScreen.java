package ares.application.graphics.menu;

import ares.application.graphics.AbstractImageLayer;
import ares.io.*;
import ares.application.graphics.ImageTools;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.ref.SoftReference;
import java.util.*;
import javax.swing.*;

/**
 * Main menu and welcome screen. Sets a background picture and handles the main menu buttons.
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class WelcomeScreen extends AbstractImageLayer {

    /**
     * Background image to be loaded
     */
    private SoftReference<BufferedImage> backgroundImage = new SoftReference<>(null);
    /**
     * Folder containing wallpapers
     */
    private final static File wallpapers = new File(AresPaths.GRAPHICS.getPath(), "Background");

    public WelcomeScreen() {
        BoxLayout bl = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(bl);
        add(Box.createRigidArea(new Dimension(0, 300)));
        updateLayer();
    }

    @Override
    protected void updateLayer() {
        if (backgroundImage.get() == null) {
            backgroundImage = new SoftReference<>(ImageTools.loadImage(randomImageFile()));
            globalImage = backgroundImage.get();
        }
    }

    /**
     * Loads a random image and sets it as the background image
     */
    public void setBackgroundImage() {
        updateLayer();
    }

    private File randomImageFile() {
        File[] backgrounds = wallpapers.listFiles();
        if (backgrounds == null || backgrounds.length == 0) {
            return new File(AresPaths.GRAPHICS.getPath(), "main_menu_background.jpg");
        }
        Integer index = new Random().nextInt(backgrounds.length);
        return backgrounds[index];
    }

    @Override
    public void paintComponent(Graphics g) {
        if (parentLayer != null) {
            parentLayer.paintComponent(g);
        }
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (globalImage != null) {
            g2.drawImage(globalImage, 0, 0, this.getWidth(), this.getHeight(), null);
        } else {
            g2.setBackground(Color.BLACK);
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
