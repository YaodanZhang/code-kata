package com.yaodanzhang.aoc.year2022;

import static com.yaodanzhang.aoc.input.Inputs.inputOf;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day8Test {

  private Day8 puzzle;

  @BeforeEach
  void setUp() {
    puzzle = new Day8(inputOf(this.getClass()));
  }

  @Test
  void should_pass_part_1() {
    assertThat(puzzle.part1Result()).isEqualTo(21);
  }

  @Test
  void should_pass_part_2() {
    assertThat(puzzle.part2Result()).isEqualTo(8);
  }

  @Test
  void should_print_out_result() {
    Day8 realPuzzle = new Day8(inputOf(Day8.class));
    System.out.println(realPuzzle.part1Result());
    System.out.println(realPuzzle.part2Result());
  }
}