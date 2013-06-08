package ares.application.player.boundaries.interactors;

import ares.application.shared.boundaries.interactors.BoardInfoInteractor;
import ares.application.shared.boundaries.interactors.BoardInteractor;
import ares.application.shared.boundaries.interactors.MiniMapInteractor;
import ares.application.shared.boundaries.interactors.OOBInteractor;


/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface PlayerBoardInteractor extends BoardInteractor, BoardInfoInteractor, OOBInteractor, MiniMapInteractor {

}
