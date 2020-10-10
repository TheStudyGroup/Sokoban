package com.c0destudy.block;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Player extends Movable
{
    public Player(int x, int y) {
        super(x, y);
        initPlayer();
    }

    private void initPlayer() {
        ImageIcon iicon = new ImageIcon("src/resources/player.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}
