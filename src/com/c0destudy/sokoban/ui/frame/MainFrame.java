package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.level.LevelManager;
import com.c0destudy.sokoban.misc.Resource;
import com.c0destudy.sokoban.skin.Skin;
import com.c0destudy.sokoban.sound.SoundManager;
import com.c0destudy.sokoban.ui.panel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

public class MainFrame extends JFrame
{
    private final ArrayList<JPanel> panels = new ArrayList<>();
    private final MainPanel         mainPanel;
    private final LevelPanel        levelPanel;
    private final RecordingPanel    recordingPanel;
    private final EditorPanel       editorPanel;
    private final SettingPanel      settingPanel;
    private final AboutPanel        aboutPanel;

    public MainFrame() {
        super();
        panels.add(mainPanel      = new MainPanel     (new MainActionListener()));
        panels.add(levelPanel     = new LevelPanel    (new LevelActionListener()));
        panels.add(recordingPanel = new RecordingPanel(new RecordingActionListener()));
        panels.add(editorPanel    = new EditorPanel   (new EditorActionListener()));
        panels.add(settingPanel   = new SettingPanel  (new SettingActionListener()));
        panels.add(aboutPanel     = new AboutPanel    (new AboutActionListener()));
        if (!LevelManager.isPausedLevelExisting()) {
            mainPanel.setContinueButtonEnabled(false);
        }

        setTitle("Sokoban Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        final JPanel panel = new JPanel();
        panel.setLayout(new OverlayLayout(panel));
        panel.addKeyListener(new TKeyAdapter());
        panels.forEach(e -> {
            e.setAlignmentY(Component.TOP_ALIGNMENT);
            panel.add(e);
        });
        selectPanel(mainPanel);
//        mainPanel.setBackground(new Color(255, 255, 255, 100));
        add(panel);
        setSize(mainPanel.getSize()); // 크기 설정
        pack();                       // 크기 맞추기
        setLocationRelativeTo(null);  // 화면 중앙으로 이동
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


    private class MainActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            final JButton button = (JButton) e.getSource();
            switch (button.getText()) {
                case "New Game":
                    selectPanel(levelPanel);
                    break;
                case "Continue":
                    FrameManager.showGameFrame(LevelManager.readLevelFromFile(Resource.PATH_LEVEL_PAUSE));
                    (new File(Resource.PATH_LEVEL_PAUSE)).delete();
                    closeUI();
                    break;
                case "Recordings":
                    selectPanel(recordingPanel);
                    break;
                case "Editor":
                    selectPanel(editorPanel);
                    break;
                case "Settings":
                    selectPanel(settingPanel);
                    break;
                case "About":
                    selectPanel(aboutPanel);
                    break;
                case "Exit Game":
                    System.exit(0);
                    break;
            }
        }
    }

    private class LevelActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            final JButton button = (JButton) e.getSource();
            switch (button.getText()) {
                case "Back":
                    selectPanel(mainPanel);
                    break;
                default:
                    FrameManager.showGameFrame(LevelManager.getNewLevel(button.getText()));
                    closeUI();
                    break;
            }
        }
    }

    private class RecordingActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            final JButton button = (JButton) e.getSource();
            switch (button.getText()) {
                case "Back":
                case "NO RECORDINGS":
                    selectPanel(mainPanel);
                    break;
                default:
                    final Level level = LevelManager.readLevelFromFile(Resource.PATH_RECORDING_ROOT + "/" + button.getText() + ".dat");
                    FrameManager.showGameFrame(level, true);
                    closeUI();
                    break;
            }
        }
    }

    private class EditorActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            final JButton button = (JButton) e.getSource();
            switch (button.getText()) {
                case "Add Level":
                    final Level emptyLevel = LevelManager.createEmptyLevel();
                    FrameManager.showEditorFrame(emptyLevel);
                    closeUI();
                    break;
                case "Back":
                    selectPanel(mainPanel);
                    break;
                default:
                    final Level level = LevelManager.getNewLevel(button.getText());
                    FrameManager.showGameFrame(level);
                    closeUI();
                    break;
            }
        }
    }

    private class SettingActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            final JButton button = (JButton) e.getSource();
            switch (button.getText()) {
                case "Back":
                    selectPanel(mainPanel);
                    break;
                case "BGM: ON":
                    button.setText("BGM: OFF");
                    SoundManager.setBackgroundEnabled(false);
                    break;
                case "BGM: OFF":
                    button.setText("BGM: ON");
                    SoundManager.setBackgroundEnabled(true);
                    break;
                default:
                    FrameManager.setSkin(new Skin(button.getText()));
                    FrameManager.showMainFrame();
                    closeUI();
                    break;
            }
        }
    }

    private class AboutActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            final JButton button = (JButton) e.getSource();
            switch (button.getText()) {
                case "Back":
                    selectPanel(mainPanel);
                    break;
                default:
                    break;
            }
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
