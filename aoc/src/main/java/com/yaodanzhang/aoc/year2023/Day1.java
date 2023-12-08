package com.yaodanzhang.aoc.year2023;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.stream.Collectors.toList;

import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.input.AllLines;
import com.yaodanzhang.aoc.input.GroupByChar;
import com.yaodanzhang.aoc.input.Pair;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.IntStream;

public class Day1 implements Puzzle<Integer> {

  private final GroupByChar input;
  private final AllLines allLines;

  Day1(Path inputFile) {
    allLines = new AllLines(inputFile);
    this.input = new GroupByChar(allLines);
  }

  @Override
  public Integer part1Result() {
    return this.input.read().stream()
        .map(line -> line.stream()
            .filter(it -> '0' <= it && it <= '9')
            .collect(toList()))
        .map(line -> (line.get(0) - '0') * 10 + (line.get(line.size() - 1) - '0'))
        .reduce(Integer::sum)
        .orElse(0);
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Override
  public Integer part2Result() {
    Map<String, Character> mapping = newHashMap();
    mapping.put("one", '1');
    mapping.put("two", '2');
    mapping.put("three", '3');
    mapping.put("four", '4');
    mapping.put("five", '5');
    mapping.put("six", '6');
    mapping.put("seven", '7');
    mapping.put("eight", '8');
    mapping.put("nine", '9');
    IntStream.range(1, 10).forEach(it -> mapping.put(String.valueOf(it), (char)('0' + it)));
    return allLines.read().stream()
        .map(line -> {
          List<Character> chars = newArrayList();
          Optional<Pair<Entry<String, Character>, Integer>> first = mapping.entrySet().stream()
              .map(it -> new Pair<>(it, line.indexOf(it.getKey())))
              .filter(it -> it.right() != -1)
              .min(Comparator.comparingInt(Pair::right));
          chars.add(first.get().left().getValue());
          Optional<Pair<Entry<String, Character>, Integer>> last = mapping.entrySet().stream()
              .map(it -> new Pair<>(it, line.lastIndexOf(it.getKey())))
              .filter(it -> it.right() != -1)
              .max(Comparator.comparingInt(Pair::right));
          chars.add(last.get().left().getValue());
          return chars;
        })
        .map(line -> (line.get(0) - '0') * 10 + (line.get(line.size() - 1) - '0'))
        .reduce(Integer::sum)
        .orElse(0);
  }
}
