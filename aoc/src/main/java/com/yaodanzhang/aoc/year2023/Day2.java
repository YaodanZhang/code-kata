package com.yaodanzhang.aoc.year2023;

import static com.google.common.collect.Maps.newHashMap;

import com.google.common.base.Splitter;
import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.input.AllLines;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Day2 implements Puzzle<Integer> {

  private final List<String> input;

  Day2(Path inputFile) {
    this.input = new AllLines(inputFile).read();
  }

  @Override
  public Integer part1Result() {
    return input.stream()
        .map(Game::create)
        .filter(game -> game.results.stream()
            .filter(
                it -> it.get(Cube.RED) > 12 || it.get(Cube.GREEN) > 13 || it.get(Cube.BLUE) > 14)
            .findAny()
            .isEmpty())
        .map(Game::gameId)
        .reduce(Integer::sum)
        .orElse(0);
  }

  @Override
  public Integer part2Result() {
    return input.stream()
        .map(Game::create)
        .map(Game::results)
        .map(game -> {
          Map<Cube, Integer> result = Game.initRound();
          game.stream()
              .flatMap(it -> it.entrySet().stream())
              .forEach(it -> {
                if (result.get(it.getKey()) < it.getValue()) {
                  result.put(it.getKey(), it.getValue());
                }
              });
          return result;
        })
        .map(it -> it.values().stream()
            .reduce((a, b) -> a * b)
            .orElse(0))
        .reduce(Integer::sum)
        .orElse(0);
  }

  private record Game(int gameId, List<Map<Cube, Integer>> results) {
      private static final Splitter GAME_SPLITTER = splitterOf(':');
      private static final Splitter SET_SPLITTER = splitterOf(';');
      private static final Splitter CUBE_SPLITTER = splitterOf(',');

    @SuppressWarnings("UnstableApiUsage")
      public static Game create(String input) {
        List<String> game = GAME_SPLITTER.splitToList(input);
        int gameId = Integer.parseInt(splitterOf(' ').splitToList(game.get(0)).get(1));
        List<Map<Cube, Integer>> results = SET_SPLITTER.splitToStream(game.get(1))
            .map(set -> {
              Map<Cube, Integer> round = initRound();
              CUBE_SPLITTER.splitToStream(set)
                  .forEach(it -> {
                    List<String> itemInput = splitterOf(' ').splitToList(it);
                    round.put(
                        Cube.valueOf(itemInput.get(1).toUpperCase()),
                        Integer.parseInt(itemInput.get(0))
                    );
                  });
              return round;
            })
            .toList();
        return new Game(gameId, results);
      }

      private static Splitter splitterOf(char separator) {
        return Splitter.on(separator).trimResults().omitEmptyStrings();
      }

      private static Map<Cube, Integer> initRound() {
        Map<Cube, Integer> round = newHashMap();
        Arrays.stream(Cube.values()).forEach(it -> round.put(it, 0));
        return round;
      }
    }

  private enum Cube {
    BLUE, RED, GREEN
  }
}
