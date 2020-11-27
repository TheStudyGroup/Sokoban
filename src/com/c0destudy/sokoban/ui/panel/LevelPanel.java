package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.level.LevelManager;
import com.c0destudy.sokoban.resource.Resource;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class LevelPanel extends BasePanel
{
    public LevelPanel(final ActionListener listener) {
        super(listener);

        final Map<String, Integer> bestScores = LevelManager.getBestScores();
        final String[]             levels   = Resource.getLevelList();
        final ArrayList<Component> levelBox = new ArrayList<>();
        for (final String level : levels) {
            final int bestScore = bestScores.getOrDefault(level, 0);
            Arrays.asList(
                makeHorizontalBox(400, 30, true, Arrays.asList(
                    makeButton(level, 230, 30, false),
                    makeHorizontalSpace(20),
                    makeLargeLabel("Best: " + (bestScore == 0 ? "none" : bestScore)))),
                makeVerticalSpace(10)
            ).forEach(levelBox::add);
        }
        if (levels.length == 0) {
            levelBox.add(makeButton("NO LEVELS", 220, 30, false));
        }

        Arrays.asList(
            makeVerticalSpace(50),
            makeTitleLabel("L E V E L S"),
            makeVerticalSpace(40),
            makeScroll(450, 240, true, true, levelBox),
            makeVerticalSpace(20),
            makeLargeButton("Back", 450, 45, true)
        ).forEach(this::add);
    }
}
