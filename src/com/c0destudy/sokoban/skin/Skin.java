package com.c0destudy.sokoban.skin;

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
        MainTitle, MainButton,
    }

    public Image[] images;
    public Font[]  fonts;

    public Skin() {
        images = new Image[IMAGES.values().length];
        fonts  = new Font[FONTS.values().length];
    }

    public Image getImage(final IMAGES type) { return images[type.ordinal()]; }
    public Font  getFont(final FONTS type)   { return fonts[type.ordinal()];  }

    public void setImage(final IMAGES type, final Image image) {
        images[type.ordinal()] = image;
    }
    public void setFont(final FONTS type, final Font font) {
        fonts[type.ordinal()] = font;
    }
}
