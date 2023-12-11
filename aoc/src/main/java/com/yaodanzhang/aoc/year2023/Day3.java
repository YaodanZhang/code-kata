package com.yaodanzhang.aoc.year2023;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.max;
import static java.lang.Math.min;

import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.algo.Coordinate;
import com.yaodanzhang.aoc.input.AllLines;
import com.yaodanzhang.aoc.input.GroupByChar;
import com.yaodanzhang.aoc.input.Pair;
import java.nio.file.Path;
import java.util.List;

public class Day3 implements Puzzle<Integer> {

  private final AllLines input;
  private final List<List<Character>> inputChars;

  Day3(Path inputFile) {
    this.input = new AllLines(inputFile);
    inputChars = new GroupByChar(this.input).read();
  }

  @Override
  public Integer part1Result() {
    List<Pair<Integer, Pair<Coordinate, Coordinate>>> numbers = createNumbers();
    return numbers.stream()
        .filter(it -> {
          boolean result = false;
          Coordinate start = it.right().left();
          Coordinate end = it.right().right();
          for (int i = start.y(); i <= end.y(); i++) {
            for (int j = start.x(); j <= end.x(); j++) {
              if (isSymbol(inputChars.get(i).get(j))) {
                result = true;
                break;
              }
            }
            if (result) {
              break;
            }
          }
          return result;
        })
        .map(Pair::left)
        .reduce(Integer::sum)
        .orElse(0);
  }

  private List<Pair<Integer, Pair<Coordinate, Coordinate>>> createNumbers() {
    List<String> inputLines = this.input.read();
    List<Pair<Integer, Pair<Coordinate, Coordinate>>> numbers = newArrayList();
    for (int i = 0; i < inputLines.size(); i++) {
      String line = inputLines.get(i);
      for (int j = 0; j < line.length();) {
        if (!isDigit(line.charAt(j))) {
          j++;
          continue;
        }
        int k = 1;
        for (; j + k < line.length(); k++) {
          if (!isDigit(line.charAt(j + k))) {
            break;
          }
        }

        Coordinate start = new Coordinate(max(j - 1, 0), max(i - 1, 0));
        Coordinate end = new Coordinate(min(j + k, line.length() - 1), min(i + 1, inputLines.size() - 1));

        numbers.add(new Pair<>(
            Integer.valueOf(line.substring(j, j + k)),
            new Pair<>(start, end)
        ));

        j += k;
      }
    }
    return numbers;
  }

  private static boolean isDigit(char character) {
    return character >= '0' && character <= '9';
  }

  private static boolean isSymbol(char character) {
    return !isDigit(character) && character != '.';
  }

  @Override
  public Integer part2Result() {
    int sum = 0;
    List<Pair<Integer, Pair<Coordinate, Coordinate>>> numbers = createNumbers();
    for (int i = 0; i < inputChars.size(); i++) {
      for (int j = 0; j < inputChars.get(i).size(); j++) {
        int x = j;
        int y = i;
        if (inputChars.get(i).get(j) == '*') {
          List<Pair<Integer, Pair<Coordinate, Coordinate>>> itemList = numbers.stream()
              .filter(it -> new Coordinate(x, y).inBetween(it.right().left(), it.right().right()))
              .toList();
          if (itemList.size() == 2) {
            sum += itemList.get(0).left() * itemList.get(1).left();
          }
        }
      }
    }
    return sum;
  }
}