package com.c0destudy.tile;

import com.c0destudy.misc.Point;

public class Movable extends Tile
{
    public Movable(final Point point) {
        super(point);
    }

    public void moveTo(final Point point) {
        setPoint(point);
    }

    public void moveDelta(final Point delta) {
        getPoint().add(delta);
    }
}
