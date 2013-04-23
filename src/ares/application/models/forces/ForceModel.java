package ares.application.models.forces;

import ares.platform.model.RoleMediatedModel;
import ares.platform.model.UserRole;
import ares.scenario.forces.Force;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public final class ForceModel extends RoleMediatedModel implements TreeModel {

    protected final Force force;

    public ForceModel(Force force, UserRole role) {
        super(role);
        this.force = force;
    }

    public String getName() {
        return force.getName();
    }

    public List<UnitModel> getUnitModels() {
        List<UnitModel> unitModels = new ArrayList<>();
        for (Unit unit : force.getActiveUnits()) {
            UnitModel unitModel = unit.getModel(userRole);
            if (unitModel != null) {
                unitModels.add(unitModel);
            }
        }
        return unitModels;
    }

    public List<FormationModel> getFormationModels() {
        List<FormationModel> formations = new ArrayList<>();
        for (Formation formation : force.getFormations()) {
            FormationModel formationModel = formation.getModel(userRole);
            if (formationModel != null) {
                formations.add(formationModel);
            }
        }
        return formations;
    }

    @Override
    public Object getRoot() {
        return force.getFormations().get(0);
    }

    @Override
    public Object getChild(Object parent, int index) {
        
    }

    @Override
    public int getChildCount(Object parent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isLeaf(Object node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
