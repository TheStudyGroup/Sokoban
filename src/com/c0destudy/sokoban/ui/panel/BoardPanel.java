package com.c0destudy.sokoban.ui.panel;

import com.c0destudy.sokoban.level.Level;
import com.c0destudy.sokoban.resource.Skin;
import com.c0destudy.sokoban.tile.Point;
import com.c0destudy.sokoban.tile.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class BoardPanel extends JPanel
{
    private final int MARGIN = 50;
    private final int TILE_SIZE;

    private final int                width;
    private final int                height;
    private final Level              level;
    private final Skin               skin;
    private final BoardMouseListener listener;
    private boolean                  isEditable;
    private boolean                  isBoundaryVisible;
    private int                      mouseTileX;
    private int                      mouseTileY;
    private String                   tileBrush;

    public BoardPanel(final Level level) {
        super();
        this.level    = level;
        this.skin     = Skin.getCurrentSkin();
        this.listener = new BoardMouseListener();
        TILE_SIZE = skin.getImageSize();

        width  = MARGIN * 2 + level.getWidth()  * skin.getImageSize();
        height = MARGIN * 2 + level.getHeight() * skin.getImageSize();
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        setEditable(false);
        setBoundaryVisible(false);
    }

    public void setEditable(final boolean value) {
        isEditable = value;
        if (isEditable) {
            mouseTileX = -1;
            mouseTileY = -1;
            tileBrush  = "";
            addMouseListener(listener);
            addMouseMotionListener(listener);
        } else {
            removeMouseListener(listener);
            removeMouseMotionListener(listener);
        }
    }

    public void setBoundaryVisible(final boolean value) {
        isBoundaryVisible = value;
    }

    public void setTileBrush(final String tileName) {
        tileBrush = tileName;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(skin.getColor(Skin.COLORS.Background));
        g.fillRect(0, 0, width, height);

        // 보드 경계 그리기
        if (isBoundaryVisible) {
            g.setColor(Color.BLACK);
            final int rectX = MARGIN - 20;
            final int rectY = MARGIN - 20;
            final int rectW = level.getWidth()  * skin.getImageSize() + 20 * 2;
            final int rectH = level.getHeight() * skin.getImageSize() + 20 * 2;
            g.fillRect(rectX, rectY, rectW, rectH);
            g.setColor(skin.getColor(Skin.COLORS.Background));
            g.fillRect(rectX + 5, rectY + 5, rectW - 10, rectH - 10);
        }

        // 타일 그리기
        drawTiles(g, level.getWalls(),    skin.getImage(Skin.IMAGES.Wall));
        drawTiles(g, level.getGoals(),    skin.getImage(Skin.IMAGES.Goal));
        drawTiles(g, level.getTriggers(), skin.getImage(Skin.IMAGES.Trigger));
        drawTiles(g, level.getBaggages(), skin.getImage(Skin.IMAGES.Baggage));

        // 플레이어 그리기
        switch (level.getPlayers().size()) {
            case 2:
                drawTile(g, level.getPlayer(1), skin.getImage(Skin.IMAGES.Player2));
            case 1:
                drawTile(g, level.getPlayer(0), skin.getImage(Skin.IMAGES.Player1));
        }

        // 포인터 그리기 (편집 모드)
        if (isEditable && mouseTileX != -1) {
            drawImage(g, mouseTileX, mouseTileY, skin.getImage(Skin.IMAGES.Pointer));
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

    private void setTile(final Point point) {
        switch (tileBrush) {
            case "Eraser":
                level.removeTileAt(point);
                break;
            case "Wall":
                if (level.isWallAt(point)) break;
                level.removeTileAt(point);
                level.addTile(new Wall(point));
                break;
            case "Baggage":
                if (level.isBaggageAt(point)) break;
                if (!level.addTile(new Baggage(point))) {
                    level.removeTileAt(point);
                    level.addTile(new Baggage(point));
                }
                break;
            case "Goal":
                if (level.isGoalAt(point)) break;
                if (!level.addTile(new Goal(point))) {
                    level.removeTileAt(point);
                    level.addTile(new Goal(point));
                }
                break;
            case "Trigger":
                if (level.isTriggerAt(point)) break;
                level.removeTileAt(point);
                level.addTile(new Trigger(point));
                break;
            case "Player":
                if (!level.addTile(new Player(point))) {
                    level.removeTileAt(point);
                    level.addTile(new Player(point));
                }
                // 플레이어가 2명을 초과할 경우 먼저 추가된 플레이어 제거
                if (level.getPlayers().size() > 2) {
                    level.getPlayers().remove(0);
                }
                break;
        }
    }

    class BoardMouseListener implements MouseListener, MouseMotionListener
    {
        private final int startX = MARGIN;
        private final int startY = MARGIN;
        private final int endX   = MARGIN + level.getWidth()  * skin.getImageSize();
        private final int endY   = MARGIN + level.getHeight() * skin.getImageSize();

        private boolean isInRange(final int x, final int y) {
            return (x >= startX && x < endX && y >= startY && y < endY);
        }

        private void setTilePosition(int x, int y, final boolean isClicked, final boolean isDragged) {
            if (isInRange(x, y)) {
                x = (x - MARGIN) / TILE_SIZE;
                y = (y - MARGIN) / TILE_SIZE;
            } else {
                x = y = -1;
            }
            if (isClicked && x != -1) {
                setTile(new Point(x, y));
                repaint();
            }
            if (x != mouseTileX || y != mouseTileY) {
                mouseTileX = x;
                mouseTileY = y;
                if (isDragged && x != -1) {
                    setTile(new Point(x, y));
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
