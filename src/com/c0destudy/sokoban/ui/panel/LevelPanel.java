package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.level.LevelManager;
import com.c0destudy.sokoban.misc.Resource;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.*;

public class LevelPanel extends BasePanel
{
    public LevelPanel(final ActionListener listener) {
        super(listener);
        initUI();
    }

    private void initUI() {
        final Map<String, Integer> bestScores = LevelManager.getBestScores();
        // final int    bestScore = bestScores.getOrDefault(levelName, 0);
        
        final ArrayList<String>    levels   = LevelManager.getLevelList();
        final ArrayList<Component> levelBox = new ArrayList<>();
        if (levels.isEmpty()) {
            levelBox.add(makeButton("NO LEVELS", 220, 30, false, fontSmallButton, listener, colorButton, colorButtonBack));
        } else {
            levels.forEach(levelName -> {
                levelBox.add(makeButton(levelName, 220, 30, false, fontSmallButton, listener, colorButton, colorButtonBack));
                levelBox.add(makeVSpace(10));
            });
        }

        Arrays.asList(
            makeVSpace(50),
            makeLabel("L E V E L S", true, fontTitle),
            makeVSpace(40),
            makeHBox(450, 240, true, Arrays.asList(
                makeScroll(250, 240, false, true, levelBox),
                makeHSpace(10),
                makeButton("INFO", 200, 240, false, fontSmallButton, listener, colorButton, colorButtonBack))),
            makeVSpace(20),
            makeButton("Back", 450, 45, true, fontLargeButton, listener, colorButton, colorButtonBack)
        ).forEach(this::add);
    }
}
