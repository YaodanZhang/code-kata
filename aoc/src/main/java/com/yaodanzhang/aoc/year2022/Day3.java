package com.yaodanzhang.aoc.year2022;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.intersection;
import static com.google.common.collect.Sets.newHashSet;

import com.google.common.collect.Sets;
import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.input.AllLines;
import com.yaodanzhang.aoc.input.GroupByChar;
import com.yaodanzhang.aoc.input.GroupByLineCount;
import com.yaodanzhang.aoc.input.Pair;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day3 implements Puzzle<Integer> {

  private final HashMap<Character, Integer> priorities;
  private final GroupByChar input;

  Day3(Path inputFile) {
    this.priorities = newHashMap();
    this.input = new GroupByChar(new AllLines(inputFile));
    IntStream.range('a', 'z' + 1).forEach(it -> priorities.put((char) it, it - 'a' + 1));
    IntStream.range('A', 'Z' + 1).forEach(it -> priorities.put((char) it, it - 'A' + 1 + 26));
  }

  @Override
  public Integer part1Result() {
    return calculatePriorities(input.read()
        .stream()
        .map(Day3::split)
        .map(it -> new Pair<>(newHashSet(it.left()), newHashSet(it.right())))
        .map(it -> intersection(it.left(), it.right())));
  }

  private static Pair<List<Character>, List<Character>> split(List<Character> it) {
    int breakPoint = it.size() / 2;
    return new Pair<>(it.subList(0, breakPoint), it.subList(breakPoint, it.size()));
  }

  @Override
  public Integer part2Result() {
    return calculatePriorities(new GroupByLineCount<>(this.input, 3).read()
        .stream()
        .map(it -> it.stream()
            .map(Set::copyOf)
            .reduce(Sets::intersection)
            .orElse(Set.of())));
  }

  private Integer calculatePriorities(Stream<Set<Character>> commonItems) {
    return commonItems
        .map(it -> it.stream()
            .map(priorities::get)
            .reduce(Integer::sum)
            .orElse(0))
        .reduce(Integer::sum)
        .orElse(0);
  }
}
