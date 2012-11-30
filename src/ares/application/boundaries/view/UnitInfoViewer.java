package ares.application.boundaries.view;

import ares.application.models.board.TileModel;
import ares.platform.view.View;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface UnitInfoViewer extends View {

    public void updateInfo(TileModel tile);

    public void clear();
}
