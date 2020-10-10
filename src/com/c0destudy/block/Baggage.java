package com.c0destudy.block;

import com.c0destudy.Point;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Baggage extends Movable
{
    public Baggage(int x, int y) {
        super(x, y);
        initBaggage();
    }

    public Baggage(final Point p) {
        this(p.getX(), p.getY());
    }

    private void initBaggage() {
        ImageIcon iicon = new ImageIcon("src/resources/baggage.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}
