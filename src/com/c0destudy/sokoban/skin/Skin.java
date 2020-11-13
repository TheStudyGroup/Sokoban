package com.c0destudy.sokoban.skin;

import com.c0destudy.sokoban.misc.Resource;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class Skin
{
    public enum IMAGES {
        Wall, Baggage, Goal, Player1, Player2, Trigger, Pointer,
        Background,
    }

    public enum COLORS {
        Background, Title, Text,
        Button, ButtonText, ButtonSelected,
    }

    public enum FONTS {
        Title, Large, Medium, Small,
    }

    private final String     name;
    private final Properties properties = new Properties();
    private final Image[]    images;
    private final Color[]    colors;
    private final Font[]     fonts;
    private final int        imageSize;

    public Skin(final String name) {
        this.name = name;
        this.images = new Image[IMAGES.values().length];
        this.colors = new Color[COLORS.values().length];
        this.fonts  = new Font[FONTS.values().length];

        try {
            final FileReader propFile = new FileReader(Resource.getSkinResourcePath(name, "skin.properties"));
            properties.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 이미지
        imageSize = getIntProp("image_size", 20);
        setImage(IMAGES.Wall,       getImage(getStringProp("image_wall"      )));
        setImage(IMAGES.Baggage,    getImage(getStringProp("image_baggage"   )));
        setImage(IMAGES.Goal,       getImage(getStringProp("image_goal"      )));
        setImage(IMAGES.Player1,    getImage(getStringProp("image_player1"   )));
        setImage(IMAGES.Player2,    getImage(getStringProp("image_player2"   )));
        setImage(IMAGES.Trigger,    getImage(getStringProp("image_trigger"   )));
        setImage(IMAGES.Pointer,    getImage(getStringProp("image_pointer"   )));
        setImage(IMAGES.Background, getImage(getStringProp("image_background")));

        // 폰트
        final String fontName = getStringProp("font", "FORCED SQUARE");
        Resource.loadFontFromResource(fontName);
        setFont(FONTS.Title,  Resource.getFont(fontName, false, getIntProp("font_size_title",  60)));
        setFont(FONTS.Large,  Resource.getFont(fontName, false, getIntProp("font_size_large",  30)));
        setFont(FONTS.Medium, Resource.getFont(fontName, false, getIntProp("font_size_medium", 23)));
        setFont(FONTS.Small,  Resource.getFont(fontName, false, getIntProp("font_size_small",  20)));

        // 색깔
        setColor(COLORS.Background,     getColorProp("color_background",    "255,255,255"));
        setColor(COLORS.Title,          getColorProp("color_title",         "0,0,0"));
        setColor(COLORS.Text,           getColorProp("color_text",          "0,0,0"));
        setColor(COLORS.Button,         getColorProp("color_button",        ""));
        setColor(COLORS.ButtonText,     getColorProp("color_button_text",   "0,0,0"));
        setColor(COLORS.ButtonSelected, getColorProp("color_button_select", ""));
    }

    // prop
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

    // private
    private Image getImage(final String imageName) { return Resource.getSkinImage(name, imageName); }
    private void setImage(final IMAGES type, final Image image) { images[type.ordinal()] = image; }
    private void setColor(final COLORS type, final Color color) { colors[type.ordinal()] = color; }
    private void setFont (final FONTS type,  final Font font)   { fonts[type.ordinal()] = font;   }

    // public
    public String getName()                   { return name;                   }
    public Image  getImage(final IMAGES type) { return images[type.ordinal()]; }
    public Color  getColor(final COLORS type) { return colors[type.ordinal()]; }
    public Font   getFont(final FONTS type)   { return fonts[type.ordinal()];  }
    public int    getImageSize()              { return imageSize;              }
}
