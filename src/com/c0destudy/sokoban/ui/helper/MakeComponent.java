package com.c0destudy.sokoban.ui.helper;

import com.c0destudy.sokoban.skin.Skin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MakeComponent
{
    public static JButton makeButton(final String text,
                                     final int width,
                                     final int height,
                                     final boolean isCenter,
                                     final Font font,
                                     final ActionListener listener
    ) {
        final JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(width, height));
        if (isCenter) button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(font);
        button.addActionListener(listener);
        return button;
    }

    public static JLabel makeLabel(final String text,
                                   final boolean isCenter,
                                   final Font Font
    ) {
        final JLabel label = new JLabel(text);
        if (isCenter) label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(Font);
        return label;
    }

    public static Box makeHBox(final int width,
                               final int height,
                               final boolean isCenter,
                               final List<Component> components
    ) {
        final Box box = Box.createHorizontalBox();
        box.setMaximumSize(new Dimension(width, height));
        if (isCenter) box.setAlignmentX(Component.CENTER_ALIGNMENT);
        components.forEach(box::add);
        return box;
    }

    public static Component makeVSpace(final int size) {
        return Box.createVerticalStrut(size);
    }

    public static Component makeHSpace(final int size) {
        return Box.createHorizontalStrut(size);
    }
}
