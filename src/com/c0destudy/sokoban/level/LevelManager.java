package com.c0destudy.sokoban.level;

import com.c0destudy.sokoban.misc.Point;
import com.c0destudy.sokoban.misc.Resource;
import com.c0destudy.sokoban.tile.Baggage;
import com.c0destudy.sokoban.tile.Goal;
import com.c0destudy.sokoban.tile.Player;
import com.c0destudy.sokoban.tile.Wall;
import com.c0destudy.sokoban.tile.Trigger;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LevelManager
{
    // txt 파일에서 사용하는 레벨 기호 모음
    private static final char LEVEL_SYMBOL_WALL            = '#';
    private static final char LEVEL_SYMBOL_BAGGAGE         = '$';
    private static final char LEVEL_SYMBOL_BAGGAGE_AT_GOAL = '*';
    private static final char LEVEL_SYMBOL_GOAL            = '.';
    private static final char LEVEL_SYMBOL_PLAYER          = '@';
    private static final char LEVEL_SYMBOL_PLAYER_AT_GOAL  = '+';
    private static final char LEVEL_SYMBOL_TRIGGER         = '!';

    /**
     * 빈 레벨 인스턴스를 생성합니다.
     *
     * @return Level     레벨 인스턴스
     */
    public static Level createEmptyLevel() {
        return new Level("empty", 20, 20, 0);
    }

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
            final List<String> lines        = Files.readAllLines(path, charset);
            final int          minMoveCount = Integer.parseInt(lines.remove(0));
            return getNewLevelFromStringList(levelName, lines, minMoveCount);
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
    private static Level getNewLevelFromStringList(final String       levelName,
                                                   final List<String> levelData,
                                                   final int          minMoveCount
    ) {
        // 레벨의 가로, 세로 크기 계산
        final int width = levelData
                .stream()
                .map(String::length)
                .reduce(0, Math::max);
        final int height = levelData.size();

        // 레밸 인스턴스 생성
        final Level level = new Level(levelName, width, height, minMoveCount);

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
                    case LEVEL_SYMBOL_BAGGAGE_AT_GOAL:
                        level.addBaggage(new Baggage(point));
                        level.addGoal(new Goal(new Point(point)));
                        break;
                    case LEVEL_SYMBOL_GOAL:
                        level.addGoal(new Goal(point));
                        break;
                    case LEVEL_SYMBOL_PLAYER:
                        level.addPlayer(new Player(point));
                        break;
                    case LEVEL_SYMBOL_PLAYER_AT_GOAL:
                        level.addPlayer(new Player(point));
                        level.addGoal(new Goal(new Point(point)));
                        break;
                    case LEVEL_SYMBOL_TRIGGER:
                    	level.addTrigger(new Trigger(point));
                    	break;
                    default:
                        break;
                }
            }
        }

        return level;
    }

    /**
     * 일시 정지된 레벨 데이터가 존재하는지 확인합니다.
     *
     * @return boolean 존재 여부
     */
    public static boolean isPausedLevelExisting() {
        return (new File(Resource.PATH_LEVEL_PAUSE)).exists();
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

    public static int getBestScore(final String levelName) {
        return getBestScores().getOrDefault(levelName, 0);
    }

    public static Map<String, Integer> getBestScores() {
        final Path    path    = Paths.get(Resource.PATH_LEVEL_BEST_SCORES);
        final Charset charset = StandardCharsets.UTF_8;
        try {
            final List<String> lines = Files.readAllLines(path, charset);
            return lines
                    .stream()
                    .filter(e -> e.contains("="))
                    .map(e -> e.split("="))
                    .map(e -> Stream.of(e)
                        .map(String::trim)
                        .toArray(String[]::new))
                    .collect(Collectors.toMap(e -> e[0], e -> Integer.parseInt(e[1])));
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    public static boolean setBestScore(final String levelName, final int score) {
        final Map<String, Integer> scores = getBestScores();
        scores.put(levelName, score);
        final String newScores = scores
                .entrySet()
                .stream()
                .map(Objects::toString)
                .reduce("", (a, b) -> a + b + "\n");
        try {
            final FileWriter     file   = new FileWriter(Resource.PATH_LEVEL_BEST_SCORES);
            final BufferedWriter writer = new BufferedWriter(file);
            writer.write(newScores);
            writer.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static ArrayList<String> getLevelList() {
        final ArrayList<String> levels    = new ArrayList<>();
        final File              directory = new File(Resource.PATH_LEVEL_ROOT);
        final String[]          files     = directory.list();

        if (files != null && files.length > 0) {
            Arrays
                .stream(files)
                .filter(e -> e.contains(".txt"))
                .map(e -> e.substring(0, e.lastIndexOf(".")))
                .forEach(levels::add);
        }

        return levels;
    }
}
