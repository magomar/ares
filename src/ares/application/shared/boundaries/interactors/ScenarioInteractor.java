package ares.application.shared.boundaries.interactors;

import ares.platform.model.UserRole;
import ares.platform.scenario.Scenario;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface ScenarioInteractor extends Interactor {

    void newScenario(Scenario scenario, UserRole userRole);

    void forgetScenario();
}
