package com.c0destudy.sokoban.recording;

import java.io.Serializable;
import java.util.ArrayList;

public class RecordingUnit implements Serializable
{
    private final long time;
    private final ArrayList<Movement> movements = new ArrayList<>();

    public RecordingUnit(final long time) {
        this.time = time;
        System.out.println(time);//debug
    }

    public long getTime() {
        return time;
    }

    public void addMovement(final Movement movement) {
        movements.add(movement);
    }

    public Movement getMovement(final int index) {
        if (index >= 0 && index < movements.size() - 1) {
            return movements.get(index);
        } else {
            return null;
        }
    }

    public Movement popMovement() {
        if (movements.size() >= 1) {
            return movements.remove(movements.size() - 1);
        } else {
            return null;
        }
    }
}
