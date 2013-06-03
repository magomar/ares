package ares.platform.engine.command;

import ares.platform.scenario.board.Board;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class ProgrammedTrack {

    private List<Objective> objectives;

    public ProgrammedTrack(ares.data.jaxb.Track track, Board board) {
        objectives = new ArrayList<>();
        for (ares.data.jaxb.Objective obj : track.getObjective()) {
            objectives.add(new Objective(obj, board));
        }
    }

    public List<Objective> getObjectives() {
        return objectives;
    }
}
