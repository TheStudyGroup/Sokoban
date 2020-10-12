package com.c0destudy.sokoban.ui.helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MakeButton
{
    public static JButton make(final String text,
                               final int width,
                               final int height,
                               final boolean isCenter,
                               final Font font,
                               final ActionListener listener
                               ) {
        final JButton button = new JButton(text);

        button.setMaximumSize(new Dimension(width, height));
        if (isCenter) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        button.setFont(font);
        button.addActionListener(listener);

        return button;
    }
}
