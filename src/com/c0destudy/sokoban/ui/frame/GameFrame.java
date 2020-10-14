package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.level.LevelManager;
import com.c0destudy.sokoban.level.Record;
import com.c0destudy.sokoban.misc.Point;
import com.c0destudy.sokoban.misc.Resource;
import com.c0destudy.sokoban.skin.Skin;
import com.c0destudy.sokoban.sound.SoundManager;
import com.c0destudy.sokoban.ui.panel.GamePanel;

import javax.swing.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends JFrame
{
    private Level           level;
    private GamePanel       gamePanel = null;
    private boolean         isReplay;
    private final Timer     replayTimer = new Timer();
    private TimerTask       replayTask;
    private long            replayTime;
    private int             replayIndex;

    public GameFrame(final Level level, final boolean isReplay) {
        super();
        this.level    = level;
        this.isReplay = isReplay;
        setTitle("Sokoban - " + level.getName());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new TWindowAdapter());
        initUI();
        if (isReplay) {
            setTitle(getTitle() + " (replay mode)");
            level.setRecordEnabled(false);
            level.resetWithoutRecords();
            gamePanel.repaint();
            startReplay();
        }
        SoundManager.playBackgroundMusic();
    }

    private void initUI() {
        gamePanel = new GamePanel(level);
        gamePanel.addKeyListener(new TKeyAdapter());
        getContentPane().add(gamePanel);
        setSize(gamePanel.getSize());
        pack();                      // 프레임 사이즈 맞추기
        setLocationRelativeTo(null); // 화면 중앙으로 이동
    }

    private void closeUI() {
        if (!isReplay) {
            if (!level.isCompleted()) {
                LevelManager.saveLevelToFile(level, Resource.PATH_LEVEL_PAUSE);
            } else {
                LevelManager.saveLevelToFile(level, String.format(Resource.PATH_RECORDING_FILE, level.getName(), level.getMoveCount()));
            }
        }
        stopReplay();
        SoundManager.stopBackgroundMusic();
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
                    SoundManager.playPlayerMoveSound(); // 이동 사운드
                    level.movePlayerAndBaggage(record.getPlayerIndex(), record.getDirection()); // 플레이어 이동
                    gamePanel.repaint();
                    replayTime = System.currentTimeMillis();
                    replayIndex++;
                }
            }
        };
        replayTimer.schedule(replayTask, 0, 10);
    }

    private void stopReplay() {
        if (replayTask != null) {
            replayTask.cancel();
        }
    }

    private class TKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {
            if (isReplay) return;

            final int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_R: // 재시작
                    level.reset();
                    gamePanel.repaint();
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
                    level.undoMove();
                    gamePanel.repaint();
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

            level.movePlayerAndBaggage(playerIndex, direction); // 플레이어 이동
            SoundManager.playPlayerMoveSound(); // 이동 사운드
            gamePanel.repaint(); // 다시 그리기
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