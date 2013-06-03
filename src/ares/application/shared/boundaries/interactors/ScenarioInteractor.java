package ares.application.shared.boundaries.interactors;

import ares.application.shared.boundaries.Interactor;
import ares.platform.scenario.Scenario;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface ScenarioInteractor extends Interactor {
    void newScenario(Scenario scenario);
    void forgetScenario();
}
