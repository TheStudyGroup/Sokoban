package com.c0destudy.sokoban.skin;

import com.c0destudy.sokoban.misc.Resource;

import java.awt.*;

public class Skin
{
    public enum IMAGES {
        Wall, Baggage, Goal, Player1, Player2, Trigger,
    }

    public enum FONTS {
        Title, LargeButton, SmallButton,
    }

    private final String  name;
    private final Image[] images;
    private final Font[]  fonts;

    public Skin(final String name) {
        this.name = name;
        this.images = new Image[IMAGES.values().length];
        this.fonts  = new Font[FONTS.values().length];

        Resource.loadFontFromResource("FORCED SQUARE");

        setFont(Skin.FONTS.Title, new Font("FORCED SQUARE", Font.PLAIN, 60));
        setFont(Skin.FONTS.LargeButton, new Font("FORCED SQUARE", Font.PLAIN, 30));
        setFont(Skin.FONTS.SmallButton, new Font("FORCED SQUARE", Font.PLAIN, 20));

        setImage(IMAGES.Wall,    Resource.getImageFromResource(name, "wall"));
        setImage(IMAGES.Baggage, Resource.getImageFromResource(name, "baggage"));
        setImage(IMAGES.Goal,    Resource.getImageFromResource(name, "goal"));
        setImage(IMAGES.Player1, Resource.getImageFromResource(name, "player1"));
        setImage(IMAGES.Player2, Resource.getImageFromResource(name, "player2"));
        setImage(IMAGES.Trigger, Resource.getImageFromResource(name, "trigger"));

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
