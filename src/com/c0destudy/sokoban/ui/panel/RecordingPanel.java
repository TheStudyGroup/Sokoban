package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.resource.Resource;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.makeScroll;
import static com.c0destudy.sokoban.ui.helper.MakeComponent.makeVSpace;

public class RecordingPanel extends BasePanel
{
    public RecordingPanel(final ActionListener listener) {
        super(listener);

        final String[]             recordings   = Resource.getRecordingList();
        final ArrayList<Component> recordingBox = new ArrayList<>();
        for (final String recording : recordings) {
            recordingBox.add(makeButton(recording, 420, 30, true));
            recordingBox.add(makeVSpace(10));
        }
        if (recordings.length == 0) {
            recordingBox.add(makeButton("NO RECORDINGS", 420, 30, true));
        }

        Arrays.asList(
            makeVSpace(50),
            makeTitleLabel("R E C O R D I N G S"),
            makeVSpace(40),
            makeScroll(450, 240, true, true, recordingBox),
            makeVSpace(20),
            makeLargeButton("Back", 450, 45, true)
        ).forEach(this::add);
    }
}
