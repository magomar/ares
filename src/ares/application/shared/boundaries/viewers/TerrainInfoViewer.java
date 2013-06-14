package ares.application.shared.boundaries.viewers;

import ares.application.shared.gui.views.View;
import ares.application.shared.models.board.TileModel;
import javax.swing.JPanel;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface TerrainInfoViewer extends View<JPanel> {

    void initialize();

    void flush();
    
    void updateTile(TileModel tileModel);
}
