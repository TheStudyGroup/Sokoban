package com.c0destudy.sokoban.skin;

import com.c0destudy.sokoban.misc.Resource;

import java.awt.*;

public class Skin
{
    public enum IMAGES {
        Wall, Baggage, Goal, Player1, Player2,
        Player1Left, Player1Right, Player1Up, Player1Down,
        Player2Left, Player2Right, Player2Up, Player2Down,
        Trigger,
    }

    public enum FONTS {
        Title, LargeButton, SmallButton,
    }

    private String  name;
    private Image[] images;
    private Font[]  fonts;

    public Skin() {
        this("Default");
    }

    public Skin(final String name) {
        images = new Image[IMAGES.values().length];
        fonts  = new Font[FONTS.values().length];

        Resource.loadFontFromResource("FORCED SQUARE");

        setFont(Skin.FONTS.Title, new Font("FORCED SQUARE", Font.PLAIN, 60));
        setFont(Skin.FONTS.LargeButton, new Font("FORCED SQUARE", Font.PLAIN, 30));
        setFont(Skin.FONTS.SmallButton, new Font("FORCED SQUARE", Font.PLAIN, 20));

        setImage(IMAGES.Wall, Resource.getImageFromResource(name, "wall"));
        setImage(IMAGES.Baggage, Resource.getImageFromResource(name, "baggage"));
        setImage(IMAGES.Goal, Resource.getImageFromResource(name, "goal"));
        setImage(IMAGES.Player1, Resource.getImageFromResource(name, "player"));
        setImage(IMAGES.Player2, Resource.getImageFromResource(name, "player"));

    }

    public String getName()                   { return name;                   }
    public Image  getImage(final IMAGES type) { return images[type.ordinal()]; }
    public Font   getFont(final FONTS type)   { return fonts[type.ordinal()];  }

    public void setImage(final IMAGES type, final Image image) {
        images[type.ordinal()] = image;
    }
    public void setFont(final FONTS type, final Font font) {
        fonts[type.ordinal()] = font;
    }
}
