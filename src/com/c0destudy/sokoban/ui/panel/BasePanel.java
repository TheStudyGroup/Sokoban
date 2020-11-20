package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.resource.Skin;
import com.c0destudy.sokoban.ui.helper.MakeComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BasePanel extends JPanel
{
    protected final Skin           skin;
    private   final ActionListener listener;

    public BasePanel(final ActionListener listener) {
        this(listener, 800, 500);
    }

    public BasePanel(final ActionListener listener, final int width, final int height) {
        super();
        this.skin     = Skin.getCurrentSkin();
        this.listener = listener;

        setMaximumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
    protected JLabel makeTitleLabel(final String text) {
        return MakeComponent.makeLabel(text, true, skin.getFont(Skin.FONTS.Title));
    }
    protected JLabel makeLargeLabel(final String text) {
        return MakeComponent.makeLabel(text, true, skin.getFont(Skin.FONTS.Large));
    }
    protected JLabel makeSmallLabel(final String text) {
        return MakeComponent.makeLabel(text, true, skin.getFont(Skin.FONTS.Small));
    }
    protected JLabel makeTransLabel(final String text) {
        return MakeComponent.makeLabel(text, true, false, skin.getFont(Skin.FONTS.Medium), skin.getColor(Skin.COLORS.Text), new Color(255, 255, 255, 180));
    }
    protected JLabel makeTransLargeLabel(final String text) {
        return MakeComponent.makeLabel(text, true, false, skin.getFont(Skin.FONTS.Large), skin.getColor(Skin.COLORS.Text), new Color(255, 255, 255, 110));
    }

    // Text
    protected JTextField makeTextBox(final int width, final int height, final boolean isCenter, final String defaultText, final int textAlignment) {
        return MakeComponent.makeTextBox(width, height, isCenter, skin.getFont(Skin.FONTS.Large), defaultText, textAlignment);
    }

    protected JFormattedTextField makeNumberTextBox(final int width, final int height, final boolean isCenter, final int defaultValue, final int textAlignment) {
        return MakeComponent.makeNumberTextBox(width, height, isCenter, skin.getFont(Skin.FONTS.Large), defaultValue, textAlignment);
    }
}
