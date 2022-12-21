package com.yaodanzhang.aoc.year2022;

import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.input.AllLines;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Day6 implements Puzzle<Integer> {

  private final String input;

  Day6(Path inputFile) {
    this.input = new AllLines(inputFile).read().get(0);
  }

  @Override
  public Integer part1Result() {
    return startingIndexOfUnique(4);
  }

  private int startingIndexOfUnique(int uniqueSizeRequired) {
    Queue<Character> queue = new LinkedList<>();
    for (int i = 0; i < input.length(); i++) {
      if (queue.size() == uniqueSizeRequired) {
        queue.poll();
      }
      queue.add(input.charAt(i));
      if (Set.copyOf(queue).size() == uniqueSizeRequired) {
        return i + 1;
      }
    }
    return 0;
  }

  @Override
  public Integer part2Result() {
    return startingIndexOfUnique(14);
  }
}
