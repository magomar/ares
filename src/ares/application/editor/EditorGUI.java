package ares.application.editor;

import ares.application.editor.controllers.EditorController;
import ares.application.player.AresPlayerGUI;
import ares.application.shared.gui.WindowUtil;
import ares.application.shared.gui.views.AbstractView;
import de.muntjak.tinylookandfeel.Theme;
import de.muntjak.tinylookandfeel.ThemeDescription;
import de.muntjak.tinylookandfeel.TinyLookAndFeel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class EditorGUI extends AbstractView<JFrame> {
    @Override
    protected JFrame layout() {
        return new JFrame();
    }

    public static void main(String[] args) {
        Toolkit.getDefaultToolkit().setDynamicLayout(true);
        System.setProperty("sun.awt.noerasebackground", "true");
        try {
            UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
            ThemeDescription[] themes = Theme.getAvailableThemes();
            for (ThemeDescription theme: themes) {
                if ("DarkOlive".equals(theme.getName())) Theme.loadTheme(theme);
            }
            UIManager.setLookAndFeel(new TinyLookAndFeel());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AresPlayerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EditorGUI mainView = new EditorGUI();
                new EditorController(mainView);
                WindowUtil.centerAndShow(mainView.contentPane);
            }
        });
    }
}
