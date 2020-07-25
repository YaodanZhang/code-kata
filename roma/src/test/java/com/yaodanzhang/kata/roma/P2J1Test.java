package com.yaodanzhang.kata.roma;

import static com.yaodanzhang.kata.roma.P2J1.fallingPower;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Random;
import java.util.zip.CRC32;
import org.junit.Test;

public class P2J1Test {

  private static final int SEED = 12345;
  private static final int TRIALS = 10000;

  @Test
  public void testFallingPower() {
    Random rng = new Random(SEED);
    CRC32 check = new CRC32();
    for (int b = -10; b < 10; b++) {
      for (int e = 0; e < 10; e++) {
        long p = fallingPower(b, e);
        check.update((int) (p & 0xFFFF));
        check.update((int) ((p >> 31) & 0xFFFF));
      }
    }
    assertEquals(4140005098L, check.getValue());
  }

  @Test
  public void testFallingPower_logan() {
    Random rng = new Random(SEED);
    CRC32 check = new CRC32();
    for (int b = -10; b < 10; b++) {
      for (int e = 0; e < 10; e++) {
        long p = P2J2.fallingPower(b, e);
        check.update((int) (p & 0xFFFF));
        check.update((int) ((p >> 31) & 0xFFFF));
      }
    }
    assertEquals(4140005098L, check.getValue());
  }

  @Test
  public void should_be_0_give_1() {
    assertThat(fallingPower(8, 0), is(1L));
    assertThat(fallingPower(8, 1), is(8L));
    assertThat(fallingPower(8, 2), is(56L));
  }

  @Test
  public void should_be_0_give_1_logan() {
    assertThat(P2J2.fallingPower(8, 0), is(1L));
    assertThat(P2J2.fallingPower(8, 1), is(8L));
    assertThat(P2J2.fallingPower(8, 2), is(56L));
    assertThat(P2J2.fallingPower(8, 3), is(8 * 7 * 6L));
  }
}
