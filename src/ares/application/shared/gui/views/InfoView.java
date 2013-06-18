package ares.application.shared.gui.views;

import ares.application.shared.boundaries.viewers.InfoViewer;
import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.components.ScenarioInfoPane;
import ares.application.shared.gui.profiles.GraphicProperties;
import ares.application.shared.gui.profiles.NonProfiledGraphicProperty;
import ares.application.shared.models.board.TileModel;
import ares.application.shared.models.forces.UnitModel;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class InfoView extends AbstractView<JPanel> implements InfoViewer {

    //    private JTextArea scenInfo;
    private JTextArea unitInfo;
    private ScenarioInfoPane scenInfo;
    private TerrainInfoView terrainInfoView;

    @Override
    protected JPanel layout() {
        JPanel panel = ComponentFactory.panel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        scenInfo = new ScenarioInfoPane();
        unitInfo = new JTextArea();
        unitInfo.setEditable(false);
        terrainInfoView = new TerrainInfoView();
        panel.add(scenInfo);
        panel.add(terrainInfoView.getContentPane());
        panel.add(unitInfo);
        return panel;
    }

    @Override
    public void setPreferredSize(Dimension size) {
        super.setPreferredSize(size);
        int imageHeight = GraphicProperties.getProperty(NonProfiledGraphicProperty.TERRAIN_INFO_HEIGHT);
        Dimension componentSize = new Dimension(size.width, 75 + ComponentFactory.BORDER_THICKNESS * 4);
        scenInfo.setMinimumSize(componentSize);
        scenInfo.setPreferredSize(componentSize);
        componentSize = new Dimension(size.width, imageHeight + ComponentFactory.BORDER_THICKNESS * 4);
        terrainInfoView.contentPane.setMinimumSize(componentSize);
        terrainInfoView.setPreferredSize(componentSize);
    }

    @Override
    public void updateTileInfo(TileModel tile) {
        terrainInfoView.updateTile(tile);
    }

    @Override
    public void updateScenarioInfo(Calendar calendar) {
        scenInfo.update(calendar);
        terrainInfoView.updateTile(null);
    }

    @Override
    public void clear() {
        unitInfo.setText("");
        terrainInfoView.updateTile(null);
    }

    @Override
    public void updateUnitInfo(UnitModel unitModel) {
        unitInfo.setText(unitModel.getDescription());
    }
}
