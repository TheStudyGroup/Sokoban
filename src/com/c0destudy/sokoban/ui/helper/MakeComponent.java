package com.c0destudy.sokoban.ui.helper;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
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
        final JButton button = new RichJButton(text);
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

        return button;
    }

    public static JLabel makeLabel(
            final String  text,
            final boolean isCenter,
            final Font    font
    ) {
        return makeLabel(text, isCenter, true, font, null, null);
    }

    public static JLabel makeLabel(
            final String  text,
            final boolean isCenter,
            final boolean isShadow,
            final Font    font,
            final Color   foregroundColor,
            final Color   backgroundColor
    ) {
        final RichJLabel label = new RichJLabel(text, isShadow);
        if (isShadow) {
            label.setForeground(Color.GRAY);
            label.setRightShadow(font.getSize() / 15, font.getSize() / 15, Color.BLACK);
        }
        if (backgroundColor != null) label.setBackground(backgroundColor);
        if (foregroundColor != null) label.setForeground(foregroundColor);
        if (isCenter) label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(font);
        return label;
    }

    public static Box makeHorizontalBox(
            final int             width,
            final int             height,
            final boolean         isCenter,
            final List<Component> components
    ) {
        final Box box = Box.createHorizontalBox();
        box.setMaximumSize(new Dimension(width, height));
        if (isCenter) box.setAlignmentX(Component.CENTER_ALIGNMENT);
        components.forEach(box::add);
        return box;
    }

    public static Component makeVerticalSpace(final int size) {
        return Box.createVerticalStrut(size);
    }

    public static Component makeHorizontalSpace(final int size) {
        return Box.createHorizontalStrut(size);
    }

    public static JScrollPane makeScroll(
            final int             width,
            final int             height,
            final boolean         isCenter,
            final boolean         isTransparent,
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

    public static JTextField makeTextBox(
            final int     width,
            final int     height,
            final boolean isCenter,
            final Font    font,
            final String  defaultText,
            final int     textAlignment
    ) {
        final JTextField text = new JTextField(defaultText, 20);
        final Dimension  size = new Dimension(width, height);
        text.setMaximumSize(size);
        text.setPreferredSize(size);
        text.setFont(font);
        text.setHorizontalAlignment(textAlignment);
        if (isCenter) text.setAlignmentX(Component.CENTER_ALIGNMENT);
        return text;
    }

    public static JFormattedTextField makeNumberTextBox(
            final int     width,
            final int     height,
            final boolean isCenter,
            final Font    font,
            final int     defaultValue,
            final int     textAlignment
    ) {
        final NumberFormat    format    = NumberFormat.getInstance();
        final NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        final JFormattedTextField text = new JFormattedTextField(formatter);
        final Dimension           size = new Dimension(width, height);
        text.setValue(defaultValue);
        text.setMaximumSize(size);
        text.setPreferredSize(size);
        text.setFont(font);
        text.setHorizontalAlignment(textAlignment);
        if (isCenter) text.setAlignmentX(Component.CENTER_ALIGNMENT);
        return text;
    }
}
