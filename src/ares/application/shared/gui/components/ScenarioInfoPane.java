package ares.application.shared.gui.components;

import ares.application.shared.gui.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ScenarioInfoPane extends JPanel {

    private final AnalogClockDayNight clock;
    private final WallCalendar calendarPane;
//    private BufferedImage backImage;

    public ScenarioInfoPane() {
        ((FlowLayout) getLayout()).setAlignment(FlowLayout.LEFT);
        Dimension componentSize = new Dimension(75, 75);
        clock = new AnalogClockDayNight();
        clock.setPreferredSize(componentSize);
        add(clock);

        calendarPane = new WallCalendar();
        calendarPane.setPreferredSize(componentSize);
        add(calendarPane);
//        backImage = FileIO.loadImage(ResourcePath.OTHER.getFile("wood.png"));
//        setTextureImage(backImage);
        setBorder(ComponentFactory.DEFAULT_BORDER);

    }

    public void update(Calendar calendar) {
        clock.update(calendar);
        calendarPane.update(calendar);
    }

}
