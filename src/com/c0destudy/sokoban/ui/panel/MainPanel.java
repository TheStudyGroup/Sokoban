package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.skin.Skin;
import com.c0destudy.sokoban.ui.helper.MakeButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel
{
    private final Skin skin;
    private final ActionListener buttonListener;

    public MainPanel(final Skin skin, final ActionListener buttonListener) {
        super();
        this.skin = skin;
        this.buttonListener = buttonListener;
        initUI();
    }

    private void initUI() {
        setPreferredSize(new Dimension(800, 500));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel lblTitle = new JLabel("S O K O B A N");
        lblTitle.setFont(skin.getFont(Skin.FONTS.MainTitle));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        final Font buttonFont = skin.getFont(Skin.FONTS.MainButton);

        add(Box.createVerticalStrut(50));
        add(lblTitle);

        add(Box.createVerticalStrut(40));
        add(MakeButton.make("New Game", 400, 45, true, buttonFont, buttonListener));

        add(Box.createVerticalStrut(20));
        add(MakeButton.make("Continue", 400, 45, true, buttonFont, buttonListener));

        add(Box.createVerticalStrut(20));
        add(MakeButton.make("Ranking", 400, 45, true, buttonFont, buttonListener));

        add(Box.createVerticalStrut(20));
        final Box box = Box.createHorizontalBox();
        box.setMaximumSize(new Dimension(400, 45));
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(MakeButton.make("Setting", 260, 45, false, buttonFont, buttonListener));
        box.add(Box.createHorizontalStrut(20));
        box.add(MakeButton.make("About", 120, 45, false, buttonFont, buttonListener));
        add(box);

        add(Box.createVerticalStrut(20));
        add(MakeButton.make("Exit Game", 400, 45, true, buttonFont, buttonListener));
    }
}
