package ares.application.shared.views;

import ares.application.shared.boundaries.viewers.MiniMapViewer;
import ares.application.shared.gui.layers.ImageLayer;
import ares.application.shared.gui.layers.TerrainLayer;
import ares.application.shared.gui.layers.UnitsLayer;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.models.ScenarioModel;
import ares.application.shared.models.forces.FormationModel;
import ares.application.shared.models.forces.UnitModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class MiniMapView extends AbstractView<JScrollPane> implements MiniMapViewer {

    private int profile;
    private JLayeredPane layeredPane;
    private TerrainLayer terrainLayer;
    private UnitsLayer unitsLayer;
    private final ImageLayer[] allLayers = {terrainLayer, unitsLayer};

    @Override
    protected JScrollPane layout() {
        JScrollPane scrollPane = new JScrollPane();
        //Create layered pane to hold all the layers
        layeredPane = new JLayeredPane();
        layeredPane.setOpaque(true);
        layeredPane.setBackground(Color.BLACK);
        // Create layers
        JViewport viewport = scrollPane.getViewport();
        terrainLayer = new TerrainLayer(viewport);
        unitsLayer = new UnitsLayer(viewport);

        // Add the last layer from each level to the layered pane
        layeredPane.add(terrainLayer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(unitsLayer, JLayeredPane.POPUP_LAYER);

        // Create and set up scroll pane as the content pane
        scrollPane.add(layeredPane);
        scrollPane.setViewportView(layeredPane);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setVisible(true);
        scrollPane.setOpaque(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(50);
        return scrollPane;
    }

    @Override
    public void loadScenario(final ScenarioModel scenario) {
        Dimension imageSize = new Dimension(GraphicsModel.INSTANCE.getBoardWidth(profile), GraphicsModel.INSTANCE.getBoardHeight(profile));
        layeredPane.setPreferredSize(imageSize);
        layeredPane.setSize(imageSize);
        for (int i = 0; i < allLayers.length; i++) {
            ImageLayer imageLayer = allLayers[i];
            imageLayer.setProfile(profile);
            imageLayer.initialize();
        }
        terrainLayer.paintTerrain(scenario);
        unitsLayer.paintAllUnits(scenario);
    }

    @Override
    public void flush() {
        for (int i = 0; i < allLayers.length; i++) {
            ImageLayer imageLayer = allLayers[i];
            imageLayer.flush();
        }

    }

    @Override
    public void updateScenario(ScenarioModel scenario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addMouseListener(MouseListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void centerViewOn(UnitModel selectedUnit, FormationModel selectedFormation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setProfile(int profile) {
        this.profile = profile;
        Dimension imageSize = new Dimension(GraphicsModel.INSTANCE.getBoardWidth(profile), GraphicsModel.INSTANCE.getBoardHeight(profile));
        layeredPane.setPreferredSize(imageSize);
        layeredPane.setSize(imageSize);
        for (int i = 0; i < allLayers.length; i++) {
            ImageLayer imageLayer = allLayers[i];
            imageLayer.setProfile(profile);
            imageLayer.initialize();
        }
    }
}
