package com.c0destudy.block;

import java.awt.Image;

public class Block
{
    private int x;
    private int y;
    private Image image;

    public Block(final int x, final int y) {
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

    public void setX(final int x) {
        this.x = x;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    public boolean isLocatedAt(final int x, final int y) {
        return (getX() == x) && (getY() == y);
    }

    public boolean isCollision(final Block block) {
        return (getX() == block.getX()) && (getY() == block.getY());
    }
}
