package com.c0destudy;

import com.c0destudy.block.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Board extends JPanel
{
    private final int OFFSET = 30;
    private final int SPACE = 20;

    private ArrayList<Wall>    walls = new ArrayList<>();
    private ArrayList<Baggage> baggs = new ArrayList<>();
    private ArrayList<Goal>    goals = new ArrayList<>();
    private Player             player;
    private int remainingBaggages = 0;

    private int boardWidth  = 0;
    private int boardHeight = 0;

    private String level
            = "    ######\n"
            + "    ##   #\n"
            + "    ##$  #\n"
            + "  ####  $##\n"
            + "  ##  $ $ #\n"
            + "#### # ## #   ######\n"
            + "##   # ## #####  ..#\n"
            + "## $  $          ..#\n"
            + "###### ### #@##  ..#\n"
            + "    ##     #########\n"
            + "    ########\n";

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld(level);
    }

    public int getBoardWidth() {
        return this.boardWidth;
    }

    public int getBoardHeight() {
        return this.boardHeight;
    }

    private void initWorld(final String map) {
        walls.clear();
        baggs.clear();
        goals.clear();
        remainingBaggages = 0;

        final String[] lines = map.split("\n");
        int longestWidth = 0;
        for (int y = 0; y < lines.length; y++) {
            for (int x = 0; x < lines[y].length(); x++) {
                longestWidth = Math.max(longestWidth, lines[y].length());
                final char blockCode = lines[y].charAt(x);
                switch (blockCode) {
                    case '#':
                        walls.add(new Wall(x, y));
                        break;
                    case '$':
                        baggs.add(new Baggage(x, y));
                        remainingBaggages++;
                        break;
                    case '.':
                        goals.add(new Goal(x, y));
                        break;
                    case '@':
                        player = new Player(x, y);
                        break;
                    default:
                        break;
                }
            }
        }

        boardWidth  = OFFSET + longestWidth * SPACE;
        boardHeight = OFFSET + lines.length * SPACE;
    }

    private void buildWorld(Graphics g) {
        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        final ArrayList<Block> blocks = new ArrayList<>();
        blocks.addAll(walls);
        blocks.addAll(goals);
        blocks.addAll(baggs);
        blocks.add(player);

        for (final Block block : blocks) {
            final int drawX = OFFSET + block.getX() * SPACE;
            final int drawY = OFFSET + block.getY() * SPACE;
            g.drawImage(block.getImage(), drawX, drawY, this);
        }

        if (isCompleted()) {
            g.setColor(new Color(0, 0, 0));
            g.drawString("Completed", 25, 20);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        buildWorld(g);
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

            if (isCompleted()) { // 게임 클리어시 이동 불가
                return;
            }

            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    dx = -1;
                    break;
                case KeyEvent.VK_RIGHT:
                    dx = 1;
                    break;
                case KeyEvent.VK_UP:
                    dy = -1;
                    break;
                case KeyEvent.VK_DOWN:
                    dy = 1;
                    break;
                default:
                    return;
            }

            // 플레이어가 이동할 새로운 좌표 계산
            int newX = player.getX() + dx;
            int newY = player.getY() + dy;

            if (isWallAt(newX, newY)) {
                return; // 벽이 있으면 이동 불가
            }

            Baggage nearBaggage = getBaggageAt(newX, newY);
            if (nearBaggage != null) {
                if (isWallAt(newX + dx, newY + dy)) {
                    return; // 짐 옆에 벽이 있어서 밀 수 없음
                }
                if (getBaggageAt(newX + dx, newY + dy) != null) {
                    return; // 짐이 연속으로 2개 있어서 밀 수 없음
                }
                if (isGoalAt(nearBaggage.getX(), nearBaggage.getY())) {
                    remainingBaggages++; // 이동 전에 짐이 골에 있는 경우
                }
                nearBaggage.move(dx, dy); // 짐이 하나만 있고 움직일 수 있는 공간이 있으면 짐을 민다
                if (isGoalAt(nearBaggage.getX(), nearBaggage.getY())) {
                    remainingBaggages--; // 이동 후에 짐이 골에 있는 경우
                }
            }
            player.move(dx, dy); // 플레이어를 이동시킨다

            repaint();
        }
    }

    private boolean isWallAt(int x, int y) {
        for (final Wall wall: walls) {
            if (wall.getX() == x && wall.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private boolean isGoalAt(int x, int y) {
        for (final Goal goal: goals) {
            if (goal.getX() == x && goal.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private Baggage getBaggageAt(int x, int y) {
        for (final Baggage bagg: baggs) {
            if (bagg.getX() == x && bagg.getY() == y) {
                return bagg;
            }
        }
        return null;
    }

    public boolean isCompleted() {
        return remainingBaggages == 0;
    }

    private void restartLevel() {
        initWorld(level);
        repaint();
    }
}
