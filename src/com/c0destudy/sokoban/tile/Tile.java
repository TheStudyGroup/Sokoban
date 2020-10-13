package com.c0destudy.sokoban.tile;

import com.c0destudy.sokoban.misc.Point;

import java.io.Serializable;

public class Tile implements Serializable
{
    private Point position;

    public         Tile(final Point position)        { this.position = position;           }
    public Point   getPosition()                     { return position;                    }
    public void    setPosition(final Point position) { this.position = position;           }
    public boolean isLocatedAt(final Point point)    { return this.position.equals(point); }
}
