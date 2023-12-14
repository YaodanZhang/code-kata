package com.yaodanzhang.aoc.year2023;

import com.google.common.base.Splitter;
import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.input.AllLines;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 implements Puzzle<Integer> {

  private final List<String> input;

  Day4(Path inputFile) {
    this.input = new AllLines(inputFile).read();
  }

  @Override
  public Integer part1Result() {
    return input.stream()
        .map(Game::create)
        .map(Game::matched)
        .map(it -> it.stream()
            .reduce(0, (points, item) -> points == 0 ? 1 : points * 2))
        .reduce(Integer::sum)
        .orElse(0);
  }

  @Override
  public Integer part2Result() {
    List<Game> games = input.stream()
        .map(Game::create)
        .toList();
    Map<Integer, List<Integer>> cardsWithDirectChildren = games.stream()
        .collect(Collectors.toMap(
            it -> it.gameId,
            it -> IntStream.range(it.gameId + 1, it.matched().size() + it.gameId + 1)
                .filter(card -> card <= games.size())
                .boxed()
                .toList()
        ));
    Map<Integer, Integer> count = games.stream()
        .collect(Collectors.toMap(it -> it.gameId, it -> 0));
    cardsWithDirectChildren.forEach((key, value) -> {
      count.put(key, count.get(key) + 1);
      value.forEach(child -> count.put(child, count.get(child) + 1));
    });
    games.forEach(game -> cardsWithDirectChildren.get(game.gameId)
        .forEach(it -> count.put(it, count.get(it) + (count.get(game.gameId) - 1))));
    return count.values()
        .stream()
        .reduce(Integer::sum)
        .orElse(0);
  }

  private record Game(int gameId, List<Integer> winningNumbers, List<Integer> numbers) {

    private static final Splitter GAME_SPLITTER = splitterOf(':');
    private static final Splitter SET_SPLITTER = splitterOf('|');
    private static final Splitter NUMBER_SPLITTER = splitterOf(' ');

    @SuppressWarnings("UnstableApiUsage")
    public static Game create(String input) {
      List<String> game = GAME_SPLITTER.splitToList(input);
      int gameId = Integer.parseInt(NUMBER_SPLITTER.splitToList(game.get(0)).get(1));

      List<List<Integer>> allNumbers = SET_SPLITTER.splitToStream(game.get(1))
          .map(it -> NUMBER_SPLITTER.splitToStream(it)
              .map(Integer::valueOf)
              .toList())
          .toList();

      return new Game(gameId, allNumbers.get(0), allNumbers.get(1));
    }

    private static Splitter splitterOf(char separator) {
      return Splitter.on(separator).trimResults().omitEmptyStrings();
    }

    private List<Integer> matched() {
      return numbers.stream()
          .filter(this.winningNumbers::contains)
          .toList();
    }
  }
}