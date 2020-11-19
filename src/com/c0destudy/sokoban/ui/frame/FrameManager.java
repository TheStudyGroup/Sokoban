package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.resource.Resource;

import javax.swing.*;
import java.awt.*;

public class FrameManager
{
    // Public
    public static void showMainFrame() {
        showFrame(new MainFrame());
    }
    public static void showGameFrame(final Level level) {
        showGameFrame(level, false);
    }
    public static void showGameFrame(final Level level, final boolean isReplay) {
        showFrame(new GameFrame(level, isReplay));
    }
    public static void showEditorFrame(final Level level) {
        showFrame(new EditorFrame(level));
    }

    // Private
    private static void showFrame(final JFrame frame) {
        EventQueue.invokeLater(() -> {
            frame.setVisible(true);
            frame.setIconImage(Resource.getIcon());
        });
    }
}
