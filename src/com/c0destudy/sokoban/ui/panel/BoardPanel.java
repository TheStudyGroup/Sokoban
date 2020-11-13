package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.skin.Skin;
import com.c0destudy.sokoban.tile.Tile;
import com.c0destudy.sokoban.ui.frame.FrameManager;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;

public class BoardPanel extends JPanel
{
    private final int MARGIN        = 50;
    private final int PADDING_RIGHT = 200;

    private final int     width;
    private final int     height;
    private final Skin    skin;
    private final Level   level;
    private final boolean isReplay;
    private final boolean showInfo;

    public BoardPanel(final Level level, final boolean isReplay, final boolean showInfo) {
        super();
        this.level    = level;
        this.skin     = FrameManager.getSkin();
        this.isReplay = isReplay;
        this.showInfo = showInfo;

        width  = MARGIN * 2 + level.getWidth()  * skin.getImageSize() + (showInfo ? PADDING_RIGHT : 0);
        height = MARGIN * 2 + level.getHeight() * skin.getImageSize();
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(skin.getColor(Skin.COLORS.Background));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // 타일 그리기
        drawTiles(g, level.getWalls(),    skin.getImage(Skin.IMAGES.Wall));
        drawTiles(g, level.getGoals(),    skin.getImage(Skin.IMAGES.Goal));
        drawTiles(g, level.getTriggers(), skin.getImage(Skin.IMAGES.Trigger));
        drawTiles(g, level.getBaggages(), skin.getImage(Skin.IMAGES.Baggage));

        // 플레이어
        switch (level.getPlayers().size()) {
            case 2:
                drawTile(g, level.getPlayer(1), skin.getImage(Skin.IMAGES.Player2));
            case 1:
                drawTile(g, level.getPlayer(0), skin.getImage(Skin.IMAGES.Player1));
        }

        if (showInfo) {
            final int textLeft = width - PADDING_RIGHT;
            final int textTop  = 30;
            g.setColor(skin.getColor(Skin.COLORS.Text));
            g.setFont(skin.getFont(Skin.FONTS.Medium));
            String levelState;
            if (level.isCompleted()) {
                levelState = "Completed!!";
            } else if (level.isFailed()) {
                levelState = "Failed...";
            } else if (isReplay) {
                levelState = ">>> REPLAY";
            } else {
                levelState = "Playing";
            }
            g.drawString("< " + level.getName() + " >",                 textLeft, textTop);
            g.drawString(levelState,                                        textLeft, textTop + 30 * 1);
            g.drawString("Score : "     + level.getScore(),             textLeft, textTop + 30 * 2);
            g.drawString("Remaining : " + level.getRemainingBaggages(), textLeft, textTop + 30 * 3);
            g.drawString("Move : "      + level.getMoveCount(),         textLeft, textTop + 30 * 4);
            g.drawString("Undo : "      + level.getUndoCount(),         textLeft, textTop + 30 * 5);
            g.drawString("HP : "        + level.getLeftHealth(),        textLeft, textTop + 30 * 6);
        }
    }

    private void drawTile(final Graphics g, final Tile tile, final Image image) {
        final int drawX = MARGIN + tile.getPosition().getX() * skin.getImageSize();
        final int drawY = MARGIN + tile.getPosition().getY() * skin.getImageSize();
        g.drawImage(image, drawX, drawY, this);
    }

    private void drawTiles(final Graphics g, final ArrayList<? extends Tile> tiles, final Image image) {
        for (final Tile tile : tiles) {
            drawTile(g, tile, image);
        }
    }
}
