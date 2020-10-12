package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.level.LevelManager;
import com.c0destudy.sokoban.misc.Resource;
import com.c0destudy.sokoban.skin.Skin;
import com.c0destudy.sokoban.ui.panel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainFrame extends JFrame implements ActionListener
{
    private final ArrayList<JPanel> panels = new ArrayList<>();
    private final MainPanel         mainPanel;
    private final LevelPanel        levelPanel;
    private final RankingPanel      rankingPanel;
    private final RecordingPanel    recordingPanel;
    private final SettingPanel      settingPanel;
    private final AboutPanel        aboutPanel;

    public MainFrame(final Skin skin) {
        super();
        panels.add(mainPanel      = new MainPanel(skin, this));
        panels.add(levelPanel     = new LevelPanel(skin, this));
        panels.add(rankingPanel   = new RankingPanel());
        panels.add(recordingPanel = new RecordingPanel());
        panels.add(settingPanel   = new SettingPanel());
        panels.add(aboutPanel     = new AboutPanel());
        if (!LevelManager.isPausedLevelExisting()) {
            mainPanel.setContinueButtonEnabled(false);
        }

        setTitle("Sokoban Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new OverlayLayout(panel));
        panels.forEach(e -> {
            e.setAlignmentY(Component.TOP_ALIGNMENT);
            panel.add(e);
        });
        selectPanel(mainPanel);
//        mainPanel.setBackground(new Color(255, 255, 255, 100));
        add(panel);
        setSize(mainPanel.getSize());
        pack();
        setLocationRelativeTo(null);
    }

    private void selectPanel(final JPanel panel) {
        panels.forEach(e -> {
            e.setVisible(false);
        });
        panel.setVisible(true);
    }

    private void closeUI() {
        dispose();
    }

    public void actionPerformed(ActionEvent e){
        final JButton button = (JButton) e.getSource();
        switch (button.getText()) {
            case "New Game":
                selectPanel(levelPanel);

//                FrameManager.showGameFrame(LevelManager.getNewLevel("Level 1"));
                break;
            case "Continue":
                FrameManager.showGameFrame(LevelManager.readLevelFromFile(Resource.PATH_LEVEL_PAUSE));
                (new File(Resource.PATH_LEVEL_PAUSE)).delete();
                closeUI();
                break;
            case "Ranking":
                selectPanel(rankingPanel);
                break;
            case "Recordings":
                selectPanel(recordingPanel);
                break;
            case "Settings":
                selectPanel(settingPanel);
                break;
            case "About":
                selectPanel(aboutPanel);
                break;
            case "Exit Game":
                closeUI();
                break;

            case "<- Back":
                selectPanel(mainPanel);

            default:
                FrameManager.showGameFrame(LevelManager.getNewLevel(button.getText()));
                closeUI();
        }
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
