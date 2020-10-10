package com.c0destudy.level;

import com.c0destudy.block.Baggage;
import com.c0destudy.block.Goal;
import com.c0destudy.block.Player;
import com.c0destudy.block.Wall;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LevelLoader
{
    /**
     * 파일로부터 레벨 인스턴스를 생성합니다.
     *
     * @param  filePath    레벨 데이터가 저장된 파일 경로
     * @return Level       레벨 인스턴스
     * @see    LevelSymbol
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
     * @see    LevelSymbol
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
                switch (line.charAt(x)) {
                    case LevelSymbol.WALL:
                        level.addWall(new Wall(x, y));
                        break;
                    case LevelSymbol.BAGGAGE:
                        level.addBaggage(new Baggage(x, y));
                        break;
                    case LevelSymbol.GOAL:
                        level.addGoal(new Goal(x, y));
                        break;
                    case LevelSymbol.PLAYER1:
                    case LevelSymbol.PLAYER2:
                        level.addPlayer(new Player(x, y));
                        break;
                    default:
                        break;
                }
            }
        }

        return level;
    }
}
