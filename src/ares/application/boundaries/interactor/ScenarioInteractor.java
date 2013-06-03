package ares.application.boundaries.interactor;

import ares.platform.scenario.Scenario;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface ScenarioInteractor extends Interactor {
    void newScenario(Scenario scenario);
    void forgetScenario();
}
