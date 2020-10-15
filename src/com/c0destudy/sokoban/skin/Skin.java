package com.c0destudy.sokoban.skin;

import com.c0destudy.sokoban.misc.Resource;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
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

    private final String  name;
    private final Color   color;
    private final Image[] images;
    private final Font[]  fonts;
    private final int     imageSize;

    public Skin(final String name) {
        this.name = name;
        this.images = new Image[IMAGES.values().length];
        this.fonts  = new Font[FONTS.values().length];

        final Properties props = new Properties();
        try {
            final FileReader propFile = new FileReader(getResourcePath("skin.properties"));
            props.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 이미지
        imageSize = Integer.parseInt(props.getProperty("image_size"));
        setImage(IMAGES.Wall,    getImage(props.getProperty("image_wall")));
        setImage(IMAGES.Baggage, getImage(props.getProperty("image_baggage")));
        setImage(IMAGES.Goal,    getImage(props.getProperty("image_goal")));
        setImage(IMAGES.Player1, getImage(props.getProperty("image_player1")));
        setImage(IMAGES.Player2, getImage(props.getProperty("image_player2")));
        setImage(IMAGES.Trigger, getImage(props.getProperty("image_trigger")));

        // 폰트
        final String fontName = props.getProperty("font", "FORCED SQUARE");
        Resource.loadFontFromResource(fontName);
        setFont(Skin.FONTS.Title,       getFont(fontName, false, props.getProperty("font_size_title", "60")));
        setFont(Skin.FONTS.Text,        getFont(fontName, false, props.getProperty("font_size_text",  "30")));
        setFont(Skin.FONTS.LargeButton, getFont(fontName, false, props.getProperty("font_size_large_button", "30")));
        setFont(Skin.FONTS.SmallButton, getFont(fontName, false, props.getProperty("font_size_small_button", "20")));

        // 기타
        final String backColor = props.getProperty("background_color", "255,255,255");
        final String[] backColors = backColor.split(",");
        color = new Color(Integer.parseInt(backColors[0]), Integer.parseInt(backColors[1]), Integer.parseInt(backColors[2]));
//        new Color(180,180,180)
    }

    // private
    private Image  getImage(final String fileName)         { return getImageFromFile(fileName); }
    private Image  getImageFromFile(final String filePath) { return (new ImageIcon(getResourcePath(filePath))).getImage(); }
    private Font getFont(final String name, final boolean isBold, final String size) {
        return getFont(name, isBold, Integer.parseInt(size));
    }
    private Font getFont(final String name, final boolean isBold, final int size) {
        return new Font(name, isBold ? Font.BOLD : Font.PLAIN, size);
    }
    private String getResourcePath(final String fileName)  { return Resource.PATH_SKIN_ROOT + name + "/" + fileName; }
    private void   setImage(final IMAGES type, final Image image) { images[type.ordinal()] = image; }
    private void   setFont(final FONTS type, final Font font)     { fonts[type.ordinal()] = font;   }

    // public
    public String getName()                   { return name;                   }
    public Color  getColor()                  { return color;                  }
    public Image  getImage(final IMAGES type) { return images[type.ordinal()]; }
    public Font   getFont(final FONTS type)   { return fonts[type.ordinal()];  }
    public int    getImageSize()              { return imageSize;              }
}
