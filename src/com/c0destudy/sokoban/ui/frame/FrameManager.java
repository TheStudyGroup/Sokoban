package com.c0destudy.sokoban.ui.frame;

import java.awt.*;

public class FrameManager
{
    public static void showMainFrame() {
        EventQueue.invokeLater(() -> {
            final MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }

    public static void showGameFrame(final String levelName) {
        EventQueue.invokeLater(() -> {
            final GameFrame frame = new GameFrame(levelName);
            frame.setVisible(true);
        });
    }
}
