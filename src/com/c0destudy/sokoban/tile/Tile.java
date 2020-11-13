package com.c0destudy.sokoban.tile;

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
