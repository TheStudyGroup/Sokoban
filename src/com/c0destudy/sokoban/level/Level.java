package com.c0destudy.sokoban.level;

import com.c0destudy.sokoban.misc.Point;
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
    private final ArrayList<Trigger> triggers = new ArrayList<>();
    private final ArrayList<Player>  players  = new ArrayList<>();
    private final ArrayList<Record>  records  = new ArrayList<>();
    private boolean                  isRecordEnabled;
    private final int                minMoveCount;
    private int                      moveCount         = 0;
    private int                      remainingBaggages = 0;
    private long                     timeLastMove      = 0;
    private int                      hp                = 3;

    public Level(final String name, final int width, final int height, final int minMoveCount) {
        this.name         = name;
        this.width        = width;
        this.height       = height;
        this.minMoveCount = minMoveCount;
        setRecordEnabled(true);
    }
 
    // 레벨 정보
    public String  getName()              { return name;              }
    public int     getWidth()             { return width;             }
    public int     getHeight()            { return height;            }
    public int     getMinMoveCount()      { return minMoveCount;      }
    public int     getMoveCount()         { return moveCount;         }
    public int     getRemainingBaggages() { return remainingBaggages; }
    public int     getLeftHealth()        { return hp;                }
    public int     getScore()             { return (minMoveCount * 4 - moveCount) * 10; }
    public boolean isCompleted()          { return remainingBaggages == 0;              }
    public boolean isFailed()             { return hp == 0 || getScore() <= 0;          }
    public boolean getRecordEnabled()     { return isRecordEnabled;                     }
    public void    setRecordEnabled(final boolean enabled) {
        isRecordEnabled = enabled;
        if (isRecordEnabled) {
            timeLastMove = System.currentTimeMillis();
        }
    }

    // 타일
    public ArrayList<Wall>    getWalls()    { return walls;    }
    public ArrayList<Goal>    getGoals()    { return goals;    }
    public ArrayList<Baggage> getBaggages() { return baggages; }
    public ArrayList<Player>  getPlayers()  { return players;  }
    public ArrayList<Trigger> getTriggers() { return triggers; }    
    public ArrayList<Movable> getMovables() {
        final ArrayList<Movable> movables = new ArrayList<>();
        movables.addAll(baggages);
        movables.addAll(players);
        return movables;
    }
    public Player getPlayer(final int index) {
        try {
            return players.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    public Record getRecord(final int index) {
        if (index >= 0 && index < records.size()) {
            return records.get(index);
        } else {
            return null;
        }
    }
    public void addWall   (final Wall wall)       { walls.add(wall);     }
    public void addGoal   (final Goal goal)       { goals.add(goal);     }
    public void addTrigger(final Trigger trigger) { triggers.add(trigger); }
    public void addPlayer (final Player player)   { players.add(player); }
    public void addBaggage(final Baggage baggage) { baggages.add(baggage); remainingBaggages++; }

    public void reset() {
        records.clear();
        resetWithoutRecords();
    }

    public void resetWithoutRecords() {
        for (final Movable movable : getMovables()) {
            movable.setPosition(movable.getOriginalPosition());
        }
        timeLastMove      = System.currentTimeMillis();
        moveCount         = 0;
        remainingBaggages = 0;
        hp                = 3;
        for (final Baggage baggage : getBaggages()) {
            if (!isGoalAt(baggage.getPosition())) {
                remainingBaggages++;
            }
        }
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
    public boolean movePlayerAndBaggage(final int playerIndex, final Point direction) {
        // 플레이어가 이동할 새로운 좌표 계산
        final Player player       = getPlayer(playerIndex);
        final Point  newPlayerPos = Point.add(player.getPosition(), direction);

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

        // 역방향 이동
        player.moveDelta(Point.reverse(direction));

        // 물건을 민 경우 물건도 역방향으로 이동시킨다
        if (record.isBaggageMoved()) {
            final Baggage baggage = getBaggageAt(Point.add(position, direction, direction));
            moveBaggage(baggage, Point.reverse(direction));
        }

        moveCount--;
    }

    /**
     * 해당 좌표에 벽이 있는지 확인합니다.
     *
     * @param  position 좌표
     * @return          벽의 존재 여부
     */
    private boolean isWallAt(final Point position) {
        for (final Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
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
            if (goal.getPosition().equals(position)) {
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
            if (player.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isTriggerAt(final Point position) {
    	for(final Trigger trigger: triggers) {
    		if(trigger.getPosition().equals(position)) {
    			return true;
    		}
    	}
		return false;
    }

    private Trigger getTriggerAt(final Point position) {
    	for(final Trigger trigger: triggers) {
    		if(trigger.getPosition().equals(position)) {
    			return trigger;
    		}
    	}
    	return null;
    }


    /**
     * 해당 좌표에 있는 물건 객체를 가져옵니다.
     *
     * @param  position 좌표
     * @return          물건 객체
     */
    private Baggage getBaggageAt(final Point position) {
        for (final Baggage baggage : baggages) {
            if (baggage.getPosition().equals(position)) {
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
            if (player.getPosition().equals(position)) {
                return player;
            }
        }
        return null;
    }
}
