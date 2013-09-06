package ares.platform.engine.action;

import ares.platform.engine.combat.Combat;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public enum InteractionType {
    COMBAT {
        @Override
        public Interaction buildInteraction(Action action) {
            switch (action.getActionType()) {
                case ASSAULT: return new Combat(action);
            }
            return null;
        }
    };
    public abstract Interaction buildInteraction(Action action);
}
