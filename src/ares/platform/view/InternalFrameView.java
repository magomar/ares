package ares.platform.view;

import java.beans.PropertyChangeEvent;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class InternalFrameView<V extends AbstractView<? extends JComponent>> extends AbstractView<JInternalFrame> {

    private final JDesktopPane desktopPane;
    private final V view;
    private final JInternalFrame internalFrame;

    public InternalFrameView(V view, JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
        this.view = view;
        this.internalFrame = ComponentFactory.internalFrame(view.getContentPane(), view.getClass().getSimpleName());
        this.internalFrame.setClosable(false);
        this.desktopPane.add(internalFrame);
    }

    @Override
    protected JInternalFrame layout() {
        return internalFrame;
    }

    public JInternalFrame getInternalFrame() {
        return internalFrame;
    }

    public void setTitle(String title) {
        internalFrame.setTitle(title);
    }

    public V getView() {
        return view;
    }

    public void show() {
        internalFrame.pack();
        internalFrame.setVisible(true);
        try {
            internalFrame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
        }    
    }

    public void close() {
        internalFrame.dispose();
        desktopPane.remove(internalFrame);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        view.modelPropertyChange(evt);
    }
}
