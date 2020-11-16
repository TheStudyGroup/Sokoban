package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.resource.Resource;
import com.c0destudy.sokoban.resource.Skin;
import com.c0destudy.sokoban.ui.frame.FrameManager;
import com.c0destudy.sokoban.ui.helper.MakeComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BasePanel extends JPanel
{
    protected final Skin           skin;
    private   final ActionListener listener;

    public BasePanel(final ActionListener listener, final int width, final int height) {
        super();
        this.skin     = Resource.getSkin();
        this.listener = listener;

        setMaximumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public BasePanel(final ActionListener listener) {
        this(listener, 800, 500);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Image image = skin.getImage(Skin.IMAGES.Background);
        if (image != null) {
            final int imageWidth  = image.getWidth(null);
            final int imageHeight = image.getHeight(null);
            int imageX = 0;
            int imageY = 0;
            if (getWidth()  < imageWidth)  imageX = -((imageWidth  - getWidth())  / 2);
            if (getHeight() < imageHeight) imageY = -((imageHeight - getHeight()) / 2);
            g.drawImage(image, imageX, imageY, null);
        } else {
            g.setColor(skin.getColor(Skin.COLORS.Background));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    // UI helper methods

    // Button
    protected JButton makeButton(final String text, final int width, final int height, final boolean isCenter, final Font font) {
        return MakeComponent.makeButton(
                text, width, height, isCenter, font, listener,
                skin.getColor(Skin.COLORS.ButtonText),
                skin.getColor(Skin.COLORS.Button));
    }
    protected JButton makeButton(final String text, final int width, final int height, final boolean isCenter) {
        return makeButton(text, width, height, isCenter, skin.getFont(Skin.FONTS.Medium));
    }
    protected JButton makeLargeButton(final String text, final int width, final int height, final boolean isCenter) {
        return makeButton(text, width, height, isCenter, skin.getFont(Skin.FONTS.Large));
    }

    // Label
    protected JLabel makeLabel(final String text, final boolean isCenter) {
        return MakeComponent.makeLabel(text, isCenter, skin.getFont(Skin.FONTS.Medium));
    }
    protected JLabel makeTitleLabel(final String text, final boolean isCenter) {
        return MakeComponent.makeLabel(text, isCenter, skin.getFont(Skin.FONTS.Title));
    }
    protected JLabel makeLargeLabel(final String text, final boolean isCenter) {
        return MakeComponent.makeLabel(text, isCenter, skin.getFont(Skin.FONTS.Large));
    }
    protected JLabel makeSmallLabel(final String text, final boolean isCenter) {
        return MakeComponent.makeLabel(text, isCenter, skin.getFont(Skin.FONTS.Small));
    }

    // Text
    public JTextField makeTextBox(
            final int     width,
            final int     height,
            final boolean isCenter,
            final String  defaultText,
            final int     textAlignment
    ) {
        return MakeComponent.makeTextBox(width, height, isCenter, skin.getFont(Skin.FONTS.Large), defaultText, textAlignment);
    }

    public JFormattedTextField makeNumberTextBox(
            final int     width,
            final int     height,
            final boolean isCenter,
            final int     defaultValue,
            final int     textAlignment
    ) {
        return MakeComponent.makeNumberTextBox(width, height, isCenter, skin.getFont(Skin.FONTS.Large), defaultValue, textAlignment);
    }
}
