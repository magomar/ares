package ares.application.models.forces;

import ares.application.gui.forces.UnitsColor;
import ares.application.models.board.TileModel;
import ares.engine.command.tactical.TacticalMission;
import ares.engine.knowledge.KnowledgeCategory;
import ares.engine.movement.MovementType;
import ares.platform.model.KnowledgeMediatedModel;
import ares.platform.model.UserRole;
import ares.scenario.forces.Unit;
import java.util.Enumeration;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class UnitModel extends KnowledgeMediatedModel implements TreeNode {

    protected final Unit unit;

    public UnitModel(Unit unit, KnowledgeCategory kLevel) {
        super(kLevel);
        this.unit = unit;
    }

    public abstract String getName();

    public abstract UnitsColor getColor();

    public abstract int getIconId();

    public abstract TileModel getLocation();

    public abstract String getFormation();

    public abstract String getForce();

    public abstract String getDescription();

    public abstract TacticalMission getTacticalMission();

    public MovementType getMovement() {
        return unit.getMovement();
    }

    public FormationModel getFormationModel(UserRole role) {
        return unit.getFormation().getModel(role);
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getChildCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TreeNode getParent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getIndex(TreeNode node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getAllowsChildren() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isLeaf() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Enumeration children() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
