package com.c0destudy.block;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Wall extends Block
{
    public Wall(int x, int y) {
        super(x, y);
        initWall();
    }
    
    private void initWall() {
        ImageIcon iicon = new ImageIcon("src/resources/wall.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}
