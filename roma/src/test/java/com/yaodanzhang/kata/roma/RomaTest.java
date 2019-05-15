package com.yaodanzhang.kata.roma;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class RomaTest {

  private final String romaNumber;
  private final int expected;

  @Parameterized.Parameters(name = "expected of \"{0}\" is {1}")
  public static Iterable<Object[]> data() {
    return asList(new Object[][]{
        {"I", 1},
        {"II", 2},
        {"III", 3},
    });
  }

  public RomaTest(String romaNumber, int expected) {
    this.romaNumber = romaNumber;
    this.expected = expected;
  }

  @Test
  public void should_get_score() {
    assertThat(new Roma().convert(romaNumber), is(expected));
  }
}