package com.c0destudy.sokoban.tile;

import com.c0destudy.sokoban.misc.Point;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Goal extends Tile
{
    public Goal(final Point point) {
        super(point);
        initArea();
    }

    private void initArea() {
        ImageIcon iicon = new ImageIcon("src/resources/goal.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}
