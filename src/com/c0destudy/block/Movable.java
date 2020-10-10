package com.c0destudy.block;

import com.c0destudy.Point;

public class Movable extends Block
{
    public Movable(int x, int y) {
        super(x, y);
    }

    public Movable(final Point point) {
        this(point.getX(), point.getY());
    }

    public void move(int dx, int dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    public void moveTo(final Point point) {
        setPoint(point);
    }
    public void moveDelta(final Point delta) {
        getPoint().add(delta);
    }
}
