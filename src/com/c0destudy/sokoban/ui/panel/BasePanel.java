package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.skin.Skin;
import com.c0destudy.sokoban.ui.frame.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BasePanel extends JPanel
{
    protected final Skin           skin;
    protected final ActionListener listener;
    protected final Font           fontTitle;
    protected final Font           fontText;
    protected final Font           fontLargeButton;
    protected final Font           fontSmallButton;
    protected final Color          colorButtonBack;
    protected final Color          colorButton;

    public BasePanel(final ActionListener listener) {
        super();
        this.skin            = FrameManager.getSkin();
        this.listener        = listener;
        this.fontTitle       = skin.getFont(Skin.FONTS.Title);
        this.fontText        = skin.getFont(Skin.FONTS.Text);
        this.fontLargeButton = skin.getFont(Skin.FONTS.LargeButton);
        this.fontSmallButton = skin.getFont(Skin.FONTS.SmallButton);
        this.colorButtonBack = skin.getButtonBackgroundColor();
        this.colorButton     = skin.getButtonForegroundColor();
        initUI();
    }

    private void initUI() {
        setMaximumSize(new Dimension(800, 500));
        setPreferredSize(new Dimension(800, 500));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Image backgroundImage = skin.getBackgroundImage();
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, null);
        } else {
            g.setColor(skin.getBackgroundColor());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
