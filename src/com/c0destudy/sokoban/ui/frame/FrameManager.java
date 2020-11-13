package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.resource.Skin;

import javax.swing.*;
import java.awt.*;

public class FrameManager
{
    private static Skin skin = new Skin("Builder");
    private static Image icon = new ImageIcon("src/resources/skins/Construction/wall.png").getImage();

    public static Skin getSkin()                { return skin;              }
    public static void setSkin(final Skin skin) { FrameManager.skin = skin; }

    public static void showMainFrame() {
        EventQueue.invokeLater(() -> {
            final MainFrame frame = new MainFrame();
            frame.setVisible(true);
            frame.setIconImage(icon);
        });
    }

    public static void showGameFrame(final Level level) {
        showGameFrame(level, false);
    }

    public static void showGameFrame(final Level level, final boolean isReplay) {
        EventQueue.invokeLater(() -> {
            final GameFrame frame = new GameFrame(level, isReplay);
            frame.setVisible(true);
            frame.setIconImage(icon);
        });
    }

    public static void showEditorFrame(final Level level) {
        EventQueue.invokeLater(() -> {
            final EditorFrame frame = new EditorFrame(level);
            frame.setVisible(true);
            frame.setIconImage(icon);
        });
    }
}
