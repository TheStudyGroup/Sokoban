package com.c0destudy.sokoban.tile;

import java.io.Serializable;

public class Point implements Serializable
{
    private int x;
    private int y;

    public     Point(final int x, final int y) { this.x = x; this.y = y;           }
    public     Point(final Point point)        { this(point.getX(), point.getY()); }
    public int getX() { return x; }
    public int getY() { return y; }

    public void add(final Point point) {
        x += point.x;
        y += point.y;
    }

    public static Point add(final Point a, final Point b) {
        return new Point(a.x + b.x, a.y + b.y);
    }

    public static Point add(final Point a, final Point b, final Point c) {
        return new Point(a.x + b.x + c.x, a.y + b.y + c.y);
    }

    public static Point reverse(final Point a) {
        return new Point(-a.x, -a.y);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Point) {
            final Point point = (Point)object;
            return x == point.x && y == point.y;
        }
        return false;
    }
}
