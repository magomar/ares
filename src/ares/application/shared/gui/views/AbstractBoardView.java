package ares.application.shared.gui.views;

import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.layerviewers.ImageLayerViewer;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.views.layerviews.AbstractLayeredImageView;
import ares.application.shared.models.ScenarioModel;
import ares.application.shared.models.forces.UnitModel;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JScrollBar;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public abstract class AbstractBoardView extends AbstractLayeredImageView implements BoardViewer {

    @Override
    public void loadScenario(ScenarioModel scenario) {
        Dimension imageSize = new Dimension(GraphicsModel.INSTANCE.getBoardWidth(profile), GraphicsModel.INSTANCE.getBoardHeight(profile));
        layeredPane.setPreferredSize(imageSize);
        for (ImageLayerViewer layerView : layerViews.values()) {
            layerView.setProfile(profile);
            layerView.initialize();
        }
//        // Assuming every BoardView has at least a TerrainLayerViewer and a UnitsLayerViewer
//        getLayerView(TerrainLayerViewer.class).updateScenario(scenario);// This is done by the controller
//        getLayerView(UnitsLayerViewer.class).updateScenario(scenario);// This is done by the controller
    }

    @Override
    public void forgetScenario() {
        for (ImageLayerViewer layerView : layerViews.values()) {
            layerView.flush();
        }
    }

    @Override
    public void centerViewOn(UnitModel selectedUnit) {
        JScrollBar verticalScrollBar = contentPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = contentPane.getHorizontalScrollBar();
        Point pos = GraphicsModel.INSTANCE.tileToPixel(selectedUnit.getLocation().getCoordinates(), profile);
        Dimension viewportSize = contentPane.getViewport().getSize();
        Dimension boardSize = layeredPane.getSize();
        int x = pos.x - Math.min(viewportSize.width / 2, boardSize.width - pos.x);
        int y = pos.y - Math.min(viewportSize.height / 2, boardSize.height - pos.y);
        horizontalScrollBar.setValue(x);
        verticalScrollBar.setValue(y);
    }
}
