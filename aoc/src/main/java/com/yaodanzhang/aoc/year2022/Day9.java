package com.yaodanzhang.aoc.year2022;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static java.util.stream.Collectors.toList;

import com.google.common.base.Splitter;
import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.input.AllLines;
import com.yaodanzhang.aoc.input.GroupByInLineSplitter;
import com.yaodanzhang.aoc.input.Pair;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import lombok.Getter;

public class Day9 implements Puzzle<Integer> {

  private final List<List<String>> input;

  Day9(Path inputFile) {
    this.input = new GroupByInLineSplitter(new AllLines(inputFile), Splitter.on(" ")).read();
  }

  @Override
  public Integer part1Result() {
    return traceTail(Map.create(2));
  }

  private int traceTail(Map initialMap) {
    return input.stream()
        .reduce(
            initialMap,
            (map, movement) -> map.travel(Move.valueOf(movement.get(0)), parseInt(movement.get(1))),
            (identity, map) -> map)
        .getTailTrack()
        .size();
  }

  @Override
  public Integer part2Result() {
    return traceTail(Map.create(10));
  }

  private static class Map {
    private final List<Coordinate> knots;

    @Getter
    private final Set<Coordinate> tailTrack;

    private Map(List<Coordinate> knots) {
      this.knots = knots;
      this.tailTrack = newHashSet();
    }

    public static Map create(int numberOfKnots) {
      return new Map(IntStream.range(0, numberOfKnots).mapToObj(it -> Coordinate.start()).collect(toList()));
    }

    Map travel(Move move, int steps) {
      for (int i = 0; i < steps; i++) {
        travel(move);
      }
      return this;
    }

    private void travel(Move move) {
      knots.set(0, move.from(knots.get(0)));
      for (int i = 1; i < knots.size(); i++) {
        knots.set(i, knots.get(i).catchUp(knots.get(i - 1)));
      }

      tailTrack.add(knots.get(knots.size() - 1));
    }
  }

  private record Coordinate(int x, int y) {
    static Coordinate start() {
      return new Coordinate(0, 0);
    }

    private boolean isTooFar(Coordinate that) {
      return abs(this.x - that.x) > 1 || abs(this.y - that.y) > 1;
    }

    Coordinate catchUp(Coordinate that) {
      if (!this.isTooFar(that)) {
        return this;
      }

      if (this.x == that.x) {
        return new Coordinate(this.x, newPositionOf(that, Coordinate::y));
      }

      if (this.y == that.y) {
        return new Coordinate(newPositionOf(that, Coordinate::x), this.y);
      }

      return new Coordinate(newPositionOf(that, Coordinate::x), newPositionOf(that, Coordinate::y));
    }

    private int newPositionOf(Coordinate that, ToIntFunction<Coordinate> coordinateSupplier) {
      int difference = coordinateSupplier.applyAsInt(that) - coordinateSupplier.applyAsInt(this);
      return coordinateSupplier.applyAsInt(this) + (difference / abs(difference));
    }

  }

  private enum Move {
    R(new Pair<>(x -> x + 1, y -> y)),
    L(new Pair<>(x -> x - 1, y -> y)),
    U(new Pair<>(x -> x, y -> y + 1)),
    D(new Pair<>(x -> x, y -> y - 1));

    private final Pair<ToIntFunction<Integer>, ToIntFunction<Integer>> movement;

    Move(Pair<ToIntFunction<Integer>, ToIntFunction<Integer>> movement) {
      this.movement = movement;
    }

    Coordinate from(Coordinate that) {
      return new Coordinate(
          movement.left().applyAsInt(that.x),
          movement.right().applyAsInt(that.y)
      );
    }
  }
}
