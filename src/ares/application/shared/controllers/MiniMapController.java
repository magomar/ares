package ares.application.shared.controllers;

import ares.application.shared.boundaries.interactors.BoardInteractor;
import ares.application.shared.boundaries.interactors.MiniMapInteractor;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.layerviewers.GridLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.TerrainLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.UnitsLayerViewer;
import ares.application.shared.commands.AresCommandGroup;
import ares.application.shared.commands.ViewCommands;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.models.ScenarioModel;
import ares.platform.action.ActionGroup;
import ares.platform.action.CommandAction;
import ares.platform.action.CommandGroup;
import ares.platform.scenario.Scenario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class MiniMapController implements ActionController {

    private final Action zoomIn = new CommandAction(ViewCommands.VIEW_ZOOM_IN, new ZoomInActionListener());
    private final Action zoomOut = new CommandAction(ViewCommands.VIEW_ZOOM_OUT, new ZoomOutActionListener());
    private final ActionGroup actions;
    private final BoardViewer miniMapView;
    private Scenario scenario;

    public MiniMapController(MiniMapInteractor interactor) {
        this.miniMapView = interactor.getMiniMapView();
        // create action groups
        Action[] viewActions = {zoomIn, zoomOut};
        CommandGroup group = AresCommandGroup.VIEW;
        actions = new ActionGroup(group.getName(), group.getText(), group.getMnemonic(), viewActions);
    }

    public void setScenario(Scenario scenario, int profile) {
        this.scenario = scenario;
        miniMapView.setProfile(profile);
        ScenarioModel scenarioModel = scenario.getModel();
        miniMapView.loadScenario(scenarioModel);
        // Render board: paint terrain and units
        ((TerrainLayerViewer) miniMapView.getLayerView(TerrainLayerViewer.NAME)).updateScenario(scenarioModel);
        ((UnitsLayerViewer) miniMapView.getLayerView(UnitsLayerViewer.NAME)).updateScenario(scenarioModel);
    }

    @Override
    public ActionGroup getActionGroup() {
        return actions;
    }

    private class ZoomInActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            miniMapView.setProfile(GraphicsModel.INSTANCE.nextActiveProfile());
            miniMapView.loadScenario(scenario.getModel());
        }
    }

    private class ZoomOutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            miniMapView.setProfile(GraphicsModel.INSTANCE.previousActiveProfile());
            miniMapView.loadScenario(scenario.getModel());
        }
    }

    private class ViewGridActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            miniMapView.switchLayerVisible(GridLayerViewer.NAME);
        }
    }

    private class ViewUnitsActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            miniMapView.switchLayerVisible(UnitsLayerViewer.NAME);
        }
    }
}
