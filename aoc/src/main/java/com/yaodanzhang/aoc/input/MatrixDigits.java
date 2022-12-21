package com.yaodanzhang.aoc.input;

import java.util.List;

public class MatrixDigits implements Input<List<List<Integer>>> {

  private final Input<List<String>> lines;

  public MatrixDigits(Input<List<String>> lines) {
    this.lines = lines;
  }

  @Override
  public List<List<Integer>> read() {
    return lines.read().stream()
        .map(it -> it.chars()
            .mapToObj(letter -> (char) letter)
            .map(Object::toString)
            .map(Integer::parseInt)
            .toList())
        .toList();
  }
}
