package com.yaodanzhang.kata.roma;

import static com.google.common.collect.Lists.newArrayList;

import com.google.common.base.Splitter;
import java.util.List;

class Roma {

  private static final Splitter SPLITTER = Splitter.fixedLength(1).omitEmptyStrings();

  int convert(String number) {
    List<String> numbers = newArrayList(SPLITTER.split(number));

    return numbers.stream().map(it -> {
      if ("I".equals(it)) {
        return 1;
      } else {
        return 0;
      }
    }).reduce(0, (a, b) -> a + b);
  }
}
