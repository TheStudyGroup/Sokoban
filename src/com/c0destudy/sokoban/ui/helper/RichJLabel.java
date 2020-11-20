package com.c0destudy.sokoban.ui.helper;

import javax.swing.*;
import java.awt.*;

public class RichJLabel extends JLabel
{
    private boolean   shadow;
    private final int tracking;
    private int       leftX;
    private int       leftY;
    private int       rightX;
    private int       rightY;
    private Color     leftColor;
    private Color     rightColor;

    public RichJLabel(final String text) {
        this(text, false, 0);
    }

    public RichJLabel(final String text, final boolean shadow) {
        this(text, shadow, 0);
    }

    public RichJLabel(final String text, final boolean shadow, final int tracking) {
        super(text, JLabel.CENTER);
        this.shadow   = shadow;
        this.tracking = tracking;
    }

    public void setLeftShadow(final int x, final int y, final Color color) {
        leftX     = x;
        leftY     = y;
        leftColor = color;
    }

    public void setRightShadow(final int x, final int y, final Color color) {
        rightX     = x;
        rightY     = y;
        rightColor = color;
    }

    public Dimension getPreferredSize() {
        if (shadow) {
            final String text = getText();
            final FontMetrics fm = this.getFontMetrics(getFont());
            int w = fm.stringWidth(text);
            w += (text.length() - 1) * tracking;
            w += leftX + rightX;
            int h = fm.getHeight();
            h += leftY + rightY;
            return new Dimension(w, h);
        } else {
            return super.getPreferredSize();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (shadow) {
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            final FontMetrics fontMetrics = this.getFontMetrics(getFont());
            int h = fontMetrics.getAscent();
            int x = 0;
            for (char ch : getText().toCharArray()) {
                int w = fontMetrics.charWidth(ch) + tracking;
                g.setColor(leftColor);
                g.drawString("" + ch, x - leftX, h - leftY);
                g.setColor(rightColor);
                g.drawString("" + ch, x + rightX, h + rightY);
                g.setColor(getForeground());
                g.drawString("" + ch, x, h);
                x += w;
            }
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        } else {
            if (!isOpaque() && getBackground().getAlpha() < 255) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(getForeground());
            }
            super.paintComponent(g);
        }
    }
}