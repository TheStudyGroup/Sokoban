package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.level.LevelManager;
import com.c0destudy.sokoban.misc.Point;
import com.c0destudy.sokoban.ui.panel.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame
{
    private final GameFrame gameFrame;
    private final String    levelName;
    private Level           level     = null;
    private GamePanel       gamePanel = null;

    public GameFrame(final String levelName) {
        super();
        this.gameFrame = this;
        this.levelName = levelName;
        setTitle("SOKOBAN - " + levelName);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new TWindowAdapter());
        initUI();
    }

    private void initUI() {
        final Level     newLevel     = LevelManager.getNewLevel(levelName);
        final GamePanel newGamePanel = new GamePanel(newLevel);
        newGamePanel.addKeyListener(new TKeyAdapter());

        getContentPane().removeAll();       // 이전 GamePanel 제거
        getContentPane().add(newGamePanel); // 새로운 GamePanel 추가
        setSize(newGamePanel.getSize());
        pack(); // 프레임 사이즈 맞추기
        setLocationRelativeTo(null); // 화면 중앙으로 이동
        newGamePanel.requestFocus(); // 포커스 적용 (레밸 재시작시 필요)

        level     = newLevel;
        gamePanel = newGamePanel;
    }

    private class TKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            // 항상 입력받을 수 있는 키
            switch (keyCode) {
                case KeyEvent.VK_R:
                    initUI();
                    return;
            }

            if (level.isCompleted()) { // 게임 클리어시 이동 불가
                return;
            }

            // 플레이어 선택
            int playerIndex;
            switch (keyCode) {
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

            // 방향 선택
            Point delta = null;
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    delta = new Point(-1, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    delta = new Point(1, 0);
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    delta = new Point(0, -1);
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    delta = new Point(0, 1);
                    break;
            }
            level.movePlayerAndBaggage(playerIndex, delta);
            gamePanel.repaint();
        }
    }

    private class TWindowAdapter extends WindowAdapter
    {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            FrameManager.showMainFrame();
            dispose();
        }
    }
}