package com.c0destudy.sokoban.recording;

import com.c0destudy.sokoban.misc.Point;

public class Movement extends Unit
{
    private final Point position;
    private final Point direction;

    public Movement(final Point position, final Point direction) {
        this.position  = new Point(position);
        this.direction = new Point(direction);
    }

    public Point getPosition() { return position; }
    public Point getDirection() { return direction; }
}
