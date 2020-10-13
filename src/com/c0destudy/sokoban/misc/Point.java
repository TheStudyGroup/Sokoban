package com.c0destudy.sokoban.misc;

import java.io.Serializable;

public class Point implements Serializable
{
    private int x;
    private int y;

    public Point(final int x, final int y) { this.x = x; this.y = y;           }
    public Point(final Point point)        { this(point.getX(), point.getY()); }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(final int x) { this.x = x; }
    public void setY(final int y) { this.y = y; }

    public void add(final Point point) {
        x += point.x;
        y += point.y;
    }

    public static Point add(final Point a, final Point b) { return new Point(a.x + b.x, a.y + b.y); }
    public static Point sub(final Point a, final Point b) { return new Point(a.x - b.x, a.y - b.y); }
    public static Point reverse(final Point a)            { return new Point(-a.x, -a.y);                 }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Point) {
            final Point point = (Point)object;
            return x == point.x && y == point.y;
        }
        return false;
    }
}
