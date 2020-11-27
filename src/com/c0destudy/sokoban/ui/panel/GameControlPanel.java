package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class GameControlPanel extends BasePanel
{
    private final Level   level;
    private       boolean isReplay;
    private long          playTime;
    private final JLabel  lblStatus;
    private final JLabel  lblScore;
    private final JLabel  lblRemaining;
    private final JLabel  lblMove;
    private final JLabel  lblUndo;
    private final JLabel  lblHP;
    private final JLabel  lblTime;
    private final JButton btnUndo;
    private final JButton btnReset;

    public GameControlPanel(final ActionListener listener, final Level level) {
        super(listener, 300, 530);
        this.level    = level;
        this.isReplay = false;
        this.playTime = 0;

        Arrays.asList(
            makeVerticalSpace(20),
            makeLargeLabel("< " + level.getName() + " >"),
            makeVerticalSpace(30),
            lblStatus = makeTransLargeLabel(""),
            makeVerticalSpace(15),
            lblScore = makeTransLargeLabel(""),
            makeVerticalSpace(15),
            lblRemaining = makeTransLargeLabel(""),
            makeVerticalSpace(15),
            lblMove = makeTransLargeLabel(""),
            makeVerticalSpace(15),
            lblUndo = makeTransLargeLabel(""),
            makeVerticalSpace(15),
            lblHP = makeTransLargeLabel(""),
            makeVerticalSpace(15),
            lblTime = makeTransLargeLabel(""),
            makeVerticalSpace(30),
            makeHorizontalBox(250, 30, true, Arrays.asList(
                btnUndo = makeButton("Undo", 120, 30, false),
                makeHorizontalSpace(10),
                btnReset = makeButton("Reset", 120, 30, false))),
            makeVerticalSpace(15),
            makeButton("Exit", 250, 30, true)
        ).forEach(this::add);

        final Dimension size = new Dimension(200, 35);
        lblStatus.setMaximumSize(size);
        lblScore.setMaximumSize(size);
        lblRemaining.setMaximumSize(size);
        lblMove.setMaximumSize(size);
        lblUndo.setMaximumSize(size);
        lblHP.setMaximumSize(size);
        lblTime.setMaximumSize(size);
    }

    public void setReplayMode(final boolean value) {
        isReplay = value;
    }

    public void setControlEnabled(final boolean value) {
        btnUndo.setEnabled(value);
        btnReset.setEnabled(value);
    }

    public void setPlayTime(final long value) {
        playTime = value;
    }

    public void update() {
        if (level == null) return;

        if (level.isCompleted()) {
            lblStatus.setText("GAME CLEAR!!");
        } else if (level.isFailed()) {
            lblStatus.setText("FAILED...");
        } else if (isReplay) {
            lblStatus.setText(">> REPLAY");
        } else {
            lblStatus.setText("PLAYING!");
        }

        lblScore    .setText("Score:  "     + level.getScore());
        lblRemaining.setText("Remaining:  " + level.getRemainingBaggages());
        lblMove     .setText("Move:  "      + level.getMoveCount());
        lblUndo     .setText("Undo:  "      + level.getUndoCount());
        lblHP       .setText("HP:  "        + level.getLeftHealth());
        lblTime     .setText("Time:  "      + timeToString(playTime));
    }

    private String timeToString(final long time) {
        final long sec  = time % 60;
        final long min  = time / 60 % 60;
        final long hour = time / 60 / 60;
        return String.format("%02d:%02d:%02d", hour, min, sec);
    }
}
