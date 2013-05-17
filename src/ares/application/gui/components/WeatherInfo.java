package ares.application.gui.components;

import ares.platform.io.FileIO;
import ares.platform.io.ResourcePath;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JComponent;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class WeatherInfo extends JComponent {

    private final BufferedImage weatherImage;
    private final Font font = new Font("Arial", Font.PLAIN, 12);

    public WeatherInfo() {
        weatherImage = FileIO.loadImage(new File(ResourcePath.OTHER.getPath(), "weather-clear.png"));
    }
        @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (weatherImage != null) {
            g2.drawImage(weatherImage, 0, 0, this.getWidth(), this.getHeight(), this);
        } 
//        if (calendar != null) {
//            g2.setFont(headFont);
//            g2.setColor(Color.WHITE);
//            g2.drawString(Integer.toString(calendar.get(Calendar.YEAR)), 10, 20);
//            g2.drawString(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US).substring(0, 3), 45, 20);
//            g2.setFont(bodyFont);
//            g2.setColor(Color.BLACK);
//            g2.drawString(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)), 15, 60);
//        }
        g2.dispose();
    }
}
