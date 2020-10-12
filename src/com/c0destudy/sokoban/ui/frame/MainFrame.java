package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.level.LevelManager;
import com.c0destudy.sokoban.misc.Resource;
import com.c0destudy.sokoban.skin.Skin;
import com.c0destudy.sokoban.ui.panel.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class MainFrame extends JFrame implements ActionListener
{
    private final MainPanel mainPanel;

    public MainFrame(final Skin skin) {
        super();
        this.mainPanel = new MainPanel(skin, this);
        setTitle("Sokoban Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        getContentPane().add(mainPanel);
        if (!LevelManager.isPausedLevelExisting()) {
            mainPanel.setContinueButtonEnabled(false);
        }
        setSize(mainPanel.getSize());
        pack();
        setLocationRelativeTo(null);
    }

    private void closeUI() {
        dispose();
    }

    public void actionPerformed(ActionEvent e){
        final JButton button = (JButton) e.getSource();
        switch (button.getText()) {
            case "New Game":
                FrameManager.showGameFrame(LevelManager.getNewLevel("Level 1"));
                break;
            case "Continue":
                FrameManager.showGameFrame(LevelManager.readLevelFromFile(Resource.PATH_LEVEL_PAUSE));
                (new File(Resource.PATH_LEVEL_PAUSE)).delete();
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
        closeUI();
    }

    private class TKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    closeUI();
                    break;
            }
        }
    }
}
