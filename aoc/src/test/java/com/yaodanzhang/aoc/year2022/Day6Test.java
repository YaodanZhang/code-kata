package com.yaodanzhang.aoc.year2022;

import static com.yaodanzhang.aoc.input.Inputs.inputOf;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day6Test {

  @Test
  void should_pass_part_1() {
    assertThat(puzzleOf("{0}.input.case0.txt").part1Result()).isEqualTo(7);
    assertThat(puzzleOf("{0}.input.case1.txt").part1Result()).isEqualTo(5);
    assertThat(puzzleOf("{0}.input.case2.txt").part1Result()).isEqualTo(6);
    assertThat(puzzleOf("{0}.input.case3.txt").part1Result()).isEqualTo(10);
    assertThat(puzzleOf("{0}.input.case4.txt").part1Result()).isEqualTo(11);
  }

  private Day6 puzzleOf(String testInputFile) {
    return new Day6(inputOf(this.getClass(), testInputFile));
  }

  @Test
  void should_pass_part_2() {
    assertThat(puzzleOf("{0}.input.case5.txt").part2Result()).isEqualTo(19);
    assertThat(puzzleOf("{0}.input.case6.txt").part2Result()).isEqualTo(23);
    assertThat(puzzleOf("{0}.input.case7.txt").part2Result()).isEqualTo(23);
    assertThat(puzzleOf("{0}.input.case8.txt").part2Result()).isEqualTo(29);
    assertThat(puzzleOf("{0}.input.case9.txt").part2Result()).isEqualTo(26);
  }

  @Test
  void should_print_out_result() {
    Day6 realPuzzle = new Day6(inputOf(Day6.class));
    System.out.println(realPuzzle.part1Result());
    System.out.println(realPuzzle.part2Result());
  }

}