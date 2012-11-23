package ares.application.gui_components;

import ares.application.models.ScenarioModel;
import ares.application.models.board.TileModel;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class ArrowLayer extends AbstractImageLayer{

    public ArrowLayer(AbstractImageLayer ail){
        super(ail);
    }
    @Override
    protected void createGlobalImage(ScenarioModel s) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void paintTile(TileModel t) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

}
