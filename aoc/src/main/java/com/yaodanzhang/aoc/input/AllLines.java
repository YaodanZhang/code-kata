package com.yaodanzhang.aoc.input;

import static java.nio.file.Files.readAllLines;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class AllLines implements Input<List<String>> {

  private final Path inputFile;

  public AllLines(Path inputFile) {
    this.inputFile = inputFile;
  }

  @Override
  public List<String> read() {
    try {
      return readAllLines(inputFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
