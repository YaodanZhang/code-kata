package com.yaodanzhang.kata.roma;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Optional.ofNullable;

import com.google.common.base.Splitter;
import java.util.List;
import java.util.function.Function;

class Roma {

  private static final Splitter SPLITTER = Splitter.fixedLength(1).omitEmptyStrings();

  int convert(String number) {
    Set set = new Set();
    Set headSet = set;
    for (String oneNumber : SPLITTER.split(number)) {
      set = set.followWith(RomaItem.valueOf(oneNumber));
    }

    return headSet.calculate();
  }

  private enum RomaItem {
    M(1000),
    D(500),
    C(100),
    L(50),
    X(10),
    V(5),
    I(1);

    private final int value;

    RomaItem(int value) {
      this.value = value;
    }
  }

  private class Set {
    private Set previous;
    private Set next;
    private List<RomaItem> items = newArrayList();

    Set followWith(RomaItem item) {
      Set frame = this;
      if (isFinished()) {
        frame = startNextSet();
      }
      frame.items.add(item);
      return frame;
    }

    private Set startNextSet() {
      next = new Set();
      next.previous = this;
      return next;
    }

    private boolean isFinished() {
      return true;
    }

    int calculate() {
      return scoreOfItems() + nextOf(Set::calculate);
    }

    private int nextOf(Function<Set, Integer> operation) {
      return ofNullable(next).map(operation).orElse(0);
    }

    private int scoreOfItems() {
      return items.stream().map(it -> it.value).reduce(0, (a, b) -> a + b);
    }

  }
}
