package com.c0destudy.sokoban.resource;

import com.c0destudy.sokoban.helper.RichProperties;

import java.awt.*;

public class Skin
{
    // Static
    private static Skin skin;
    public static Skin getCurrentSkin() { return skin; }
    public static void setCurrentSkin(final Skin skin) { Skin.skin = skin; }

    // Enum
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

    // Instance
    private final String  name;
    private final Image[] images;
    private final Color[] colors;
    private final Font[]  fonts;
    private final int     imageSize;

    public Skin(final String name) {
        final String         path = Resource.getSkinResourcePath(name, "skin.properties");
        final RichProperties prop = new RichProperties(path);
        this.name   = name;
        this.images = new Image[IMAGES.values().length];
        this.colors = new Color[COLORS.values().length];
        this.fonts  = new Font[FONTS.values().length];

        // 이미지
        imageSize = prop.getInteger("image_size", 20);
        setImage(IMAGES.Wall,       getImage(prop.getString("image_wall"      )));
        setImage(IMAGES.Baggage,    getImage(prop.getString("image_baggage"   )));
        setImage(IMAGES.Goal,       getImage(prop.getString("image_goal"      )));
        setImage(IMAGES.Player1,    getImage(prop.getString("image_player1"   )));
        setImage(IMAGES.Player2,    getImage(prop.getString("image_player2"   )));
        setImage(IMAGES.Trigger,    getImage(prop.getString("image_trigger"   )));
        setImage(IMAGES.Pointer,    getImage(prop.getString("image_pointer"   )));
        setImage(IMAGES.Background, getImage(prop.getString("image_background")));

        // 폰트
        final String fontName = prop.getString("font", "FORCED SQUARE");
        Resource.loadFontFromResource(fontName);
        setFont(FONTS.Title,  Resource.getFont(fontName, false, prop.getInteger("font_size_title",  60)));
        setFont(FONTS.Large,  Resource.getFont(fontName, false, prop.getInteger("font_size_large",  30)));
        setFont(FONTS.Medium, Resource.getFont(fontName, false, prop.getInteger("font_size_medium", 23)));
        setFont(FONTS.Small,  Resource.getFont(fontName, false, prop.getInteger("font_size_small",  20)));

        // 색깔
        setColor(COLORS.Background,     prop.getColor("color_background",    "255,255,255"));
        setColor(COLORS.Title,          prop.getColor("color_title",         "0,0,0"));
        setColor(COLORS.Text,           prop.getColor("color_text",          "0,0,0"));
        setColor(COLORS.Button,         prop.getColor("color_button",        ""));
        setColor(COLORS.ButtonText,     prop.getColor("color_button_text",   "0,0,0"));
        setColor(COLORS.ButtonSelected, prop.getColor("color_button_select", ""));
    }

    // Private
    private Image getImage(final String imageName) { return Resource.getSkinImage(name, imageName); }
    private void  setImage(final IMAGES type, final Image image) { images[type.ordinal()] = image; }
    private void  setColor(final COLORS type, final Color color) { colors[type.ordinal()] = color; }
    private void  setFont (final FONTS type,  final Font font)   { fonts[type.ordinal()] = font;   }

    // Public
    public String getName()                   { return name;                   }
    public Image  getImage(final IMAGES type) { return images[type.ordinal()]; }
    public Color  getColor(final COLORS type) { return colors[type.ordinal()]; }
    public Font   getFont(final FONTS type)   { return fonts[type.ordinal()];  }
    public int    getImageSize()              { return imageSize;              }
}
