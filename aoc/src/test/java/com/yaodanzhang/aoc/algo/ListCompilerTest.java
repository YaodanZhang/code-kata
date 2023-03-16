package com.yaodanzhang.aoc.algo;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;

import com.yaodanzhang.aoc.algo.ListCompiler.MultiChildrenItem;
import com.yaodanzhang.aoc.algo.ListCompiler.SingleValueItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListCompilerTest {

  private ListCompiler<Integer> compiler;

  @BeforeEach
  void setUp() {
    compiler = new ListCompiler<>(Integer::parseInt);
  }

  @Test
  void shouldCompileList() {
    assertThat(compiler.compile("[]")).isEqualTo(new MultiChildrenItem());
    assertThat(compiler.compile("[1]")).isEqualTo(
        new MultiChildrenItem(of(
            new SingleValueItem<>(1)
        )));
    assertThat(compiler.compile("[1, 2]")).isEqualTo(
        new MultiChildrenItem(of(
            new SingleValueItem<>(1),
            new SingleValueItem<>(2)
        )));
    assertThat(compiler.compile("[1, []]")).isEqualTo(
        new MultiChildrenItem(of(
            new SingleValueItem<>(1),
            new MultiChildrenItem(of())
        )));
    assertThat(compiler.compile("[1, [2]]")).isEqualTo(
        new MultiChildrenItem(of(
            new SingleValueItem<>(1),
            new MultiChildrenItem(of(
                new SingleValueItem<>(2)
            ))
        )));
    assertThat(compiler.compile("[1, [2, 3]]")).isEqualTo(
        new MultiChildrenItem(of(
            new SingleValueItem<>(1),
            new MultiChildrenItem(of(
                new SingleValueItem<>(2),
                new SingleValueItem<>(3)
            ))
        )));
    assertThat(compiler.compile("[[[0,10]],[7,9,[[2,1]]]]")).isEqualTo(
        new MultiChildrenItem(of(
            new MultiChildrenItem(of(
                new MultiChildrenItem(of(
                    new SingleValueItem<>(0),
                    new SingleValueItem<>(10)
                ))
            )),
            new MultiChildrenItem(of(
                new SingleValueItem<>(7),
                new SingleValueItem<>(9),
                new MultiChildrenItem(of(
                    new MultiChildrenItem(of(
                        new SingleValueItem<>(2),
                        new SingleValueItem<>(1)
                    ))
                ))

            ))
        )));
  }
}