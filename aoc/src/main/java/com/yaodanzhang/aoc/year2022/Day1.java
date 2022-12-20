package com.yaodanzhang.aoc.year2022;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.reverseOrder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day1 {

  @SuppressWarnings({"ConstantConditions"})
  public static void main(String[] args) throws URISyntaxException, IOException {
    Path inputFile = Path.of(Day1.class.getResource("day1.input.txt").toURI());
    List<String> input = Files.readAllLines(inputFile);

    List<List<String>> groupedInput = getGroupedInput(input);

    List<Integer> result = groupedInput.stream()
        .map(it -> it.stream()
            .map(Integer::parseInt)
            .reduce(Integer::sum)
            .orElse(0))
        .sorted(comparingInt(a -> a))
        .sorted(reverseOrder())
        .toList();
    System.out.println(result.get(0) + result.get(1) + result.get(2));
  }

  private static List<List<String>> getGroupedInput(List<String> input) {
    List<List<String>> groupedInput = newArrayList();

    int i = 0;
    while (i < input.size()) {
      List<String> group = newArrayList();
      int j = i;
      for (; j < input.size() && !input.get(j).isBlank(); j++) {
        group.add(input.get(j));
      }
      if (!group.isEmpty()) {
        groupedInput.add(group);
      }
      i = j + 1;
    }
    return groupedInput;
  }

}
