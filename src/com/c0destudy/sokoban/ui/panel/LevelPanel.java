package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.misc.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.*;

public class LevelPanel extends BasePanel
{
    public LevelPanel(final ActionListener listener) {
        super(listener);
        initUI();
    }

    private void initUI() {
        final File                 levelDir = new File(Resource.PATH_LEVEL_ROOT);
        final String[]             files    = levelDir.list();
        final ArrayList<Component> levels   = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (final String fileName : files) {
                final String levelName = fileName.substring(0, fileName.lastIndexOf("."));
                Arrays.asList(
                    makeHBox(400, 30, true, Arrays.asList(
                        makeButton(levelName, 280, 30, false, fontLargeButton, listener, colorButton, colorButtonBack),
                        makeHSpace(20),
                        makeLabel("Best: 0", false, fontText))),
                    makeVSpace(10)
                ).forEach(levels::add);
            }
        } else {
            levels.add(makeButton("NO LEVELS", 400, 30, true, fontSmallButton, listener, colorButton, colorButtonBack));
            levels.add(makeVSpace(10));
        }

        Arrays.asList(
            makeVSpace(50),
            makeLabel("L E V E L S", true, fontTitle),
            makeVSpace(40),
            makeScroll(450, 240, true, true, levels),
            makeVSpace(20),
            makeButton("Back", 450, 45, true, fontLargeButton, listener, colorButton, colorButtonBack)
        ).forEach(this::add);
    }
}
