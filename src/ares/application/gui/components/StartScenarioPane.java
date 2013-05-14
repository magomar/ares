package ares.application.gui.components;

import ares.platform.model.UserRole;
import ares.scenario.Scenario;
import ares.scenario.forces.Force;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class StartScenarioPane extends JOptionPane {

    public StartScenarioPane(Scenario scenario) {
        Force[] forces = scenario.getForces();
        UserRole[] options = new UserRole[forces.length + 1];
        for (int i = 0; i < forces.length; i++) {
            Force force = forces[i];
            options[i] = UserRole.getForceRole(force);
        }
        options[forces.length] = UserRole.GOD;
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setText(scenario.getDescription());
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        setMessage(scrollPane);
        setOptions(options);
        setMessageType(QUESTION_MESSAGE);
        setOptionType(YES_NO_CANCEL_OPTION);
        setInitialValue(options[2]);
    }

    public UserRole showOptionDialog(Component parent) {
        JDialog dialog = createDialog(parent, "Select User Role");
        dialog.setVisible(true);
        dialog.dispose();
        selectInitialValue();
        UserRole role = (UserRole) getValue();
        return role;
    }

}
