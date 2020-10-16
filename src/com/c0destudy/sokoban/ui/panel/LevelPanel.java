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
        final File                 levelDir   = new File(Resource.PATH_LEVEL_ROOT);
        final String[]             levelFiles = levelDir.list();
        final ArrayList<Component> levelComps = new ArrayList<>();

        if (levelFiles != null && levelFiles.length > 0) {
            for (final String fileName : levelFiles) {
                if (!fileName.contains(".txt")) continue;
                final String levelName = fileName.substring(0, fileName.lastIndexOf("."));
                final int    bestScore = bestScores.getOrDefault(levelName, 0);

                Arrays.asList(
                    makeHBox(400, 30, true, Arrays.asList(
                        makeButton(levelName, 230, 30, false, fontLargeButton, listener, colorButton, colorButtonBack),
                        makeHSpace(20),
                        makeLabel("Best: " + (bestScore == 0 ? "none" : bestScore), false, fontText))),
                    makeVSpace(10)
                ).forEach(levelComps::add);
            }
        } else {
            levelComps.add(makeButton("NO LEVELS", 400, 30, true, fontSmallButton, listener, colorButton, colorButtonBack));
            levelComps.add(makeVSpace(10));
        }

        Arrays.asList(
            makeVSpace(50),
            makeLabel("L E V E L S", true, fontTitle),
            makeVSpace(40),
            makeScroll(450, 240, true, true, levelComps),
            makeVSpace(20),
            makeButton("Back", 450, 45, true, fontLargeButton, listener, colorButton, colorButtonBack)
        ).forEach(this::add);
    }
}
