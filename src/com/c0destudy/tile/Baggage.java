package com.c0destudy.tile;

import com.c0destudy.misc.Point;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Baggage extends Movable
{
    public Baggage(final Point point) {
        super(point);
        initBaggage();
    }

    private void initBaggage() {
        ImageIcon iicon = new ImageIcon("src/resources/baggage.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}
