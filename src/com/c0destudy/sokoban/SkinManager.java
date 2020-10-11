package com.c0destudy.sokoban;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SkinManager
{
    public static Font fontMainTitle = null;
    public static Font fontMainButton = null;

    public static boolean loadSkin() {
        loadFontFromResource("FORCED SQUARE");

        fontMainTitle = new Font("FORCED SQUARE", Font.PLAIN, 60);
        fontMainButton = new Font("FORCED SQUARE", Font.PLAIN, 30);

        return true;
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
