package com.c0destudy.block;

import java.awt.Image;

public class Block {
    private final int SPACE = 20;

    private int x;
    private int y;
    private Image image;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setImage(Image img) {
        image = img;
    }

    public boolean isLeftCollision(Block block) {
        return getX() - SPACE == block.getX() && getY() == block.getY();
    }

    public boolean isRightCollision(Block block) {
        return getX() + SPACE == block.getX() && getY() == block.getY();
    }

    public boolean isTopCollision(Block block) {
        return getY() - SPACE == block.getY() && getX() == block.getX();
    }

    public boolean isBottomCollision(Block block) {
        return getY() + SPACE == block.getY() && getX() == block.getX();
    }
}
