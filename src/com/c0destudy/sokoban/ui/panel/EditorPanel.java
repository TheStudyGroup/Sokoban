package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.level.LevelManager;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.*;

public class EditorPanel extends BasePanel
{
    public EditorPanel(final ActionListener listener) {
        super(listener);
        initUI();
    }

    private void initUI() {
        final ArrayList<String>    levels   = LevelManager.getLevelList();
        final ArrayList<Component> levelBox = new ArrayList<>();
        levelBox.add(makeButton("CREATE NEW", 220, 30, false));
        levelBox.add(makeVSpace(20));
        levels.forEach(levelName -> {
            levelBox.add(makeButton(levelName, 220, 30, false));
            levelBox.add(makeVSpace(10));
        });

        Arrays.asList(
            makeVSpace(50),
            makeTitleLabel("E D I T O R", true),
            makeVSpace(40),
            makeHBox(450, 240, true, Arrays.asList(
                makeScroll(250, 240, false, true, levelBox),
                makeHSpace(10),
                makeButton("INFO", 200, 240, false))),
            makeVSpace(20),
            makeLargeButton("Back", 450, 45, true)
        ).forEach(this::add);
    }
}