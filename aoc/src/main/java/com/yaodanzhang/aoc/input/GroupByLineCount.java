package com.yaodanzhang.aoc.input;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public class GroupByLineCount<T> implements Input<List<List<T>>> {

  private final Input<List<T>> lines;
  private final int count;

  public GroupByLineCount(Input<List<T>> lines, int count) {
    this.lines = lines;
    this.count = count;
  }

  @Override
  public List<List<T>> read() {
    List<List<T>> result = newArrayList();

    List<T> input = lines.read();


    List<T> group = newArrayList();
    for (int i = 0; i < input.size(); i++) {
      group.add(input.get(i));
      if (i % count == (count - 1)) {
        result.add(group);
        group = newArrayList();
      }
    }

    if (!group.isEmpty()) {
      result.add(group);
    }

    return result;
  }
}
