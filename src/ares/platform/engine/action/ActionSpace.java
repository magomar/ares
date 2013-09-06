package ares.platform.engine.action;

import ares.platform.engine.combat.Combat;
import ares.platform.scenario.board.Tile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ActionSpace {

    /**
     * Map of interactions grouped by location. This map associates map tiles with actions happening at that location
     */
    private final Map<Tile, Interaction> interactions;

    public ActionSpace() {
        interactions = new HashMap<>();
    }

    public void putAction(Tile location, Action action) {
        if (interactions.containsKey(location)) {
            interactions.get(location).addAction(action);
        } else {
            Interaction interaction = new Combat(action);
            interactions.put(location, interaction);
        }
    }

    public void resolveInteractions() {
        for (Map.Entry<Tile, Interaction> entry : interactions.entrySet()) {
            Tile tile = entry.getKey();
            Interaction interaction= entry.getValue();
            interaction.execute();
        }
    }
}
