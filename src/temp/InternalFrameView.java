package temp;

import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.views.AbstractView;

import javax.swing.*;

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

    public void hide() {
        internalFrame.setVisible(false);
    }

    public void close() {
        internalFrame.dispose();
        desktopPane.remove(internalFrame);
    }
}
