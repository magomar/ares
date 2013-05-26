package ares.application.gui.components;

import ares.platform.io.FileIO;
import ares.platform.io.ResourcePath;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.Calendar;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ScenarioInfoPane extends JTexturedPanel {

    private AnalogClockDayNight clock;
    private WallCalendar calendarPane;
    private BufferedImage backImage;

    public ScenarioInfoPane() {
        ((FlowLayout) getLayout()).setAlignment(FlowLayout.LEADING);
        clock = new AnalogClockDayNight();
        clock.setPreferredSize(new Dimension(75, 75));
        add(clock);
        
        calendarPane = new WallCalendar();
        calendarPane.setPreferredSize(new Dimension(75, 75));
        add(calendarPane);
        backImage = FileIO.loadImage(ResourcePath.OTHER.getFile("wood.png"));
        setTextureImage(backImage);

    }

    public void update(Calendar calendar) {
        clock.update(calendar);
        calendarPane.update(calendar);
    }

}
