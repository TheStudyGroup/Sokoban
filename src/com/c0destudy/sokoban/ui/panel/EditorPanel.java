package com.c0destudy.sokoban.ui.panel;

import java.awt.event.ActionListener;
import java.util.Arrays;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.*;

public class EditorPanel extends BasePanel
{
    public EditorPanel(final ActionListener listener) {
        super(listener);
        initUI();
    }

    private void initUI() {
        Arrays.asList(
            makeVSpace(50),
            makeLabel("E D I T O R", true, fontTitle),
            makeVSpace(40),
            makeLabel("TODO :)", true, fontText),
            makeVSpace(220),
            makeVSpace(20),
            makeButton("Back", 450, 45, true, fontLargeButton, listener, colorButton, colorButtonBack)
        ).forEach(this::add);
    }
}
