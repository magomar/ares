package ares.platform.scenario.forces;

import ares.application.shared.models.forces.ForceModel;
import ares.platform.model.ModelProvider;
import ares.platform.model.UserRole;
import ares.platform.scenario.Scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class Force implements ModelProvider<ForceModel> {

    private final int id;
    private final String name;
    private final int proficiency;
    private final int supply;
    private final int flag;
    private final List<Formation> formations;
    private final Map<UserRole, ForceModel> models;

    public Force(ares.data.wrappers.scenario.Force force, Scenario scenario) {
        id = force.getId();
        name = force.getName();
        formations = new ArrayList<>();
        proficiency = force.getProficiency();
        supply = force.getSupply();
        flag = force.getFlag();
        Map<Integer, Formation> formMap = new HashMap<>(force.getFormation().size());
        for (ares.data.wrappers.scenario.Formation formation : force.getFormation()) {
            Formation f = new Formation(formation, this, scenario);
            formMap.put(f.getId(), f);
        }
        for (ares.data.wrappers.scenario.Formation formation : force.getFormation()) {
            Formation child = formMap.get(formation.getId());
            Formation parent = formMap.get(formation.getParent());
            child.setSuperior(parent);
            if (parent != null) {
                parent.getSubordinates().add(child);
            }
        }
        formations.addAll(formMap.values());
        models = new HashMap<>();


    }

    public void initialize(Force[] forces) {
        models.put(UserRole.GOD, new ForceModel(this, UserRole.GOD));
        for (Force f : forces) {
            models.put(UserRole.getForceRole(f), new ForceModel(this, UserRole.getForceRole(f)));
        }
    }

    public List<Unit> getActiveUnits() {
        List<Unit> activeUnits = new ArrayList<>();
        for (Formation formation : formations) {
            activeUnits.addAll(formation.getAvailableUnits());
        }
        return activeUnits;
    }

    public int getFlag() {
        return flag;
    }

    public List<Formation> getFormations() {
        return formations;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getProficiency() {
        return proficiency;
    }

    public int getSupply() {
        return supply;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Force other = (Force) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public ForceModel getModel(UserRole role) {
        return models.get(role);
    }
}
