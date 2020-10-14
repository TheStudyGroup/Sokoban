package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.skin.Skin;
import com.c0destudy.sokoban.skin.SkinManager;
import com.c0destudy.sokoban.sound.SoundManager;

import java.awt.*;

public class FrameManager
{
    public static void showMainFrame() {
        Skin skin = SkinManager.getSkin();

        EventQueue.invokeLater(() -> {
            final MainFrame frame = new MainFrame(skin);
            frame.setVisible(true);
        });
    }

    public static void showGameFrame(final Level level) {
        Skin skin = SkinManager.getSkin();

        EventQueue.invokeLater(() -> {
            final GameFrame frame = new GameFrame(skin, level);
            frame.setVisible(true);
            SoundManager.playBackgroundMusic();
        });
    }
}
