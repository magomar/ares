package ares.application.gui.components;

import ares.platform.io.FileIO;
import ares.platform.io.ResourcePath;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ScenarioInfoPane extends JTexturedPanel {

    private AnalogClockDayNight clock;
    private WallCalendar calendar;
    private WeatherInfo weather;
    private BufferedImage backImage;

    public ScenarioInfoPane() {
        ((FlowLayout) getLayout()).setAlignment(FlowLayout.LEADING);
        clock = new AnalogClockDayNight();
        clock.setPreferredSize(new Dimension(75, 75));
        add(clock);
        
        calendar = new WallCalendar();
        calendar.setPreferredSize(new Dimension(75, 75));
        add(calendar);
        backImage = FileIO.loadImage(new File(ResourcePath.OTHER.getPath(), "wood.png"));
        setTextureImage(backImage);
        
        weather = new WeatherInfo();
        weather.setPreferredSize(new Dimension(32,32));
        add(weather);

    }

    public void update(Calendar calendar) {
        clock.update(calendar);
        this.calendar.update(calendar);
    }

}
