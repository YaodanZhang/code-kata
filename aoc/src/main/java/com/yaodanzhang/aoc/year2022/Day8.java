package com.yaodanzhang.aoc.year2022;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Comparator.naturalOrder;

import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.input.AllLines;
import com.yaodanzhang.aoc.input.MatrixDigits;
import com.yaodanzhang.aoc.input.Pair;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class Day8 implements Puzzle<Integer> {

  public static final List<Pair<Function<Integer, Integer>, Function<Integer, Integer>>> DIRECTIONS = List.of(
      new Pair<>(it -> it - 1, it -> it),
      new Pair<>(it -> it, it -> it - 1),
      new Pair<>(it -> it + 1, it -> it),
      new Pair<>(it -> it, it -> it + 1)
  );
  private final List<List<Integer>> input;

  Day8(Path inputFile) {
    this.input = new MatrixDigits(new AllLines(inputFile)).read();
  }

  @Override
  public Integer part1Result() {
    int count = 0;
    for (int i = 1; i < input.size() - 1; i++) {
      for (int j = 1; j < input.get(i).size() - 1; j++) {
        if (visible(input, i, j)) {
          count++;
        }
      }
    }
    return count + input.size() * 2 + input.get(0).size() * 2 - 4;
  }

  private boolean visible(List<List<Integer>> matrix, int y, int x) {
    return DIRECTIONS.stream()
        .anyMatch(
            it -> visible(matrix,
                matrix.get(y).get(x),
                it.left().apply(y),
                it.right().apply(x),
                it));
  }

  private boolean visible(List<List<Integer>> matrix,
      int item,
      int y,
      int x,
      Pair<Function<Integer, Integer>, Function<Integer, Integer>> direction) {

    if (y >= matrix.size() || y < 0 || x >= matrix.get(y).size() || x < 0) {
      return true;
    }

    if (matrix.get(y).get(x) >= item) {
      return false;
    }

    return visible(matrix, item, direction.left().apply(y), direction.right().apply(x), direction);
  }

  @Override
  public Integer part2Result() {
    List<List<Integer>> scores = newArrayList();
    for (int i = 1; i < input.size() - 1; i++) {
      List<Integer> row = newArrayList();
      for (int j = 1; j < input.get(i).size() - 1; j++) {
        row.add(calculateScenicScore(input, i, j));
      }
      scores.add(row);
    }
    return scores.stream()
        .flatMap(Collection::stream)
        .max(naturalOrder())
        .orElse(0);

  }

  private int calculateScenicScore(List<List<Integer>> matrix, int y, int x) {
    return DIRECTIONS.stream()
        .map(it -> visibleWithCounter(matrix,
            matrix.get(y).get(x),
            it.left().apply(y),
            it.right().apply(x),
            0,
            it))
        .reduce((a, b) -> a * b)
        .orElse(0);
  }

  private int visibleWithCounter(List<List<Integer>> matrix,
      int item,
      int y,
      int x,
      int counter,
      Pair<Function<Integer, Integer>, Function<Integer, Integer>> direction) {

    if (y >= matrix.size() || y < 0 || x >= matrix.get(y).size() || x < 0) {
      return counter;
    }

    if (matrix.get(y).get(x) >= item) {
      return counter + 1;
    }

    return visibleWithCounter(matrix, item, direction.left().apply(y), direction.right().apply(x), counter + 1, direction);
  }
}
