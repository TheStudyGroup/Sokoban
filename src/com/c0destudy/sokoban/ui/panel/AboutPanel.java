package com.c0destudy.sokoban.ui.panel;

import java.awt.event.ActionListener;
import java.util.Arrays;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.*;

public class AboutPanel extends BasePanel
{
    public AboutPanel(final ActionListener listener) {
        super(listener);
        initUI();
    }

    private void initUI() {
        Arrays.asList(
            makeVSpace(50),
            makeTitleLabel("A B O U T", true),
            makeVSpace(40),
            makeLabel("Sokoban Game", true),
            makeVSpace(220),
            makeVSpace(20),
            makeLargeButton("Back", 450, 45, true)
        ).forEach(this::add);
    }
}
