package com.yaodanzhang.aoc.year2022;

import static com.google.common.base.Splitter.on;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.intersection;
import static java.lang.Integer.parseInt;
import static java.util.Set.copyOf;
import static java.util.stream.IntStream.range;

import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.input.AllLines;
import com.yaodanzhang.aoc.input.GroupByInLineSplitter;
import com.yaodanzhang.aoc.input.Pair;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Day4 implements Puzzle<Integer> {

  private final GroupByInLineSplitter input;

  Day4(Path inputFile) {
    this.input = new GroupByInLineSplitter(new AllLines(inputFile), on(","));
  }

  @Override
  public Integer part1Result() {
    return (int) getAssignmentPairs()
        .filter(it -> it.left().containsAll(it.right()) || it.right().containsAll(it.left()))
        .count();
  }

  private List<Integer> assignmentOf(String assignment) {
    List<String> positions = newArrayList(
        on("-").trimResults().omitEmptyStrings().split(assignment));
    return range(parseInt(positions.get(0)), parseInt(positions.get(1)) + 1)
        .boxed().toList();
  }

  @Override
  public Integer part2Result() {
    return (int) getAssignmentPairs()
        .filter(it -> !intersection(it.left(), it.right()).isEmpty())
        .count();

  }

  private Stream<Pair<Set<Integer>, Set<Integer>>> getAssignmentPairs() {
    return input.read()
        .stream()
        .map(it -> new Pair<>(assignmentOf(it.get(0)), assignmentOf(it.get(1))))
        .map(it -> new Pair<>(copyOf(it.left()), copyOf(it.right())));
  }
}
