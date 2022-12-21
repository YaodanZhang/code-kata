package com.yaodanzhang.aoc.input;

import static java.nio.file.Path.of;
import static java.text.MessageFormat.format;

import java.net.URISyntaxException;
import java.nio.file.Path;

public final class Inputs {

  private Inputs() {}

  public static <T> Path inputOf(Class<T> puzzleClass) {
    return inputOf(puzzleClass, "{0}.input.txt");
  }

  @SuppressWarnings("ConstantConditions")
  public static <T> Path inputOf(Class<T> puzzleClass, String filenamePattern) {
    try {
      return of(
          puzzleClass.getResource(format(filenamePattern, puzzleClass.getSimpleName().toLowerCase())).toURI());
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
