package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.skin.Skin;

import java.awt.*;

public class FrameManager
{
    private static Skin skin = new Skin("Soccer");

    public static Skin getSkin()                { return skin;              }
    public static void setSkin(final Skin skin) { FrameManager.skin = skin; }

    public static void showMainFrame() {
        EventQueue.invokeLater(() -> {
            final MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }

    public static void showGameFrame(final Level level) {
        showGameFrame(level, false);
    }

    public static void showGameFrame(final Level level, final boolean isReplay) {
        EventQueue.invokeLater(() -> {
            final GameFrame frame = new GameFrame(level, isReplay);
            frame.setVisible(true);
        });
    }
}
