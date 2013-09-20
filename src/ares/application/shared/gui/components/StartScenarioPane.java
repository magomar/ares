package ares.application.shared.gui.components;

import ares.platform.io.AresFileType;
import ares.platform.model.UserRole;
import ares.platform.scenario.Scenario;
import ares.platform.scenario.forces.Force;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class StartScenarioPane extends JOptionPane {

    public StartScenarioPane(Scenario scenario, File scenarioFile) {
        Force[] forces = scenario.getForces();
        UserRole[] roles = new UserRole[forces.length + 1];
        for (int i = 0; i < forces.length; i++) {
            Force force = forces[i];
            roles[i] = UserRole.getForceRole(force);
        }
        roles[forces.length] = UserRole.GOD;
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setText(scenario.getDescription());
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        setMessage(scrollPane);
        setOptions(roles);
        setMessageType(QUESTION_MESSAGE);
        setOptionType(YES_NO_CANCEL_OPTION);
        String imageFilePath = scenarioFile.getPath().replace(AresFileType.SCENARIO.getFileExtension(), ".jpg");
        Icon thumbnail = new ImageIcon(imageFilePath);
        setIcon(thumbnail);
        setInitialValue(roles[2]);
    }

    public UserRole showOptionDialog(Component parent) {
        JDialog dialog = createDialog(parent, "Select User Role");
        dialog.setVisible(true);
        dialog.dispose();
        selectInitialValue();
        return  (UserRole) getValue();
    }

}
