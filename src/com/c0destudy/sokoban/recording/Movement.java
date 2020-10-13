package com.c0destudy.sokoban.recording;

import com.c0destudy.sokoban.misc.Point;

import java.io.Serializable;

public class Movement implements Serializable
{
    private final RecordingType type;
    private final Point         position;
    private final Point         direction;

    public Movement(final RecordingType type, final Point position, final Point direction) {
        this.type      = type;
        this.position  = new Point(position); // deep copy
        this.direction = new Point(direction);
    }

    public RecordingType getType()      { return type;      }
    public Point         getPosition()  { return position;  }
    public Point         getDirection() { return direction; }
}
