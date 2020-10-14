package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.skin.Skin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.*;

public class MainPanel extends JPanel
{
    private final Skin           skin;
    private final ActionListener buttonListener;
    private       JButton        btnContinue;

    public MainPanel(final Skin skin, final ActionListener buttonListener) {
        super();
        this.skin = skin;
        this.buttonListener = buttonListener;
        initUI();
    }

    private void initUI() {
        setPreferredSize(new Dimension(800, 500));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final Font buttonFont = skin.getFont(Skin.FONTS.LargeButton);
        Arrays.asList(
            makeVSpace(50),
            makeLabel("S O K O B A N", true, skin.getFont(Skin.FONTS.Title)),
            makeVSpace(40),
            makeButton("New Game", 450, 45, true, buttonFont, buttonListener),
            makeVSpace(20),
            btnContinue = makeButton("Continue", 450, 45, true, buttonFont, buttonListener),
            makeVSpace(20),
            makeHBox(450, 45, true, Arrays.asList(
                makeButton("Ranking", 215, 45, false, buttonFont, buttonListener),
                makeHSpace(20),
                makeButton("Recordings", 215, 45, false, buttonFont, buttonListener))),
            makeVSpace(20),
            makeHBox(450, 45, true, Arrays.asList(
                makeButton("Settings", 215, 45, false, buttonFont, buttonListener),
                makeHSpace(20),
                makeButton("About", 215, 45, false, buttonFont, buttonListener))),
            makeVSpace(20),
            makeButton("Exit Game", 450, 45, true, buttonFont, buttonListener)
        ).forEach(this::add);
    }

    public void setContinueButtonEnabled(final boolean enabled) {
        btnContinue.setEnabled(enabled);
    }
}
