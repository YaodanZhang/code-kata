package com.yaodanzhang.aoc.input;

import static java.nio.file.Path.of;
import static java.text.MessageFormat.format;

import java.net.URISyntaxException;
import java.nio.file.Path;

public final class Inputs {

  private Inputs() {}

  @SuppressWarnings("ConstantConditions")
  public static <T> Path inputOf(Class<T> puzzleClass) {
    try {
      return of(
          puzzleClass.getResource(format("{0}.input.txt", puzzleClass.getSimpleName().toLowerCase())).toURI());
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
