package ares.application.analyser.views;

import ares.application.analyser.boundaries.viewers.PathfinderBenchmarkViewer;
import ares.application.shared.gui.views.AbstractView;
import javax.swing.JPanel;

/**
 *
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public class PathfinderBenchmarkView extends AbstractView<JPanel> implements PathfinderBenchmarkViewer {

    private JPanel configurationsPanel;
    private PathfinderConfigurationView configurationView;
    
    @Override
    protected JPanel layout() {
        JPanel container = new JPanel();
        configurationsPanel = new JPanel();
        configurationView = new PathfinderConfigurationView();
        
        configurationsPanel.add(configurationView.getContentPane());
        container.add(configurationsPanel);
        
        return container;
    }
    
}
