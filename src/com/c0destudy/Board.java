package com.c0destudy;

import com.c0destudy.block.*;
import com.c0destudy.level.Level;
import com.c0destudy.level.LevelLoader;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Board extends JPanel
{
    private final int OFFSET = 30;
    private final int BLOCK_SIZE = 20;

    private Level level;

    public Board() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        initLevel();
    }

    private void initLevel() {
        String path = "src/resources/levels/Level 2.txt";
        level = LevelLoader.loadLevelFromFile(path);
    }

    public int getBoardWidth() {
        return OFFSET + level.getWidth() * BLOCK_SIZE;
    }

    public int getBoardHeight() {
        return OFFSET + level.getHeight() * BLOCK_SIZE;
    }

    /**
     * 화면에 보드를 출력합니다.
     *
     * 경고: 직접 호출하지 마십시오.
     * 보드를 다시 그리는 경우 repaint() 메서드를 사용해야 합니다.
     *
     * @param g 스윙 그래픽 객체
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (final Block block : level.getAllBlocks()) {
            final int drawX = OFFSET + block.getX() * BLOCK_SIZE;
            final int drawY = OFFSET + block.getY() * BLOCK_SIZE;
            g.drawImage(block.getImage(), drawX, drawY, this);
        }

        g.setColor(new Color(0, 0, 0));
        if (level.isCompleted()) {
            g.drawString("Completed", 25, 20);
        } else {
            g.drawString("Remaining : " + level.getRemainingBaggages(), 25, 20);
        }
    }

    private class TAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            // 항상 입력받을 수 있는 키
            switch (keyCode) {
                case KeyEvent.VK_R:
                    restartLevel();
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
            int dx = 0;
            int dy = 0;
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    dx = -1;
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    dx = 1;
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    dy = -1;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    dy = 1;
                    break;
            }
            level.movePlayerAndBaggage(playerIndex, dx, dy);
            repaint();
        }
    }

    private void restartLevel() {
        initLevel();
        repaint();
    }
}
