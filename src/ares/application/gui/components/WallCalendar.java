package ares.application.gui.components;

import ares.platform.io.FileIO;
import ares.platform.io.ResourcePath;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JComponent;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class WallCalendar extends JComponent {

    private final BufferedImage backgroundImage;
    private Calendar calendar;
    private final Font headFont = new Font("Arial", Font.PLAIN, 12);
    private final Font bodyFont = new Font("Arial Black", Font.BOLD, 30);

    public WallCalendar() {
        backgroundImage = FileIO.loadImage(new File(ResourcePath.OTHER.getPath(), "calendar_background.png"));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        } else {
            g2.setBackground(Color.BLACK);
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        if (calendar != null) {
            g2.setFont(headFont);
            g2.setColor(Color.WHITE);
            g2.drawString(Integer.toString(calendar.get(Calendar.YEAR)), 10, 20);
            g2.drawString(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US).substring(0, 3), 45, 20);
            g2.setFont(bodyFont);
            g2.setColor(Color.BLACK);
            g2.drawString(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)), 15, 60);
        }
        g2.dispose();
    }

    public void update(Calendar calendar) {
        this.calendar = calendar;
        repaint();
    }
//    private String monthToString(int month) {
//        return new DateFormatSymbols().getMonths()[month - 1];
//    }
}
