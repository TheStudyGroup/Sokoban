package com.c0destudy.sokoban.tile;

public class Movable extends Tile
{
    private Point currentPosition;

    public Movable(final Point position) {
        super(position);
        currentPosition = new Point(position);
    }

    @Override
    public Point getPosition() {
        return currentPosition;
    }

    public Point getOriginalPosition() {
        return new Point(super.getPosition());
    }

    public void setPosition(final Point position) {
        currentPosition = position;
    }

    public void moveTo   (final Point position)  { setPosition(position); }
    public void moveDelta(final Point direction) { getPosition().add(direction); }
}
