package com.c0destudy.sokoban.level;

import com.c0destudy.sokoban.helper.Point;

import java.io.Serializable;

public class Record implements Serializable
{
    private final long    time;
    private final int     playerIndex;
    private final Point   position;
    private final Point   direction;
    private final boolean isBaggageMoved;

    public Record(final long    time,
                  final int     playerIndex,
                  final Point   position,
                  final Point   direction,
                  final boolean isBaggageMoved) {
        this.time           = time;
        this.playerIndex    = playerIndex;
        this.position       = new Point(position); // deep copy
        this.direction      = new Point(direction);
        this.isBaggageMoved = isBaggageMoved;
    }

    public long    getTime()        { return time;           }
    public int     getPlayerIndex() { return playerIndex;    }
    public Point   getPosition()    { return position;       }
    public Point   getDirection()   { return direction;      }
    public boolean isBaggageMoved() { return isBaggageMoved; }
}
