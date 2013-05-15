package ares.application.gui.components;

import ares.platform.io.FileIO;
import ares.platform.io.ResourcePath;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import javax.swing.JPanel;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ScenarioInfoPane extends JPanel {

    private AnalogClockDayNight clock;
    private WallCalendar calendarPane;

    public ScenarioInfoPane() {
        clock = new AnalogClockDayNight();
        clock.setPreferredSize(new Dimension(70, 70));
        add(clock);
        
        calendarPane = new WallCalendar();
        calendarPane.setPreferredSize(new Dimension(70, 70));
        add(calendarPane);

    }

    public void update(Calendar calendar) {
        clock.update(calendar);
        calendarPane.update(calendar);
    }
    

}
