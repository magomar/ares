package ares.application.shared.gui.views;

import ares.application.shared.boundaries.viewers.TerrainInfoViewer;
import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.components.TerrainInfo;
import ares.application.shared.gui.profiles.GraphicProperties;
import ares.application.shared.gui.profiles.NonProfiledGraphicProperty;
import ares.application.shared.models.board.TileModel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class TerrainInfoView extends AbstractView<JPanel> implements TerrainInfoViewer {

    private TileModel tile;

    private TerrainInfo terrainInfo;
    private JTextArea tileInfo;

    @Override
    public void updateTile(TileModel tileModel) {
        this.tile = tileModel;
        terrainInfo.updateTile(tileModel);
        if (tile != null)
            tileInfo.setText(tile.getDescription());
        else tileInfo.setText("");

    }

    @Override
    protected JPanel layout() {
        terrainInfo = new TerrainInfo();
        tileInfo = new JTextArea();
        tileInfo.setEditable(false);
        JPanel panel = ComponentFactory.panel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(ComponentFactory.DEFAULT_BORDER);
        panel.add(terrainInfo);
        panel.add(Box.createRigidArea(new Dimension(5, 5)));
        panel.add(tileInfo);

        return panel;
    }

    @Override
    public void clear() {
        tileInfo.setText("");
        terrainInfo.updateTile(null);
    }

    @Override
    public final void flush() {
        clear();
        terrainInfo.flush();
    }

    public void setPreferredSize(Dimension size) {
        super.setPreferredSize(size);
        int terrainInfoWidth = GraphicProperties.getProperty(NonProfiledGraphicProperty.TERRAIN_INFO_WIDTH);
        Dimension componentSize;
        componentSize = new Dimension(terrainInfoWidth, size.height);
        terrainInfo.setPreferredSize(componentSize);
        componentSize = new Dimension(size.width - terrainInfoWidth - 5, size.height);
        tileInfo.setPreferredSize(componentSize);
    }
}
