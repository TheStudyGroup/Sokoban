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
}
