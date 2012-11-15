package ares.platform.model;

import ares.scenario.board.InformationLevel;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class AbstractModel implements Model {

    protected final InformationLevel informationLevel;

    public AbstractModel(InformationLevel informationLevel) {
        this.informationLevel = informationLevel;
    }
    
    @Override
    public InformationLevel getInformationLevel() {
        return informationLevel;
    }
}
