package com.yaodanzhang.aoc.year2022;

import static com.yaodanzhang.aoc.input.Inputs.inputOf;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day11Test {

  private Day11 puzzle;

  @BeforeEach
  void setUp() {
    puzzle = new Day11(inputOf(this.getClass()));
  }

  @Test
  void should_pass_part_1() {
    assertThat(puzzle.part1Result()).isEqualTo(10605);
  }

  @Test
  void should_pass_part_2() {
    assertThat(puzzle.part2Result()).isEqualTo(2713310158L);
  }

  @Test
  void should_print_out_result() {
    Day11 realPuzzle = new Day11(inputOf(Day11.class));
    System.out.println(realPuzzle.part1Result());
    System.out.println(realPuzzle.part2Result());
  }
}