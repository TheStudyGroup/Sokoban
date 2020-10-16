package com.c0destudy.sokoban.ui.panel;

import java.awt.event.ActionListener;
import java.util.Arrays;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.*;
import static com.c0destudy.sokoban.ui.helper.MakeComponent.makeButton;

public class AboutPanel extends BasePanel
{
    public AboutPanel(final ActionListener listener) {
        super(listener);
        initUI();
    }

    private void initUI() {
        Arrays.asList(
                makeVSpace(50),
                makeLabel("A B O U T", true, fontTitle),
                makeVSpace(40),
                makeLabel("Sokoban Game", true, fontText),
                makeVSpace(220),
                makeVSpace(20),
                makeButton("Back", 450, 45, true, fontLargeButton, listener, colorButton, colorButtonBack)
        ).forEach(this::add);
    }
}
