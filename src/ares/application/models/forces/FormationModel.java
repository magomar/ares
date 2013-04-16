package ares.application.models.forces;

import ares.platform.model.RoleMediatedModel;
import ares.platform.model.UserRole;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class FormationModel extends RoleMediatedModel implements TreeNode {

    private final Formation formation;

    public FormationModel(Formation formation, UserRole userRole) {
        super(userRole);
        this.formation = formation;
    }

    public String getName() {
        return formation.getName();
    }

    public List<FormationModel> getSubordinatesModels() {
        List<FormationModel> subordinates = new ArrayList<>();
        for (Formation subordinate : formation.getSubordinates()) {
            FormationModel formationModel = subordinate.getModel(userRole);
            if (formationModel != null) {
                subordinates.add(formationModel);
            }
        }
        return subordinates;
    }

    public List<UnitModel> getUnitModels() {
        List<UnitModel> unitModels = new ArrayList<>();
        for (Unit unit : formation.getAvailableUnits()) {
            UnitModel unitModel = unit.getModel(userRole);
            if (unitModel != null) {
                unitModels.add(unitModel);
            }
        }
        return unitModels;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        List<Formation> subordinates = formation.getSubordinates();
        int numSubordinates = subordinates.size();
        if (childIndex < numSubordinates) {
            return subordinates.get(childIndex).getModel(userRole);
        } else {
            return formation.getAvailableUnits().get(childIndex - numSubordinates).getModel(userRole);
        }
    }

    @Override
    public int getChildCount() {
        return formation.getAvailableUnits().size() + formation.getSubordinates().size();
    }

    @Override
    public TreeNode getParent() {
        return formation.getSuperior().getModel(userRole);
    }

    @Override
    public int getIndex(TreeNode node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration children() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
