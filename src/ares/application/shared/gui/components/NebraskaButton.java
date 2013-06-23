package ares.application.shared.gui.components;

import temp.ColorPalette;
import temp.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class NebraskaButton extends JButton implements ComponentListener, KeyListener {

    protected static final int DEFAULT_WIDTH = 150;
    protected static final int DEFAULT_HEIGHT = 50;

    protected static final Insets INSETS_MARGIN = new Insets(2, 5, 2, 5);

    protected static final int BORDER_WIDTH = 5;

    protected float alpha = 1f;
    protected double m_dWidthFill = 0d;
    protected double m_dHeightFill = 0d;

    protected Shape m_shape = null;
    protected Area m_areaFill = null;
    protected Area m_areaDraw = null;

    protected RoundRectangle2D m_rrect2dFill = null;
    protected Rectangle2D m_rect2dAFill = null;
    protected Rectangle2D m_rect2dBFill = null;

    protected double m_dWidthDraw = 0d;
    protected double m_dHeightDraw = 0d;

    protected RoundRectangle2D m_rect2dDraw = null;
    protected Rectangle2D m_rect2dADraw = null;
    protected Rectangle2D m_rect2dBDraw = null;

    protected int m_nStringWidthMax = 0;
    protected int m_nMinWidth = 0;


    public NebraskaButton(Action action, float alpha) {
        this(action, 0, alpha);
    }

    public NebraskaButton(Action action, int nMinWidth, float alpha) {
        super(action);

        this.alpha = alpha;
        m_nMinWidth = nMinWidth;

        this.setContentAreaFilled(false);
        this.setMargin(INSETS_MARGIN);
        this.setFocusPainted(false);

        this.addComponentListener(this);
        this.addKeyListener(this);

        //determine the buttons initial size ----------------------

        //WARNING: Use UIManager font, else font here is not dynamic
        Font font = (Font) UIManager.get("Button.font");
        Frame frame = JOptionPane.getRootFrame();
        FontMetrics fm = frame.getFontMetrics(font);

        m_nStringWidthMax = fm.stringWidth(this.getText());
        m_nStringWidthMax =
                Math.max(m_nStringWidthMax, fm.stringWidth(this.getText()));

        //WARNING: use getMargin. it refers to dist btwn text and border.
        //also use getInsets. it refers to the width of the border
        int nWidth = Math.max(m_nMinWidth,
                m_nStringWidthMax +
                        this.getMargin().left +
                        this.getInsets().left +
                        this.getMargin().right +
                        this.getInsets().right);

        this.setPreferredSize(new Dimension(nWidth, DEFAULT_HEIGHT));

        //set the initial draw and fill dimensions ------------------

        m_dWidthFill = (double) this.getPreferredSize().width - 1;
        m_dHeightFill = (double) this.getPreferredSize().height - 1;

        m_dWidthDraw =
                ((double) this.getPreferredSize().width - 1) - (BORDER_WIDTH - 1);
        m_dHeightDraw =
                ((double) this.getPreferredSize().height - 1) - (BORDER_WIDTH - 1);

        this.setShape();
        this.setForeground(UIManager.getColor("Button.background"));
        this.setFont(getFont().deriveFont(Font.BOLD, 16));
    }


    public void setButtonText(String strText) {
        super.setText(strText);

        int nWidth = Math.max(
                m_nMinWidth,
                m_nStringWidthMax +
                        this.getInsets().left +
                        this.getInsets().right);
        int nHeight = Math.max(0, this.getPreferredSize().height);
        this.setPreferredSize(new Dimension(nWidth, nHeight));

        m_dWidthFill = this.getBounds().width - 1;
        m_dHeightFill = this.getBounds().height - 1;

        if (m_dWidthFill <= 0 || m_dHeightFill <= 0) {
            m_dWidthFill = (double) this.getPreferredSize().width - 1;
            m_dHeightFill = (double) this.getPreferredSize().height - 1;
        }

        m_dWidthDraw = m_dWidthFill - (BORDER_WIDTH - 1);
        m_dHeightDraw = m_dHeightFill - (BORDER_WIDTH - 1);

        this.setShape();
    }

    protected void setShape() {

        //area --------------------------------------

        double dArcLengthFill = Math.min(m_dWidthFill, m_dHeightFill);
        double dOffsetFill = dArcLengthFill / 2;

        m_rrect2dFill = new RoundRectangle2D.Double(
                0d, 0d, m_dWidthFill, m_dHeightFill,
                dArcLengthFill, dArcLengthFill);
        //WARNING: arclength and archeight are divided by 2
        //when they get into the roundedrectangle shape

        m_rect2dAFill = new Rectangle2D.Double(
                0d, dOffsetFill, m_dWidthFill - dOffsetFill,
                m_dHeightFill - dOffsetFill);
        m_rect2dBFill = new Rectangle2D.Double(
                dOffsetFill, 0d, m_dWidthFill - dOffsetFill,
                m_dHeightFill - dOffsetFill);

        m_areaFill = new Area(m_rrect2dFill);
        m_areaFill.add(new Area(m_rect2dAFill));
        m_areaFill.add(new Area(m_rect2dBFill));

        //border ------------------------------------------------

        double dArcLengthDraw = Math.min(m_dWidthDraw, m_dHeightDraw);
        double dOffsetDraw = dArcLengthDraw / 2;

        m_rect2dDraw = new RoundRectangle2D.Double(
                (BORDER_WIDTH - 1) / 2,
                (BORDER_WIDTH - 1) / 2,
                m_dWidthDraw,
                m_dHeightDraw,
                dArcLengthDraw,
                dArcLengthDraw);

        m_rect2dADraw = new Rectangle2D.Double(
                (BORDER_WIDTH - 1) / 2,
                dOffsetDraw + (BORDER_WIDTH - 1) / 2,
                m_dWidthDraw - dOffsetDraw,
                m_dHeightDraw - dOffsetDraw);

        m_rect2dBDraw = new Rectangle2D.Double(
                dOffsetDraw + (BORDER_WIDTH - 1) / 2,
                (BORDER_WIDTH - 1) / 2,
                m_dWidthDraw - dOffsetDraw,
                m_dHeightDraw - dOffsetDraw);

        m_areaDraw = new Area(m_rect2dDraw);
        m_areaDraw.add(new Area(m_rect2dADraw));
        m_areaDraw.add(new Area(m_rect2dBDraw));
    }


    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        AlphaComposite newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2.setComposite(newComposite);

        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2.setRenderingHints(hints);
//        Color focus = UIManager.getColor("Button.light");
        Color focus = ColorPalette.ARMY_YELLOW;
        Color background = UIManager.getColor("Button.foreground");
        Color select = ColorUtil.shade(focus, 0.5);
        if (getModel().isArmed()) {
            g2.setColor(select);
        } else {
            if (this.hasFocus()) {
                g2.setColor(focus);
            } else {
                g2.setColor(background);
            }
        }

        g2.fill(m_areaFill);

        super.paintComponent(g2);
    }

    @Override
    protected void paintBorder(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2.setRenderingHints(hints);
        Color borderColor =   UIManager.getColor("Button.background");
        g2.setColor(borderColor);

        Stroke strokeOld = g2.getStroke();
        g2.setStroke(
                new BasicStroke(
                        BORDER_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
        );
        g2.draw(m_areaDraw);

        if (this.hasFocus()) {
            g2.setColor(Color.white);
            g2.draw(m_areaDraw);
        }

        g2.setStroke(strokeOld);
    }

    @Override
    public boolean contains(int nX, int nY) {
        if (null == m_shape || m_shape.getBounds().equals(getBounds())) {
            m_shape = new Rectangle2D.Float(
                    0, 0, this.getBounds().width, this.getBounds().height);
        }
        return m_shape.contains(nX, nY);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        m_shape = new Rectangle2D.Float(
                0, 0, this.getBounds().width, this.getBounds().height);

        m_dWidthFill = (double) this.getBounds().width - 1;
        m_dHeightFill = (double) this.getBounds().height - 1;

        m_dWidthDraw = ((double) this.getBounds().width - 1) -
                (BORDER_WIDTH - 1);
        m_dHeightDraw = ((double) this.getBounds().height - 1) -
                (BORDER_WIDTH - 1);

        this.setShape();
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    /**
     * This is so the button is triggered when it has focus and we press the Enter key.
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == this && e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.doClick();
        }
    }

    public void keyReleased(KeyEvent e) {
//        if (getModel().isArmed()) getModel().setArmed(false);
    }

    public void keyTyped(KeyEvent e) {
    }

}
