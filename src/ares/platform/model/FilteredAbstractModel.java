package ares.platform.model;

import ares.scenario.board.InformationLevel;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class FilteredAbstractModel implements FilteredModel {

    protected final InformationLevel informationLevel;

    public FilteredAbstractModel(InformationLevel informationLevel) {
        this.informationLevel = informationLevel;
    }
    
    @Override
    public InformationLevel getInformationLevel() {
        return informationLevel;
    }
}
