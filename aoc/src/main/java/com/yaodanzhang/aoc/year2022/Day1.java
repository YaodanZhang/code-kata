package com.yaodanzhang.aoc.year2022;

import static java.util.Comparator.comparingInt;
import static java.util.Comparator.reverseOrder;

import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.input.AllLines;
import com.yaodanzhang.aoc.input.GroupByEmptyLine;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Day1 implements Puzzle<Integer> {

  private final GroupByEmptyLine input;

  Day1(Path inputFile) {
    this.input = new GroupByEmptyLine(new AllLines(inputFile));
  }

  private Stream<Integer> sortCaloriesDesc(GroupByEmptyLine input) {
    return input.read().stream()
        .map(it -> it.stream()
            .map(Integer::parseInt)
            .reduce(Integer::sum)
            .orElse(0))
        .sorted(comparingInt(a -> a))
        .sorted(reverseOrder());
  }

  @Override
  public Integer part1Result() {
    return sortCaloriesDesc(input)
        .findFirst()
        .orElse(0);
  }

  @Override
  public Integer part2Result() {
    return sortCaloriesDesc(input)
        .limit(3)
        .reduce(Integer::sum)
        .orElse(0);
  }
}
