package ares.platform.application;

import ares.platform.view.AbstractView;
import ares.platform.view.WindowUtil;
import javax.swing.JFrame;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractAresApplication extends AbstractView<JFrame> {

    public void setTitle(String title) {
        contentPane.setTitle(title);
    }

    public void show() {
        WindowUtil.centerAndShow(contentPane);
    }

    public abstract void switchCard(String cardName);
}
