package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.resource.Resource;
import com.c0destudy.sokoban.resource.Sound;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.makeScroll;
import static com.c0destudy.sokoban.ui.helper.MakeComponent.makeVSpace;

public class SettingPanel extends BasePanel
{
    public SettingPanel(final ActionListener listener) {
        super(listener);
        initUI();
    }

    private void initUI() {
        final String[]             skins   = Resource.getSkinList();
        final ArrayList<Component> skinBox = new ArrayList<>();
        for (final String skin : skins) {
            skinBox.add(makeButton(skin, 400, 30, true));
            skinBox.add(makeVSpace(10));
        }
        if (skins.length == 0) {
            skinBox.add(makeButton("NO SKIN", 400, 30, true));
        }

        final String soundText = Sound.getBackgroundEnabled() ? "BGM: ON" : "BGM: OFF";
        Arrays.asList(
            makeVSpace(50),
            makeTitleLabel("S E T T I N G S", true),
            makeVSpace(40),
            makeLabel("S K I N", true),
            makeVSpace(10),
            makeScroll(450, 120, true, true, skinBox),
            makeVSpace(20),
            makeLabel("S O U N D", true),
            makeVSpace(10),
            makeButton(soundText, 400, 30, true),
            makeVSpace(20),
            makeLargeButton("Back", 450, 45, true)
        ).forEach(this::add);
    }
}
