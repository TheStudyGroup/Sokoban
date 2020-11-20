package com.c0destudy.sokoban.ui.helper;

import javax.swing.*;
import java.awt.*;

public class RichJButton extends JButton
{
    public RichJButton(final String text) {
        super(text);
    }

    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
        if (color.getAlpha() < 255) {
            setOpaque(false);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!isOpaque() && getBackground().getAlpha() < 255) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(getForeground());
        }
        super.paintComponent(g);
    }
}
