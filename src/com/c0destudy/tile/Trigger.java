package com.c0destudy.tile;

import com.c0destudy.misc.Point;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Trigger extends Tile {

	public Trigger(final Point point) {
		super(point);
		initTrigger();
	}

	private void initTrigger() {
		ImageIcon iicon = new ImageIcon("src/resources/trigger.png");
		Image image = iicon.getImage();
		setImage(image);
	}

}
