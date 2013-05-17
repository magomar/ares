package ares.application.gui.components;

import ares.platform.io.FileIO;
import ares.platform.io.ResourcePath;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import javax.swing.JPanel;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ScenarioInfoPane extends JTexturedPanel {

    private AnalogClockDayNight clock;
    private WallCalendar calendarPane;
    private BufferedImage backImage;

    public ScenarioInfoPane() {
        clock = new AnalogClockDayNight();
        clock.setPreferredSize(new Dimension(75, 75));
        add(clock);
        
        calendarPane = new WallCalendar();
        calendarPane.setPreferredSize(new Dimension(75, 75));
        add(calendarPane);
        backImage = FileIO.loadImage(new File(ResourcePath.OTHER.getPath(), "wood.png"));
        setTextureImage(backImage);

    }

    public void update(Calendar calendar) {
        clock.update(calendar);
        calendarPane.update(calendar);
    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g;
//        g2.drawImage(backImage, 0, 0, this);
//        g2.dispose();
//    }
    

}
