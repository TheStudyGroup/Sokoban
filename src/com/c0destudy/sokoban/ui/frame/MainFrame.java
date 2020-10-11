package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.SkinManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener
{
    private JPanel contentPane = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }

    public MainFrame() {
        super();
        SkinManager.loadSkin();
        initUI();
    }

    private void initUI() {
        setTitle("SOKOBAN GAME");
        setSize(800,500);
        setContentPane(getBaseContentPane());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel getBaseContentPane() {
        if (contentPane == null) {
            contentPane = new JPanel();
            contentPane.setLayout(null);
            contentPane.setBackground(Color.GRAY); // for test

            JPanel panel = new JPanel();
            panel.setBounds(0, 0, getWidth(), getHeight()); // full size
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            contentPane.add(panel);

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

            lblTitle.setFont(SkinManager.fontMainTitle);
            btnStart.setFont(SkinManager.fontMainButton);
            btnContinue.setFont(SkinManager.fontMainButton);
            btnRanking.setFont(SkinManager.fontMainButton);
            btnSetting.setFont(SkinManager.fontMainButton);
            btnAbout.setFont(SkinManager.fontMainButton);
            btnExit.setFont(SkinManager.fontMainButton);

            btnStart.addActionListener(this);
            btnContinue.addActionListener(this);
            btnRanking.addActionListener(this);
            btnSetting.addActionListener(this);
            btnAbout.addActionListener(this);
            btnExit.addActionListener(this);

            Dimension dimButton = new Dimension(400,45);
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

            panel.add(Box.createVerticalStrut(30));
            panel.add(lblTitle);
            panel.add(Box.createVerticalStrut(30));
            panel.add(btnStart);
            panel.add(Box.createVerticalStrut(20));
            panel.add(btnContinue);
            panel.add(Box.createVerticalStrut(20));
            panel.add(btnRanking);
            panel.add(Box.createVerticalStrut(20));
            panel.add(boxSettingAbout);
            panel.add(Box.createVerticalStrut(20));
            panel.add(btnExit);
        }

        return contentPane;
    }

    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        switch (button.getText()) {
            case "New Game":
                break;
            case "Continue":
                break;
            case "Ranking":
                break;
            case "Setting":
                break;
            case "About":
                break;
            case "Exit Game":
                dispose();
                break;
        }
    }
}

