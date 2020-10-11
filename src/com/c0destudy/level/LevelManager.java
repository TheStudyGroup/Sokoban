package com.c0destudy.level;

import com.c0destudy.misc.Point;
import com.c0destudy.tile.Baggage;
import com.c0destudy.tile.Goal;
import com.c0destudy.tile.Player;
import com.c0destudy.tile.Wall;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LevelManager
{
    // txt 파일에서 사용하는 레벨 기호 모음
    public static final char WALL    = '#';
    public static final char BAGGAGE = '$';
    public static final char GOAL    = '.';
    public static final char PLAYER1 = '@';
    public static final char PLAYER2 = '%';

    /**
     * 파일로부터 레벨 인스턴스를 생성합니다.
     *
     * @param  filePath    레벨 데이터가 저장된 파일 경로
     * @return Level       레벨 인스턴스
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
     * 문자열 리스트로부터 레벨 인스턴스를 생성합니다.
     *
     * 레벨의 가로/세로 크기는 자동으로 계산됩니다.
     *
     * @param  levelData   레벨 데이터가 행별로 구분된 리스트
     * @return Level       레벨 인스턴스
     */
    public static Level loadLevelFromStringList(final List<String> levelData) {
        // 레벨의 가로, 세로 크기 계산
        final int width = levelData
                .stream()
                .map(String::length)
                .reduce(0, Math::max);
        final int height = levelData.size();

        // 레밸 인스턴스 생성
        final Level level = new Level(width, height);

        // 각종 블럭 추가
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
