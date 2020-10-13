package com.c0destudy.sokoban.level;

import com.c0destudy.sokoban.misc.Point;
import com.c0destudy.sokoban.recording.Movement;
import com.c0destudy.sokoban.recording.Recording;
import com.c0destudy.sokoban.recording.RecordingType;
import com.c0destudy.sokoban.recording.RecordingUnit;
import com.c0destudy.sokoban.tile.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable
{
    private static final long        serialVersionUID = 1L;
    private final String             name;
    private final int                width;
    private final int                height;
    private final ArrayList<Wall>    walls    = new ArrayList<>();
    private final ArrayList<Goal>    goals    = new ArrayList<>();
    private final ArrayList<Baggage> baggages = new ArrayList<>();
    private final ArrayList<Player>  players  = new ArrayList<>();
    private long                     timeLastMove;
    private int                      moveCount         = 0;
    private int                      remainingBaggages = 0;
    private final Recording          recording = new Recording();

    public Level(final String name, final int width, final int height) {
        this.name         = name;
        this.width        = width;
        this.height       = height;
        this.timeLastMove = System.currentTimeMillis();
    }

    // 레벨 정보
    public String  getName()      { return name;   }
    public int     getWidth()     { return width;  }
    public int     getHeight()    { return height; }
    public int     getMoveCount() { return moveCount; }
    public int     getRemainingBaggages() { return remainingBaggages; }
    public boolean isCompleted()          { return remainingBaggages == 0; }

    // 타일
    public ArrayList<Wall>    getWalls()    { return walls;    }
    public ArrayList<Goal>    getGoals()    { return goals;    }
    public ArrayList<Baggage> getBaggages() { return baggages; }
    public ArrayList<Player>  getPlayers()  { return players;  }
    public ArrayList<Tile>    getAllTiles() {
        final ArrayList<Tile> tiles = new ArrayList<>();
        tiles.addAll(walls);
        tiles.addAll(goals);
        tiles.addAll(baggages);
        tiles.addAll(players);
        return tiles;
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
     * 이동하는 방향에 물건이 있으면 물건도 같이 이동시킵니다.
     * 단, 물건이 연속으로 2개 있거나 벽이 있는 경우에는 이동할 수 없습니다.
     *
     * @param  playerIndex 이동할 플레이어 번호 (0-based)
     * @param  direction   플레이어의 좌표 변화량
     * @return             이동 여부 (이동 실패시 false)
     */
    public boolean movePlayerAndBaggage(final int playerIndex, final Point direction) {
        // 플레이어가 이동할 새로운 좌표 계산
        final Player player       = getPlayer(playerIndex);
        final Point  newPlayerPos = Point.add(player.getPosition(), direction);

        // 이동할 위치에 벽이 있는 경우 => 이동 불가
        if (isWallAt(newPlayerPos)) return false;

        // 이동할 위치에 다른 플레이어가 있는 경우 => 이동 불가
        if (isPlayerAt(newPlayerPos)) return false;

        // 플레이어가 물건(baggage)을 미는 경우
        final Baggage       nearBaggage   = getBaggageAt(newPlayerPos);
        final long          currentTime   = System.currentTimeMillis();
        final RecordingUnit recordingUnit = new RecordingUnit(currentTime - timeLastMove);
        if (nearBaggage != null) {
            // 물건이 이동될 새로운 좌표 계산
            final Point newBaggagePos = Point.add(nearBaggage.getPosition(), direction);

            // 물건이 이동될 위치에 벽이 있는 경우 => 이동 불가
            if (isWallAt(newBaggagePos)) return false;

            // 물건이 이동될 위치에 물건이 있는 경우 (연속 2개) => 이동 불가
            if (getBaggageAt(newBaggagePos) != null) return false;

            // 물건이 이동될 위치에 다른 플레이어가 있는 경우 => 이동 불가
            if (isPlayerAt(newBaggagePos)) return false;

            // 이동 전에 물건이 목적지에 있는 경우
            if (isGoalAt(nearBaggage.getPosition())) remainingBaggages++;

            // 물건을 이동시킬 수 있는 공간이 있으면 물건을 민다
            recordingUnit.addMovement(new Movement(RecordingType.Baggage, nearBaggage.getPosition(), direction));
            nearBaggage.moveDelta(direction);

            // 이동 후에 물건이 목적지에 있는 경우
            if (isGoalAt(nearBaggage.getPosition())) remainingBaggages--;
        }

        // 플레이어 이동 및 기록
        recordingUnit.addMovement(new Movement(RecordingType.Player, player.getPosition(), direction));
        recording.addRecord(recordingUnit);
        timeLastMove = currentTime;
        player.moveDelta(direction);
        moveCount++;
        return true;
    }

    public boolean undoMove() {
        final RecordingUnit recordingUnit = recording.popRecord();
        if (recordingUnit == null) return false;

        Movement movement;
        while ((movement = recordingUnit.popMovement()) != null) {
            final Point oldPos  = movement.getPosition();
            final Point currPos = Point.add(movement.getPosition(), movement.getDirection());
            switch (movement.getType()) {
                case Player:
                    getPlayerAt(currPos).moveTo(oldPos);
                    break;
                case Baggage:
                    final Baggage baggage = getBaggageAt(currPos);
                    if (isGoalAt(baggage.getPosition())) remainingBaggages++;
                    baggage.moveTo(oldPos);
                    if (isGoalAt(baggage.getPosition())) remainingBaggages--;
            }
        }

        moveCount++; // undo도 이동으로 계산
        return true;
    }

    /**
     * 해당 좌표에 벽이 있는지 확인합니다.
     *
     * @param  position 좌표
     * @return          벽의 존재 여부
     */
    private boolean isWallAt(final Point position) {
        for (final Wall wall : walls) {
            if (wall.isLocatedAt(position)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 해당 좌표에 목적지가 있는지 확인합니다.
     *
     * @param  position 좌표
     * @return          목적지의 존재 여부
     */
    private boolean isGoalAt(final Point position) {
        for (final Goal goal : goals) {
            if (goal.isLocatedAt(position)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 해당 좌표에 플레이어가 있는지 확인합니다.
     *
     * @param  position 좌표
     * @return          플레이어의 존재 여부
     */
    private boolean isPlayerAt(final Point position) {
        for (final Player player : players) {
            if (player.isLocatedAt(position)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 해당 좌표에 있는 물건 객체를 가져옵니다.
     *
     * @param  position 좌표
     * @return          물건 객체
     */
    private Baggage getBaggageAt(final Point position) {
        for (final Baggage baggage : baggages) {
            if (baggage.isLocatedAt(position)) {
                return baggage;
            }
        }
        return null;
    }

    /**
     * 해당 좌표에 있는 플레이어 객체를 가져옵니다.
     *
     * @param  position 좌표
     * @return          플레이어 객체
     */
    private Player getPlayerAt(final Point position) {
        for (final Player player : players) {
            if (player.isLocatedAt(position)) {
                return player;
            }
        }
        return null;
    }
}
