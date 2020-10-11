package com.c0destudy.misc;

public class Point {
    private int x;
    private int y;

    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Point(final Point point) {
        this(point.getX(), point.getY());
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(final int x) { this.x = x; }
    public void setY(final int y) { this.y = y; }

    public void add(final Point point) {
        setX(getX() + point.getX());
        setY(getY() + point.getY());
    }

    public static Point add(final Point a, final Point b) {
        return new Point(a.getX() + b.getX(), a.getY() + b.getY());
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Point) {
            final Point point = (Point)object;
            return getX() == point.getX() && getY() == point.getY();
        }
        return false;
    }
}
