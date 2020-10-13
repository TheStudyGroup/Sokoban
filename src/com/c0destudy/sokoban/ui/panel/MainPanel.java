package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.skin.Skin;
import com.c0destudy.sokoban.skin.SkinManager;

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

        JLabel lblTitle = new JLabel("S O K O B A N");
        JButton btnStart = new JButton("New Game");
        JButton btnContinue = new JButton("Continue");
        JButton btnRanking = new JButton("Ranking");
        JButton btnSetting = new JButton("Setting");
        JButton btnAbout = new JButton("About");
        JButton btnExit = new JButton("Exit Game");
        Box boxSettingAbout = Box.createHorizontalBox();
        boxSettingAbout.add(btnSetting);
        boxSettingAbout.add(Box.createHorizontalStrut(20));
        boxSettingAbout.add(btnAbout);

        lblTitle.setFont(skin.getFont(Skin.FONTS.MainTitle));
        final Font buttonFont = skin.getFont(Skin.FONTS.MainButton);
        btnStart.setFont(buttonFont);
        btnContinue.setFont(buttonFont);
        btnRanking.setFont(buttonFont);
        btnSetting.setFont(buttonFont);
        btnAbout.setFont(buttonFont);
        btnExit.setFont(buttonFont);

        btnStart.addActionListener(buttonListener);
        btnContinue.addActionListener(buttonListener);
        btnRanking.addActionListener(buttonListener);
        btnSetting.addActionListener(buttonListener);
        btnAbout.addActionListener(buttonListener);
        btnExit.addActionListener(buttonListener);

        final Dimension dimButton = new Dimension(400,45);
        btnStart.setMaximumSize(dimButton);
        btnContinue.setMaximumSize(dimButton);
        btnRanking.setMaximumSize(dimButton);
        boxSettingAbout.setMaximumSize(dimButton);
        btnSetting.setMaximumSize(new Dimension(260,45));
        btnAbout.setMaximumSize(new Dimension(120,45));
        btnExit.setMaximumSize(dimButton);

        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnContinue.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRanking.setAlignmentX(Component.CENTER_ALIGNMENT);
        boxSettingAbout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(50));
        add(lblTitle);
        add(Box.createVerticalStrut(40));
        add(btnStart);
        add(Box.createVerticalStrut(20));
        add(btnContinue);
        add(Box.createVerticalStrut(20));
        add(btnRanking);
        add(Box.createVerticalStrut(20));
        add(boxSettingAbout);
        add(Box.createVerticalStrut(20));
        add(btnExit);
    }
}
