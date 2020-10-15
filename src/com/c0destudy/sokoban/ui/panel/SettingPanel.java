package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.misc.Resource;
import com.c0destudy.sokoban.skin.Skin;
import com.c0destudy.sokoban.sound.SoundManager;
import com.c0destudy.sokoban.ui.frame.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.*;
import static com.c0destudy.sokoban.ui.helper.MakeComponent.makeButton;

public class SettingPanel extends JPanel
{
    private final Skin           skin;
    private final ActionListener buttonListener;

    public SettingPanel(final ActionListener buttonListener) {
        super();
        this.skin           = FrameManager.getSkin();
        this.buttonListener = buttonListener;
        initUI();
    }

    private void initUI() {
        setPreferredSize(new Dimension(800, 500));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final Font fontBtnLarge = skin.getFont(Skin.FONTS.LargeButton);
        final Font fontBtnSmall = skin.getFont(Skin.FONTS.SmallButton);

        final File     skinRoot = new File(Resource.PATH_SKIN_ROOT);
        final String[] skinDirs = skinRoot.list();
        final ArrayList<Component> skinComponents = new ArrayList<>();

        if (skinDirs != null && skinDirs.length > 0) {
            for (final String skinName : skinDirs) {
                skinComponents.add(makeButton(skinName, 400, 30, true, fontBtnSmall, buttonListener));
                skinComponents.add(makeVSpace(10));
            }
        } else {
            skinComponents.add(makeButton("NO SKIN", 400, 30, true, fontBtnSmall, buttonListener));
            skinComponents.add(makeVSpace(10));
        }

        Arrays.asList(
            makeVSpace(50),
            makeLabel("S E T T I N G S", true, skin.getFont(Skin.FONTS.Title)),
            makeVSpace(40),
            makeLabel("S K I N", true, fontBtnLarge),
            makeVSpace(10),
            makeScroll(450, 120, true, skinComponents),
            makeVSpace(20),
            makeLabel("S O U N D", true, fontBtnLarge),
            makeVSpace(10),
            makeButton(SoundManager.getBackgroundEnabled() ? "BGM: ON" : "BGM: OFF", 400, 30, true, fontBtnSmall, buttonListener),
            makeVSpace(20),
            makeButton("Back", 450, 45, true, fontBtnLarge, buttonListener)
        ).forEach(this::add);
    }
}
