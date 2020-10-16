package com.c0destudy.sokoban.ui.panel;

import javax.swing.*;
import java.awt.*;
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
            makeLabel("S O K O B A N", true, fontTitle),
            makeVSpace(40),
            makeButton("New Game", 450, 45, true, fontLargeButton, listener, colorButton, colorButtonBack),
            makeVSpace(20),
            btnContinue = makeButton("Continue", 450, 45, true, fontLargeButton, listener, colorButton, colorButtonBack),
            makeVSpace(20),
            makeHBox(450, 45, true, Arrays.asList(
                makeButton("Ranking", 215, 45, false, fontLargeButton, listener, colorButton, colorButtonBack),
                makeHSpace(20),
                makeButton("Recordings", 215, 45, false, fontLargeButton, listener, colorButton, colorButtonBack))),
            makeVSpace(20),
            makeHBox(450, 45, true, Arrays.asList(
                makeButton("Settings", 215, 45, false, fontLargeButton, listener, colorButton, colorButtonBack),
                makeHSpace(20),
                makeButton("About", 215, 45, false, fontLargeButton, listener, colorButton, colorButtonBack))),
            makeVSpace(20),
            makeButton("Exit Game", 450, 45, true, fontLargeButton, listener, colorButton, colorButtonBack)
        ).forEach(this::add);
    }

    public void setContinueButtonEnabled(final boolean enabled) {
        btnContinue.setEnabled(enabled);
    }
}
