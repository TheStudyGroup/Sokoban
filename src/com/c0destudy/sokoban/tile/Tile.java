package com.c0destudy.sokoban.tile;

import com.c0destudy.sokoban.misc.Point;

import java.awt.Image;
import java.io.Serializable;

public class Tile implements Serializable
{
    private Point point;

    public         Tile(final Point point)        { this.point = point;              }
    public Point   getPoint()                     { return point;                    }
    public void    setPoint(final Point point)    { this.point = point;              }
    public boolean isLocatedAt(final Point point) { return this.point.equals(point); }
}
