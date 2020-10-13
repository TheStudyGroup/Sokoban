package com.c0destudy.sokoban.skin;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SkinManager
{
    private static final String PATH_RESOURCES_IMAGE = "src/resources/skins/%s/%s.png";
    private static final String PATH_RESOURCES_FONT  = "src/resources/fonts/%s.ttf";

    public static Skin getSkin() {
        loadFontFromResource("FORCED SQUARE");

        final Skin skin = new Skin();

        skin.setFont(Skin.FONTS.MainTitle, new Font("FORCED SQUARE", Font.PLAIN, 60));
        skin.setFont(Skin.FONTS.MainButton, new Font("FORCED SQUARE", Font.PLAIN, 30));

        String skinName = "Default";

        skin.setImage(Skin.IMAGES.Wall, getImageFromResource(skinName, "wall"));
        skin.setImage(Skin.IMAGES.Baggage, getImageFromResource(skinName, "baggage"));
        skin.setImage(Skin.IMAGES.Goal, getImageFromResource(skinName, "goal"));
        skin.setImage(Skin.IMAGES.Player1, getImageFromResource(skinName, "player"));
        skin.setImage(Skin.IMAGES.Player2, getImageFromResource(skinName, "player"));
        skin.setImage(Skin.IMAGES.Trigger, getImageFromResource(skinName, "trigger"));

        return skin;
    }

    private static Image getImageFromResource(final String skinName, final String imageName) {
        final String    path = String.format(PATH_RESOURCES_IMAGE, skinName, imageName);
        final ImageIcon icon = new ImageIcon(path);
        return icon.getImage();
    }

    private static void loadFontFromResource(final String name) {
        try {
            final String path = String.format(PATH_RESOURCES_FONT, name);
            final File   file = new File(path);
            final Font   font = Font.createFont(Font.TRUETYPE_FONT, file);
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
