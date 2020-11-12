package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.ui.panel.BoardPanel;

import javax.swing.*;
import java.awt.event.*;

public class EditorFrame extends JFrame
{
    private final Level      level;
    private final BoardPanel boardPanel;

    public EditorFrame(final Level level) {
        super();
        this.level      = level;
        this.boardPanel = new BoardPanel(level, false, false);

        setTitle("Sokoban Level Editor - " + level.getName());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new TWindowAdapter());

        getContentPane().add(boardPanel);
        setSize(boardPanel.getSize());
        pack(); // 프레임 사이즈 맞추기
        setLocationRelativeTo(null); // 화면 중앙으로 이동
    }

    private void closeUI() {
        FrameManager.showMainFrame();
        dispose();
    }

    private class TWindowAdapter extends WindowAdapter
    {
        @Override
        public void windowClosing(final WindowEvent windowEvent) {
            closeUI();
        }
    }
}