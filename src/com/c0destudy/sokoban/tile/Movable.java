package com.c0destudy.sokoban.tile;

import com.c0destudy.sokoban.misc.Point;

public class Movable extends Tile
{
    public      Movable  (final Point position)  { super(position); }
    public void moveTo   (final Point position)  { setPosition(position); }
    public void moveDelta(final Point direction) { getPosition().add(direction); }
}
