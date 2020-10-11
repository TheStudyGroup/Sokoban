package com.c0destudy.tile;

import com.c0destudy.misc.Point;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Wall extends Tile
{
    public Wall(final Point point) {
        super(point);
        initWall();
    }

    private void initWall() {
        ImageIcon iicon = new ImageIcon("src/resources/wall.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}
