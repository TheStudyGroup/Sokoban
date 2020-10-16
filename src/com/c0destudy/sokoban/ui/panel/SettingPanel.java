package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.misc.Resource;
import com.c0destudy.sokoban.sound.SoundManager;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.*;
import static com.c0destudy.sokoban.ui.helper.MakeComponent.makeButton;

public class SettingPanel extends BasePanel
{
    public SettingPanel(final ActionListener listener) {
        super(listener);
        initUI();
    }

    private void initUI() {
        final File                 skinRoot  = new File(Resource.PATH_SKIN_ROOT);
        final String[]             skinDirs  = skinRoot.list();
        final ArrayList<Component> skinComps = new ArrayList<>();
        if (skinDirs != null && skinDirs.length > 0) {
            for (final String skinName : skinDirs) {
                skinComps.add(makeButton(skinName, 400, 30, true, fontSmallButton, listener, colorButton, colorButtonBack));
                skinComps.add(makeVSpace(10));
            }
        } else {
            skinComps.add(makeButton("NO SKIN", 400, 30, true, fontSmallButton, listener, colorButton, colorButtonBack));
            skinComps.add(makeVSpace(10));
        }
//new Color()
        final String soundText = SoundManager.getBackgroundEnabled() ? "BGM: ON" : "BGM: OFF";
        Arrays.asList(
            makeVSpace(50),
            makeLabel("S E T T I N G S", true, fontTitle),
            makeVSpace(40),
            makeLabel("S K I N", true, fontLargeButton),
            makeVSpace(10),
            makeScroll(450, 120, true, true, skinComps),
            makeVSpace(20),
            makeLabel("S O U N D", true, fontLargeButton),
            makeVSpace(10),
            makeButton(soundText, 400, 30, true, fontSmallButton, listener, colorButton, colorButtonBack),
            makeVSpace(20),
            makeButton("Back", 450, 45, true, fontLargeButton, listener, colorButton, colorButtonBack)
        ).forEach(this::add);
    }
}
