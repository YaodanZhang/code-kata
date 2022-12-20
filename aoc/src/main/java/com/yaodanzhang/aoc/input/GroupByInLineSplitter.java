package com.yaodanzhang.aoc.input;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

import com.google.common.base.Splitter;
import java.util.List;

public class GroupByInLineSplitter implements Input<List<List<String>>> {

  private final Input<List<String>> lines;
  private final Splitter splitter;

  public GroupByInLineSplitter(Input<List<String>> lines, Splitter splitter) {
    this.lines = lines;
    this.splitter = splitter.omitEmptyStrings().trimResults();
  }

  @Override
  public List<List<String>> read() {
    return lines.read().stream()
        .map(it -> newArrayList(splitter.split(it)))
        .collect(toList());
  }
}
