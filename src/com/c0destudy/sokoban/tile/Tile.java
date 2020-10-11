package com.c0destudy.sokoban.tile;

import com.c0destudy.sokoban.misc.Point;

import java.awt.Image;

public class Tile
{
    private Point point;
    private Image image;

    public Tile(final Point point) { this.point = point; }

    public Point getPoint() { return point; }
    public Image getImage() { return image; }
    public void  setPoint(final Point point) { this.point = point; }
    public void  setImage(final Image image) { this.image = image; }

    public int getX() { return point.getX(); }
    public int getY() { return point.getY(); }
    public void setX(final int x) { point.setX(x); }
    public void setY(final int y) { point.setY(y); }

    public boolean isLocatedAt(final Point point) { return this.point.equals(point); }
}
