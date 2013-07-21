package ares.platform.engine.command;

import ares.data.wrappers.scenario.*;
import ares.platform.engine.action.ActionSpace;
import ares.platform.engine.command.operational.plans.OperationalPlan;
import ares.platform.engine.command.operational.plans.OperationalStance;
import ares.platform.scenario.board.Board;
import ares.platform.scenario.forces.Formation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class ProgrammedOpponent {

    private final OperationalStance operationalStance;
    private final int activates;
    private final Frontage frontage;
    private final boolean onlyPO;
    private final Emphasis emphasis;
    private final SupportScope supportscope;
    private final List<ProgrammedTrack> tracks;
    private ActionSpace actionSpace;
    private boolean active;

    public ProgrammedOpponent(Orders orders, Board board) {
        operationalStance = OperationalStance.valueOf(orders.getOperationalStance().name());
        activates = orders.getActivates();
        frontage = orders.getFrontage();
        onlyPO = orders.isOnlyPO();
        emphasis = orders.getEmphasis();
        supportscope = orders.getSupportscope();
        tracks = new ArrayList<>();
        this.actionSpace = actionSpace;
        for (Track track : orders.getTrack()) {
            tracks.add(new ProgrammedTrack(track, board));
        }
    }

    public OperationalPlan obtainOperationalPlan(Formation formation) {
        List<Objective> objectives;
        if (tracks.isEmpty()) objectives = new ArrayList<>();
        else objectives = tracks.get(0).getObjectives();
        OperationalPlan opPlan = operationalStance.buildOperationalPlan(formation, objectives, emphasis, supportscope, actionSpace);
        return opPlan;
    }

    public void activate(ActionSpace actionSpace) {
        active = true;
        this.actionSpace = actionSpace;
    }

    public int getActivates() {
        return activates;
    }

    public OperationalStance getOperationalStance() {
        return operationalStance;
    }

    public Frontage getFrontage() {
        return frontage;
    }

    public boolean isOnlyPO() {
        return onlyPO;
    }

    public Emphasis getEmphasis() {
        return emphasis;
    }

    public SupportScope getSupportscope() {
        return supportscope;
    }

    public List<ProgrammedTrack> getTracks() {
        return tracks;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "ProgrammedOpponent{" + "operationalStance=" + operationalStance + ", activates=" + activates + ", frontage=" + frontage + ", onlyPO=" + onlyPO + ", emphasis=" + emphasis + ", supportscope=" + supportscope + ", tracks=" + tracks + ", active=" + active + '}';
    }


}
