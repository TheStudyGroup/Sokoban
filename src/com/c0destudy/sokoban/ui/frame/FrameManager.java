package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.resource.Resource;

import java.awt.*;

public class FrameManager
{
    public static void showMainFrame() {
        EventQueue.invokeLater(() -> {
            final MainFrame frame = new MainFrame();
            frame.setVisible(true);
            frame.setIconImage(Resource.getIcon());
        });
    }

    public static void showGameFrame(final Level level) {
        showGameFrame(level, false);
    }

    public static void showGameFrame(final Level level, final boolean isReplay) {
        EventQueue.invokeLater(() -> {
            final GameFrame frame = new GameFrame(level, isReplay);
            frame.setVisible(true);
            frame.setIconImage(Resource.getIcon());
        });
    }

    public static void showEditorFrame(final Level level) {
        EventQueue.invokeLater(() -> {
            final EditorFrame frame = new EditorFrame(level);
            frame.setVisible(true);
            frame.setIconImage(Resource.getIcon());
        });
    }
}
