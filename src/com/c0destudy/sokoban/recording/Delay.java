package com.c0destudy.sokoban.recording;

public class Delay extends Unit
{
    private final int milliseconds;

    public Delay(final int milliseconds) {
        this.milliseconds = milliseconds;
    }

    public int getMilliseconds() {
        return milliseconds;
    }
}
