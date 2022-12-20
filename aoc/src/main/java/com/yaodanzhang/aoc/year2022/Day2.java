package com.yaodanzhang.aoc.year2022;

import static com.google.common.collect.Lists.newArrayList;

import com.google.common.base.Splitter;
import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.input.AllLines;
import com.yaodanzhang.aoc.input.GroupByInLineSplitter;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Day2 implements Puzzle<Integer> {

  public static final Map<String, Shape> GIVEN_ENCODING = Map.of("A", Shape.ROCK, "B",
      Shape.PAPER, "C", Shape.SCISSORS);
  private final GroupByInLineSplitter input;

  Day2(Path inputFile) {
    this.input = new GroupByInLineSplitter(new AllLines(inputFile), Splitter.on(" "));
  }

  @Override
  public Integer part1Result() {
    Map<String, Shape> guess = Map.of("X", Shape.ROCK, "Y", Shape.PAPER, "Z", Shape.SCISSORS);

    return getResult(it -> {
      List<Shape> shapes = newArrayList();
      shapes.add(GIVEN_ENCODING.get(it.get(0)));
      shapes.add(guess.get(it.get(1)));
      return shapes;
    });
  }

  @Override
  public Integer part2Result() {
    Map<String, Function<Shape, Shape>> decideMyShape = Map.of(
        "X", opponent -> Shape.values()[(opponent.ordinal() + 3 - 1) % 3],
        "Y", opponent -> opponent,
        "Z", opponent -> Shape.values()[(opponent.ordinal() + 1) % 3]
    );

    return getResult(it -> {
      List<Shape> shapes = newArrayList();
      shapes.add(GIVEN_ENCODING.get(it.get(0)));
      shapes.add(decideMyShape.get(it.get(1)).apply(shapes.get(0)));
      return shapes;
    });
  }

  private Integer getResult(Function<List<String>, List<Shape>> shapeTransformer) {
    return input.read().stream()
        .map(shapeTransformer)
        .map(it -> it.get(1).score + it.get(1).round(it.get(0)))
        .reduce(Integer::sum)
        .orElse(0);
  }

  private enum Shape {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    private final int score;

    Shape(int score) {
      this.score = score;
    }

    int round(Shape opponent) {
      if (this == opponent) {
        return 3;
      }

      if (Math.abs(this.ordinal() - opponent.ordinal()) == 1) {
        return this.ordinal() - opponent.ordinal() > 0 ? 6 : 0;
      }

      if (this == ROCK) {
        return 6;
      }

      return 0;
    }

  }
}
