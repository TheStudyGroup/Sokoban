package com.c0destudy.sokoban.skin;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SkinManager
{
    public static Skin getSkin() {
        loadFontFromResource("FORCED SQUARE");

        final Skin skin = new Skin();

        skin.fontMainTitle = new Font("FORCED SQUARE", Font.PLAIN, 60);
        skin.fontMainButton = new Font("FORCED SQUARE", Font.PLAIN, 30);

        return skin;
    }

    private static void loadFontFromResource(final String fontName) {
        try {
            final File file = new File("src/resources/fonts/" + fontName + ".ttf");
            final Font font = Font.createFont(Font.TRUETYPE_FONT, file);
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
