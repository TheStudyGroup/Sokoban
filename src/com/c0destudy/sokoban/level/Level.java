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
     * �뵆�젅�씠�뼱�쓽 醫뚰몴 蹂��솕�웾�쓣 �엯�젰諛쏆븘 �뵆�젅�씠�뼱瑜� �씠�룞�븯怨�,
     * 臾쇨굔�쓣 誘몃뒗 寃쎌슦 臾쇨굔�룄 �씠�룞�떆�궢�땲�떎.
     *
     * �씠�룞�븯�뒗 諛⑺뼢�뿉 臾쇨굔�씠 �엳�쑝硫� 臾쇨굔�룄 媛숈씠 �씠�룞�떆�궢�땲�떎.
     * �떒, 臾쇨굔�씠 �뿰�냽�쑝濡� 2媛� �엳嫄곕굹 踰쎌씠 �엳�뒗 寃쎌슦�뿉�뒗 �씠�룞�븷 �닔 �뾾�뒿�땲�떎.
     *
     * @param  playerIndex �씠�룞�븷 �뵆�젅�씠�뼱 踰덊샇 (0-based)
     * @param  delta       �뵆�젅�씠�뼱�쓽 醫뚰몴 蹂��솕�웾
     * @return             �씠�룞 �뿬遺� (�씠�룞 �떎�뙣�떆 false)
     */
    public boolean movePlayerAndBaggage(final int playerIndex, final Point delta) {
        // �뵆�젅�씠�뼱媛� �씠�룞�븷 �깉濡쒖슫 醫뚰몴 怨꾩궛
        final Player player       = getPlayer(playerIndex);
        final Point  newPlayerPos = Point.add(player.getPoint(), delta);

        // �씠�룞�븷 �쐞移섏뿉 踰쎌씠 �엳�뒗 寃쎌슦 => �씠�룞 遺덇�
        if (isWallAt(newPlayerPos)) return false;

        // �씠�룞�븷 �쐞移섏뿉 �떎瑜� �뵆�젅�씠�뼱媛� �엳�뒗 寃쎌슦 => �씠�룞 遺덇�
        if (isPlayerAt(newPlayerPos)) return false;
        
        Trigger onTrigger = PlayerOnTrigger(newPlayerPos);
        if (onTrigger!= null) {
        	if(isTriggerAt(onTrigger.getPoint()))  hp--;
        }

        // �뵆�젅�씠�뼱媛� 臾쇨굔(baggage)�쓣 誘몃뒗 寃쎌슦
        Baggage nearBaggage = getBaggageAt(newPlayerPos);
        if (nearBaggage != null) {
            // 臾쇨굔�씠 �씠�룞�맆 �깉濡쒖슫 醫뚰몴 怨꾩궛
            final Point newBaggagePos = Point.add(nearBaggage.getPoint(), delta);

            // 臾쇨굔�씠 �씠�룞�맆 �쐞移섏뿉 踰쎌씠 �엳�뒗 寃쎌슦 => �씠�룞 遺덇�
            if (isWallAt(newBaggagePos)) return false;

            // 臾쇨굔�씠 �씠�룞�맆 �쐞移섏뿉 臾쇨굔�씠 �엳�뒗 寃쎌슦 (�뿰�냽 2媛�) => �씠�룞 遺덇�
            if (getBaggageAt(newBaggagePos) != null) return false;

            // 臾쇨굔�씠 �씠�룞�맆 �쐞移섏뿉 �떎瑜� �뵆�젅�씠�뼱媛� �엳�뒗 寃쎌슦 => �씠�룞 遺덇�
            if (isPlayerAt(newBaggagePos)) return false;

            // �씠�룞 �쟾�뿉 臾쇨굔�씠 紐⑹쟻吏��뿉 �엳�뒗 寃쎌슦
            if (isGoalAt(nearBaggage.getPoint())) remainingBaggages++;

            // 臾쇨굔�쓣 �씠�룞�떆�궗 �닔 �엳�뒗 怨듦컙�씠 �엳�쑝硫� 臾쇨굔�쓣 誘쇰떎
            nearBaggage.moveDelta(delta);

            // �씠�룞 �썑�뿉 臾쇨굔�씠 紐⑹쟻吏��뿉 �엳�뒗 寃쎌슦
            if (isGoalAt(nearBaggage.getPoint())) remainingBaggages--;
            
        }

        // �뵆�젅�씠�뼱 �씠�룞
        player.moveDelta(delta);
        moveCount++;
        return true;
    }

    /**
     * �빐�떦 醫뚰몴�뿉 踰쎌씠 �엳�뒗吏� �솗�씤�빀�땲�떎.
     *
     * @param  point 醫뚰몴
     * @return       踰쎌쓽 議댁옱 �뿬遺�
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
     * �빐�떦 醫뚰몴�뿉 紐⑹쟻吏�媛� �엳�뒗吏� �솗�씤�빀�땲�떎.
     *
     * @param  point 醫뚰몴
     * @return       紐⑹쟻吏��쓽 議댁옱 �뿬遺�
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
     * �빐�떦 醫뚰몴�뿉 �뵆�젅�씠�뼱媛� �엳�뒗吏� �솗�씤�빀�땲�떎.
     *
     * @param  point 醫뚰몴
     * @return       �뵆�젅�씠�뼱�쓽 議댁옱 �뿬遺�
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
     * �빐�떦 醫뚰몴�뿉 �엳�뒗 臾쇨굔 媛앹껜瑜� 媛��졇�샃�땲�떎.
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
