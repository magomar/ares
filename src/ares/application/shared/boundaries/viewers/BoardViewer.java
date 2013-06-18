package ares.application.shared.boundaries.viewers;

import ares.application.shared.boundaries.viewers.layerviewers.LayeredImageViewer;

import java.awt.*;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface BoardViewer extends LayeredImageViewer {

    public void centerViewOn(Point location);
}
