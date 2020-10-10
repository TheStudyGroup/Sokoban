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
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;

    private ArrayList<Wall>    walls = new ArrayList<>();
    private ArrayList<Baggage> baggs = new ArrayList<>();
    private ArrayList<Goal>    goals = new ArrayList<>();


    private Player player;
    private int boardWidth  = 0;
    private int boardHeight = 0;
    
    private boolean isCompleted = false;

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
        final String[] lines = map.split("\n");
        int longestWidth = 0;
        walls.clear();
        baggs.clear();
        goals.clear();

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

        ArrayList<Block> blocks = new ArrayList<>();
        blocks.addAll(walls);
        blocks.addAll(goals);
        blocks.addAll(baggs);
        blocks.add(player);

        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            int drawX = OFFSET + block.getX() * SPACE;
            int drawY = OFFSET + block.getY() * SPACE;

            if (block instanceof Player || block instanceof Baggage) {
                drawX += 2;
                drawY += 2;
            }

            g.drawImage(block.getImage(), drawX, drawY, this);

            if (isCompleted) {
                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 25, 20);
            }
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
            if (isCompleted) {
                return;
            }

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (checkWallCollision(player, LEFT_COLLISION)) {
                        return;
                    }
                    if (checkBagCollision(LEFT_COLLISION)) {
                        return;
                    }
                    player.move(-1, 0);
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    if (checkWallCollision(player, RIGHT_COLLISION)) {
                        return;
                    }
                    if (checkBagCollision(RIGHT_COLLISION)) {
                        return;
                    }
                    player.move(1, 0);
                    break;
                    
                case KeyEvent.VK_UP:
                    if (checkWallCollision(player, TOP_COLLISION)) {
                        return;
                    }
                    if (checkBagCollision(TOP_COLLISION)) {
                        return;
                    }
                    player.move(0, -1);
                    break;
                    
                case KeyEvent.VK_DOWN:
                    if (checkWallCollision(player, BOTTOM_COLLISION)) {
                        return;
                    }
                    if (checkBagCollision(BOTTOM_COLLISION)) {
                        return;
                    }
                    player.move(0, 1);
                    break;
                    
                case KeyEvent.VK_R:
                    restartLevel();
                    break;
                    
                default:
                    break;
            }

            repaint();
        }
    }

    private boolean checkWallCollision(Movable block, int type) {
        switch (type) {
            case LEFT_COLLISION:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (block.isLeftCollision(wall)) {
                        return true;
                    }
                }
                return false;
                
            case RIGHT_COLLISION:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (block.isRightCollision(wall)) {
                        return true;
                    }
                }
                return false;
                
            case TOP_COLLISION:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (block.isTopCollision(wall)) {
                        return true;
                    }
                }
                return false;
                
            case BOTTOM_COLLISION:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (block.isBottomCollision(wall)) {
                        return true;
                    }
                }
                return false;
                
            default:
                break;
        }
        
        return false;
    }

    private boolean checkBagCollision(int type) {
        switch (type) {
            case LEFT_COLLISION:
                for (int i = 0; i < baggs.size(); i++) {
                    Baggage bag = baggs.get(i);
                    if (player.isLeftCollision(bag)) {
                        for (int j = 0; j < baggs.size(); j++) {
                            Baggage item = baggs.get(j);
                            if (!bag.equals(item)) {
                                if (bag.isLeftCollision(item)) {
                                    return true;
                                }
                            }
                            if (checkWallCollision(bag, LEFT_COLLISION)) {
                                return true;
                            }
                        }
                        bag.move(-1, 0);
                        isCompleted();
                    }
                }
                return false;
                
            case RIGHT_COLLISION:
                for (int i = 0; i < baggs.size(); i++) {
                    Baggage bag = baggs.get(i);
                    if (player.isRightCollision(bag)) {
                        for (int j = 0; j < baggs.size(); j++) {
                            Baggage item = baggs.get(j);
                            if (!bag.equals(item)) {
                                if (bag.isRightCollision(item)) {
                                    return true;
                                }
                            }
                            if (checkWallCollision(bag, RIGHT_COLLISION)) {
                                return true;
                            }
                        }
                        bag.move(1, 0);
                        isCompleted();
                    }
                }
                return false;

            case TOP_COLLISION:
                for (int i = 0; i < baggs.size(); i++) {
                    Baggage bag = baggs.get(i);
                    if (player.isTopCollision(bag)) {
                        for (int j = 0; j < baggs.size(); j++) {
                            Baggage item = baggs.get(j);
                            if (!bag.equals(item)) {
                                if (bag.isTopCollision(item)) {
                                    return true;
                                }
                            }
                            if (checkWallCollision(bag, TOP_COLLISION)) {
                                return true;
                            }
                        }
                        bag.move(0, -1);
                        isCompleted();
                    }
                }
                return false;
                
            case BOTTOM_COLLISION:
                for (int i = 0; i < baggs.size(); i++) {
                    Baggage bag = baggs.get(i);
                    if (player.isBottomCollision(bag)) {
                        for (int j = 0; j < baggs.size(); j++) {
                            Baggage item = baggs.get(j);
                            if (!bag.equals(item)) {
                                if (bag.isBottomCollision(item)) {
                                    return true;
                                }
                            }
                            if (checkWallCollision(bag,BOTTOM_COLLISION)) {
                                return true;
                            }
                        }
                        bag.move(0, 1);
                        isCompleted();
                    }
                }
                break;
                
            default:
                break;
        }

        return false;
    }

    public void isCompleted() {
        int nOfBags = baggs.size();
        int finishedBags = 0;

        for (int i = 0; i < nOfBags; i++) {
            Baggage bag = baggs.get(i);
            
            for (int j = 0; j < nOfBags; j++) {
                Goal goal = goals.get(j);

                if (bag.getX() == goal.getX() && bag.getY() == goal.getY()) {
                    finishedBags += 1;
                }
            }
        }

        if (finishedBags == nOfBags) {
            isCompleted = true;
            repaint();
        }
    }

    private void restartLevel() {
        goals.clear();
        baggs.clear();
        walls.clear();

        initWorld(level);

        if (isCompleted) {
            isCompleted = false;
        }
    }
}
