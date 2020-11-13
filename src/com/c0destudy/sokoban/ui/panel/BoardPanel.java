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
    private final int PADDING_TOP    = 80;
    private final int PADDING_BOTTOM = 20;
    private final int MARGIN         = 50;

    private final int     width;
    private final int     height;
    private final int     drawLeft;
    private final int     drawTop;
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

        width    = Math.max(MARGIN * 2 + level.getWidth()  * skin.getImageSize(), 600);
        height   = MARGIN * 2 + level.getHeight() * skin.getImageSize() + PADDING_TOP + PADDING_BOTTOM;
        drawLeft = width / 2 - (level.getWidth() * skin.getImageSize()) / 2;
        drawTop  = MARGIN + PADDING_TOP;

        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(skin.getBackgroundColor());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // 타일 그리기
        drawTiles(g, level.getWalls(),    skin.getImage(Skin.IMAGES.Wall));
        drawTiles(g, level.getGoals(),    skin.getImage(Skin.IMAGES.Goal));
        drawTiles(g, level.getTriggers(), skin.getImage(Skin.IMAGES.Trigger));
        drawTiles(g, level.getBaggages(), skin.getImage(Skin.IMAGES.Baggage));

        // 플레이어
        drawTile(g, level.getPlayer(0), skin.getImage(Skin.IMAGES.Player1));
        if (level.getPlayers().size() == 2) {
            drawTile(g, level.getPlayer(1), skin.getImage(Skin.IMAGES.Player2));
        }

        if (showInfo) {
            g.setColor(new Color(0, 0, 0));
            g.setFont(skin.getFont(Skin.FONTS.Text));
            String levelState;
            if (level.isCompleted()) {
                levelState = "Completed!!";
            } else if (level.isFailed()) {
                levelState = "Failed...";
            } else if (isReplay) {
                levelState = ">> REPLAY: " + level.getName();
            } else {
                levelState = "PLAY: " + level.getName();
            }
            g.drawString("Remaining : "  + level.getRemainingBaggages(), 400, 30);
            g.drawString("Move Count : " + level.getMoveCount(), 30, 70);
            g.drawString("HP : "         + level.getLeftHealth(), 400, 70);
            g.drawString("Score : "      + level.getScore(), 30, 30);
            g.drawString(levelState, 30, height - 30);
        }
    }

    private void drawTile(final Graphics g, final Tile tile, final Image image) {
        final int drawX = drawLeft + tile.getPosition().getX() * skin.getImageSize();
        final int drawY = drawTop  + tile.getPosition().getY() * skin.getImageSize();
        g.drawImage(image, drawX, drawY, this);
    }

    private void drawTiles(final Graphics g, final ArrayList<? extends Tile> tiles, final Image image) {
        for (final Tile tile : tiles) {
            drawTile(g, tile, image);
        }
    }
}