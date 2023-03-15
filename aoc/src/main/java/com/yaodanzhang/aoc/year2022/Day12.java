package com.yaodanzhang.aoc.year2022;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

import com.google.common.base.MoreObjects;
import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.input.AllLines;
import com.yaodanzhang.aoc.input.GroupByChar;
import com.yaodanzhang.aoc.input.Pair;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import lombok.Setter;

public class Day12 implements Puzzle<Integer> {

  private final List<List<Character>> input;

  private final Map<Coordinate, GraphNode> coordinateGraphNodeMap;

  Day12(Path inputFile) {
    this.input = new GroupByChar(new AllLines(inputFile)).read();
    coordinateGraphNodeMap = new HashMap<>();
  }

  @Override
  public Integer part1Result() {
    List<List<GraphNode>> nodes = createGraph();

    GraphNode start = startOf(nodes, it -> coordinateGraphNodeMap.get(it).height == 'S').get(0);
    start.setDistance(0);

    return minDistanceFrom(start);
  }

  private int minDistanceFrom(GraphNode start) {
    List<GraphNode> s = this.initializeArray(input, null);

    PriorityQueue<GraphNode> distanceMinDeap = new PriorityQueue<>(comparingInt(it -> it.distance));
    Set<GraphNode> distanceMinDeapSet = newHashSet();

    distanceMinDeap.add(start);
    distanceMinDeapSet.add(start);

    GraphNode current = null;
    while (!isEnd(current)) {
      current = distanceMinDeap.poll();
      if (current == null) {
        return MAX_VALUE;
      }
      distanceMinDeapSet.remove(current);

      s.set(current.coordinate.arrayIndex(input), current);

      int newDistance = current.distance + 1;
      current.children.forEach(it -> {
        if (newDistance < it.distance) {
          if (distanceMinDeapSet.contains(it)) {
            distanceMinDeap.remove(it);
            distanceMinDeapSet.remove(it);
          }
          it.distance = newDistance;
          distanceMinDeap.add(it);
          distanceMinDeapSet.add(it);
        }
      });
    }

    return current.distance;
  }

  private List<List<GraphNode>> createGraph() {
    List<List<GraphNode>> nodes = newArrayList();
    for (int y = 0; y < input.size(); y++) {
      ArrayList<GraphNode> line = newArrayList();
      nodes.add(line);
      for (int x = 0; x < input.get(y).size(); x++) {
        char height = input.get(y).get(x);
        GraphNode node = new GraphNode(new Coordinate(x, y), height);
        line.add(node);
        coordinateGraphNodeMap.put(node.coordinate, node);
      }
    }

    for (int y = 0; y < input.size(); y++) {
      for (int x = 0; x < input.get(y).size(); x++) {
        GraphNode node = nodes.get(y).get(x);
        Arrays.stream(Move.values()).filter(it -> {
          Coordinate next = it.from(node.coordinate);
          return next.x > -1
              && next.y > -1
              && next.y < input.size()
              && next.x < input.get(next.y).size()
              && getHeight(next) - getHeight(node.coordinate) <= 1;
        }).forEach(it -> node.addChild(coordinateGraphNodeMap.get(it.from(node.coordinate))));
      }
    }
    return nodes;
  }

  private static boolean isEnd(GraphNode current) {
    return current != null && current.height == 'E';
  }

  List<GraphNode> startOf(List<List<GraphNode>> nodes, Predicate<Coordinate> isStart) {
    return nodes.stream()
        .flatMap(Collection::stream)
        .filter(it -> isStart.test(it.coordinate))
        .collect(toList());
  }

  <E> List<E> initializeArray(List<List<Character>> input,
      @SuppressWarnings("SameParameterValue") E initValue) {
    return input.stream()
        .flatMap(Collection::stream)
        .map(it -> initValue)
        .collect(toList());
  }

  private char getHeight(Coordinate point) {
    char height = input.get(point.y).get(point.x);
    if (height == 'S') {
      return 'a';
    }

    if (height == 'E') {
      return 'z';
    }
    return height;
  }

  @Override
  public Integer part2Result() {
    List<List<GraphNode>> nodes = createGraph();
    PriorityQueue<Integer> distances = new PriorityQueue<>();

    List<GraphNode> startingPoints = startOf(nodes, it -> this.getHeight(it) == 'a');

    for (GraphNode start : startingPoints) {
      resetDistance(nodes);
      start.distance = 0;
      distances.add(minDistanceFrom(start));
    }

    return distances.peek();

  }

  private void resetDistance(List<List<GraphNode>> nodes) {
    nodes.stream()
        .flatMap(Collection::stream)
        .forEach(node -> node.distance = MAX_VALUE);
  }

  private record Coordinate(int x, int y) {

    private int arrayIndex(List<List<Character>> input) {
      return y * input.get(0).size() + x;
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

  private static class GraphNode {

    @Setter
    private int distance;
    private final List<GraphNode> children;
    private final Coordinate coordinate;
    private final char height;

    GraphNode(Coordinate coordinate, char height) {
      this.coordinate = coordinate;
      this.height = height;
      this.children = newArrayList();
      distance = MAX_VALUE;
    }

    void addChild(GraphNode child) {
      this.children.add(child);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      GraphNode graphNode = (GraphNode) o;
      return coordinate.equals(graphNode.coordinate);
    }

    @Override
    public int hashCode() {
      return Objects.hash(coordinate);
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
          .add("distance", distance)
          .add("coordinate", coordinate)
          .add("height", height)
          .toString();
    }
  }
}
