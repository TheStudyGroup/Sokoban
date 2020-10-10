package com.c0destudy.block;

public class Movable extends Block
{
    public Movable(int x, int y) {
        super(x, y);
    }

    public void move(int dx, int dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    public boolean isLeftCollision(Block block) {
        return getX() - 1 == block.getX() && getY() == block.getY();
    }

    public boolean isRightCollision(Block block) {
        return getX() + 1 == block.getX() && getY() == block.getY();
    }

    public boolean isTopCollision(Block block) {
        return getY() - 1 == block.getY() && getX() == block.getX();
    }

    public boolean isBottomCollision(Block block) {
        return getY() + 1 == block.getY() && getX() == block.getX();
    }
}
