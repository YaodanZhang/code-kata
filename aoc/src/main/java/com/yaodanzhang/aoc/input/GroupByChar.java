package com.yaodanzhang.aoc.input;

import static java.util.stream.Collectors.toList;

import java.util.List;

public class GroupByChar implements Input<List<List<Character>>> {

  private final Input<List<String>> lines;

  public GroupByChar(Input<List<String>> lines) {
    this.lines = lines;
  }

  @Override
  public List<List<Character>> read() {
    return lines.read().stream()
        .map(it -> it.chars()
            .mapToObj(letter -> (char) letter)
            .collect(toList()))
        .collect(toList());
  }
}
