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
    private final int BLOCK_SIZE = 20;

    private final ArrayList<Wall>    walls = new ArrayList<>();
    private final ArrayList<Baggage> baggs = new ArrayList<>();
    private final ArrayList<Goal>    goals = new ArrayList<>();
    private Player                   player1;
    private Player                   player2;
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
            + "    ## %   #########\n"
            + "    ########\n";

    public Board() {
        initBoard();
        System.out.println(level);
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        loadMapFromString(level);
    }

    public int getBoardWidth() {
        return this.boardWidth;
    }

    public int getBoardHeight() {
        return this.boardHeight;
    }

    /**
     * 문자열로부터 맵을 불러옵니다.
     *
     * 맵 크기는 자동으로 계산되고, 맵을 불러온 뒤에는 보드 크기를 계산합니다.
     * 블럭 코드: 벽(#), 물건($), 목적지(.), 플레이어1(@), 플레이어2(%)
     *
     * @param map 문자열로 된 맵 데이터
     */
    private void loadMapFromString(final String map) {
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
                        player1 = new Player(x, y);
                        break;
                    case '%':
                        player2 = new Player(x, y);
                        break;
                    default:
                        break;
                }
            }
        }

        boardWidth  = OFFSET + longestWidth * BLOCK_SIZE;
        boardHeight = OFFSET + lines.length * BLOCK_SIZE;
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

        final ArrayList<Block> blocks = new ArrayList<>();
        blocks.addAll(walls);
        blocks.addAll(goals);
        blocks.addAll(baggs);
        if (player1 != null) blocks.add(player1);
        if (player2 != null) blocks.add(player2);

        for (final Block block : blocks) {
            final int drawX = OFFSET + block.getX() * BLOCK_SIZE;
            final int drawY = OFFSET + block.getY() * BLOCK_SIZE;
            g.drawImage(block.getImage(), drawX, drawY, this);
        }

        g.setColor(new Color(0, 0, 0));
        if (isCompleted()) {
            g.drawString("Completed", 25, 20);
        } else {
            g.drawString("Remaining : " + remainingBaggages, 25, 20);
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

            if (isCompleted()) { // 게임 클리어시 이동 불가
                return;
            }

            // 플레이어 선택
            Player player;
            switch (keyCode) {
                case KeyEvent.VK_LEFT: // Player1
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    player = player1;
                    break;
                case KeyEvent.VK_A: // Player 2
                case KeyEvent.VK_D:
                case KeyEvent.VK_W:
                case KeyEvent.VK_S:
                    player = player2;
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
            movePlayerAndBaggage(player, dx, dy);
            repaint();
        }
    }

    /**
     * 플레이어의 좌표 변화량을 입력받아 플레이어를 이동하고,
     * 물건을 미는 경우 물건도 이동시킵니다.
     *
     * 플레이어를 왼쪽으로 이동시키는 경우 dx는 -1이고 dy는 0입니다.
     * 이동하는 방향에 물건이 있으면 물건도 같이 이동시킵니다.
     * 단, 물건이 연속으로 2개 있거나 벽이 있는 경우에는 이동할 수 없습니다.
     *
     * @param  player 이동할 플레이어 객체
     * @param  dx     x 좌표 변화량
     * @param  dy     y 좌표 변화량
     * @return        플레이어가 이동한 경우 true를, 이동하지 못한 경우 false를 반환합니다.
     */
    private boolean movePlayerAndBaggage(final Player player, final int dx, final int dy) {
        // 플레이어가 이동할 새로운 좌표 계산
        final int newPlayerX = player.getX() + dx;
        final int newPlayerY = player.getY() + dy;

        // 이동할 위치에 벽이 있는 경우 => 이동 불가
        if (isWallAt(newPlayerX, newPlayerY)) return false;

        // 이동할 위치에 다른 플레이어가 있는 경우 => 이동 불가
        if (player1 != null && player1.isLocatedAt(newPlayerX, newPlayerY)) return false;
        if (player2 != null && player2.isLocatedAt(newPlayerX, newPlayerY)) return false;

        // 플레이어가 물건(baggage)을 미는 경우
        Baggage nearBaggage = getBaggageAt(newPlayerX, newPlayerY);
        if (nearBaggage != null) {
            // 물건이 이동될 새로운 좌표 계산
            final int newBaggageX = nearBaggage.getX() + dx;
            final int newBaggageY = nearBaggage.getY() + dy;

            // 물건이 이동될 위치에 벽이 있는 경우 => 이동 불가
            if (isWallAt(newBaggageX, newBaggageY)) return false;

            // 물건이 이동될 위치에 물건이 있는 경우 (연속 2개) => 이동 불가
            if (getBaggageAt(newBaggageX, newBaggageY) != null) return false;

            // 물건이 이동될 위치에 다른 플레이어가 있는 경우 => 이동 불가
            if (player1 != null && player1.isLocatedAt(newBaggageX, newBaggageY)) return false;
            if (player2 != null && player2.isLocatedAt(newBaggageX, newBaggageY)) return false;

            // 이동 전에 물건이 목적지에 있는 경우
            if (isGoalAt(nearBaggage.getX(), nearBaggage.getY())) remainingBaggages++;

            // 물건을 이동시킬 수 있는 공간이 있으면 물건을 민다
            nearBaggage.move(dx, dy);

            // 이동 후에 물건이 목적지에 있는 경우
            if (isGoalAt(nearBaggage.getX(), nearBaggage.getY())) remainingBaggages--;
        }

        // 플레이어 이동
        player.move(dx, dy);
        return true;
    }

    /**
     * 해당 좌표에 벽이 있는지 확인합니다.
     *
     * @param  x x 좌표
     * @param  y y 좌표
     * @return   벽의 존재 여부
     */
    private boolean isWallAt(int x, int y) {
        for (final Wall wall: walls) {
            if (wall.isLocatedAt(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 해당 좌표에 목적지가 있는지 확인합니다.
     *
     * @param  x x 좌표
     * @param  y y 좌표
     * @return   목적지의 존재 여부
     */
    private boolean isGoalAt(int x, int y) {
        for (final Goal goal: goals) {
            if (goal.isLocatedAt(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 해당 좌표에 있는 물건 객체를 가져옵니다.
     *
     * @param  x x 좌표
     * @param  y y 좌표
     * @return   물건 객체 (없는 경우 null)
     */
    private Baggage getBaggageAt(int x, int y) {
        for (final Baggage bagg: baggs) {
            if (bagg.isLocatedAt(x, y)) {
                return bagg;
            }
        }
        return null;
    }

    public boolean isCompleted() {
        return remainingBaggages == 0;
    }

    private void restartLevel() {
        loadMapFromString(level);
        repaint();
    }
}
