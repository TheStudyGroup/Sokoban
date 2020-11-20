package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.level.LevelManager;
import com.c0destudy.sokoban.level.Record;
import com.c0destudy.sokoban.resource.Resource;
import com.c0destudy.sokoban.resource.Skin;
import com.c0destudy.sokoban.resource.Sound;
import com.c0destudy.sokoban.tile.Point;
import com.c0destudy.sokoban.ui.panel.BoardPanel;
import com.c0destudy.sokoban.ui.panel.GameControlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends JFrame
{
    private final Level            level;
    private final BoardPanel       boardPanel;
    private final GameControlPanel controlPanel;
    private final boolean          isReplay;
    private final Timer            replayTimer = new Timer();
    private TimerTask              replayTask;
    private long                   replayTime;
    private int                    replayIndex;

    public GameFrame(final Level level, final boolean isReplay) {
        super();
        this.level        = level;
        this.isReplay     = isReplay;
        this.boardPanel   = new BoardPanel(level);
        this.controlPanel = new GameControlPanel(new ControlActionListener(), level, isReplay);
        boardPanel.addKeyListener(new BoardKeyAdapter());

        setTitle("Sokoban - " + level.getName());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new TWindowAdapter());
        initUI();
        Sound.playBackgroundMusic();
        if (isReplay) {
            setTitle(getTitle() + " (replay mode)");
            level.setRecordEnabled(false);
            level.resetWithoutRecords();
            startReplay();
        }
        updateScreen();
    }

    private void initUI() {
        // Layout
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Skin.getCurrentSkin().getColor(Skin.COLORS.Background));
        panel.add(boardPanel);
        panel.add(controlPanel);
        boardPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        controlPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        add(panel);
        pack();

        // Size
        int width  = boardPanel.getWidth() + controlPanel.getWidth();
        int height = Math.max(boardPanel.getHeight(), controlPanel.getHeight());
        panel.setPreferredSize(new Dimension(width, height));
        setSize(panel.getSize());    // 크기 설정
        pack();                      // 크기 맞추기
        setLocationRelativeTo(null); // 화면 중앙으로 이동
    }

    private void closeUI() {
        if (!isReplay || !level.isFailed()) {
            if (!level.isCompleted()) {
                // 아직 클리어하지 않은 경우 일시 정지 파일 저장 (Continue 기능)
                LevelManager.saveLevelToFile(level, Resource.getPausedPath());
            } else {
                // 최고 기록 업데이트
                final int bestScore = LevelManager.getBestScore(level.getName());
                System.out.println(bestScore);
                System.out.println(level.getScore());
                if (bestScore < level.getScore() || bestScore == 0) {
                    LevelManager.setBestScore(level.getName(), level.getScore());
                }
                // 리플레이용 파일 저장
                final String path = Resource.getRecordingPath(level.getName(), level.getScore(), level.getMoveCount());
                LevelManager.saveLevelToFile(level, path);
            }
        }
        stopReplay();
        Sound.stopBackgroundMusic();
        FrameManager.showMainFrame();
        dispose();
    }

    private void startReplay() {
        if (replayTask != null) return;
        replayTime  = System.currentTimeMillis();
        replayIndex = 0;
        replayTask  = new TimerTask() {
            @Override
            public void run() {
                final Record record = level.getRecord(replayIndex);
                if (record == null) {
                    stopReplay();
                    return;
                }
                if (System.currentTimeMillis() - replayTime >= record.getTime()) {
                    Sound.playPlayerMoveSound(); // 이동 사운드
                    level.movePlayer(record.getPlayerIndex(), record.getDirection()); // 플레이어 이동
                    updateScreen();
                    replayTime = System.currentTimeMillis();
                    replayIndex++;
                }
            }
        };
        replayTimer.schedule(replayTask, 0, 10);
    }

    private void stopReplay() {
        if (level.isCompleted() || level.isFailed()) {
            Sound.stopBackgroundMusic();
        }
        if (replayTask != null) {
            replayTask.cancel();
        }
    }

    private void updateScreen() {
        boardPanel.repaint();
        controlPanel.update();
    }

    private void resetLevel() {
        level.reset();
        updateScreen();
    }

    private void undoLevel() {
        level.undoMove();
        updateScreen();
    }

    private class BoardKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {
            if (isReplay) return;

            final int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_R: // 재시작
                    resetLevel();
                    return;
                case KeyEvent.VK_ESCAPE:
                    closeUI();
                    return;
            }

            if (level.isCompleted() || level.isFailed()) {
                return;
            }

            int playerIndex;
            switch (keyCode) {
                case KeyEvent.VK_U: // undo
                    undoLevel();
                    return;
                case KeyEvent.VK_LEFT: // Player1
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    playerIndex = 0;
                    break;
                case KeyEvent.VK_A: // Player 2
                case KeyEvent.VK_D:
                case KeyEvent.VK_W:
                case KeyEvent.VK_S:
                    playerIndex = 1;
                    break;
                default:
                    return;
            }

            Point direction = null;
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    direction = new Point(-1, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    direction = new Point(1, 0);
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    direction = new Point(0, -1);
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    direction = new Point(0, 1);
                    break;
            }

            level.movePlayer(playerIndex, direction);
            updateScreen();
            Sound.playPlayerMoveSound();
            if (level.isCompleted() || level.isFailed()) {
                Sound.stopBackgroundMusic();
            }
        }
    }

    private class ControlActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            final JButton button = (JButton) e.getSource();
            final String  text   = button.getText();
            switch (text) {
                case "Exit":
                    closeUI();
                    return;
                case "Undo":
                    undoLevel();
                    break;
                case "Reset":
                    resetLevel();
                    break;
            }
            boardPanel.requestFocus();
        }
    }

    private class TWindowAdapter extends WindowAdapter
    {
        @Override
        public void windowClosing(final WindowEvent windowEvent) {
            closeUI();
        }
    }
}