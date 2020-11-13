package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.skin.Skin;
import com.c0destudy.sokoban.ui.frame.FrameManager;
import com.c0destudy.sokoban.ui.helper.MakeComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BasePanel extends JPanel
{
    private final Skin           skin;
    private final ActionListener listener;

    public BasePanel(final ActionListener listener, final int width, final int height) {
        super();
        this.skin     = FrameManager.getSkin();
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
        final Image backgroundImage = skin.getBackgroundImage();
        if (backgroundImage != null) {
            final int imageWidth  = backgroundImage.getWidth(null);
            final int imageHeight = backgroundImage.getHeight(null);
            int imageX = 0;
            int imageY = 0;
            if (getWidth()  < imageWidth)  imageX = -((imageWidth  - getWidth())  / 2);
            if (getHeight() < imageHeight) imageY = -((imageHeight - getHeight()) / 2);
            g.drawImage(backgroundImage, imageX, imageY, null);
        } else {
            g.setColor(skin.getBackgroundColor());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    // UI helper methods

    protected JButton makeButton(final String text, final int width, final int height, final boolean isCenter, final Font font) {
        return MakeComponent.makeButton(
                text,
                width,
                height,
                isCenter,
                font,
                listener,
                skin.getButtonForegroundColor(),
                skin.getButtonBackgroundColor());
    }

    protected JButton makeButton(final String text, final int width, final int height, final boolean isCenter) {
        return makeButton(text, width, height, isCenter, skin.getFont(Skin.FONTS.SmallButton));
    }

    protected JButton makeLargeButton(final String text, final int width, final int height, final boolean isCenter) {
        return makeButton(text, width, height, isCenter, skin.getFont(Skin.FONTS.LargeButton));
    }

    protected JLabel makeLabel(final String text, final boolean isCenter, final Font font) {
        return MakeComponent.makeLabel(text, isCenter, font);
    }

    protected JLabel makeLabel(final String text, final boolean isCenter) {
        return makeLabel(text, isCenter, skin.getFont(Skin.FONTS.Text));
    }

    protected JLabel makeTitleLabel(final String text, final boolean isCenter) {
        return makeLabel(text, isCenter, skin.getFont(Skin.FONTS.Title));
    }
}
