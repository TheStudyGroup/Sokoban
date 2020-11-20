package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.level.LevelManager;
import com.c0destudy.sokoban.resource.Resource;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.*;

public class LevelPanel extends BasePanel
{
    public LevelPanel(final ActionListener listener) {
        super(listener);

        final Map<String, Integer> bestScores = LevelManager.getBestScores();
        // final int    bestScore = bestScores.getOrDefault(levelName, 0);

        final String[]             levels   = Resource.getLevelList();
        final ArrayList<Component> levelBox = new ArrayList<>();
        for (final String level : levels) {
            levelBox.add(makeButton(level, 220, 30, false));
            levelBox.add(makeVSpace(10));
        }
        if (levels.length == 0) {
            levelBox.add(makeButton("NO LEVELS", 220, 30, false));
        }

        Arrays.asList(
            makeVSpace(50),
            makeTitleLabel("L E V E L S"),
            makeVSpace(40),
            makeHBox(450, 240, true, Arrays.asList(
                makeScroll(250, 240, false, true, levelBox),
                makeHSpace(10),
                makeButton("TODO", 200, 240, false))),
            makeVSpace(20),
            makeLargeButton("Back", 450, 45, true)
        ).forEach(this::add);
    }
}
