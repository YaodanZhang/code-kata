package com.yaodanzhang.aoc.year2022;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.newHashMap;
import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;

import com.google.common.base.Splitter;
import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.input.AllLines;
import com.yaodanzhang.aoc.input.GroupByEmptyLine;
import java.nio.file.Path;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class Day5 implements Puzzle<String> {

  private final GroupByEmptyLine input;

  Day5(Path inputFile) {
    this.input = new GroupByEmptyLine(new AllLines(inputFile));
  }

  @Override
  public String part1Result() {
    return calculateResult(Stacks::on);
  }

  @Override
  public String part2Result() {
    return calculateResult(Stacks::onChunky);
  }

  private String calculateResult(BiFunction<Stacks, String, Stacks> onCommand) {
    List<List<String>> inputLines = input.read();
    Stacks stacks = new Stacks(inputLines.get(0));

    Stacks result = inputLines.get(1)
        .stream()
        .reduce(stacks, onCommand, (identity, newStacks) -> newStacks);

    return result.topOfAll()
        .stream()
        .reduce("", (identity, it) -> identity + it);
  }

  private static class Stacks {

    public static final Splitter INDEX_SPLITTER = Splitter.on(" ").omitEmptyStrings().trimResults();
    private final Map<String, Deque<String>> stacks;
    private final List<String> indexes;

    public Stacks(List<String> input) {
      stacks = newHashMap();
      String indexLine = input.get(input.size() - 1);
      indexes = INDEX_SPLITTER.splitToList(indexLine);

      for (String index : indexes) {
        Deque<String> stack = newLinkedList();
        int position = indexLine.indexOf(index);
        input.stream()
            .limit(input.size() - 1L)
            .filter(it -> it.length() > position)
            .map(it -> valueOf(it.charAt(position)))
            .filter(it -> !it.isBlank())
            .forEach(stack::addLast);
        stacks.put(index, stack);
      }
    }

    public Stacks on(String command) {
      List<String> commands = INDEX_SPLITTER.splitToList(command);
      int itemsCount = Integer.parseInt(commands.get(1));
      String from = commands.get(3);
      String to = commands.get(5);
      for (int i = 0; i < itemsCount; i++) {
        String item = stacks.get(from).pop();
        stacks.get(to).push(item);
      }
      return this;
    }

    public Stacks onChunky(String command) {
      List<String> commands = INDEX_SPLITTER.splitToList(command);
      int itemsCount = Integer.parseInt(commands.get(1));
      String from = commands.get(3);
      String to = commands.get(5);
      Deque<String> tempStack = newLinkedList();
      for (int i = 0; i < itemsCount; i++) {
        tempStack.push(stacks.get(from).pop());
      }
      while (!tempStack.isEmpty()) {
        stacks.get(to).push(tempStack.pop());
      }
      return this;
    }

    public List<String> topOfAll() {
      return indexes.stream()
          .map(stacks::get)
          .map(it -> ofNullable(it.peek()).orElse(" "))
          .toList();
    }
  }
}
