package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.misc.Resource;
import com.c0destudy.sokoban.skin.Skin;
import com.c0destudy.sokoban.tile.Tile;

import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel
{
    private final int MARGIN = 30;
    private final int BLOCK_SIZE = 20;
    
    private       Skin  skin;
    private final Level level;

    public GamePanel(final Skin skin, final Level level) {
        super();
        this.level = level;
        this.skin  = skin;
        final int width  = MARGIN * 2 + level.getWidth() * BLOCK_SIZE;
        final int height = MARGIN * 2 + level.getHeight() * BLOCK_SIZE;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
    }

    public void setSkin(final Skin skin) {
        this.skin = skin;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Wall
        Image image = skin.getImage(Skin.IMAGES.Wall);
        for (final Tile tile : level.getWalls()) drawTile(g, tile, image);
        // Goal
        image = skin.getImage(Skin.IMAGES.Goal);
        for (final Tile tile : level.getGoals()) drawTile(g, tile, image);
        // Trigger
        image = skin.getImage(Skin.IMAGES.Trigger);
        for (final Tile tile : level.getTriggers()) drawTile(g, tile, image);
        // Baggage
        image = skin.getImage(Skin.IMAGES.Baggage);
        for (final Tile tile : level.getBaggages()) drawTile(g, tile, image);
        // Player
        drawTile(g, level.getPlayer(0), skin.getImage(Skin.IMAGES.Player1));
        if (level.getPlayers().size() == 2) {
            drawTile(g, level.getPlayer(1), skin.getImage(Skin.IMAGES.Player2));
        }

        g.setColor(new Color(0, 0, 0));
        
        if (level.isCompleted()) {
            g.drawString("Completed", 25, 20);
            g.drawString("Grade:" + level.getGrade(level.getMoveCount()), 25, 30);
//            if(int(level.getGrade(level.getMoveCount(), stage))< int(level.callHighGrade(stage)) )
//            	level.writeHighGrade(grade);
        }
        else if (level.isFailed()) {
            g.drawString("Failed", 25, 25);
        } else {
        	
            g.drawString("Remaining : " + level.getRemainingBaggages(), 25, 10);
            g.drawString("Move Count : " + level.getMoveCount(), 25, 20);
            g.drawString("HP:" + level.getLeftHealth(), 25, 30);
//            g.drawString("" + level.getGrade(level.getMoveCount()), 25, 40);
//            g.drawString("High Grade:" + level.callHighGrade(stage), 25, 40);
        }
    }

    private void drawTile(final Graphics g, final Tile tile, final Image image) {
        final int drawX = MARGIN + tile.getPosition().getX() * BLOCK_SIZE;
        final int drawY = MARGIN + tile.getPosition().getY() * BLOCK_SIZE;
        g.drawImage(image, drawX, drawY, this);
    }
}
