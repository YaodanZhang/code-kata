package com.yaodanzhang.aoc.year2022;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.Comparator.reverseOrder;

import com.google.common.base.Splitter;
import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.input.AllLines;
import com.yaodanzhang.aoc.input.GroupByEmptyLine;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class Day11 implements Puzzle<Long> {

  private static final Splitter KEY_VALUE_SPLITTER = Splitter.on(":").omitEmptyStrings()
      .trimResults();
  private static final Splitter ITEM_SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();
  private static final Splitter SPACE_SPLITTER = Splitter.on(" ").omitEmptyStrings().trimResults();
  private static final Splitter OPERATION_SPLITTER = Splitter.on("=").trimResults()
      .omitEmptyStrings();
  private final List<List<String>> input;

  Day11(Path inputFile) {
    this.input = new GroupByEmptyLine(new AllLines(inputFile)).read();
  }

  @Override
  public Long part1Result() {
    List<Monkey> monkeys = input.stream()
        .map(line -> new Monkey(
            readItems(line.get(1)),
            readOperation(line.get(2)),
            Throw.to(
                readDivisible(line.get(3)),
                readThrowToIndex(line.get(4)),
                readThrowToIndex(line.get(5))
            ),
            it -> it / 3
        ))
        .toList();

    return calculateMonkeyBusiness(monkeys, 20);
  }

  @Override
  public Long part2Result() {
    List<Monkey> monkeys = input.stream()
        .map(line -> new Monkey(
            readItems(line.get(1)),
            readOperation(line.get(2)),
            Throw.to(
                readDivisible(line.get(3)),
                readThrowToIndex(line.get(4)),
                readThrowToIndex(line.get(5))
            ),
            it -> it
        ))
        .toList();

    return calculateMonkeyBusiness(monkeys, 10000);
  }

  private static Long calculateMonkeyBusiness(List<Monkey> monkeys, int round) {
    for (int i = 0; i < round; i++) {
      for (Monkey monkey : monkeys) {
        monkey.testAndThrow(monkeys);
      }
    }

    return monkeys.stream()
        .map(Monkey::getInspectedItems)
        .sorted(reverseOrder())
        .limit(2)
        .reduce((a, b) -> a * b)
        .orElse(0L);
  }

  private int readThrowToIndex(String input) {
    List<String> throwToInput = SPACE_SPLITTER.splitToList(input);
    return parseInt(throwToInput.get(throwToInput.size() - 1));
  }

  private static Queue<Long> readItems(String input) {
    String itemsInput = KEY_VALUE_SPLITTER.splitToList(input).get(1);
    Queue<Long> items = new LinkedList<>();
    ITEM_SPLITTER.splitToList(itemsInput)
        .stream()
        .map(Long::parseLong)
        .forEach(items::add);
    return items;
  }

  private static Expression readOperation(String input) {
    String operationsInput = OPERATION_SPLITTER.splitToList(input).get(1);
    List<String> operations = SPACE_SPLITTER.splitToList(operationsInput);

    return new Expression(
        Element.of(operations.get(0)),
        Operation.of(operations.get(1)),
        Element.of(operations.get(2))
    );
  }

  private static Divisible readDivisible(String input) {
    List<String> divisibleTestInput = SPACE_SPLITTER.splitToList(input);
    return Divisible.by(parseLong(divisibleTestInput.get(divisibleTestInput.size() - 1)));
  }

  private record Expression(Element left, Operation operation, Element right)
      implements Function<Long, Long> {

    @Override
    public Long apply(Long input) {
      return operation.apply(left.apply(input), right.apply(input));
    }
  }

  private record Element(Function<Long, Long> operator) implements Function<Long, Long> {

    static Element of(String element) {
      if (element.equalsIgnoreCase("old")) {
        return new Element(it -> it);
      }
      return new Element(it -> parseLong(element));
    }

    @Override
    public Long apply(Long input) {
      return operator.apply(input);
    }
  }

  public record Operation(BiFunction<Long, Long, Long> operation) implements BiFunction<Long, Long, Long> {

    public static Operation of(String symbol) {
      if (symbol.equals("+")) {
        return new Operation(Long::sum);
      }

      if (symbol.equals("*")) {
        return new Operation((a, b) -> a * b);
      }

      throw new IllegalArgumentException("Symbol: " + symbol);
    }

    @Override
    public Long apply(Long left, Long right) {
      return operation.apply(left, right);
    }
  }

  public record Throw(Predicate<Long> test, int indexIf, int indexIfNot) implements BiConsumer<List<Monkey>, Long> {
    @Override
    public void accept(List<Monkey> monkeys, Long item) {
      if (test.test(item)) {
        monkeys.get(indexIf).accept(item);
      } else {
        monkeys.get(indexIfNot).accept(item);
      }
    }

    public static Throw to(Predicate<Long> test, int indexIf, int indexIfNot) {
      return new Throw(test, indexIf, indexIfNot);
    }
  }

  public record Divisible(long against) implements Predicate<Long> {
    @Override
    public boolean test(Long input) {
      return input % against == 0;
    }

    public static Divisible by(long against) {
      return new Divisible(against);
    }
  }

  private static class Monkey {

    private final Queue<Long> items;
    private final Expression operation;
    private final Throw throwTo;
    private final Function<Long, Long> getBored;
    private final AtomicLong inspectionCounter;

    public Monkey(Queue<Long> items, Expression operation, Throw throwTo,
        Function<Long, Long> getBored) {
      this.items = items;
      this.operation = operation;
      this.throwTo = throwTo;
      this.getBored = getBored;
      this.inspectionCounter = new AtomicLong(0);
    }

    private boolean hasItem() {
      return !items.isEmpty();
    }

    public void testAndThrow(List<Monkey> monkeys) {
      while (hasItem()) {
        throwTo.accept(monkeys, inspect(items.poll()));
      }
    }

    private long inspect(Long input) {
      long outcome = operation.andThen(getBored).apply(input);
      inspectionCounter.incrementAndGet();
      return outcome;
    }

    public long getInspectedItems() {
      return inspectionCounter.get();
    }

    public void accept(Long item) {
      items.add(item);
    }
  }
}
