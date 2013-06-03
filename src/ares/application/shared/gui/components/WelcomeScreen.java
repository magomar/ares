package ares.application.shared.gui.components;

import ares.platform.io.ResourcePath;
import ares.platform.io.FileIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.ref.SoftReference;
import java.util.*;
import javax.swing.*;

/**
 * Main menu and welcome screen. Sets a background picture and handles the main menu buttons.
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public final class WelcomeScreen extends JPanel {

    /**
     * Background image to be loaded
     */
    private SoftReference<BufferedImage> backgroundImage = new SoftReference<>(null);

    public WelcomeScreen() {
        BoxLayout bl = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(bl);
        add(Box.createRigidArea(new Dimension(0, 300)));
        updateBackgroundImage();
    }

    public void updateBackgroundImage() {
        if (backgroundImage.get() == null) {
            BufferedImage bi = FileIO.loadImage(randomImageFile());
            backgroundImage = new SoftReference<>(bi);
        }
    }

    private File randomImageFile() {
        File[] backgrounds = ResourcePath.GRAPHICS_BACKGROUND.getFolderPath().toFile().listFiles();
        if (backgrounds == null || backgrounds.length == 0) {
            return ResourcePath.GRAPHICS.getFile("main_menu_background.jpg");
        }
        Integer index = new Random().nextInt(backgrounds.length);
        return backgrounds[index];
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (backgroundImage.get() != null) {
            g2.drawImage(backgroundImage.get(), 0, 0, this.getWidth(), this.getHeight(), this);
        } else {
            g2.setBackground(Color.BLACK);
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
