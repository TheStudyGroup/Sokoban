package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.misc.Resource;
import com.c0destudy.sokoban.skin.Skin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.*;
import static com.c0destudy.sokoban.ui.helper.MakeComponent.makeButton;

public class RecordingPanel extends JPanel
{
    private final Skin           skin;
    private final ActionListener buttonListener;

    public RecordingPanel(final Skin skin, final ActionListener buttonListener) {
        super();
        this.skin = skin;
        this.buttonListener = buttonListener;
        initUI();
    }

    private void initUI() {
        setPreferredSize(new Dimension(800, 500));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final Font buttonFont = skin.getFont(Skin.FONTS.LargeButton);
        final Font buttonFont2 = skin.getFont(Skin.FONTS.SmallButton);

        File levelDir = new File(Resource.PATH_RECORDING_ROOT);
        String[] files = levelDir.list();
        ArrayList<Component> levels = new ArrayList<>();

        if (files != null && files.length > 0) {
            for (final String fileName : files) {
                final String levelName = fileName.substring(0, fileName.lastIndexOf("."));
                levels.add(makeButton(levelName, 400, 30, true, buttonFont2, buttonListener));
                levels.add(makeVSpace(10));
            }
        } else {
            levels.add(makeButton("NO RECORDINGS", 400, 30, true, buttonFont2, buttonListener));
            levels.add(makeVSpace(10));
        }

        Arrays.asList(
            makeVSpace(50),
            makeLabel("R E C O R D I N G S", true, skin.getFont(Skin.FONTS.Title)),
            makeVSpace(40),
            makeScroll(450, 240, true, levels),
            makeVSpace(20),
            makeHBox(450, 45, true, Arrays.asList(
                makeButton("<- Back", 215, 45, false, buttonFont, buttonListener),
                makeHSpace(20),
                makeButton("Replay ->", 215, 45, false, buttonFont, buttonListener)))
        ).forEach(this::add);
    }
}
