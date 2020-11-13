package com.c0destudy.sokoban.ui.panel;

import java.awt.event.ActionListener;
import java.util.Arrays;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.*;

public class EditorControlPanel extends BasePanel
{
    public EditorControlPanel(final ActionListener listener) {
        super(listener, 300, 500);
        initUI();
    }

    private void initUI() {
        Arrays.asList(
            makeVSpace(30),
            makeLabel("< LEVEL EDITOR >", true),
            makeVSpace(30),
            makeButton("Eraser",      220, 30, true),
            makeVSpace(10),
            makeButton("Wall",        220, 30, true),
            makeVSpace(10),
            makeButton("Baggage",     220, 30, true),
            makeVSpace(10),
            makeButton("Goal",        220, 30, true),
            makeVSpace(10),
            makeButton("Trigger",     220, 30, true),
            makeVSpace(10),
            makeButton("Player (1P)", 220, 30, true),
            makeVSpace(10),
            makeButton("Player (2P)", 220, 30, true),
            makeVSpace(100),
            makeHBox(250, 30, true, Arrays.asList(
                makeButton("Cancel",  120, 30, false),
                makeHSpace(10),
                makeButton("Save",    120, 30, false)))
        ).forEach(this::add);
    }
}
