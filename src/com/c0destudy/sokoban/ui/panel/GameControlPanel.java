package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

import static com.c0destudy.sokoban.ui.helper.MakeComponent.*;

public class GameControlPanel extends BasePanel
{
    private final Level   level;
    private final boolean isReplay;
    private final JLabel  lbStatus;
    private final JLabel  lbScore;
    private final JLabel  lbRemaining;
    private final JLabel  lbMove;
    private final JLabel  lbUndo;
    private final JLabel  lbHP;

    public GameControlPanel(final ActionListener listener, final Level level, final boolean isReplay) {
        super(listener, 300, 530);
        this.level    = level;
        this.isReplay = isReplay;

        Arrays.asList(
            makeVSpace(30),
            makeLargeLabel("< " + level.getName() + " >"),
            makeVSpace(30),
            lbStatus = makeTransLargeLabel(""),
            makeVSpace(20),
            lbScore = makeTransLargeLabel(""),
            makeVSpace(20),
            lbRemaining = makeTransLargeLabel(""),
            makeVSpace(20),
            lbMove = makeTransLargeLabel(""),
            makeVSpace(20),
            lbUndo = makeTransLargeLabel(""),
            makeVSpace(20),
            lbHP = makeTransLargeLabel(""),
            makeVSpace(30),
            makeHBox(250, 30, true, Arrays.asList(
                makeButton("Undo", 120, 30, false),
                makeHSpace(10),
                makeButton("Reset", 120, 30, false))),
            makeVSpace(20),
            makeButton("Exit", 250, 30, true)
        ).forEach(this::add);

        final Dimension size = new Dimension(200, 35);
        lbStatus.setMaximumSize(size);
        lbScore.setMaximumSize(size);
        lbRemaining.setMaximumSize(size);
        lbMove.setMaximumSize(size);
        lbUndo.setMaximumSize(size);
        lbHP.setMaximumSize(size);
    }

    public void update() {
        if (level == null) return;
        invalidate();
        if (level.isCompleted()) {
            lbStatus.setText("GAME CLEAR!!");
        } else if (level.isFailed()) {
            lbStatus.setText("  failed...");
        } else if (isReplay) {
            lbStatus.setText("  >> REPLAY");
        } else {
            lbStatus.setText("  Playing...");
        }

        lbScore.setText("  Score:  " + level.getScore());
        lbRemaining.setText("  Remaining:  " + level.getRemainingBaggages());
        lbMove.setText("  Move:  " + level.getMoveCount());
        lbUndo.setText("  Undo:  " + level.getUndoCount());
        lbHP.setText("  HP:  " + level.getLeftHealth());
    }
}
