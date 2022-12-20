package com.yaodanzhang.aoc.input;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public class GroupByEmptyLine implements Input<List<List<String>>> {

  private final Input<List<String>> lines;

  public GroupByEmptyLine(Input<List<String>> lines) {
    this.lines = lines;
  }

  @Override
  public List<List<String>> read() {
    List<String> input = lines.read();
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
