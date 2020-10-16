package com.c0destudy.sokoban.ui.helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MakeComponent
{
    public static JButton makeButton(
            final String         text,
            final int            width,
            final int            height,
            final boolean        isCenter,
            final Font           font,
            final ActionListener listener
    ) {
        final JButton button = new JButton(text);
        final Dimension size = new Dimension(width, height);
        button.setMaximumSize(size);   // for BoxLayout
        button.setMinimumSize(size);   // for BoxLayout
        button.setPreferredSize(size); // for JScrollPane
        if (isCenter) button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(font);
        button.addActionListener(listener);
        return button;
    }

    public static JButton makeButton(
            final String         text,
            final int            width,
            final int            height,
            final boolean        isCenter,
            final Font           font,
            final ActionListener listener,
            final Color          foregroundColor,
            final Color          backgroundColor
    ) {
        if (backgroundColor == null || foregroundColor == null) {
            return makeButton(text, width, height, isCenter, font, listener);
        }
        final JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBackground().getAlpha() < 255) {
                    g.setColor(getBackground());
                    g.fillRect(0, 0, getWidth(), getHeight());
                    g.setColor(getForeground());
                }
                super.paintComponent(g);
            }
        };
        final Dimension size = new Dimension(width, height);
        button.setMaximumSize(size);   // for BoxLayout
        button.setMinimumSize(size);   // for BoxLayout
        button.setPreferredSize(size); // for JScrollPane
        if (isCenter) button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(font);
        button.addActionListener(listener);
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setFocusPainted(false); // 포커스 테두리 제거
        if (backgroundColor.getAlpha() < 255) button.setOpaque(false);
        return button;
    }

    public static JLabel makeLabel(final String text,
                                   final boolean isCenter,
                                   final Font Font
    ) {
        final RichJLabel label = new RichJLabel(text);
        label.setForeground(Color.GRAY);
        label.setRightShadow(Font.getSize() / 15,Font.getSize() / 15, Color.BLACK);
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
//        return Box.createRigidArea(new Dimension(1, size));
        return Box.createVerticalStrut(size);
    }

    public static Component makeHSpace(final int size) {
//        return Box.createRigidArea(new Dimension(size, 1));
        return Box.createHorizontalStrut(size);
    }

    public static JScrollPane makeScroll(final int width,
                                         final int height,
                                         final boolean isCenter,
                                         final boolean isTransparent,
                                         final List<Component> components

    ) {
        final JPanel      panel  = new JPanel();
        final JScrollPane scroll = new JScrollPane(panel);
        final Dimension   size   = new Dimension(width, height);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        scroll.setMaximumSize(size);
        scroll.setPreferredSize(size);
        if (isCenter) scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        if (isTransparent) {
            panel.setOpaque(false);
            scroll.setOpaque(false);
            scroll.getViewport().setOpaque(false);
        }
        components.forEach(panel::add);
        return scroll;
    }
}
