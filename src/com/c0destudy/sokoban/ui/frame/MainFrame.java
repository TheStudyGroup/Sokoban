package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.level.LevelManager;
import com.c0destudy.sokoban.skin.Skin;
import com.c0destudy.sokoban.ui.panel.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener
{
    private final MainPanel mainPanel;
    private final Skin      skin;

    public MainFrame(final Skin skin) {
        super();
        this.mainPanel = new MainPanel(skin, this);
        this.skin = skin;
        setTitle("Sokoban Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        getContentPane().add(mainPanel);
        setSize(mainPanel.getSize());
        pack();
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e){
        final JButton button = (JButton) e.getSource();
        switch (button.getText()) {
            case "New Game":
                FrameManager.showGameFrame(LevelManager.getNewLevel("Level 2"));
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
                break;
        }
        dispose();
    }
}
