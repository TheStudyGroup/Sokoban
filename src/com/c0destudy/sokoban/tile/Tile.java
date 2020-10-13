package com.c0destudy.sokoban.tile;

import com.c0destudy.sokoban.misc.Point;

import java.io.Serializable;

public class Tile implements Serializable
{
    private final Point position;

    public Tile(final Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }
}
