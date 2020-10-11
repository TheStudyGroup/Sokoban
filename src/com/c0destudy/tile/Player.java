package com.c0destudy.tile;

import com.c0destudy.misc.Point;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Player extends Movable
{
    public Player(final Point point) {
        super(point);
        initPlayer();
    }

    private void initPlayer() {
        ImageIcon iicon = new ImageIcon("src/resources/player.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}
