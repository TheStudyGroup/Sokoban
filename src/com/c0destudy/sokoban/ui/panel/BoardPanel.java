package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.resource.Skin;
import com.c0destudy.sokoban.tile.Tile;
import com.c0destudy.sokoban.ui.frame.FrameManager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class BoardPanel extends JPanel
{
    private final int MARGIN        = 50;
    private final int PADDING_RIGHT = 200;
    private final int TILE_SIZE;

    private final int                width;
    private final int                height;
    private final Level              level;
    private final Skin               skin;
    private final BoardMouseListener listener;
    private final boolean            isReplay;
    private boolean                  isEditable;
    private final boolean            showInfo;
    private int                      mouseTileX;
    private int                      mouseTileY;

    public BoardPanel(final Level level, final boolean isReplay, final boolean showInfo) {
        super();
        this.level    = level;
        this.skin     = FrameManager.getSkin();
        this.listener = new BoardMouseListener();
        this.isReplay = isReplay;
        this.showInfo = showInfo;
        TILE_SIZE = skin.getImageSize();

        width  = MARGIN * 2 + level.getWidth()  * skin.getImageSize() + (showInfo ? PADDING_RIGHT : 0);
        height = MARGIN * 2 + level.getHeight() * skin.getImageSize();
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        setEditable(false);
    }

    public void setEditable(final boolean isEditable) {
        this.isEditable = isEditable;
        if (isEditable) {
            mouseTileX = -1;
            mouseTileY = -1;
            addMouseListener(listener);
            addMouseMotionListener(listener);
        } else {
            removeMouseListener(listener);
            removeMouseMotionListener(listener);
        }
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

        if (isEditable && mouseTileX != -1) {
            drawImage(g, mouseTileX, mouseTileY, skin.getImage(Skin.IMAGES.Pointer));
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

    private void drawImage(final Graphics g, final int tileX, final int tileY, final Image image) {
        final int drawX = MARGIN + tileX * TILE_SIZE;
        final int drawY = MARGIN + tileY * TILE_SIZE;
        g.drawImage(image, drawX, drawY, this);
    }

    private void drawTile(final Graphics g, final Tile tile, final Image image) {
        drawImage(g, tile.getPosition().getX(), tile.getPosition().getY(), image);
    }

    private void drawTiles(final Graphics g, final ArrayList<? extends Tile> tiles, final Image image) {
        for (final Tile tile : tiles) {
            drawTile(g, tile, image);
        }
    }

    private void setTile(final int x, final int y) {
        // TODO
    }

    class BoardMouseListener implements MouseListener, MouseMotionListener
    {
        private final int startX = MARGIN;
        private final int startY = MARGIN;
        private final int endX   = MARGIN + level.getWidth()  * skin.getImageSize();
        private final int endY   = MARGIN + level.getHeight() * skin.getImageSize();

        private boolean isInRange(final int x, final int y) {
            return (x >= startX && x <= endX && y >= startY && y <= endY);
        }

        private void setTilePosition(int x, int y, final boolean isClicked, final boolean isDragged) {
            if (isInRange(x, y)) {
                x = (x - MARGIN) / TILE_SIZE;
                y = (y - MARGIN) / TILE_SIZE;
            } else {
                x = y = -1;
            }
            if (isClicked && x != -1) {
                setTile(x, y);
            }
            if (x != mouseTileX || y != mouseTileY) {
                mouseTileX = x;
                mouseTileY = y;
                if (isDragged && x != -1) {
                    setTile(x, y);
                }
                repaint();
            }
        }

        public void mousePressed (MouseEvent e) { setTilePosition(e.getX(), e.getY(), true,  false); }
        public void mouseReleased(MouseEvent e) { }
        public void mouseClicked (MouseEvent e) { }
        public void mouseEntered (MouseEvent e) { }
        public void mouseExited  (MouseEvent e) { setTilePosition(-1, -1,       false, false); }
        public void mouseDragged (MouseEvent e) { setTilePosition(e.getX(), e.getY(), false, true);  }
        public void mouseMoved   (MouseEvent e) { setTilePosition(e.getX(), e.getY(), false, false); }
    }
}
