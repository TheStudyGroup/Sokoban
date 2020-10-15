package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.skin.Skin;
import com.c0destudy.sokoban.tile.Tile;
import com.c0destudy.sokoban.ui.frame.FrameManager;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GamePanel extends JPanel
{
    private final int PADDING_TOP = 50;
    private final int MARGIN = 50;

    private final Skin    skin;
    private final Level   level;
    private final boolean isReplay;

    public GamePanel(final Level level, final boolean isReplay) {
        super();
        this.level    = level;
        this.skin     = FrameManager.getSkin();
        this.isReplay = isReplay;
        final int width  = MARGIN * 2 + level.getWidth()  * skin.getImageSize();
        final int height = MARGIN * 2 + level.getHeight() * skin.getImageSize() + PADDING_TOP;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(skin.getColor());
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

        g.setColor(new Color(0, 0, 0));
        g.setFont(skin.getFont(Skin.FONTS.Text));
        String levelState;
        if (level.isCompleted()) {
            levelState = "Completed";
        } else if (level.isFailed()) {
            levelState = "Failed";
        } else if (isReplay) {
            levelState = "Replay: " + level.getName();
        } else {
            levelState = "Play: " + level.getName();
        }
        g.drawString(levelState, 30, 30);
        g.drawString("Remaining : " + level.getRemainingBaggages(), 300, 30);
        g.drawString("Move Count : " + level.getMoveCount(), 30, 70);
        g.drawString("HP:" + level.getLeftHealth(), 300, 70);
    }

    private void drawTile(final Graphics g, final Tile tile, final Image image) {
        final int drawX = MARGIN + tile.getPosition().getX() * skin.getImageSize();
        final int drawY = MARGIN + tile.getPosition().getY() * skin.getImageSize() + PADDING_TOP;
        g.drawImage(image, drawX, drawY, this);
    }

    private void drawTiles(final Graphics g, final ArrayList<? extends Tile> tiles, final Image image) {
        for (final Tile tile : tiles) {
            drawTile(g, tile, image);
        }
    }
}
