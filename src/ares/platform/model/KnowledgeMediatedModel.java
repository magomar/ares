package ares.platform.model;

import ares.scenario.board.KnowledgeLevel;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public abstract class KnowledgeMediatedModel  {

    protected final KnowledgeLevel kLevel;

    public KnowledgeMediatedModel(KnowledgeLevel informationLevel) {
        this.kLevel = informationLevel;
    }
    
    public KnowledgeLevel getKnowledgeLevel() {
        return kLevel;
    }
}
