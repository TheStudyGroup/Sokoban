package com.c0destudy.sokoban.recording;

import com.c0destudy.sokoban.misc.Point;

public class Movement extends Unit
{
    private final Point point;
    private final Point delta;

    public Movement(final Point point, final Point delta) {
        this.point = new Point(point);
        this.delta = new Point(delta);
    }

    public Point getPoint() { return point; }
    public Point getDelta() { return delta; }
}
