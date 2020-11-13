package com.c0destudy.sokoban.skin;

import com.c0destudy.sokoban.misc.Resource;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class Skin
{
    public enum IMAGES {
        Wall, Baggage, Goal, Player1, Player2, Trigger,
    }

    public enum FONTS {
        Title, Text,
        LargeButton, SmallButton,
    }

    private final String     name;
    private final Properties properties = new Properties();
    private final Color      backgroundColor;
    private final Image      backgroundImage;
    private final Color      buttonBackgroundColor;
    private final Color      buttonForegroundColor;
    private final Image[]    images;
    private final Font[]     fonts;
    private final int        imageSize;

    public Skin(final String name) {
        this.name = name;
        this.images = new Image[IMAGES.values().length];
        this.fonts  = new Font[FONTS.values().length];

        try {
            final FileReader propFile = new FileReader(getResourcePath("skin.properties"));
            properties.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 이미지
        imageSize = getIntProp("image_size", 20);
        setImage(IMAGES.Wall,    getImage(getStringProp("image_wall"   )));
        setImage(IMAGES.Baggage, getImage(getStringProp("image_baggage")));
        setImage(IMAGES.Goal,    getImage(getStringProp("image_goal"   )));
        setImage(IMAGES.Player1, getImage(getStringProp("image_player1")));
        setImage(IMAGES.Player2, getImage(getStringProp("image_player2")));
        setImage(IMAGES.Trigger, getImage(getStringProp("image_trigger")));
        setImage(IMAGES.Trigger, getImage(getStringProp("image_pointer")));

        // 폰트
        final String fontName = getStringProp("font", "FORCED SQUARE");
        Resource.loadFontFromResource(fontName);
        setFont(Skin.FONTS.Title,       getFont(fontName, false, getIntProp("font_size_title", 60)));
        setFont(Skin.FONTS.Text,        getFont(fontName, false, getIntProp("font_size_text",  30)));
        setFont(Skin.FONTS.LargeButton, getFont(fontName, false, getIntProp("font_size_large_button", 30)));
        setFont(Skin.FONTS.SmallButton, getFont(fontName, false, getIntProp("font_size_small_button", 23)));

        // 배경
        backgroundColor = getColorProp("background_color", "255,255,255");
        backgroundImage = getImage(getStringProp("background_image", "background.png"));
        buttonBackgroundColor = getColorProp("button_background", "");
        buttonForegroundColor = getColorProp("button_foreground", "");
    }

    // private
    private String getStringProp(final String key) {
        return properties.getProperty(key, "");
    }
    private String getStringProp(final String key, final String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    private int getIntProp(final String key, final int defaultValue) {
        return Integer.parseInt(getStringProp(key, Integer.toString(defaultValue)));
    }
    private Color getColorProp(final String key, final String defaultValue) {
        try {
            final String[] strings = getStringProp(key, defaultValue).split(",");
            final int[]    values  = Arrays.stream(strings).mapToInt(Integer::parseInt).toArray();
            switch (values.length) {
                case 3:
                    return new Color(values[0], values[1], values[2]);
                case 4:
                    return new Color(values[0], values[1], values[2], values[3]);
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    private Image getImage(final String fileName) {
        if (fileName == null || "".equals(fileName)) return null;
        final String filePath = getResourcePath(fileName);
        final File   file     = new File(filePath);
        if (!file.exists()) return null;
        return (new ImageIcon(filePath)).getImage();
    }
    private Font getFont(final String name, final boolean isBold, final int size) {
        return new Font(name, isBold ? Font.BOLD : Font.PLAIN, size);
    }
    private String getResourcePath(final String fileName)  {
        return Resource.PATH_SKIN_ROOT + name + "/" + fileName;
    }
    private void setImage(final IMAGES type, final Image image) { images[type.ordinal()] = image; }
    private void setFont(final FONTS type, final Font font)     { fonts[type.ordinal()] = font;   }

    // public
    public String getName()                   { return name;                   }
    public Color  getBackgroundColor()        { return backgroundColor;        }
    public Image  getBackgroundImage()        { return backgroundImage;        }
    public Color  getButtonBackgroundColor()  { return buttonBackgroundColor;  }
    public Color  getButtonForegroundColor()  { return buttonForegroundColor;  }
    public Image  getImage(final IMAGES type) { return images[type.ordinal()]; }
    public Font   getFont(final FONTS type)   { return fonts[type.ordinal()];  }
    public int    getImageSize()              { return imageSize;              }
}
