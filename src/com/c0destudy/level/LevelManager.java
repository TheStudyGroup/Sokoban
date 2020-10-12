package com.c0destudy.level;

import com.c0destudy.misc.Point;
import com.c0destudy.tile.Baggage;
import com.c0destudy.tile.Goal;
import com.c0destudy.tile.Player;
import com.c0destudy.tile.Wall;
import com.c0destudy.tile.Trigger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LevelManager
{
    // txt �뙆�씪�뿉�꽌 �궗�슜�븯�뒗 �젅踰� 湲고샇 紐⑥쓬
    public static final char WALL    = '#';
    public static final char BAGGAGE = '$';
    public static final char GOAL    = '.';
    public static final char Trigger = '!';
    public static final char PLAYER1 = '@';
    public static final char PLAYER2 = '%';

    /**
     * �뙆�씪濡쒕��꽣 �젅踰� �씤�뒪�꽩�뒪瑜� �깮�꽦�빀�땲�떎.
     *
     * @param  filePath    �젅踰� �뜲�씠�꽣媛� ���옣�맂 �뙆�씪 寃쎈줈
     * @return Level       �젅踰� �씤�뒪�꽩�뒪
     */
    public static Level loadLevelFromFile(final String filePath) {
        final Path    path    = Paths.get(filePath);
        final Charset charset = StandardCharsets.UTF_8;

        try {
            final List<String> lines = Files.readAllLines(path, charset);
            return loadLevelFromStringList(lines);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 臾몄옄�뿴 由ъ뒪�듃濡쒕��꽣 �젅踰� �씤�뒪�꽩�뒪瑜� �깮�꽦�빀�땲�떎.
     *
     * �젅踰⑥쓽 媛�濡�/�꽭濡� �겕湲곕뒗 �옄�룞�쑝濡� 怨꾩궛�맗�땲�떎.
     *
     * @param  levelData   �젅踰� �뜲�씠�꽣媛� �뻾蹂꾨줈 援щ텇�맂 由ъ뒪�듃
     * @return Level       �젅踰� �씤�뒪�꽩�뒪
     */
    public static Level loadLevelFromStringList(final List<String> levelData) {
        // �젅踰⑥쓽 媛�濡�, �꽭濡� �겕湲� 怨꾩궛
        final int width = levelData
                .stream()
                .map(String::length)
                .reduce(0, Math::max);
        final int height = levelData.size();

        // �젅諛� �씤�뒪�꽩�뒪 �깮�꽦
        final Level level = new Level(width, height);

        // 媛곸쥌 釉붾윮 異붽�
        for (int y = 0; y < levelData.size(); y++) {
            final String line = levelData.get(y);
            for (int x = 0; x < line.length(); x++) {
                final Point point = new Point(x, y);
                switch (line.charAt(x)) {
                    case WALL:
                        level.addWall(new Wall(point));
                        break;
                    case BAGGAGE:
                        level.addBaggage(new Baggage(point));
                        break;
                    case GOAL:
                        level.addGoal(new Goal(point));
                        break;
                    case Trigger:
                    	level.addTrigger(new Trigger(point));
                    	break;
                    case PLAYER1:
                    case PLAYER2:
                        level.addPlayer(new Player(point));
                        break;
                    default:
                        break;
                }
            }
        }

        return level;
    }

    // TODO
    public static boolean saveLevelToFile(final String filePath, final Level level) {
        return true;
    }
}
