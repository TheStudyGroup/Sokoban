package com.c0destudy.sokoban.recording;

import java.io.Serializable;
import java.util.ArrayList;

public class Recording implements Serializable
{
    private final ArrayList<RecordingUnit> records = new ArrayList<>();

    public Recording() {

    }

    public void addRecord(final RecordingUnit unit) { records.add(unit); }

    public RecordingUnit getRecord(final int index) {
        if (index >= 0 && index < records.size() - 1) {
            return records.get(index);
        } else {
            return null;
        }
    }

    public RecordingUnit getLastRecord() {
        if (records.size() >= 1) {
            return records.get(records.size() - 1);
        } else {
            return null;
        }
    }

    public RecordingUnit popRecord() {
        if (records.size() >= 1) {
            return records.remove(records.size() - 1);
        } else {
            return null;
        }
    }
}
