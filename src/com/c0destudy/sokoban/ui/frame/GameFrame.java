package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.level.LevelManager;
import com.c0destudy.sokoban.level.Record;
import com.c0destudy.sokoban.resource.Resource;
import com.c0destudy.sokoban.resource.Skin;
import com.c0destudy.sokoban.resource.Sound;
import com.c0destudy.sokoban.helper.Point;
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
    private long                   playTime;
    private final Timer            gameTimer = new Timer();
    private TimerTask              gameTimerTask;
    private final Timer            replayTimer = new Timer();
    private TimerTask              replayTimerTask;
    private long                   replayLastTime;
    private int                    replayRecordIndex;

    public GameFrame(final Level level, final boolean isReplay) {
        super();
        this.level        = level;
        this.isReplay     = isReplay;
        this.boardPanel   = new BoardPanel(level);
        this.controlPanel = new GameControlPanel(new ControlActionListener(), level);
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
            controlPanel.setReplayMode(true);
            controlPanel.setControlEnabled(false);
            startReplay();
        }

        startTimer();
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
        if (isReplay) { // 리플레이
            stopReplay();
        } else if (level.isCompleted()) { // 게임 클리어
            // 최고 기록 업데이트
            final int bestScore = LevelManager.getBestScore(level.getName());
            if (bestScore < level.getScore() || bestScore == 0) {
                LevelManager.setBestScore(level.getName(), level.getScore());
            }
            // 리플레이용 파일 저장
            final String path = Resource.getRecordingPath(level.getName(), level.getScore(), level.getMoveCount());
            LevelManager.saveLevelToFile(level, path);
        } else if (!level.isFailed()) { // 아직 플레이중인 경우 (클리어도 아니고 게임 오버도 아닌 경우)
            // 일시 정지 파일 저장 (Continue 기능)
            LevelManager.saveLevelToFile(level, Resource.getPausedPath());
        }
        Sound.stopBackgroundMusic();
        stopTimer();
        FrameManager.showMainFrame();
        dispose();
    }

    private void startTimer() {
        if (gameTimerTask != null) return;
        playTime = 0;
        gameTimerTask = new TimerTask() {
            @Override
            public void run() {
                playTime++;
                controlPanel.setPlayTime(playTime);
                controlPanel.update();
            }
        };
        gameTimer.scheduleAtFixedRate(gameTimerTask, 0, 1000);
    }

    private void stopTimer() {
        if (gameTimerTask != null) {
            gameTimerTask.cancel();
            gameTimerTask = null;
        }
    }

    private void startReplay() {
        if (replayTimerTask != null) return;
        replayLastTime = System.currentTimeMillis();
        replayRecordIndex = 0;
        replayTimerTask = new TimerTask() {
            @Override
            public void run() {
                final Record record = level.getRecord(replayRecordIndex);
                if (record == null) {
                    stopReplay();
                    stopTimer();
                    return;
                }
                if (System.currentTimeMillis() - replayLastTime >= record.getTime()) {
                    Sound.playPlayerMoveSound(); // 이동 사운드
                    level.movePlayer(record.getPlayerIndex(), record.getDirection()); // 플레이어 이동
                    updateScreen();
                    replayLastTime = System.currentTimeMillis();
                    replayRecordIndex++;
                }
            }
        };
        replayTimer.schedule(replayTimerTask, 0, 10);
    }

    private void stopReplay() {
        if (level.isCompleted() || level.isFailed()) {
            Sound.stopBackgroundMusic();
        }
        if (replayTimerTask != null) {
            replayTimerTask.cancel();
            replayTimerTask = null;
        }
    }

    private void updateScreen() {
        boardPanel.repaint();
        controlPanel.update();
    }

    private void resetLevel() {
        level.reset();
        updateScreen();
        stopTimer();
        startTimer();
    }

    private void undoLevel() {
        level.undoMove();
        updateScreen();
    }

    private class BoardKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {
            final int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ESCAPE)           { closeUI();    return; } // Exit
            if (isReplay)                                { return;               }
            if (level.isCompleted() || level.isFailed()) { return;               }
            if (keyCode == KeyEvent.VK_R)                { resetLevel(); return; } // Reset
            if (keyCode == KeyEvent.VK_U)                { undoLevel();  return; } // Undo

            int playerIndex;
            switch (keyCode) {
                case KeyEvent.VK_LEFT: // Player 1
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

            if (level.isCompleted() || level.isFailed()) {
                controlPanel.setControlEnabled(false);
                stopTimer();
                Sound.stopBackgroundMusic();
                // TODO: 클리어 효과음
                // TODO: 게임 오버 효과음
            } else {
                Sound.playPlayerMoveSound();
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