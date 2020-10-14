package com.c0destudy.sokoban.level;

import com.c0destudy.sokoban.misc.Point;
import com.c0destudy.sokoban.tile.*;

import java.util.ArrayList;

public class Level
{
    private final String             name;
    private final int                width;
    private final int                height;
    private final ArrayList<Wall>    walls    = new ArrayList<>();
    private final ArrayList<Goal>    goals    = new ArrayList<>();
    private final ArrayList<Baggage> baggages = new ArrayList<>();
    private final ArrayList<Trigger> triggers = new ArrayList<>();
    private final ArrayList<Player>  players  = new ArrayList<>();
    private int                      moveCount         = 0;
    private int                      remainingBaggages = 0;
    private int                      hp = 3;

    public Level(final String name, final int width, final int height) {
        this.name   = name;
        this.width  = width;
        this.height = height;
    }

    // 레벨 정보
    public String  getName()      { return name;   }
    public int     getWidth()     { return width;  }
    public int     getHeight()    { return height; }
    public int     getMoveCount() { return moveCount; }
    public int     getRemainingBaggages() { return remainingBaggages; }
    public int     getLeftHealth()        { return hp;     }
    public boolean isFailed()             { return hp == 0;}
    public boolean isCompleted()          { return remainingBaggages == 0; }

    // 타일
    public ArrayList<Wall>    getWalls()    { return walls;    }
    public ArrayList<Goal>    getGoals()    { return goals;    }
    public ArrayList<Baggage> getBaggages() { return baggages; }
    public ArrayList<Player>  getPlayers()  { return players;  }
    public ArrayList<Trigger> getTriggers() { return triggers; }
    public ArrayList<Tile>    getAllTiles() {
        final ArrayList<Tile> tiles = new ArrayList<>();
        tiles.addAll(walls);
        tiles.addAll(goals);
        tiles.addAll(baggages);
        tiles.addAll(triggers);
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
    public void addWall   (final Wall wall)       { walls.add(wall);       }
    public void addGoal   (final Goal goal)       { goals.add(goal);       }
    public void addTrigger(final Trigger trigger) { triggers.add(trigger); }
    public void addPlayer (final Player player)   { players.add(player);   }
    public void addBaggage(final Baggage baggage) { baggages.add(baggage); remainingBaggages++; }

    /**
     * 플레이어의 좌표 변화량을 입력받아 플레이어를 이동하고,
     * 물건을 미는 경우 물건도 이동시킵니다.
     *
     * 이동하는 방향에 물건이 있으면 물건도 같이 이동시킵니다.
     * 단, 물건이 연속으로 2개 있거나 벽이 있는 경우에는 이동할 수 없습니다.
     *
     * @param  playerIndex 이동할 플레이어 번호 (0-based)
     * @param  delta       플레이어의 좌표 변화량
     * @return             이동 여부 (이동 실패시 false)
     */
    public boolean movePlayerAndBaggage(final int playerIndex, final Point delta) {
        // 플레이어가 이동할 새로운 좌표 계산
        final Player player       = getPlayer(playerIndex);
        final Point  newPlayerPos = Point.add(player.getPoint(), delta);

        // 이동할 위치에 벽이 있는 경우 => 이동 불가
        if (isWallAt(newPlayerPos)) return false;

        // 이동할 위치에 다른 플레이어가 있는 경우 => 이동 불가
        if (isPlayerAt(newPlayerPos)) return false;
        
        Trigger onTrigger = PlayerOnTrigger(newPlayerPos);
        if (onTrigger!= null) {
        	if(isTriggerAt(onTrigger.getPoint()))  hp--;
        }

        // 플레이어가 물건(baggage)을 미는 경우
        Baggage nearBaggage = getBaggageAt(newPlayerPos);
        if (nearBaggage != null) {
            // 물건이 이동될 새로운 좌표 계산
            final Point newBaggagePos = Point.add(nearBaggage.getPoint(), delta);

            // 물건이 이동될 위치에 벽이 있는 경우 => 이동 불가
            if (isWallAt(newBaggagePos)) return false;

            // 물건이 이동될 위치에 물건이 있는 경우 (연속 2개) => 이동 불가
            if (getBaggageAt(newBaggagePos) != null) return false;

            // 물건이 이동될 위치에 다른 플레이어가 있는 경우 => 이동 불가
            if (isPlayerAt(newBaggagePos)) return false;

            // 이동 전에 물건이 목적지에 있는 경우
            if (isGoalAt(nearBaggage.getPoint())) remainingBaggages++;

            // 물건을 이동시킬 수 있는 공간이 있으면 물건을 민다
            nearBaggage.moveDelta(delta);

            // 이동 후에 물건이 목적지에 있는 경우
            if (isGoalAt(nearBaggage.getPoint())) remainingBaggages--;
            
        }

        // 플레이어 이동
        player.moveDelta(delta);
        moveCount++;
        return true;
    }

    /**
     * 해당 좌표에 벽이 있는지 확인합니다.
     *
     * @param  point 좌표
     * @return       벽의 존재 여부
     */
    private boolean isWallAt(final Point point) {
        for (final Wall wall: walls) {
            if (wall.isLocatedAt(point)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 해당 좌표에 목적지가 있는지 확인합니다.
     *
     * @param  point 좌표
     * @return       목적지의 존재 여부
     */
    private boolean isGoalAt(final Point point) {
        for (final Goal goal: goals) {
            if (goal.isLocatedAt(point)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 해당 좌표에 플레이어가 있는지 확인합니다.
     *
     * @param  point 좌표
     * @return       플레이어의 존재 여부
     */
    private boolean isPlayerAt(final Point point) {
        for (final Player player: players) {
            if (player.isLocatedAt(point)) {
            	
                return true;
            }
        }
        return false;
    }
    
    private boolean isTriggerAt(final Point point) {
    	for(final Trigger trigger: triggers) {
    		if(trigger.isLocatedAt(point)) {
    			
    			return true;
    		}
    	}
		return false;
    }

    private Trigger PlayerOnTrigger(final Point point) {
    	for(final Trigger trigger:triggers) {
    		if(trigger.isLocatedAt(point)) {
    			return trigger;
    		}
    	}
    	return null;
    }


    /**
     * 해당 좌표에 있는 물건 객체를 가져옵니다.
     *
     * @param  point 좌표
     * @return       물건 객체 (없을 경우 null)
     */
    private Baggage getBaggageAt(final Point point) {
        for (final Baggage baggage: baggages) {
            if (baggage.isLocatedAt(point)) {
                return baggage;
            }
        }
        return null;
    }
 
}
