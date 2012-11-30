package ares.platform.application;

import ares.platform.view.View;
import ares.platform.view.WindowUtil;
import javax.swing.JFrame;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractAresApplication {

    private final AresLookupService<View> views;
//    private final LookupService<AbstractView<? extends JComponent>> views = new LookupService<>();
    private final JFrame mainFrame;

    public AbstractAresApplication() {
        views = new AresLookupService<>();
        configureMVC();
        this.mainFrame = layout();
    }

    /**
     * This method, which is invoked by the constructor, has to be overriden by subclasses in order to create and
     * connect all the application-specific MVC components: Models, Views and Controllers
     */
    protected abstract void configureMVC();

    /**
     * This metho creates the main frame of the application
     *
     * @return
     */
    protected abstract JFrame layout();

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void setTitle(String title) {
        mainFrame.setTitle(title);
    }

    protected void show() {
//        WindowUtil.showFrame(mainFrame);
        WindowUtil.centerAndShow(mainFrame);
    }

    protected final <T extends View> void addView(Class<T> viewClass, T view) {
        views.put(viewClass, view);
    }
    
//    protected final void addView(Class<? extends AbstractView<? extends JComponent>> viewClass, AbstractView<? extends JComponent> view) {
//        views.put(viewClass, view);
//    }
//
//    protected final <T extends AbstractView<? extends JComponent>> T getView(Class<T> viewClass) {
//        return views.get(viewClass);
//    }
//
//    @SuppressWarnings("unchecked")
//    protected final <T extends AbstractView<? extends JComponent>> InternalFrameView<T> getInternalFrameView(Class<T> viewClass) {
//        return (InternalFrameView<T>) views.get(viewClass);
//    }

    public AresLookupService<View> getViews() {
        return views;
    }
}
