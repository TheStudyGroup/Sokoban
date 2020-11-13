package com.c0destudy.sokoban.ui.panel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.*;

public class MainPanel extends BasePanel
{
    private JButton btnContinue;

    public MainPanel(final ActionListener listener) {
        super(listener);
        initUI();
    }

    private void initUI() {
        Arrays.asList(
            makeVSpace(50),
            makeTitleLabel("S O K O B A N", true),
            makeVSpace(40),
            makeLargeButton("New Game", 450, 45, true),
            makeVSpace(20),
            btnContinue = makeLargeButton("Continue", 450, 45, true),
            makeVSpace(20),
            makeHBox(450, 45, true, Arrays.asList(
                makeLargeButton("Recordings", 215, 45, false),
                makeHSpace(20),
                makeLargeButton("Editor", 215, 45, false))),
            makeVSpace(20),
            makeHBox(450, 45, true, Arrays.asList(
                makeLargeButton("Settings", 215, 45, false),
                makeHSpace(20),
                makeLargeButton("About", 215, 45, false))),
            makeVSpace(20),
            makeLargeButton("Exit Game", 450, 45, true)
        ).forEach(this::add);
    }

    public void setContinueButtonEnabled(final boolean enabled) {
        btnContinue.setEnabled(enabled);
    }
}
