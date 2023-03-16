package com.yaodanzhang.aoc.year2022;

import static java.util.List.of;
import static java.util.stream.Collectors.toList;

import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.algo.ListCompiler;
import com.yaodanzhang.aoc.algo.ListCompiler.Item;
import com.yaodanzhang.aoc.algo.ListCompiler.MultiChildrenItem;
import com.yaodanzhang.aoc.algo.ListCompiler.SingleValueItem;
import com.yaodanzhang.aoc.input.AllLines;
import com.yaodanzhang.aoc.input.GroupByEmptyLine;
import com.yaodanzhang.aoc.input.Pair;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Day13 implements Puzzle<Integer> {

  private final GroupByEmptyLine input;
  private final ListCompiler<Integer> compiler;

  Day13(Path inputFile) {
    this.input = new GroupByEmptyLine(new AllLines(inputFile));
    compiler = new ListCompiler<>(Integer::parseInt);
  }

  @Override
  public Integer part1Result() {
    List<Pair<Item, Item>> pairs = input.read().stream()
        .map(it -> new Pair<>(compiler.compile(it.get(0)), compiler.compile(it.get(1))))
        .toList();
    int sum = 0;
    for (int i = 1; i <= pairs.size(); i++) {
      if (isRightOrdered(pairs.get(i - 1).left(), pairs.get(i - 1).right()).inRightOrder) {
        sum += i;
      }
    }
    return sum;
  }

  @SuppressWarnings("unchecked")
  private Result isRightOrdered(Item left, Item right) {
    if (left instanceof SingleValueItem<?>
        && right instanceof SingleValueItem<?>) {
      return Result.compare(((SingleValueItem<Integer>) left).getValue(),
          ((SingleValueItem<Integer>) right).getValue());
    }

    if (left instanceof SingleValueItem<?>) {
      left = new MultiChildrenItem(of(left));
    }
    if (right instanceof SingleValueItem<?>) {
      right = new MultiChildrenItem(of(right));
    }

    return isRightOrdered((MultiChildrenItem) left, (MultiChildrenItem) right);
  }

  private Result isRightOrdered(MultiChildrenItem left, MultiChildrenItem right) {
    List<Item> leftChildren = left.getChildren();
    List<Item> rightChildren = right.getChildren();

    for (int i = 0; i < leftChildren.size() && i < rightChildren.size(); i++) {
      Result result = isRightOrdered(leftChildren.get(i), rightChildren.get(i));
      if (!result.shouldContinue) {
        return result;
      }
    }
    return Result.compare(leftChildren.size(), rightChildren.size());
  }

  @Override
  public Integer part2Result() {
    List<String> additionalPackets = of("[[2]]", "[[6]]");
    List<String> packets = input.read().stream()
        .flatMap(Collection::stream)
        .collect(toList());
    packets.addAll(additionalPackets);

    List<String> sortedResult = packets.stream()
        .map(compiler::compile)
        .sorted((o1, o2) -> isRightOrdered(o1, o2).ordinal() - 1)
        .map(Objects::toString)
        .toList();
    return additionalPackets.stream()
        .map(it -> sortedResult.indexOf(it) + 1)
        .reduce((a , b) -> a * b)
        .orElse(0);
  }

  private enum Result {
    RIGHT(false, true), NEXT(true, true), WRONG(false, false);

    private final boolean shouldContinue;
    private final boolean inRightOrder;

    Result(boolean shouldContinue, boolean inRightOrder) {
      this.shouldContinue = shouldContinue;
      this.inRightOrder = inRightOrder;
    }

    static Result compare(int left, int right) {
      if (left == right) {
        return NEXT;
      }

      if (left < right) {
        return RIGHT;
      }

      return WRONG;
    }
  }

}
