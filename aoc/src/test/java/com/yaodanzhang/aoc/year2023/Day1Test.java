package com.yaodanzhang.aoc.year2023;

import static com.yaodanzhang.aoc.input.Inputs.inputOf;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day1Test {

  @Test
  void should_pass_part_1() {
    assertThat(puzzleOf("{0}.input.case0.txt").part1Result()).isEqualTo(142);
  }

  @Test
  void should_pass_part_2() {
    assertThat(puzzleOf("{0}.input.case1.txt").part2Result()).isEqualTo(281);
  }

  @Test
  void should_print_out_result() {
    Day1 realPuzzle = new Day1(inputOf(Day1.class));
    System.out.println(realPuzzle.part1Result());
    System.out.println(realPuzzle.part2Result());
  }


  private Day1 puzzleOf(String testInputFile) {
    return new Day1(inputOf(this.getClass(), testInputFile));
  }
}