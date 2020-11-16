package com.c0destudy.sokoban.resource;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Resource
{
    private static final String PATH_MAIN_ICON         = "src/resources/icons/icon.png";
    private static final String PATH_LEVEL_ROOT        = "src/resources/levels/";
    private static final String PATH_SKIN_ROOT         = "src/resources/skins/";
    private static final String PATH_FONT_ROOT         = "src/resources/fonts/";
    private static final String PATH_DATA_ROOT         = "data/";
    public  static final String PATH_LEVEL_PAUSE       = PATH_DATA_ROOT + "pause.dat";
    public  static final String PATH_LEVEL_BEST_SCORES = PATH_DATA_ROOT + "bestscores.txt";
    private static final String PATH_USER_LEVEL_ROOT   = PATH_DATA_ROOT + "userlevels/";
    public  static final String PATH_RECORDING_ROOT    = PATH_DATA_ROOT + "recordings/";
    public  static final String PATH_RECORDING_FILE    = PATH_RECORDING_ROOT + "%s (%d pts) (%d moves).dat";

    public static final String PATH_SOUND_BACKGROUND  = "src/resources/sounds/game.wav";
    public static final String PATH_SOUND_PLAYER_MOVE = "src/resources/sounds/move.wav";

    private static Image icon;
    private static Skin  skin;

    public static void initResource() {
        createDirectory(PATH_DATA_ROOT);
        createDirectory(PATH_RECORDING_ROOT);
        createDirectory(PATH_USER_LEVEL_ROOT);
        icon = new ImageIcon(PATH_MAIN_ICON).getImage();
        skin = new Skin("Builder");
    }
    public static Image getIcon()                { return icon;          }
    public static Skin  getSkin()                { return skin;          }
    public static void  setSkin(final Skin skin) { Resource.skin = skin; }
    private static void createDirectory(final String path) {
        final File dir = new File(path);
        if (!dir.exists()) {
            try {
                dir.mkdir();
            } catch(Exception e) {
                e.getStackTrace();
            }
        }
    }

    // Font
    public static void loadFontFromResource(final String name) {
        try {
            final String path = Resource.PATH_FONT_ROOT + name + ".ttf";
            final File   file = new File(path);
            final Font   font = Font.createFont(Font.TRUETYPE_FONT, file);
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
    public static Font getFont(final String name, final boolean isBold, final int size) {
        return new Font(name, isBold ? Font.BOLD : Font.PLAIN, size);
    }


    // Skin
    public static String getSkinResourcePath(final String skinName, final String fileName)  {
        return Resource.PATH_SKIN_ROOT + skinName + "/" + fileName;
    }
    public static Image getSkinImage(final String skinName, final String imageName) {
        if (imageName == null || "".equals(imageName)) return null;
        final String filePath = getSkinResourcePath(skinName, imageName);
        final File   file     = new File(filePath);
        if (!file.exists()) return null;
        return (new ImageIcon(filePath)).getImage();
    }


    // File
    private static String[] getDirectoryList(final String path, final String extension) {
        final File     directory = new File(path);
        final String[] entities  = directory.list();
        if (entities != null && entities.length > 0) {
            if (extension != null && !"".equals(extension)) {
                return Arrays
                    .stream(entities)
                    .filter(e -> e.contains("." + extension))
                    .map(e -> e.substring(0, e.lastIndexOf(".")))
                    .toArray(String[]::new);
            } else {
                return entities;
            }
        }
        return new String[0];
    }
    public static String[] getLevelList()     { return getDirectoryList(Resource.PATH_LEVEL_ROOT,     "txt"); }
    public static String[] getRecordingList() { return getDirectoryList(Resource.PATH_RECORDING_ROOT, "dat"); }
    public static String[] getSkinList()      { return getDirectoryList(Resource.PATH_SKIN_ROOT,      null);  }

    // Path
    public static String getLevelPath(final String levelName) { return PATH_LEVEL_ROOT + levelName + ".txt"; }
}
