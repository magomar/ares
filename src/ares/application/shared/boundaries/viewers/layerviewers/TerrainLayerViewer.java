/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ares.application.shared.boundaries.viewers.layerviewers;

import ares.application.shared.models.ScenarioModel;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface TerrainLayerViewer extends ImageLayerViewer {

    void updateScenario(ScenarioModel scenario);

}
