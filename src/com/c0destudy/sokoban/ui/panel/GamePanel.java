package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.misc.Point;
import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.tile.Tile;
import com.c0destudy.sokoban.level.LevelManager;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public class GamePanel extends JPanel
{
    private final int MARGIN = 30;
    private final int BLOCK_SIZE = 20;

    private final Level level;

    public GamePanel(final Level level) {
        this.level = level;
        final int width  = MARGIN * 2 + level.getWidth() * BLOCK_SIZE;
        final int height = MARGIN * 2 + level.getHeight() * BLOCK_SIZE;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
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

        for (final Tile tile : level.getAllTiles()) {
            final int drawX = MARGIN + tile.getX() * BLOCK_SIZE;
            final int drawY = MARGIN + tile.getY() * BLOCK_SIZE;
            g.drawImage(tile.getImage(), drawX, drawY, this);
        }

        g.setColor(new Color(0, 0, 0));
        if (level.isCompleted()) {
            g.drawString("Completed", 25, 20);
        } else {
            g.drawString("Remaining : " + level.getRemainingBaggages(), 25, 20);
        }
    }
}
