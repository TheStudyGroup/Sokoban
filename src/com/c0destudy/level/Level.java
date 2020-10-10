package com.c0destudy.level;

import com.c0destudy.block.*;

import java.util.ArrayList;

public class Level
{
    private final int                width;
    private final int                height;
    private final ArrayList<Wall>    walls    = new ArrayList<>();
    private final ArrayList<Goal>    goals    = new ArrayList<>();
    private final ArrayList<Baggage> baggages = new ArrayList<>();
    private final ArrayList<Player>  players  = new ArrayList<>();
    private int                      remainingBaggages = 0;

    // 생성자
    public Level(final int width, final int height) {
        this.width  = width;
        this.height = height;
    }

    // 레벨 정보
    public int getWidth()  { return width;  }
    public int getHeight() { return height; }
    public int getRemainingBaggages() { return remainingBaggages; }
    public boolean isCompleted()      { return remainingBaggages == 0; }

    // 블럭
    public ArrayList<Block> getAllBlocks() {
        final ArrayList<Block> blocks = new ArrayList<>();
        blocks.addAll(walls);
        blocks.addAll(goals);
        blocks.addAll(baggages);
        blocks.addAll(players);
        return blocks;
    }
    public Player getPlayer(final int index) {
        try {
            return players.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    public void addWall   (final Wall wall)       { walls.add(wall);     }
    public void addGoal   (final Goal goal)       { goals.add(goal);     }
    public void addPlayer (final Player player)   { players.add(player); }
    public void addBaggage(final Baggage baggage) { baggages.add(baggage); remainingBaggages++; }

    /**
     * 플레이어의 좌표 변화량을 입력받아 플레이어를 이동하고,
     * 물건을 미는 경우 물건도 이동시킵니다.
     *
     * 플레이어를 왼쪽으로 이동시키는 경우 dx는 -1이고 dy는 0입니다.
     * 이동하는 방향에 물건이 있으면 물건도 같이 이동시킵니다.
     * 단, 물건이 연속으로 2개 있거나 벽이 있는 경우에는 이동할 수 없습니다.
     *
     * @param  playerIndex 이동할 플레이어 번호 (0-based)
     * @param  dx          플레이어의 x 좌표 변화량
     * @param  dy          플레이어의 y 좌표 변화량
     * @return             이동 여부 (이동 실패시 false)
     */
    public boolean movePlayerAndBaggage(final int playerIndex, final int dx, final int dy) {
        // 플레이어가 이동할 새로운 좌표 계산
        final Player player = getPlayer(playerIndex);
        final int newPlayerX = player.getX() + dx;
        final int newPlayerY = player.getY() + dy;

        // 이동할 위치에 벽이 있는 경우 => 이동 불가
        if (isWallAt(newPlayerX, newPlayerY)) return false;

        // 이동할 위치에 다른 플레이어가 있는 경우 => 이동 불가
        if (isPlayerAt(newPlayerX, newPlayerY)) return false;

        // 플레이어가 물건(baggage)을 미는 경우
        Baggage nearBaggage = getBaggageAt(newPlayerX, newPlayerY);
        if (nearBaggage != null) {
            // 물건이 이동될 새로운 좌표 계산
            final int newBaggageX = nearBaggage.getX() + dx;
            final int newBaggageY = nearBaggage.getY() + dy;

            // 물건이 이동될 위치에 벽이 있는 경우 => 이동 불가
            if (isWallAt(newBaggageX, newBaggageY)) return false;

            // 물건이 이동될 위치에 물건이 있는 경우 (연속 2개) => 이동 불가
            if (getBaggageAt(newBaggageX, newBaggageY) != null) return false;

            // 물건이 이동될 위치에 다른 플레이어가 있는 경우 => 이동 불가
            if (isPlayerAt(newBaggageX, newBaggageY)) return false;

            // 이동 전에 물건이 목적지에 있는 경우
            if (isGoalAt(nearBaggage.getX(), nearBaggage.getY())) remainingBaggages++;

            // 물건을 이동시킬 수 있는 공간이 있으면 물건을 민다
            nearBaggage.move(dx, dy);

            // 이동 후에 물건이 목적지에 있는 경우
            if (isGoalAt(nearBaggage.getX(), nearBaggage.getY())) remainingBaggages--;
        }

        // 플레이어 이동
        player.move(dx, dy);
        return true;
    }

    /**
     * 해당 좌표에 벽이 있는지 확인합니다.
     *
     * @param  x x 좌표
     * @param  y y 좌표
     * @return   벽의 존재 여부
     */
    private boolean isWallAt(int x, int y) {
        for (final Wall wall: walls) {
            if (wall.isLocatedAt(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 해당 좌표에 목적지가 있는지 확인합니다.
     *
     * @param  x x 좌표
     * @param  y y 좌표
     * @return   목적지의 존재 여부
     */
    private boolean isGoalAt(int x, int y) {
        for (final Goal goal: goals) {
            if (goal.isLocatedAt(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 해당 좌표에 플레이어가 있는지 확인합니다.
     *
     * @param  x x 좌표
     * @param  y y 좌표
     * @return   플레이어의 존재 여부
     */
    private boolean isPlayerAt(int x, int y) {
        for (final Player player: players) {
            if (player.isLocatedAt(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 해당 좌표에 있는 물건 객체를 가져옵니다.
     *
     * @param  x x 좌표
     * @param  y y 좌표
     * @return   물건 객체 (없는 경우 null)
     */
    private Baggage getBaggageAt(int x, int y) {
        for (final Baggage baggage: baggages) {
            if (baggage.isLocatedAt(x, y)) {
                return baggage;
            }
        }
        return null;
    }
}
