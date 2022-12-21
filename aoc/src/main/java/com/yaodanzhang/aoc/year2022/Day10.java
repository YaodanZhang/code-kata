package com.yaodanzhang.aoc.year2022;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Integer.parseInt;
import static java.util.List.of;

import com.google.common.base.Splitter;
import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.input.AllLines;
import java.nio.file.Path;
import java.util.List;

public class Day10 implements Puzzle<Integer> {

  private static final Splitter SPLITTER = Splitter.on(" ").trimResults().omitEmptyStrings();
  private final List<String> input;

  Day10(Path inputFile) {
    this.input = new AllLines(inputFile).read();
  }

  @Override
  public Integer part1Result() {
    List<Integer> register = getCycles();

    return signalStrength(register, 20)
        + signalStrength(register, 60)
        + signalStrength(register, 100)
        + signalStrength(register, 140)
        + signalStrength(register, 180)
        + signalStrength(register, 220);
  }

  private List<Integer> getCycles() {
    List<Integer> register = newArrayList();

    int x = 1;
    for (String line : input) {
      Command command = commandFactory(line, x);
      register.addAll(command.cycles());
      x = command.outcome();
    }
    return register;
  }

  private static int signalStrength(List<Integer> register, int cycle) {
    return register.get(cycle - 1) * cycle;
  }

  @Override
  public Integer part2Result() {
    List<Integer> register = getCycles();

    int width = 40;

    List<List<Character>> crt = newArrayList();
    List<Character> row = null;

    for (int i = 0; i < register.size(); i++) {
      if (i % width == 0) {
        row = newArrayList();
        crt.add(row);
      }
      if (Math.abs(register.get(i) - (i % width)) < 2) {
        row.add('#');
      } else {
        row.add('.');
      }
    }

    for (List<Character> characters : crt) {
      for (Character character : characters) {
        System.out.print(character);
      }
      System.out.println();
    }

    return 0;
  }

  private static Command commandFactory(String line, int x) {
    if (line.equals("noop")) {
      return new Noop(x);
    }

    List<String> commandInput = SPLITTER.splitToList(line);

    return new Addx(x, parseInt(commandInput.get(1)));
  }

  private interface Command {
    List<Integer> cycles();
    int outcome();
  }

  private record Noop(int x) implements Command {
    @Override
    public List<Integer> cycles() {
      return of(x);
    }

    @Override
    public int outcome() {
      return x;
    }
  }

  private record Addx(int x, int modification) implements Command {
    @Override
    public List<Integer> cycles() {
      return of(x, x);
    }

    @Override
    public int outcome() {
      return x + modification;
    }
  }
}
