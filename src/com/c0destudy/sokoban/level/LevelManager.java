package com.c0destudy.sokoban.level;

import com.c0destudy.sokoban.misc.Point;
import com.c0destudy.sokoban.misc.Resource;
import com.c0destudy.sokoban.tile.Baggage;
import com.c0destudy.sokoban.tile.Goal;
import com.c0destudy.sokoban.tile.Player;
import com.c0destudy.sokoban.tile.Wall;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LevelManager
{
    // txt 파일에서 사용하는 레벨 기호 모음
    private static final char LEVEL_SYMBOL_WALL    = '#';
    private static final char LEVEL_SYMBOL_BAGGAGE = '$';
    private static final char LEVEL_SYMBOL_GOAL    = '.';
    private static final char LEVEL_SYMBOL_PLAYER  = '@';

    /**
     * 레벨 인스턴스를 생성합니다.
     *
     * @param  levelName 레벨 이름
     * @return Level     레벨 인스턴스
     */
    public static Level getNewLevel(final String levelName) {
        final Path    path    = Paths.get(String.format(Resource.PATH_LEVEL, levelName));
        final Charset charset = StandardCharsets.UTF_8;

        try {
            final List<String> lines = Files.readAllLines(path, charset);
            return getNewLevelFromStringList(levelName, lines);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 문자열 리스트로부터 레벨 인스턴스를 생성합니다.
     *
     * 레벨의 가로/세로 크기는 자동으로 계산됩니다.
     *
     * @param  levelName 레벨 이름
     * @param  levelData 레벨 데이터가 행별로 구분된 리스트
     * @return Level     레벨 인스턴스
     */
    private static Level getNewLevelFromStringList(final String levelName, final List<String> levelData) {
        // 레벨의 가로, 세로 크기 계산
        final int width = levelData
                .stream()
                .map(String::length)
                .reduce(0, Math::max);
        final int height = levelData.size();

        // 레밸 인스턴스 생성
        final Level level = new Level(levelName, width, height);

        // 각종 블럭 추가
        for (int y = 0; y < levelData.size(); y++) {
            final String line = levelData.get(y);
            for (int x = 0; x < line.length(); x++) {
                final Point point = new Point(x, y);
                switch (line.charAt(x)) {
                    case LEVEL_SYMBOL_WALL:
                        level.addWall(new Wall(point));
                        break;
                    case LEVEL_SYMBOL_BAGGAGE:
                        level.addBaggage(new Baggage(point));
                        break;
                    case LEVEL_SYMBOL_GOAL:
                        level.addGoal(new Goal(point));
                        break;
                    case LEVEL_SYMBOL_PLAYER:
                        level.addPlayer(new Player(point));
                        break;
                    default:
                        break;
                }
            }
        }

        return level;
    }

    /**
     * 레벨 인스턴스를 직렬화하여 파일로 저장합니다.
     *
     * @param  level    레벨 인스턴스
     * @param  filePath 파일 경로
     * @return boolean  성공 여부
     */
    public static boolean saveLevelToFile(final Level level, final String filePath) {
        try {
            final FileOutputStream     fos = new FileOutputStream(filePath);
            final BufferedOutputStream bos = new BufferedOutputStream(fos);
            final ObjectOutputStream   out = new ObjectOutputStream(bos);
            out.writeObject(level);
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 레벨 인스턴스를 파일에서 불러와 역직렬화합니다.
     *
     * @param  filePath 파일 경로
     * @return Level    레벨 인스턴스
     */
    public static Level readLevelFromFile(final String filePath) {
        try {
            final FileInputStream     fis   = new FileInputStream(filePath);
            final BufferedInputStream bis   = new BufferedInputStream(fis);
            final ObjectInputStream   in    = new ObjectInputStream(bis);
            final Level               level = (Level) in.readObject();
            in.close();
            return level;
        } catch (Exception e) {
            return null;
        }
    }
}
