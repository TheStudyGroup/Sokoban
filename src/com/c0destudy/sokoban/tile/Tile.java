package com.c0destudy.sokoban.tile;

import com.c0destudy.sokoban.misc.Point;

import java.awt.Image;

public class Tile
{
    private Point point;

    public         Tile(final Point point)        { this.point = point;              }
    public Point   getPoint()                     { return point;                    }
    public void    setPoint(final Point point)    { this.point = point;              }
    public boolean isLocatedAt(final Point point) { return this.point.equals(point); }
}
