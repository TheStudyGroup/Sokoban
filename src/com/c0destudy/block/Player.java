package com.c0destudy.block;

import com.c0destudy.Point;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Player extends Movable
{
    public Player(int x, int y) {
        super(x, y);
        initPlayer();
    }

    public Player(final Point point) {
        this(point.getX(), point.getY());
    }

    private void initPlayer() {
        ImageIcon iicon = new ImageIcon("src/resources/player.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}
