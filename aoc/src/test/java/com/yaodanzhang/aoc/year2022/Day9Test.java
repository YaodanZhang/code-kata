package com.yaodanzhang.aoc.year2022;

import static com.yaodanzhang.aoc.input.Inputs.inputOf;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day9Test {

  @Test
  void should_pass_part_1() {
    assertThat(puzzleOf("{0}.input.case1.txt").part1Result()).isEqualTo(13);
  }

  @Test
  void should_pass_part_2() {
    assertThat(puzzleOf("{0}.input.case2.txt").part1Result()).isEqualTo(88);
  }

  @Test
  void should_print_out_result() {
    Day9 realPuzzle = new Day9(inputOf(Day9.class));
    System.out.println(realPuzzle.part1Result());
    System.out.println(realPuzzle.part2Result());
  }

  private Day9 puzzleOf(String testInputFile) {
    return new Day9(inputOf(this.getClass(), testInputFile));
  }

}