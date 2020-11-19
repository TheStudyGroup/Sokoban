package com.c0destudy.sokoban.level;

import com.c0destudy.sokoban.resource.Resource;
import com.c0destudy.sokoban.tile.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LevelManager
{
    // txt 파일에서 사용하는 레벨 기호 모음
    private static final char LEVEL_SYMBOL_SPACE           = ' ';
    private static final char LEVEL_SYMBOL_WALL            = '#';
    private static final char LEVEL_SYMBOL_BAGGAGE         = '$';
    private static final char LEVEL_SYMBOL_BAGGAGE_AT_GOAL = '*';
    private static final char LEVEL_SYMBOL_GOAL            = '.';
    private static final char LEVEL_SYMBOL_TRIGGER         = '!';
    private static final char LEVEL_SYMBOL_PLAYER          = '@';
    private static final char LEVEL_SYMBOL_PLAYER_AT_GOAL  = '+';

    /**
     * 빈 레벨 인스턴스를 생성합니다.
     *
     * @return Level 레벨 인스턴스
     */
    public static Level createEmptyLevel() {
        return new Level("MyLevel", 15, 15, 100);
    }

    /**
     * 레벨 인스턴스를 생성합니다.
     *
     * @param  levelName 레벨 이름
     * @return Level     레벨 인스턴스
     */
    public static Level createLevelFromFile(final String levelName) {
        final Path    path    = Paths.get(Resource.getLevelPath(levelName));
        final Charset charset = StandardCharsets.UTF_8;
        try {
            final List<String> lines        = Files.readAllLines(path, charset);
            final int          minMoveCount = Integer.parseInt(lines.remove(0));
            return createLevelFromStringList(levelName, lines, minMoveCount);
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
    private static Level createLevelFromStringList(final String levelName, final List<String> levelData, final int minMoveCount) {
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
                        level.addTile(new Wall(point));
                        break;
                    case LEVEL_SYMBOL_BAGGAGE:
                        level.addTile(new Baggage(point));
                        break;
                    case LEVEL_SYMBOL_BAGGAGE_AT_GOAL:
                        level.addTile(new Baggage(point));
                        level.addTile(new Goal(new Point(point)));
                        break;
                    case LEVEL_SYMBOL_GOAL:
                        level.addTile(new Goal(point));
                        break;
                    case LEVEL_SYMBOL_TRIGGER:
                        level.addTile(new Trigger(point));
                        break;
                    case LEVEL_SYMBOL_PLAYER:
                        level.addTile(new Player(point));
                        break;
                    case LEVEL_SYMBOL_PLAYER_AT_GOAL:
                        level.addTile(new Player(point));
                        level.addTile(new Goal(new Point(point)));
                        break;
                    default:
                        break;
                }
            }
        }

        return level;
    }

    private static char getLevelSymbolAt(final Level level, final int x, final int y) {
        final Point p = new Point(x, y);
        if (level.isWallAt(p))    return LEVEL_SYMBOL_WALL;
        if (level.isTriggerAt(p)) return LEVEL_SYMBOL_TRIGGER;
        if (level.isBaggageAt(p)) return level.isGoalAt(p) ? LEVEL_SYMBOL_BAGGAGE_AT_GOAL : LEVEL_SYMBOL_BAGGAGE;
        if (level.isPlayerAt(p))  return level.isGoalAt(p) ? LEVEL_SYMBOL_PLAYER_AT_GOAL  : LEVEL_SYMBOL_PLAYER;
        if (level.isGoalAt(p))    return LEVEL_SYMBOL_GOAL;
        return LEVEL_SYMBOL_SPACE;
    }

    private static String convertLevelToString(final Level level) {
        final StringBuilder lines = new StringBuilder();
        for (int y = 0; y < level.getHeight(); y++) {
            final StringBuilder line = new StringBuilder();
            for (int x = 0; x < level.getWidth(); x++) {
                line.append(getLevelSymbolAt(level, x, y));
            }
            lines.append(trimRight(line.toString()));
            lines.append("\n");
        }
        return trimVertical(lines.toString());
    }

    public static boolean saveLevelToTextFile(final Level level) {
        try {
            final OutputStream output    = new FileOutputStream(Resource.getLevelPath(level.getName()));
            final String       levelData = level.getDifficulty() + "\n" + convertLevelToString(level);
            output.write(levelData.getBytes());
        } catch (Exception e) {
            e.getStackTrace();
            return false;
        }
        return true;
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


    // String Helper
    public static String trimLeft    (final String string) { return string.replaceAll("^\\s+",""); }
    public static String trimRight   (final String string) { return string.replaceAll("\\s+$",""); }
    public static String trimVertical(final String string) {
        final String[] lines = string.split("\\n");
        int start = 0, end = lines.length - 1;
        for (; start < lines.length; start++) {
            if (!lines[start].trim().equals("")) break;
        }
        for (; end >= 0; end--) {
            if (!lines[end].trim().equals("")) break;
        }
        final StringBuilder result = new StringBuilder();
        for (int i = start; i <= end; i++) {
            result.append(lines[i]);
            if (i != end) {
                result.append("\n");
            }
        }
        return result.toString();
    }
}
