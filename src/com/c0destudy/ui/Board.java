package com.c0destudy.ui;

import com.c0destudy.misc.Point;
import com.c0destudy.tile.*;
import com.c0destudy.level.Level;
import com.c0destudy.level.LevelManager;
import com.c0destudy.sound.SoundManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
        String path = "src/resources/levels/Level 1.txt";
        level = LevelManager.loadLevelFromFile(path);
    }

    public int getBoardWidth() {
        return OFFSET + level.getWidth() * BLOCK_SIZE;
    }

    public int getBoardHeight() {
        return OFFSET + level.getHeight() * BLOCK_SIZE;
    }

    /**
     * �솕硫댁뿉 蹂대뱶瑜� 異쒕젰�빀�땲�떎.
     *
     * 寃쎄퀬: 吏곸젒 �샇異쒗븯吏� 留덉떗�떆�삤.
     * 蹂대뱶瑜� �떎�떆 洹몃━�뒗 寃쎌슦 repaint() 硫붿꽌�뱶瑜� �궗�슜�빐�빞 �빀�땲�떎.
     *
     * @param g �뒪�쐷 洹몃옒�뵿 媛앹껜
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (final Tile tile : level.getAllTiles()) {
            final int drawX = OFFSET + tile.getX() * BLOCK_SIZE;
            final int drawY = OFFSET + tile.getY() * BLOCK_SIZE;
            g.drawImage(tile.getImage(), drawX, drawY, this);
        }

        g.setColor(new Color(0, 0, 0));
        
        if (level.isCompleted()) {
            g.drawString("Completed", 25, 20);
        }
        else if (level.isFailed()) {
        	g.drawString("Failed", 25, 25);
        } else {
            g.drawString("Remaining : " + level.getRemainingBaggages(), 25, 20);
            g.drawString("HP:" + level.getLeftHealth(), 25, 30);
        }
      
    }

    private class TAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            
            // �빆�긽 �엯�젰諛쏆쓣 �닔 �엳�뒗 �궎
            switch (keyCode) {
                case KeyEvent.VK_R:
                    restartLevel();
                    return;
            }
            

            if (level.isCompleted()||level.isFailed()) { // 寃뚯엫 �겢由ъ뼱�떆 �씠�룞 遺덇�
                return;
            }

            // �뵆�젅�씠�뼱 �꽑�깮
            int playerIndex;
            switch (keyCode) {
                case KeyEvent.VK_LEFT: // Player1
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    SoundManager.playPlayerMoveSound();
                    playerIndex = 0;
                    break;
                case KeyEvent.VK_A: // Player 2
                case KeyEvent.VK_D:
                case KeyEvent.VK_W:
                case KeyEvent.VK_S:
                    SoundManager.playPlayerMoveSound();
                    playerIndex = 1;
                    break;
                default:
                    return;
            }

            // 諛⑺뼢 �꽑�깮
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
            repaint();
        }
    }

    private void restartLevel() {
        initLevel();
        repaint();
    }
}
