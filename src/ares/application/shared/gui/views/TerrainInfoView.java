package ares.application.shared.gui.views;

import ares.application.shared.boundaries.viewers.TerrainInfoViewer;
import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.components.TerrainInfo;
import ares.application.shared.models.board.TileModel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class TerrainInfoView extends AbstractView<JPanel> implements TerrainInfoViewer {

    private TileModel tile;

    private TerrainInfo terrainInfo;

    @Override
    public void updateTile(TileModel tileModel) {
        this.tile = tileModel;
        terrainInfo.updateTile(tileModel);

    }

    @Override
    protected JPanel layout() {
        terrainInfo = new TerrainInfo();
        JPanel content = ComponentFactory.panel();
        content.setBorder(ComponentFactory.DEFAULT_BORDER);
        content.add(terrainInfo);
        return content;
    }


    @Override
    public final void flush() {
        terrainInfo.flush();
    }

    public void setPreferredSize(Dimension size) {
        super.setPreferredSize(size);

        terrainInfo.setPreferredSize(size);
    }
}
