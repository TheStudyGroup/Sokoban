package com.c0destudy.block;

import com.c0destudy.Point;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Wall extends Block
{
    public Wall(int x, int y) {
        super(x, y);
        initWall();
    }

    public Wall(final Point point) {
        this(point.getX(), point.getY());
    }

    private void initWall() {
        ImageIcon iicon = new ImageIcon("src/resources/wall.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}
