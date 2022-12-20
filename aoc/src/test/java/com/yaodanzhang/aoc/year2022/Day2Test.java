package com.yaodanzhang.aoc.year2022;

import static com.yaodanzhang.aoc.input.Inputs.inputOf;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day2Test {

  private Day2 puzzle;

  @BeforeEach
  void setUp() {
    puzzle = new Day2(inputOf(this.getClass()));
  }

  @Test
  void should_pass_part_1() {
    assertThat(puzzle.part1Result()).isEqualTo(15);
  }

  @Test
  void should_pass_part_2() {
    assertThat(puzzle.part2Result()).isEqualTo(12);
  }

  @Test
  void should_print_out_result() {
    Day2 realPuzzle = new Day2(inputOf(Day2.class));
    System.out.println(realPuzzle.part1Result());
    System.out.println(realPuzzle.part2Result());
  }

}