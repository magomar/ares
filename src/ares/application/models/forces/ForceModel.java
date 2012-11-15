package ares.application.models.forces;

import ares.platform.model.FilteredAbstractModel;
import ares.scenario.board.InformationLevel;
import ares.scenario.forces.Force;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public abstract class ForceModel extends FilteredAbstractModel {
    protected final Force force;

    protected ForceModel(Force force, InformationLevel informationLevel) {
        super(informationLevel);
        this.force = force;
    }

    public String getName() {
        return force.getName();
    }

}
