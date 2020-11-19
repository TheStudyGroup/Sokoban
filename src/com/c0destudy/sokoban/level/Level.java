package com.c0destudy.sokoban.level;

import com.c0destudy.sokoban.tile.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable
{
    private static final long        serialVersionUID = 1L;
    private String                   name;
    private int                      width;
    private int                      height;
    private int                      difficulty;
    private final ArrayList<Wall>    walls    = new ArrayList<>();
    private final ArrayList<Goal>    goals    = new ArrayList<>();
    private final ArrayList<Baggage> baggages = new ArrayList<>();
    private final ArrayList<Trigger> triggers = new ArrayList<>();
    private final ArrayList<Player>  players  = new ArrayList<>();
    private final ArrayList<Record>  records  = new ArrayList<>();
    private boolean                  isRecordEnabled;
    private int                      moveCount         = 0;
    private int                      undoCount         = 0;
    private int                      remainingBaggages = 0;
    private long                     timeLastMove      = 0;
    private int                      hp                = 3;

    public Level(final String name, final int width, final int height, final int difficulty) {
        this.name       = name;
        this.width      = width;
        this.height     = height;
        this.difficulty = difficulty;
        setRecordEnabled(true);
    }

    // 레벨 정보
    public String  getName()              { return name;              }
    public int     getWidth()             { return width;             }
    public int     getHeight()            { return height;            }
    public int     getDifficulty()        { return difficulty;        }
    public int     getMoveCount()         { return moveCount;         }
    public int     getUndoCount()         { return undoCount;         }
    public int     getRemainingBaggages() { return remainingBaggages; }
    public int     getLeftHealth()        { return hp;                }
    public int     getScore()             { return (difficulty * 4 - moveCount) * 10;  }
    public boolean isCompleted()          { return remainingBaggages == 0;             }
    public boolean isFailed()             { return hp == 0 || getScore() <= 0;         }
    public boolean getRecordEnabled()     { return isRecordEnabled;                    }
    public void    setName      (final String name)    { this.name = name;             }
    public void    setDifficulty(final int difficulty) { this.difficulty = difficulty; }
    public void    setRecordEnabled(final boolean enabled) {
        isRecordEnabled = enabled;
        if (isRecordEnabled) {
            timeLastMove = System.currentTimeMillis();
        }
    }
    private void updateRemainingBaggages() {
        int count = 0;
        for (final Baggage baggage : getBaggages()) {
            if (!isGoalAt(baggage.getPosition())) {
                count++;
            }
        }
        remainingBaggages = count;
    }

    // Tile
    public ArrayList<Wall>    getWalls()    { return walls;    }
    public ArrayList<Baggage> getBaggages() { return baggages; }
    public ArrayList<Goal>    getGoals()    { return goals;    }
    public ArrayList<Trigger> getTriggers() { return triggers; }
    public ArrayList<Player>  getPlayers()  { return players;  }
    public ArrayList<Movable> getMovables() {
        final ArrayList<Movable> movables = new ArrayList<>();
        movables.addAll(baggages);
        movables.addAll(players);
        return movables;
    }
    public ArrayList<Tile> getTiles() {
        final ArrayList<Tile> tiles = new ArrayList<>();
        tiles.addAll(walls);
        tiles.addAll(goals);
        tiles.addAll(baggages);
        tiles.addAll(players);
        tiles.addAll(triggers);
        return tiles;
    }
    public Player getPlayer(final int index) {
        try {
            return players.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    private Baggage getBaggageAt(final Point position) {
        for (final Baggage baggage : baggages) {
            if (baggage.getPosition().equals(position)) {
                return baggage;
            }
        }
        return null;
    }

    private boolean isInRange(final Point position) {
        if (position.getX() < 0 || position.getX() >= width)  return false;
        if (position.getY() < 0 || position.getY() >= height) return false;
        return true;
    }

    public boolean addTile(final Tile tile) {
        final Point pos = tile.getPosition();
        if (tile instanceof Wall) {           // Wall
            if (isTileAt(pos)) return false;
            walls.add((Wall) tile);
        } else if (tile instanceof Baggage) { // Baggage
            if (isWallAt(pos) || isBaggageAt(pos) || isTriggerAt(pos) || isPlayerAt(pos)) return false;
            baggages.add((Baggage) tile);
            updateRemainingBaggages();
        } else if (tile instanceof Goal) {    // Goal
            if (isWallAt(pos) || isTriggerAt(pos) || isGoalAt(pos)) return false;
            goals.add((Goal) tile);
            updateRemainingBaggages();
        } else if (tile instanceof Trigger) { // Trigger
            if (isTileAt(pos)) return false;
            triggers.add((Trigger) tile);
        } else if (tile instanceof Player) {  // Player
            if (isWallAt(pos) || isBaggageAt(pos) || isTriggerAt(pos) || isPlayerAt(pos)) return false;
            players.add((Player) tile);
        }
        return true;
    }

    public boolean removeTile(final Tile tile) {
        if (tile instanceof Wall) {           // Wall
            return walls.remove(tile);
        } else if (tile instanceof Baggage) { // Baggage
            if (baggages.remove(tile)) {
                updateRemainingBaggages();
                return true;
            }
        } else if (tile instanceof Goal) {    // Goal
            if (goals.remove(tile)) {
                updateRemainingBaggages();
                return true;
            }
        } else if (tile instanceof Trigger) { // Trigger
            return triggers.remove(tile);
        } else if (tile instanceof Player) {  // Player
            return players.remove(tile);
        }
        return false;
    }

    public void removeTileAt(final Point point) {
        getTiles()
            .stream()
            .filter(e -> e.getPosition().equals(point))
            .forEach(this::removeTile);
        updateRemainingBaggages();
    }

    public boolean isWallAt   (final Point p) { return walls     .stream().anyMatch(e -> e.getPosition().equals(p)); }
    public boolean isBaggageAt(final Point p) { return baggages  .stream().anyMatch(e -> e.getPosition().equals(p)); }
    public boolean isGoalAt   (final Point p) { return goals     .stream().anyMatch(e -> e.getPosition().equals(p)); }
    public boolean isTriggerAt(final Point p) { return triggers  .stream().anyMatch(e -> e.getPosition().equals(p)); }
    public boolean isPlayerAt (final Point p) { return players   .stream().anyMatch(e -> e.getPosition().equals(p)); }
    public boolean isTileAt   (final Point p) { return getTiles().stream().anyMatch(e -> e.getPosition().equals(p)); }

    public Record getRecord(final int index) {
        if (index >= 0 && index < records.size()) {
            return records.get(index);
        } else {
            return null;
        }
    }

    public void reset() {
        records.clear();
        resetWithoutRecords();
    }

    public void resetWithoutRecords() {
        for (final Movable movable : getMovables()) {
            movable.setPosition(movable.getOriginalPosition());
        }
        timeLastMove = System.currentTimeMillis();
        moveCount    = 0;
        hp           = 3;
        updateRemainingBaggages();
    }

    public void resetAndResize(final int width, final int height) {
        if (width <= 0 || width > 30 || height <= 0 || height > 30) return;
        this.width  = width;
        this.height = height;
        reset();
        getTiles()
            .stream()
            .filter(e -> !isInRange(e.getPosition()))
            .forEach(this::removeTile); // 범위를 벗어난 타일은 제거한다.
    }

    /**
     * 플레이어의 좌표 변화량을 입력받아 플레이어를 이동하고,
     * 물건을 미는 경우 물건도 이동시킵니다.
     *
     * 이동하는 방향에 물건이 있으면 물건도 같이 이동시킵니다.
     * 단, 물건이 연속으로 2개 있거나 벽이 있는 경우에는 이동할 수 없습니다.
     *
     * @param  playerIndex 이동할 플레이어 인덱스
     * @param  direction   플레이어의 이동 방향 (좌표 변화량)
     * @return             성공 여부
     */
    public boolean movePlayer(final int playerIndex, final Point direction) {
        // 플레이어가 이동할 새로운 좌표 계산
        final Player player       = getPlayer(playerIndex);
        final Point  newPlayerPos = Point.add(player.getPosition(), direction);

        // 범위를 벗어나는 경우 => 이동 불가
        if (!isInRange(newPlayerPos)) return false;

        // 이동할 위치에 벽이 있는 경우 => 이동 불가
        if (isWallAt(newPlayerPos)) return false;

        // 이동할 위치에 다른 플레이어가 있는 경우 => 이동 불가
        if (isPlayerAt(newPlayerPos)) return false;

        // 함정이 있는 경우 => 체력 감소
        if (isTriggerAt(newPlayerPos)) hp--;

        // 플레이어가 물건(baggage)을 미는 경우
        final Baggage baggage = getBaggageAt(newPlayerPos);
        if (baggage != null) {
            if (!moveBaggage(baggage, direction)) return false;
        }

        // 기록 (undo와 replay를 위함)
        if (getRecordEnabled()) {
            final long   currentTime = System.currentTimeMillis();
            final Record record      = new Record(
                    currentTime - timeLastMove,
                    playerIndex,
                    player.getPosition(),
                    direction,
                    baggage != null);
            records.add(record);
            timeLastMove = currentTime;
        }

        // 플레이어 이동
        player.moveDelta(direction);
        moveCount++;
        return true;
    }

    /**
     * 물건을 이동시킵니다.
     *
     * 남은 물건의 개수는 자동으로 계산됩니다.
     * 물건을 이동할 수 없거나 찾을 수 없는 경우에는 false를 반환합니다.
     *
     * @param  baggage   이동할 물건 객체
     * @param  direction 물건의 이동 방향 (좌표 변화량)
     * @return           성공 여부
     */
    private boolean moveBaggage(final Baggage baggage, final Point direction) {
        // 물건이 이동될 새로운 좌표 계산
        final Point position = Point.add(baggage.getPosition(), direction);

        // 물건이 이동될 위치에 벽이 있는 경우 => 이동 불가
        if (isWallAt(position)) return false;

        // 물건이 이동될 위치에 물건이 있는 경우 (연속 2개) => 이동 불가
        if (getBaggageAt(position) != null) return false;

        // 물건이 이동될 위치에 플레이어가 있는 경우 => 이동 불가
        if (isPlayerAt(position)) return false;

        // 이동 전에 물건이 목적지에 있는 경우
        if (isGoalAt(baggage.getPosition())) remainingBaggages++;

        // 물건을 이동시킬 수 있는 공간이 있으면 물건을 민다
        baggage.moveDelta(direction);

        // 이동 후에 물건이 목적지에 있는 경우
        if (isGoalAt(baggage.getPosition())) remainingBaggages--;

        return true;
    }

    /**
     * 플레이어 이동을 취소합니다.
     *
     * 물건을 움직인 경우 물건도 이전 위치로 다시 이동시킵니다.
     */
    public void undoMove() {
        if (!getRecordEnabled() || records.size() == 0) return;

        // 마지막 움직임 기록 가져오기
        final Record record    = records.remove(records.size() - 1);
        final Player player    = getPlayer(record.getPlayerIndex());
        final Point  position  = record.getPosition();
        final Point  direction = record.getDirection();

        // 플레이어 역방향 이동
        player.moveDelta(Point.reverse(direction));

        // 물건을 민 경우 => 물건도 역방향 이동
        if (record.isBaggageMoved()) {
            final Baggage baggage = getBaggageAt(Point.add(position, direction, direction));
            moveBaggage(baggage, Point.reverse(direction));
        }

        moveCount--;
        undoCount++;
    }
}
