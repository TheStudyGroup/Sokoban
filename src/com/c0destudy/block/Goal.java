package com.c0destudy.block;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Goal extends Block
{
    public Goal(int x, int y) {
        super(x, y);
        initArea();
    }

    private void initArea() {
        ImageIcon iicon = new ImageIcon("src/resources/goal.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}
