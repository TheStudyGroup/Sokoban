package com.c0destudy.sokoban.ui.panel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MainPanel extends BasePanel
{
    private final JButton btnContinue;

    public MainPanel(final ActionListener listener) {
        super(listener);

        Arrays.asList(
            makeVerticalSpace(50),
            makeTitleLabel("S O K O B A N"),
            makeVerticalSpace(40),
            makeLargeButton("New Game", 450, 45, true),
            makeVerticalSpace(20),
            btnContinue = makeLargeButton("Continue", 450, 45, true),
            makeVerticalSpace(20),
            makeHorizontalBox(450, 45, true, Arrays.asList(
                makeLargeButton("Recordings", 215, 45, false),
                makeHorizontalSpace(20),
                makeLargeButton("Editor", 215, 45, false))),
            makeVerticalSpace(20),
            makeHorizontalBox(450, 45, true, Arrays.asList(
                makeLargeButton("Settings", 215, 45, false),
                makeHorizontalSpace(20),
                makeLargeButton("About", 215, 45, false))),
            makeVerticalSpace(20),
            makeLargeButton("Exit Game", 450, 45, true)
        ).forEach(this::add);
    }

    public void setContinueButtonEnabled(final boolean enabled) {
        btnContinue.setEnabled(enabled);
    }
}
