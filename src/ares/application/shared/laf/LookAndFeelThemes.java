package ares.application.shared.laf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import javax.swing.*;
import javax.swing.plaf.*;

public class LookAndFeelThemes {

    public static void loadDarkTheme() {
        // Primary
        UIManager.put("control", new ColorUIResource(0x888888));
        UIManager.put("info", new ColorUIResource(0x0));
        UIManager.put("nimbusAlertYellow", new ColorUIResource(0xffdc23));
        UIManager.put("nimbusBase", new ColorUIResource(0x0));
        UIManager.put("nimbusDisabledText", new ColorUIResource(0xcccccc));
        UIManager.put("nimbusFocus", new ColorUIResource(0x73a4d1));
        UIManager.put("nimbusGreen", new ColorUIResource(0xccffcc));
        UIManager.put("nimbusInfoBlue", new ColorUIResource(0xccccff));
        UIManager.put("nimbusLightBackground", new ColorUIResource(0x333333));
        UIManager.put("nimbusOrange", new ColorUIResource(0xbf6204));
        UIManager.put("nimbusRed", new ColorUIResource(0xa92e22));
        UIManager.put("nimbusSelectedText", new ColorUIResource(0x111111));
        UIManager.put("nimbusSelectionBackground", new ColorUIResource(0xdddddd));
        UIManager.put("text", new ColorUIResource(0xffffff));
        // Secondary
        UIManager.put("activeCaption", new ColorUIResource(0x666666));
        UIManager.put("background", new ColorUIResource(0x444444));
        UIManager.put("controlDkShadow", new ColorUIResource(0x888888));
        UIManager.put("controlLHighlight", new ColorUIResource(0x555555));
        UIManager.put("controlHighlight", new ColorUIResource(0x222222));
        UIManager.put("controlShadow", new ColorUIResource(0x55555));
        UIManager.put("controlText", new ColorUIResource(0xeeeeee));
        UIManager.put("desktop", new ColorUIResource(0xcccccc));
        UIManager.put("inactiveCaption", new ColorUIResource(0x333333));
        UIManager.put("infotext", new ColorUIResource(0xffffff));
        UIManager.put("menu", new ColorUIResource(0x0));
        UIManager.put("menuText", new ColorUIResource(0xffffff));
        UIManager.put("nimbusBlueGrey", new ColorUIResource(0xa9b0be));
        UIManager.put("nimbusFocus", new ColorUIResource(0x9999));
        UIManager.put("nimbusDisabledText", new ColorUIResource(0xcccccc));
        UIManager.put("nimbusSelectedText", new ColorUIResource(0xffffff));
        UIManager.put("nimbusLightBackground", new ColorUIResource(0x333333));
        UIManager.put("nimbusInfoBlue", new ColorUIResource(0x2f5cb4));
        UIManager.put("nimbusInfoBlue", new ColorUIResource(0xccccff));
        UIManager.put("scrollbar", new ColorUIResource(0xaaaaaa));
        UIManager.put("textBackground", new ColorUIResource(0xbbbbbb));
        UIManager.put("textForeground", new ColorUIResource(0xeeeeee));
        UIManager.put("textHighl", new ColorUIResource(0x222222));
        UIManager.put("textHighlight", new ColorUIResource(0xbbbbbb));
        UIManager.put("textHighlightText", new ColorUIResource(0x222222));
        UIManager.put("textInactiveText", new ColorUIResource(0xcccccc));

    }

    public static void finalizeDarkTheme() {
        UIManager.getLookAndFeelDefaults().put("MenuBar[Enabled].backgroundPainter", new FillPainter(new Color(0, 0, 0)));
        UIManager.getLookAndFeelDefaults().put("MenuBar.background", new ColorUIResource(0x0));
        UIManager.getLookAndFeelDefaults().put("MenuBar.foreground", new ColorUIResource(0xffffff));
        UIManager.getLookAndFeelDefaults().put("ToolBar[Enabled].handleIconPainter", new FillPainter(new Color(0, 0, 0)));
        UIManager.getLookAndFeelDefaults().put("ToolBar.background", new ColorUIResource(0x0));
        UIManager.getLookAndFeelDefaults().put("ToolBar.foreground", new ColorUIResource(0xffffff));
//        UIManager.getLookAndFeelDefaults().put("Menu.background", new ColorUIResource(0x0));
//        UIManager.getLookAndFeelDefaults().put("Menu.disabled", new ColorUIResource(0x999999));
//        UIManager.getLookAndFeelDefaults().put("Menu.disabledText", new ColorUIResource(0xaaaaaa));
        UIManager.getLookAndFeelDefaults().put("MenuItem[Enabled].textForeground", new ColorUIResource(0xffffff));
        UIManager.getLookAndFeelDefaults().put("MenuItem[Disabled].textForeground", new ColorUIResource(0xaaaaaa));
    }

    static class FillPainter implements Painter<JComponent> {

        private final Color color;

        FillPainter(final Color c) {
            color = c;
        }

        @Override
        public void paint(Graphics2D g, JComponent object, int width, int height) {
            g.setColor(color);
            g.fillRect(0, 0, width - 1, height - 1);
        }
    }
}